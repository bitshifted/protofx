/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.property.internal;

import java.util.function.Consumer;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class BackingFieldListChangeListener<T> implements ListChangeListener<T> {

  private final Consumer<ObservableList<T>> consumer;

  public BackingFieldListChangeListener(Consumer<ObservableList<T>> consumer) {
    this.consumer = consumer;
  }

  @Override
  public void onChanged(Change<? extends T> change) {
    consumer.accept((ObservableList<T>) change.getList());
  }
}
