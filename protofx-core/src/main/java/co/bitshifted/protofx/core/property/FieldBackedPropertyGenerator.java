/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.property;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

/** Generates JavaFX properties backed by POJO fields. */
public final class FieldBackedPropertyGenerator {

  private FieldBackedPropertyGenerator() {}

  /**
   * Generates string property backed by class field.
   *
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   * @return new instance of {@link StringFieldProperty}
   */
  public static StringFieldProperty stringFieldProperty(
      Supplier<String> supplier, Consumer<String> consumer) {
    return new StringFieldProperty(supplier, consumer);
  }

  /**
   * Generates integer property backed by class field.
   *
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   * @return new instance of {@link IntegerFieldProperty}
   */
  public static IntegerFieldProperty integerFieldProperty(
      Supplier<Integer> supplier, Consumer<Integer> consumer) {
    return new IntegerFieldProperty(supplier, fromIntegerConsumer(consumer));
  }

  /**
   * Generates boolean property backed by class field.
   *
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   * @return new instance of {@link BooleanFieldProperty}
   */
  public static BooleanFieldProperty booleanFieldProperty(
      Supplier<Boolean> supplier, Consumer<Boolean> consumer) {
    return new BooleanFieldProperty(supplier, consumer);
  }

  /**
   * Generates double property backed by class field.
   *
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   * @return new instance of {@link DoubleFieldProperty}
   */
  public static DoubleFieldProperty doubleFieldProperty(
      Supplier<Double> supplier, Consumer<Double> consumer) {
    return new DoubleFieldProperty(supplier, fromDoubleConsumer(consumer));
  }

  /**
   * Generates float property backed by class field.
   *
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   * @return new instance of {@link FloatFieldProperty}
   */
  public static FloatFieldProperty floatFieldProperty(
      Supplier<Float> supplier, Consumer<Float> consumer) {
    return new FloatFieldProperty(supplier, fromFloatConsumer(consumer));
  }

  /**
   * Generates object property backed by class field.
   *
   * @param <T> the type of the wrapped value
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   * @return new instance of {@link ObjectFieldProperty}
   */
  public static <T> ObjectFieldProperty<T> objectFieldProperty(
      Supplier<T> supplier, Consumer<T> consumer) {
    return new ObjectFieldProperty<T>(supplier, consumer);
  }

  /**
   * Generates list property backed by class field.
   *
   * @param <T> the type of the elements in the list
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   * @return new instance of {@link ListFieldProperty}
   */
  public static <T> ListFieldProperty<T> listFieldProperty(
      Supplier<ObservableList<T>> supplier, Consumer<ObservableList<T>> consumer) {
    return new ListFieldProperty<>(supplier, consumer);
  }

  /**
   * Generates set property backed by class field.
   *
   * @param <T> the type of the elements in the set
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   * @return new instance of {@link SetFieldProperty}
   */
  public static <T> SetFieldProperty<T> setFieldProperty(
      Supplier<ObservableSet<T>> supplier, Consumer<ObservableSet<T>> consumer) {
    return new SetFieldProperty<>(supplier, consumer);
  }

  /**
   * Generates map property backed by class field.
   *
   * @param <K> the type of the keys in the map
   * @param <V> the type of the values in the map
   * @param supplier supplier used to get current field value
   * @param consumer consumer used to set field to a new value
   * @return new instance of {@link MapFieldProperty}
   */
  public static <K, V> MapFieldProperty<K, V> mapFieldProperty(
      Supplier<ObservableMap<K, V>> supplier, Consumer<ObservableMap<K, V>> consumer) {
    return new MapFieldProperty<>(supplier, consumer);
  }

  private static Consumer<Number> fromIntegerConsumer(Consumer<Integer> c) {
    return number -> c.accept(number.intValue());
  }

  private static Consumer<Number> fromFloatConsumer(Consumer<Float> c) {
    return number -> c.accept(number.floatValue());
  }

  private static Consumer<Number> fromDoubleConsumer(Consumer<Double> c) {
    return number -> c.accept(number.doubleValue());
  }
}
