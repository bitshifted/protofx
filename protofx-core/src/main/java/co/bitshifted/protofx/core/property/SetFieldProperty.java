/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.property;

import co.bitshifted.protofx.core.property.internal.BackingFieldChangeListener;
import co.bitshifted.protofx.core.property.internal.BackingFieldSetChangeListener;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

/**
 * Implementation of JavaFX {@code SimpleSetProperty} backed by POJO field.
 *
 * @param <T> the type of the elements in the set
 */
public class SetFieldProperty<T> extends SimpleSetProperty<T> {

  private final Consumer<ObservableSet<T>> consumer;
  private final ChangeListener<ObservableSet<T>> listener;
  private final SetChangeListener<T> setChangeListener;
  private ObservableValue<? extends ObservableSet<T>> observableValue;

  /**
   * Creates new instance of the property.
   *
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   */
  public SetFieldProperty(
      Supplier<ObservableSet<T>> supplier, Consumer<ObservableSet<T>> consumer) {
    super(supplier.get());
    this.consumer = consumer;
    this.listener = new BackingFieldChangeListener<>(this.consumer);
    this.setChangeListener = new BackingFieldSetChangeListener<>(this.consumer);
  }

  @Override
  public boolean add(T t) {
    var status = super.add(t);
    consumer.accept(get());
    return status;
  }

  @Override
  public boolean addAll(Collection<? extends T> collection) {
    var status = super.addAll(collection);
    consumer.accept(get());
    return status;
  }

  /**
   * Sets new value for the property. Underlying field will be updated as well.
   *
   * @param value value to set
   */
  @Override
  public void set(ObservableSet<T> value) {
    super.set(value);
    consumer.accept(value);
  }

  /**
   * Sets new value for the property. Underlying field will be updated as well.
   *
   * @param value value to set
   */
  @Override
  public void setValue(ObservableSet<T> value) {
    super.setValue(value);
    consumer.accept(value);
  }

  /**
   * Binds this property to specified {@code ObservableValue}
   *
   * @param observableValue value to bind
   */
  @Override
  public void bind(ObservableValue<? extends ObservableSet<T>> observableValue) {
    super.bind(observableValue);
    this.observableValue = observableValue;
    this.observableValue.addListener(listener);
    this.observableValue.getValue().addListener(setChangeListener);
  }

  /** Unbinds this property from specified {@code ObservableValue} */
  @Override
  public void unbind() {
    super.unbind();
    if (observableValue != null) {
      this.observableValue.removeListener(listener);
      this.observableValue.getValue().removeListener(setChangeListener);
    }
  }
}
