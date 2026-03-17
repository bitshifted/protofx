/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.prefs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

/**
 * A preference entry for a collection of values.
 *
 * @param <T> the type of the values
 */
public class MultiValuePreferenceEntry<T> extends BasePreferenceEntry<Collection<T>> {

  /**
   * Creates a new multi-value preference entry.
   *
   * @param root the root node for the preference
   * @param name the name of the preference
   * @param defaultValue the default value if the preference is not set
   */
  public MultiValuePreferenceEntry(String root, String name, Collection<T> defaultValue) {
    super(root, name, defaultValue);
  }

  @Override
  protected Property<Collection<T>> createProperty(Collection<T> defaultValue) {
    return new SimpleObjectProperty<>(defaultValue);
  }

  @Override
  protected void doSave() {
    baseNode.putByteArray(name, getObjectBytes(property.getValue()));
  }

  @Override
  protected void doSave(Collection<T> value) {
    baseNode.putByteArray(name, getObjectBytes(value));
  }

  private byte[] getObjectBytes(Collection<T> object) {
    var bytes = new ByteArrayOutputStream();
    try (var oos = new ObjectOutputStream(bytes)) {
      oos.writeObject(object);
      return bytes.toByteArray();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }
}
