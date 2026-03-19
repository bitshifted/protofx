# Localization

The `co.bitshifted.protofx.core.l10n` package provides a set of classes to help with the localization of a JavaFX application. It is built on top of the standard Java `ResourceBundle` API, but provides a more modern and developer-friendly interface.

## Introduction

The localization package consists of the following main components:

*   **`LocaleManager`**: An interface that defines the API for managing the application's supported locales.
*   **`DefaultLocaleManager`**: The default implementation of `LocaleManager`.
*   **`ResourceBundleManager`**: An interface that defines the API for loading resource bundles.
*   **`DefaultResourceBundleManager`**: The default implementation of `ResourceBundleManager`.
*   **`ObservableResourceBundle`**: A wrapper around a `ResourceBundle` that allows you to observe changes to the bundle.

## `LocaleManager`

The `LocaleManager` is responsible for managing the application's supported locales. It provides methods for getting the current locale, getting a list of supported locales, and converting a string to a `Locale` object.

To get an instance of `LocaleManager`, you can inject it into your classes using a dependency injection framework like Dagger or Guice.

```java
@Inject
private LocaleManager localeManager;
```

Once you have an instance of `LocaleManager`, you can use it to get the current locale:

```java
StringPreferenceEntry currentLocale = localeManager.getCurrentLocale();
```

The `getCurrentLocale()` method returns a `StringPreferenceEntry` object, which is a special type of preference entry that is specific to the localization package. This object can be used to get and set the current locale, and it will automatically be persisted to the user's preferences.

## `ResourceBundleManager`

The `ResourceBundleManager` is responsible for loading resource bundles. It provides a single method, `loadResourceBundle()`, which takes the name of a resource bundle and returns an `ObservableResourceBundle` object.

To get an instance of `ResourceBundleManager`, you can inject it into your classes using a dependency injection framework.

```java
@Inject
private ResourceBundleManager resourceBundleManager;
```

Once you have an instance of `ResourceBundleManager`, you can use it to load a resource bundle:

```java
ObservableResourceBundle resourceBundle = resourceBundleManager.loadResourceBundle("my-bundle");
```

The `loadResourceBundle()` method will automatically load the correct resource bundle for the current locale.

## `ObservableResourceBundle`

The `ObservableResourceBundle` is a wrapper around a `ResourceBundle` that allows you to observe changes to the bundle. This is useful for updating your UI when the locale changes.

To get the value of a resource from an `ObservableResourceBundle`, you can use the `getString()` method:

```java
String myString = resourceBundle.getString("my-string");
```

To observe changes to the resource bundle, you can add a listener to the `resourceBundle` property:

```java
resourceBundle.resourceBundleProperty().addListener((observable, oldValue, newValue) -> {
  // The resource bundle has changed, so update your UI.
});
```

## Code Samples

Here is an example of how to use the localization package to localize a simple "Hello, world!" application:

**1. Create a resource bundle for each supported locale.**

For example, you could create the following files:

*   `hello-world_en.properties`:
    ```properties
    hello.world=Hello, world!
    ```
*   `hello-world_fr.properties`:
    ```properties
    hello.world=Bonjour, le monde !
    ```

**2. Inject `ResourceBundleManager` into your application.**

```java
@Inject
private ResourceBundleManager resourceBundleManager;
```

**3. Load the resource bundle.**

```java
ObservableResourceBundle resourceBundle = resourceBundleManager.loadResourceBundle("hello-world");
```

**4. Bind the resource to a UI control.**

```java
Label label = new Label();
label.textProperty().bind(resourceBundle.getStringBinding("hello.world"));
```

Now, when the locale changes, the text of the label will automatically be updated.
