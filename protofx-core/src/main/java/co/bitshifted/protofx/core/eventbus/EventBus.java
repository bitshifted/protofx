/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.eventbus;

import java.util.concurrent.Flow;

/**
 * Defines method for implementation of simple Event Bus pattern which allows asynchronous
 * communication between component using publish/subscribe model.
 */
public interface EventBus {

  /**
   * Subscribes given {@code handler} to events of type {@code eventClass}
   *
   * @param handler event handler
   * @param eventClass event class
   * @param <T> event type
   */
  <T> void subscribe(Flow.Subscriber<Event> handler, Class<T> eventClass);

  /**
   * Subscribes given {@code handler} to events of type {@code eventClass}. Whenever an event of
   * type {@code eventClass} is published, the {@code handler} will be notified and can react
   * accordingly.
   *
   * <p>Handler can be any object with at least one method annotated with {@link
   * co.bitshifted.protofx.core.annotations.EventBusSubscriptionHandler} and with parameter of type
   * {@code eventClass}. If handler has multiple methods annotated with
   *
   * @param handler event handler
   * @param eventClass event class
   * @param <T> event type
   */
  <T> void subscribe(Object handler, Class<T> eventClass);

  /**
   * Removes specified handler from subscription. Handler will no longer receive published events.
   *
   * @param handler handler to unsubscribe
   */
  void unsubscribe(Object handler);

  /**
   * Publishes given event to event bus, notifying all interested subscribers.
   *
   * @param event event to publisj
   * @param <T> event type
   */
  <T> void publishEvent(T event);
}
