# Preferences

The `co.bitshifted.protofx.core.prefs` package provides a simple and flexible way to manage user preferences in a JavaFX application. It is built on top of the standard Java Preferences API, but provides a more modern and developer-friendly interface.

## Introduction

The preferences package consists of three main components:

* **`PreferenceManager`**: An interface that defines the API for getting different types of preference entries.
* **`BasePreferenceEntry`**: An abstract class that provides the basic functionality for a preference entry, including handling pending values and JavaFX properties.
* **Preference-specific entry classes**: Concrete implementations of `BasePreferenceEntry` for different data types (e.g., `StringPreferenceEntry`, `IntegerPreferenceEntry`, etc.).

## `PreferenceManager`

The `PreferenceManager` is the main entry point for working with preferences. It provides methods for getting preference entries for different data types. To get an instance of `PreferenceManager`, you can inject it into your classes using a dependency injection framework like Dagger or Guice.

```java
@Inject
private PreferenceManager preferenceManager;
```

Once you have an instance of `PreferenceManager`, you can use it to get preference entries. For example, to get a string preference entry, you can use the `getStringPreferenceEntry()` method:

```java
StringPreferenceEntry myPref = preferenceManager.getStringPreferenceEntry("my.pref", "default value");
```

The first argument to this method is the name of the preference, and the second argument is the default value to use if the preference is not set.

By default, `PreferenceManager` saves all preferences under "master node" defined as return value of `ApplicationConfig#defaultPreferenceRootNode()`. It is also possible to use custom root node for preference, by using additional parameter:

```java
StringPreferenceEntry myPref = preferenceManager.getStringPreferenceEntry("custom-root-node", "my.pref", "default value");
```

## Preference Entries

A preference entry represents a single preference value. It provides methods for getting and setting the value of the preference, as well as a JavaFX property that can be used to bind the preference to UI controls.

To get the value of a preference, you can use the `getValue()` method:

```java
String value = myPref.getValue();
```

To set the value of a preference, you can use the `save()` method:

```java
myPref.save("new value");
```

This will immediately persist the new value to the underlying storage.

### Pending Values

Sometimes, you may want to change the value of a preference without immediately persisting it. For example, you may want to allow the user to make changes in a settings dialog and then click an "Apply" button to save the changes.

To support this use case, preference entries have the concept of a "pending value". You can set a pending value using the `setPendingValue()` method:

```java
myPref.setPendingValue("new value");
```

This will change the value of the preference in memory, but it will not be persisted until you call the `save()` method:

```java.
myPref.save();
```

You can check if a preference has a pending value using the `hasPendingValue()` method, and you can clear a pending value using the `clearPendingValue()` method.

### JavaFX Properties

Each preference entry has a JavaFX property that can be used to bind the preference to UI controls. To get the property, you can use the `getProperty()` method:

```java
StringProperty property = myPref.getProperty();
```

You can then bind this property to a UI control, such as a `TextField`:

```java
textField.textProperty().bindBidirectional(property);
```

Now, any changes made to the text field will be automatically reflected in the preference, and any changes made to the preference will be automatically reflected in the text field.

## Code Samples

Here are some examples of how to use the preferences package:

**Getting and setting a string preference:**

```java
StringPreferenceEntry myPref = preferenceManager.getStringPreferenceEntry("my.pref", "default value");
String value = myPref.getValue();
myPref.save("new value");
```

**Binding a boolean preference to a check box:**

```java
BooleanPreferenceEntry myPref = preferenceManager.getBooleanPreferenceEntry("my.pref", false);
checkBox.selectedProperty().bindBidirectional(myPref.getProperty());
```

**Using a pending value to implement an "Apply" button:**

```java
// In the settings dialog
myPref.setPendingValue(newValue);

// When the "Apply" button is clicked
myPref.save();
```
