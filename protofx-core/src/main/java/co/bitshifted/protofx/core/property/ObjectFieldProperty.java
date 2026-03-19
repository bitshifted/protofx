/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.property;

import co.bitshifted.protofx.core.property.internal.BackingFieldChangeListener;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Implementation of JavaFX {@code SimpleObjectProperty} backed by POJO field.
 *
 * @param <T> the type of the wrapped value
 */
public class ObjectFieldProperty<T> extends SimpleObjectProperty<T> {

  private final Consumer<T> consumer;
  private ChangeListener<T> listener;
  private ObservableValue<? extends T> observableValue;

  /**
   * Creates new instance of the property.
   *
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   */
  public ObjectFieldProperty(Supplier<T> supplier, Consumer<T> consumer) {
    super(supplier.get());
    this.consumer = consumer;
    this.listener = new BackingFieldChangeListener<>(this.consumer);
  }

  /**
   * Sets new value for the property. Underlying field will be updated as well.
   *
   * @param value value to set
   */
  @Override
  public void set(T value) {
    super.set(value);
    consumer.accept(value);
  }

  /**
   * Sets new value for the property. Underlying field will be updated as well.
   *
   * @param value value to set
   */
  @Override
  public void setValue(T value) {
    super.setValue(value);
    consumer.accept(value);
  }

  /**
   * Binds this property to specified {@code ObservableValue}
   *
   * @param observableValue value to bind
   */
  @Override
  public void bind(ObservableValue<? extends T> observableValue) {
    super.bind(observableValue);
    this.observableValue = observableValue;
    this.observableValue.addListener(listener);
  }

  /** Unbinds this property from specified {@code ObservableValue} */
  @Override
  public void unbind() {
    super.unbind();
    if (observableValue != null) {
      this.observableValue.removeListener(listener);
    }
  }
}
