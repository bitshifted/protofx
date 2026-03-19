/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.property;

import co.bitshifted.protofx.core.property.internal.BackingFieldChangeListener;
import co.bitshifted.protofx.core.property.internal.BackingFieldListChangeListener;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Implementation of JavaFX {@code SimpleListProperty} backed by POJO field.
 *
 * @param <T> the type of the elements in the list
 */
public class ListFieldProperty<T> extends SimpleListProperty<T> {

  private final Consumer<ObservableList<T>> consumer;
  private final ChangeListener<ObservableList<T>> listener;
  private final ListChangeListener<T> listChangeListener;
  private ObservableValue<? extends ObservableList<T>> observableValue;

  /**
   * Creates new instance of the property.
   *
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   */
  public ListFieldProperty(
      Supplier<ObservableList<T>> supplier, Consumer<ObservableList<T>> consumer) {
    super(supplier.get());
    this.consumer = consumer;
    this.listener = new BackingFieldChangeListener<>(this.consumer);
    this.listChangeListener = new BackingFieldListChangeListener<>(this.consumer);
  }

  @Override
  public boolean add(T t) {
    var status = super.add(t);
    consumer.accept(get());
    return status;
  }

  /**
   * Sets new value for the property. Underlying field will be updated as well.
   *
   * @param value value to set
   */
  @Override
  public void set(ObservableList<T> value) {
    super.set(value);
    consumer.accept(value);
  }

  /**
   * Sets new value for the property. Underlying field will be updated as well.
   *
   * @param value value to set
   */
  @Override
  public void setValue(ObservableList<T> value) {
    super.setValue(value);
    consumer.accept(value);
  }

  /**
   * Binds this property to specified {@code ObservableValue}
   *
   * @param observableValue value to bind
   */
  @Override
  public void bind(ObservableValue<? extends ObservableList<T>> observableValue) {
    super.bind(observableValue);
    this.observableValue = observableValue;
    this.observableValue.addListener(listener);
    this.observableValue.getValue().addListener(listChangeListener);
  }

  /** Unbinds this property from specified {@code ObservableValue} */
  @Override
  public void unbind() {
    super.unbind();
    if (observableValue != null) {
      this.observableValue.removeListener(listener);
      this.observableValue.getValue().removeListener(listChangeListener);
    }
  }
}
