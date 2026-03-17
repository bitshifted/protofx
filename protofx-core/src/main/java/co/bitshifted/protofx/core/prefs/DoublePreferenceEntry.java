/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;

/** A preference entry for double values. */
public class DoublePreferenceEntry extends NumberPreferenceEntry<Double> {

  /**
   * Creates a new double preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   */
  protected DoublePreferenceEntry(String root, String name, Double defaultValue) {
    super(root, name, defaultValue);
  }

  @Override
  protected Property<Number> createProperty(Double defaultValue) {
    return new SimpleDoubleProperty(defaultValue);
  }

  @Override
  protected void doSave() {
    baseNode.putDouble(name, property.getValue().doubleValue());
  }

  @Override
  protected void doSave(Double value) {
    baseNode.putDouble(name, value);
  }

  @Override
  public Double getValue() {
    return property.getValue().doubleValue();
  }
}
