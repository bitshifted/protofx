/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;

/** A preference entry for integer values. */
public class IntegerPreferenceEntry extends NumberPreferenceEntry<Integer> {

  /**
   * Creates a new integer preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   */
  protected IntegerPreferenceEntry(String root, String name, Integer defaultValue) {
    super(root, name, defaultValue);
  }

  @Override
  protected Property<Number> createProperty(Integer defaultValue) {
    return new SimpleIntegerProperty(defaultValue);
  }

  @Override
  protected void doSave() {
    baseNode.putInt(name, property.getValue().intValue());
  }

  @Override
  protected void doSave(Integer value) {
    baseNode.putInt(name, value);
  }

  @Override
  public Integer getValue() {
    return property.getValue().intValue();
  }
}
