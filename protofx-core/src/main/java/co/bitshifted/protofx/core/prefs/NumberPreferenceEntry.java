/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

import javafx.beans.property.Property;

import java.util.Optional;
import java.util.prefs.Preferences;

/**
 * A base class for number preference entries.
 * @param <T> the type of the number
 */
public abstract class NumberPreferenceEntry<T extends Number> implements PendingValuePreference<T> {

    protected final Property<Number> property;
    protected final String name;
    protected final Preferences baseNode;
    protected Optional<T> pendingValue;

    /**
     * Creates a new number preference entry.
     * @param root the root node for the preference
     * @param name the name of the preference
     * @param defaultValue the default value if the preference is not set
     */
    protected NumberPreferenceEntry(String root, String name, T defaultValue) {
        this.name = name;
        this.baseNode = Preferences.userRoot().node(root);
        this.property = createProperty(defaultValue);
        property.addListener((observableValue, oldValue, newValue) -> save());
        this.pendingValue = Optional.empty();
    }

    /**
     * Creates a property for the preference.
     * @param defaultValue the default value
     * @return the property
     */
    protected abstract Property<Number> createProperty(T defaultValue);

    /**
     * Saves the current value of the preference.
     */
    protected abstract void doSave();

    /**
     * Saves the given value of the preference.
     * @param value the value to save
     */
    protected abstract void doSave(T value);

    /**
     * Returns the current value of the preference.
     * @return the current value
     */
    public abstract T getValue();

    @Override
    public void save() {
        if(pendingValue != null && pendingValue.isPresent()) {
            property.setValue(pendingValue.get());
            pendingValue = Optional.empty();
        }
        doSave();
    }

    /**
     * Saves the given value of the preference.
     * @param value the value to save
     */
    public void save(T value) {
        if(pendingValue.isPresent()) {
            pendingValue = Optional.empty();
        }
        doSave(value);
        property.setValue(value);
    }

    /**
     * Returns the property for the preference.
     * @return the property
     */
    public Property<Number> getProperty() {
        return property;
    }

    @Override
    public void setPendingValue(T value) {
        this.pendingValue = Optional.ofNullable(value);
    }

    @Override
    public boolean hasPendingValue() {
        return this.pendingValue.isPresent();
    }


    @Override
    public void clearPendingValue() {
        this.pendingValue = Optional.empty();
    }
}
