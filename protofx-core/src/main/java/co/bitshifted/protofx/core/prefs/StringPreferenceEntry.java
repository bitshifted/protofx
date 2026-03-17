/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;

/** A preference entry for string values. */
public class StringPreferenceEntry extends BasePreferenceEntry<String> {

  /**
   * Creates a new string preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   */
  public StringPreferenceEntry(String root, String name, String defaultValue) {
    super(root, name, defaultValue);
  }

  @Override
  protected Property<String> createProperty(String defaultValue) {
    return new SimpleStringProperty(defaultValue);
  }

  @Override
  protected void doSave() {
    baseNode.put(name, property.getValue());
  }

  @Override
  protected void doSave(String value) {
    baseNode.put(name, value);
  }
}
