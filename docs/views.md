# Views

Package `co.bitshifted.protofx.core.view` provides core functionalities for managing and loading JavaFX views and building dialogs within the ProtoFX framework. It aims to simplify the process of integrating FXML-based views and handling internationalization for view components.

## `FxViewAware` Interface

The `FxViewAware` interface defines a contract for any class that represents a view which can be loaded by an `FxViewLoader`. It provides essential information about the view's FXML file, resource bundle, and root node.

### Methods:

*   `String viewName()`: Returns a unique name for the view.
*   `ResourceBundle resourceBundle()`: Returns the `ResourceBundle` associated with this view for internationalization.
*   `URL fxmlUrl()`: Returns the URL of the FXML file that defines the view's layout.
*   `Node viewRoot()`: Returns the root `Node` of the view if it's not loaded from an FXML file (e.g., programmatically created).
*   `default Charset charset()`: Returns the `Charset` to be used when loading the FXML file. Defaults to `StandardCharsets.UTF_8`.

### Example Implementation:

```java
public class MyCustomView implements FxViewAware {

    @Override
    public String viewName() {
        return "MyCustomView";
    }

    @Override
    public ResourceBundle resourceBundle() {
        // Assuming you have a ResourceBundleManager or similar
        return ResourceBundle.getBundle("bundles.MyCustomViewBundle");
    }

    @Override
    public URL fxmlUrl() {
        return getClass().getResource("/fxml/MyCustomView.fxml");
    }

    @Override
    public Node viewRoot() {
        // If the view is not FXML-based, return the root node here.
        // Otherwise, this can return null if fxmlUrl() is provided.
        return null;
    }
}
```

## `FxViewLoader` Interface

The `FxViewLoader` interface defines the contract for loading JavaFX views, typically from FXML files. It provides overloaded methods to load views based on `FxViewAware` implementations or objects annotated with `@FxView`.

### Methods:

*   `Node loadView(FxViewAware view) throws ViewLoadException`:
    Loads a view defined by an `FxViewAware` instance. If `view.fxmlUrl()` is `null`, it returns `view.viewRoot()`.
*   `Node loadView(Object viewObject) throws ViewLoadException`:
    Loads a view from an arbitrary `viewObject`. If `viewObject` implements `FxViewAware`, it delegates to the first `loadView` method. Otherwise, it expects the `viewObject`'s class to be annotated with `@FxView`.
*   `<T extends Node> T loadView(Object viewObject, Class<T> viewType) throws ViewLoadException`:
    Loads a view from an `viewObject` and casts the root node to the specified `viewType`.

### Exceptions:

*   `ViewLoadException`: Thrown if an error occurs during the view loading process (e.g., FXML file not found, I/O error).

## `DefaultFxViewLoader` Class

`DefaultFxViewLoader` is the default implementation of the `FxViewLoader` interface. It uses `javafx.fxml.FXMLLoader` internally and supports loading views from `FxViewAware` implementations or classes annotated with `@co.bitshifted.protofx.core.annotations.FxView`. It also integrates with `ResourceBundleManager` for internationalization.

### Constructor:

```java
@Inject
public DefaultFxViewLoader(ResourceBundleManager resourceBundleManager)
```

Requires an injected `ResourceBundleManager` to handle resource bundles.

### View Loading Mechanism:

1.  **Using `FxViewAware`**: If the view object implements `FxViewAware`, the loader uses the `fxmlUrl()`, `resourceBundle()`, `charset()`, and sets the `FxViewAware` instance as the FXML controller.
2.  **Using `@FxView` Annotation**: If the view object does not implement `FxViewAware`, the loader looks for the `@FxView` annotation on the view object's class.
    *   `@FxView(fxmlUrl = "path/to/view.fxml")`: Specifies the FXML file URL.
    *   `@FxView(resourceBundle = "bundleName")`: Specifies the base name of the resource bundle.
    *   `@FxView(charset = "UTF-8")`: Specifies the character set for the FXML file.
    *   If `fxmlUrl` is empty, it attempts to find a field annotated with `@ViewRootNode` within the `viewObject` and returns that `Node`.

### Example with `@FxView` and `@ViewRootNode`:

```java
import co.bitshifted.protofx.core.annotations.FxView;
import co.bitshifted.protofx.core.annotations.ViewRootNode;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

@FxView(fxmlUrl = "/fxml/AnnotatedView.fxml", resourceBundle = "bundles.AnnotatedViewBundle")
public class AnnotatedViewController {

    @ViewRootNode // Used if fxmlUrl is empty or null
    private VBox rootContainer;

    @FXML
    private Label messageLabel;

    public void initialize() {
        // Controller initialization logic
        messageLabel.setText("Hello from Annotated View!");
    }

    public VBox getRootContainer() {
        return rootContainer;
    }
}

// To load this view:
// FxViewLoader loader = new DefaultFxViewLoader(resourceBundleManager);
// AnnotatedViewController controller = new AnnotatedViewController();
// Node viewRoot = loader.loadView(controller);
```

