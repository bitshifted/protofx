/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;

/** A preference entry for boolean values. */
public class BooleanPreferenceEntry extends BasePreferenceEntry<Boolean> {

  /**
   * Creates a new boolean preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   */
  public BooleanPreferenceEntry(String root, String name, Boolean defaultValue) {
    super(root, name, defaultValue);
  }

  @Override
  protected Property<Boolean> createProperty(Boolean defaultValue) {
    return new SimpleBooleanProperty(defaultValue);
  }

  @Override
  protected void doSave() {
    baseNode.putBoolean(name, property.getValue());
  }

  @Override
  protected void doSave(Boolean value) {
    baseNode.putBoolean(name, value);
  }
}
