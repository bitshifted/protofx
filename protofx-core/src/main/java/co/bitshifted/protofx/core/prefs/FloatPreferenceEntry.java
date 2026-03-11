/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.prefs.Preferences;

public class FloatPreferenceEntry {

    private final SimpleFloatProperty property;
    private final String root;
    private final String name;
    private final Preferences baseNode;

    public FloatPreferenceEntry(String root, String name, Float defaultValue) {
        this.root = root;
        this.name = name;
        this.property = new SimpleFloatProperty(defaultValue);
        this.baseNode = Preferences.userRoot().node(root);
    }

    public Float getValue() {
        return property.getValue();
    }

    public void save() {
        baseNode.putFloat(name, property.getValue());
    }

    public void save(Float value) {
        baseNode.putFloat(name, value);
        property.setValue(value);
    }

    public SimpleFloatProperty getProperty() {
        return property;
    }
}
