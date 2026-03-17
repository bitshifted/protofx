/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleLongProperty;

/** A preference entry for long values. */
public class LongPreferenceEntry extends NumberPreferenceEntry<Long> {
  /**
   * Creates a new long preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   */
  protected LongPreferenceEntry(String root, String name, Long defaultValue) {
    super(root, name, defaultValue);
  }

  @Override
  protected Property<Number> createProperty(Long defaultValue) {
    return new SimpleLongProperty(defaultValue);
  }

  @Override
  protected void doSave() {
    baseNode.putLong(name, property.getValue().longValue());
  }

  @Override
  protected void doSave(Long value) {
    baseNode.putLong(name, value);
  }

  @Override
  public Long getValue() {
    return property.getValue().longValue();
  }

  /**
   * Saves the given value of the preference.
   *
   * @param value the value to save
   */
  public void save(Long value) {
    baseNode.putLong(name, value);
    property.setValue(value);
  }
}
