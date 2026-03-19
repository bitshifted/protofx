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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/** Implementation of JavaFX {@code SimpleBooleanProperty} backed by POJO field. */
public class BooleanFieldProperty extends SimpleBooleanProperty {

  private final Consumer<Boolean> consumer;
  private ChangeListener<Boolean> listener;
  private ObservableValue<? extends Boolean> observableValue;

  /**
   * Creates new instance of the property.
   *
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   */
  public BooleanFieldProperty(Supplier<Boolean> supplier, Consumer<Boolean> consumer) {
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
  public void set(boolean value) {
    super.set(value);
    consumer.accept(value);
  }

  /**
   * Sets new value for the property. Underlying field will be updated as well.
   *
   * @param value value to set
   */
  @Override
  public void setValue(Boolean value) {
    super.setValue(value);
    consumer.accept(value);
  }

  /**
   * Binds this property to specified {@code ObservableValue}
   *
   * @param observableValue value to bind
   */
  @Override
  public void bind(ObservableValue<? extends Boolean> observableValue) {
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
