/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.property.internal;

import java.util.function.Consumer;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

public class BackingFieldSetChangeListener<T> implements SetChangeListener<T> {

  private final Consumer<ObservableSet<T>> consumer;

  public BackingFieldSetChangeListener(Consumer<ObservableSet<T>> consumer) {
    this.consumer = consumer;
  }

  @Override
  public void onChanged(Change<? extends T> change) {
    consumer.accept((ObservableSet<T>) change.getSet());
  }
}
