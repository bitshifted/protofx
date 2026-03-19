/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.property;

import co.bitshifted.protofx.core.property.internal.BackingFieldChangeListener;
import co.bitshifted.protofx.core.property.internal.BackingFieldMapChangeListener;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

/**
 * Implementation of JavaFX {@code SimpleMapProperty} backed by POJO field.
 *
 * @param <K> the type of the keys in the map
 * @param <V> the type of the values in the map
 */
public class MapFieldProperty<K, V> extends SimpleMapProperty<K, V> {

  private final Consumer<ObservableMap<K, V>> consumer;
  private final ChangeListener<ObservableMap<K, V>> listener;
  private final MapChangeListener<K, V> mapChangeListener;
  private ObservableValue<? extends ObservableMap<K, V>> observableValue;

  /**
   * Creates new instance of the property.
   *
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   */
  public MapFieldProperty(
      Supplier<ObservableMap<K, V>> supplier, Consumer<ObservableMap<K, V>> consumer) {
    super(supplier.get());
    this.consumer = consumer;
    this.listener = new BackingFieldChangeListener<>(this.consumer);
    this.mapChangeListener = new BackingFieldMapChangeListener<>(this.consumer);
  }

  /**
   * Sets new value for the property. Underlying field will be updated as well.
   *
   * @param value value to set
   */
  @Override
  public void set(ObservableMap<K, V> value) {
    super.set(value);
    consumer.accept(value);
  }

  /**
   * Sets new value for the property. Underlying field will be updated as well.
   *
   * @param value value to set
   */
  @Override
  public void setValue(ObservableMap<K, V> value) {
    super.setValue(value);
    consumer.accept(value);
  }

  /**
   * Binds this property to specified {@code ObservableValue}
   *
   * @param observableValue value to bind
   */
  @Override
  public void bind(ObservableValue<? extends ObservableMap<K, V>> observableValue) {
    super.bind(observableValue);
    this.observableValue = observableValue;
    this.observableValue.addListener(listener);
    this.observableValue.getValue().addListener(mapChangeListener);
  }

  /** Unbinds this property from specified {@code ObservableValue} */
  @Override
  public void unbind() {
    super.unbind();
    if (observableValue != null) {
      this.observableValue.removeListener(listener);
      this.observableValue.getValue().removeListener(mapChangeListener);
    }
  }
}
