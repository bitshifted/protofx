/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.property.internal;

import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Listener used to bind properties to observable values. It uses provided consumer to update value
 * of the underlying field.
 *
 * @param <T> property type
 */
public class BackingFieldChangeListener<T> implements ChangeListener<T> {

  private final Consumer<T> consumer;

  /**
   * Creates new instance of the listener.
   *
   * @param consumer consumer used to update field
   */
  public BackingFieldChangeListener(Consumer<T> consumer) {
    this.consumer = consumer;
  }

  @Override
  public void changed(ObservableValue<? extends T> observableValue, T t, T newValue) {
    consumer.accept(newValue);
  }
}
