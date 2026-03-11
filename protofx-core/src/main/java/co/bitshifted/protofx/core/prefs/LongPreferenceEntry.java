/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.prefs.Preferences;

public class LongPreferenceEntry {

    private final SimpleLongProperty property;
    private final String root;
    private final String name;
    private final Preferences baseNode;

    public LongPreferenceEntry(String root, String name, Long defaultValue) {
        this.root = root;
        this.name = name;
        this.property = new SimpleLongProperty(defaultValue);
        this.baseNode = Preferences.userRoot().node(root);
    }

    public Long getValue() {
        return property.getValue();
    }

    public void save() {
        baseNode.putLong(name, property.getValue());
    }

    public void save(Long value) {
        baseNode.putLong(name, value);
        property.setValue(value);
    }

    public SimpleLongProperty getProperty() {
        return property;
    }
}