## `DialogBuilder` Class

The `DialogBuilder` class provides a fluent API for constructing JavaFX `Dialog` instances. It simplifies the process of setting up dialog properties, content, button types, result converters, and event handlers.

### Usage:

You can create a new `DialogBuilder` instance using the static `newBuilder()` methods.

*   `public static <R> DialogBuilder<R> newBuilder(Class<R> clazz)`: Creates a builder for a dialog with a specific result type.
*   `public static DialogBuilder<Void> newBuilder()`: Creates a builder for a dialog with a `Void` result type (e.g., for simple alert dialogs).

### Methods:

*   `DialogBuilder<T> withResourceBundleManager(ResourceBundleManager resourceBundleManager)`: Sets the `ResourceBundleManager` to be used for resolving localized strings.
*   `DialogBuilder<T> withResourceBundleName(String resourceBundleName)`: Loads a specific resource bundle by name using the configured `ResourceBundleManager`.
*   `DialogBuilder<T> withTitle(String title)`: Sets the title of the dialog.
*   `DialogBuilder<T> withTitleKey(String key)`: Sets the title of the dialog using a key from the loaded resource bundle.
*   `DialogBuilder<T> withButtonTypes(ButtonType... buttonTypes)`: Adds one or more `ButtonType` instances to the dialog's `DialogPane`.
*   `DialogBuilder<T> withContent(Node content)`: Sets the main content `Node` of the dialog.
*   `DialogBuilder<T> withResultConverter(Callback<ButtonType, T> converter)`: Sets the result converter for the dialog, which maps a `ButtonType` to the dialog's result type `T`.
*   `DialogBuilder<T> withButtonTypeActionHandler(ButtonType type, EventHandler handler)`: Associates an `EventHandler` with a specific `ButtonType` in the dialog. This handler will be triggered when the corresponding button is activated.
*   `Dialog<T> build()`: Constructs and returns the configured `Dialog` instance.

### Example Usage:

```java
import co.bitshifted.protofx.core.l10n.ResourceBundleManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

// Assuming resourceBundleManager is an initialized instance of ResourceBundleManager
ResourceBundleManager resourceBundleManager = new ResourceBundleManager(); // Or injected

// Example 1: Simple Info Dialog
Dialog<Void> infoDialog = DialogBuilder.newBuilder()
    .withTitle("Information")
    .withContent(new Label("This is a simple information dialog."))
    .withButtonTypes(ButtonType.OK)
    .build();
infoDialog.showAndWait();

// Example 2: Confirmation Dialog with custom result
class ConfirmationResult {
    boolean confirmed;
    // ... other data
}

Dialog<ConfirmationResult> confirmationDialog = DialogBuilder.newBuilder(ConfirmationResult.class)
    .withResourceBundleManager(resourceBundleManager)
    .withResourceBundleName("bundles.MyDialogBundle") // Assuming this bundle exists
    .withTitleKey("confirmation.dialog.title") // Key from MyDialogBundle
    .withContent(new Label("Are you sure you want to proceed?"))
    .withButtonTypes(ButtonType.YES, ButtonType.NO)
    .withResultConverter(buttonType -> {
        ConfirmationResult result = new ConfirmationResult();
        if (buttonType == ButtonType.YES) {
            result.confirmed = true;
        } else {
            result.confirmed = false;
        }
        return result;
    })
    .withButtonTypeActionHandler(ButtonType.YES, event -> {
        System.out.println("YES button clicked!");
        // Additional logic before dialog closes
    })
    .build();

confirmationDialog.showAndWait().ifPresent(result -> {
    if (result.confirmed) {
        System.out.println("User confirmed the action.");
    } else {
        System.out.println("User cancelled the action.");
    }
});

// Example 3: Dialog with custom content and no specific result type
VBox customContent = new VBox(10);
customContent.getChildren().addAll(
    new Label("Enter your details:"),
    new javafx.scene.control.TextField("Name"),
    new javafx.scene.control.TextField("Email")
);

Dialog<Void> customDialog = DialogBuilder.newBuilder()
    .withTitle("Custom Input Dialog")
    .withContent(customContent)
    .withButtonTypes(ButtonType.APPLY, ButtonType.CANCEL)
    .withButtonTypeActionHandler(ButtonType.APPLY, event -> {
        System.out.println("Apply button clicked in custom dialog.");
        // Process input from text fields
    })
    .build();
customDialog.showAndWait();
```
