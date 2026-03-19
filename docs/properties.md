# Properties

The `co.bitshifted.protofx.core.property` package provides a powerful mechanism to bridge the gap between standard Java POJO (Plain Old Java Object) fields and the JavaFX Property system. This allows you to easily bind your UI components to your data model without requiring your model objects to be written with JavaFX-specific properties.

## Introduction

In JavaFX, data binding is a key feature that helps keep the UI and the underlying data model in sync. However, this typically requires the model to use JavaFX properties (e.g., `StringProperty`, `IntegerProperty`). This can be undesirable if you want to keep your model layer free of UI-framework dependencies.

This package solves that problem by providing custom property implementations that are "backed" by the getter and setter methods of a standard POJO field. When the property's value changes (e.g., through a UI control), the underlying POJO's setter is automatically called.

## `FieldBackedPropertyGenerator`

The main entry point for creating these properties is the `FieldBackedPropertyGenerator` class. This final utility class provides a set of static factory methods for creating different types of properties.

You will typically use method references to your POJO's getters and setters to instantiate the properties.

## Code Samples

Let's walk through a complete example of binding UI controls to a simple POJO.

### 1. The POJO Model

First, we have a simple `Person` class with standard fields and getter/setter methods. Notice that it contains no JavaFX-specific code.

```java
// In your data model
public class Person {
    private String name;
    private int age;
    private boolean isEmployed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isEmployed() {
        return isEmployed;
    }

    public void setEmployed(boolean employed) {
        isEmployed = employed;
    }
}
```

### 2. Creating Field-Backed Properties

Next, in your UI code (e.g., a controller or view), you can create an instance of your model and then generate properties that are linked to its fields.

```java
import co.bitshifted.protofx.core.property.FieldBackedPropertyGenerator;
import javafx.beans.property.*;

// In your UI controller/view
Person person = new Person();
person.setName("John Doe");
person.setAge(42);
person.setEmployed(true);

// Generate properties backed by the person object's fields
StringProperty nameProperty = FieldBackedPropertyGenerator.stringFieldProperty(person::getName, person::setName);
IntegerProperty ageProperty = FieldBackedPropertyGenerator.integerFieldProperty(person::getAge, person::setAge);
BooleanProperty employedProperty = FieldBackedPropertyGenerator.booleanFieldProperty(person::isEmployed, person::setEmployed);
```

### 3. Binding to a JavaFX UI

Finally, you can bind these properties directly to your JavaFX UI controls using standard binding methods.

```java
// Create some UI controls
TextField nameField = new TextField();
TextField ageField = new TextField(); // Using a TextField for simplicity
CheckBox employedCheckBox = new CheckBox("Is Employed?");

// Bind the controls to the properties
nameField.textProperty().bindBidirectional(nameProperty);
employedCheckBox.selectedProperty().bindBidirectional(employedProperty);

// For numeric types, you might need a string converter or use specific controls
// For example, with age:
ageField.setText(String.valueOf(ageProperty.get()));
ageProperty.addListener((obs, oldVal, newVal) -> {
    // Update the underlying Person object when the property changes
    ageField.setText(String.valueOf(newVal));
});
```

Now, any change in the `nameField` or `employedCheckBox` will automatically update the `person` object, and any programmatic change to the `person` object's fields (if the property is refreshed) will be reflected in the UI.

## Supported Property Types

The `FieldBackedPropertyGenerator` provides factory methods for a variety of common data types:

| Method | Returns |
|---|---|
| `stringFieldProperty(...)` | `StringFieldProperty` |
| `integerFieldProperty(...)` | `IntegerFieldProperty` |
| `booleanFieldProperty(...)` | `BooleanFieldProperty` |
| `doubleFieldProperty(...)` | `DoubleFieldProperty` |
| `floatFieldProperty(...)` | `FloatFieldProperty` |
| `objectFieldProperty(...)` | `ObjectFieldProperty<T>` |
| `listFieldProperty(...)` | `ListFieldProperty<T>` |
| `setFieldProperty(...)` | `SetFieldProperty<T>` |
| `mapFieldProperty(...)` | `MapFieldProperty<K, V>` |
