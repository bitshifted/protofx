# Application Configuration with `ProtoFXGuiceModule`

The `ProtoFXGuiceModule` class is the central point for configuring your ProtoFX application using Google Guice for dependency injection. By extending this class, you can customize the application's bindings, register your own services, and manage application-specific settings.

## Extending `ProtoFXGuiceModule`

To create a custom configuration for your application, you need to create a new class that extends `ProtoFXGuiceModule`. This new class will serve as your application's main Guice module.

### Example of a Custom Module:

```java
import co.bitshifted.protofx.core.di.ApplicationConfig;
import co.bitshifted.protofx.core.di.ProtoFXGuiceModule;
import com.google.inject.Scopes;

public class MyAppModule extends ProtoFXGuiceModule {

    public MyAppModule(ApplicationConfig applicationConfig) {
        super(applicationConfig);
    }

    @Override
    protected void customBindings() {
        // Bind your application-specific services here
        bind(MyApiService.class).to(DefaultMyApiService.class).in(Scopes.SINGLETON);
        bind(MyDataRepository.class).toInstance(new MyDataRepository("jdbc:mysql://localhost/mydb"));
    }

    @Override
    protected List<Object> models() {
        // Register your application's models
        return List.of(new MyDataModel());
    }

    @Override
    protected List<Class> eventBusSubscriptionHandlers() {
        // Register your event bus handlers
        return List.of(MyEventHandler.class);
    }
}
```

## Overriding Configuration Methods

The `ProtoFXGuiceModule` class provides several `protected` methods that you can override to customize the application's configuration.

### `customBindings()`

The `customBindings()` method is the primary place to define your application's custom Guice bindings. You can bind your own services, repositories, or any other components that your application requires.

#### How-to Tips:

*   **Use Scopes:** Always specify a scope for your bindings. Use `Scopes.SINGLETON` for services that should have only one instance throughout the application's lifecycle.
*   **Bind Interfaces to Implementations:** It's a good practice to bind interfaces to their concrete implementations. This makes your code more modular and easier to test.
*   **Use `@Inject`:** In your application classes, use the `@Inject` annotation to get instances of your bound services.

### `models()`

The `models()` method allows you to register your application's data models. A model is a simple POJO (Plain Old Java Object) that holds data, and its fields annotated with `@ModelData` will be bound and available for injection.

#### How-to Tips:

*   **`@ModelData` Annotation:** Use the `@ModelData` annotation on the fields of your model class that you want to be injectable. You must provide a `name` for each `@ModelData` field, which will be used for named injection.
*   **Injecting Model Data:** To inject a model data field, use the `@Inject` annotation along with `@Named("your_field_name")`.

#### Example Model:

```java
import co.bitshifted.protofx.core.annotations.ModelData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MyDataModel {

    @ModelData(name = "username")
    private final StringProperty username = new SimpleStringProperty("Default User");

    public StringProperty usernameProperty() {
        return username;
    }
}
```

#### Injecting the Model Data:

```java
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.beans.property.StringProperty;

public class MyViewController {

    @Inject
    @Named("username")
    private StringProperty username;

    public void initialize() {
        System.out.println("The current username is: " + username.get());
    }
}
```

### `eventBusSubscriptionHandlers()`

If your application uses the event bus (enabled by default), you can register your event handler classes in the `eventBusSubscriptionHandlers()` method. These classes must be annotated with `@EventBusSubscriptionHandler`.

#### How-to Tips:

*   **`@EventBusSubscriptionHandler` Annotation:** This annotation marks a class as an event handler. You can specify whether it should be a singleton.
*   **`@Subscribe` Annotation:** Inside your event handler class, create methods annotated with `@Subscribe` to handle specific event types.

#### Example Event Handler:

```java
import co.bitshifted.protofx.core.annotations.EventBusSubscriptionHandler;
import com.google.common.eventbus.Subscribe;

@EventBusSubscriptionHandler(singleton = true)
public class MyEventHandler {

    @Subscribe
    public void onUserLogin(UserLoginEvent event) {
        System.out.println("User logged in: " + event.getUsername());
    }
}
```

## Application Initialization

Once you have created your custom module, you need to pass it to the `ProtoFX` application instance to initialize your application with your custom configuration.

### Example Initialization:

```java
import co.bitshifted.protofx.ProtoFX;
import co.bitshifted.protofx.core.di.ApplicationConfig;

public class Main extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) {
        ApplicationConfig config = new ApplicationConfig.Builder()
            .withAppName("My Awesome App")
            .withVersion("1.0.0")
            .build();

        MyAppModule module = new MyAppModule(config);

        ProtoFX.start(this, primaryStage, module);
    }
}
```

By following these guidelines, you can effectively configure and extend your ProtoFX application to meet your specific requirements.
