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

public abstract class NumberPreferenceEntry<T extends Number> implements PendingValuePreference<T> {

    protected final Property<Number> property;
    protected final String name;
    protected final Preferences baseNode;
    protected Optional<T> pendingValue;

    protected NumberPreferenceEntry(String root, String name, T defaultValue) {
        this.name = name;
        this.baseNode = Preferences.userRoot().node(root);
        this.property = createProperty(defaultValue);
        property.addListener((observableValue, oldValue, newValue) -> save());
        this.pendingValue = Optional.empty();
    }

    protected abstract Property<Number> createProperty(T defaultValue);

    protected abstract void doSave();

    protected abstract void doSave(T value);

    public abstract T getValue();

    @Override
    public void save() {
        if(pendingValue != null && pendingValue.isPresent()) {
            property.setValue(pendingValue.get());
            pendingValue = Optional.empty();
        }
        doSave();
    }

    public void save(T value) {
        if(pendingValue.isPresent()) {
            pendingValue = Optional.empty();
        }
        doSave(value);
        property.setValue(value);
    }

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
