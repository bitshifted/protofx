# UI components

Module `protofx-ui` provides ready-to-use  UI components. In order to use them, include `ProtFxUIGuiceModule` in your application,
along with the core module:

```java
public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ProtoFxGuiceModule(),new ProtoFXUiGuiceModule());
        // ... use the injector
    }
}
```

## Language selection combo box

Use this component to display list of languages available in application, and allow user to select one of them:

```java
class MyView {
    private ComboBox<Locale> languageCombo;
    
    public MyView(@Named(NamedValues.LOCALE_COMBO_INITIALIZER) Consumer<ComboBox<Locale>> initializer) {
        languageCombo = new ComboBox<>();
        initializer.accept(languageCombo);
    }
}
```