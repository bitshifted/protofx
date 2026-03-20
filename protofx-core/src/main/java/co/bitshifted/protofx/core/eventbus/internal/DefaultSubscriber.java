/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.eventbus.internal;

import co.bitshifted.protofx.core.eventbus.Event;
import java.lang.reflect.Method;
import java.util.concurrent.Flow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSubscriber implements Flow.Subscriber<Event> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSubscriber.class);

  private final Object receiver;
  private final Method handler;
  private Flow.Subscription subscription;

  public DefaultSubscriber(Object receiver, Method handler) {
    this.receiver = receiver;
    this.handler = handler;
  }

  @Override
  public void onSubscribe(Flow.Subscription subscription) {
    this.subscription = subscription;
    subscription.request(1);
  }

  @Override
  public void onNext(Event event) {
    try {
      handler.invoke(receiver, event.source());
      subscription.request(1);
    } catch (Throwable th) {
      LOGGER.error("Failed to invoke event handler");
      throw new RuntimeException(th);
    }
  }

  @Override
  public void onError(Throwable throwable) {
    LOGGER.error("Error receiving event", throwable);
  }

  @Override
  public void onComplete() {
    LOGGER.info("Subscription closed");
  }
}
