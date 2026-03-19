/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.property.internal;

import java.util.function.Consumer;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

public class BackingFieldMapChangeListener<K, V> implements MapChangeListener<K, V> {

  private final Consumer<ObservableMap<K, V>> consumer;

  public BackingFieldMapChangeListener(Consumer<ObservableMap<K, V>> consumer) {
    this.consumer = consumer;
  }

  @Override
  public void onChanged(Change<? extends K, ? extends V> change) {
    consumer.accept((ObservableMap<K, V>) change.getMap());
  }
}
