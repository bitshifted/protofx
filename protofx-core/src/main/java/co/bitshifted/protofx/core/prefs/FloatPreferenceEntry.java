/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleFloatProperty;

/** A preference entry for float values. */
public class FloatPreferenceEntry extends NumberPreferenceEntry<Float> {

  /**
   * Creates a new float preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   */
  protected FloatPreferenceEntry(String root, String name, Float defaultValue) {
    super(root, name, defaultValue);
  }

  @Override
  protected Property<Number> createProperty(Float defaultValue) {
    return new SimpleFloatProperty(defaultValue);
  }

  @Override
  protected void doSave() {
    baseNode.putDouble(name, property.getValue().floatValue());
  }

  @Override
  protected void doSave(Float value) {
    baseNode.putFloat(name, value);
  }

  @Override
  public Float getValue() {
    return property.getValue().floatValue();
  }
}
