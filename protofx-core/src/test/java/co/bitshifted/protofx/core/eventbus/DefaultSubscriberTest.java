/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.eventbus;

import co.bitshifted.protofx.core.eventbus.internal.DefaultEvent;
import co.bitshifted.protofx.core.eventbus.internal.DefaultSubscriber;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Flow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DefaultSubscriberTest {

  private Flow.Subscription mockSubscription;
  private Method mockMethod;
  private EventHandler receiver;

  @BeforeEach
  void setup() {
    mockSubscription = Mockito.mock(Flow.Subscription.class);
    mockMethod = Mockito.mock(Method.class);
    receiver = new EventHandler();
  }

  @Test
  void onSubscribeSuccess() {
    var subscriber = new DefaultSubscriber(receiver, mockMethod);
    subscriber.onSubscribe(mockSubscription);
    Mockito.verify(mockSubscription).request(1);
  }

  @Test
  void onNextSuccess() throws Exception {
    var subscriber = new DefaultSubscriber(receiver, mockMethod);
    subscriber.onSubscribe(mockSubscription);
    var event = new DefaultEvent(new TestEvent("test"));
    subscriber.onNext(event);
    Mockito.verify(mockMethod).invoke(receiver, event.source());
    Mockito.verify(mockSubscription, Mockito.times(2)).request(1);
  }

  @Test
  void onNextException() throws Exception {
    var subscriber = new DefaultSubscriber(receiver, mockMethod);
    subscriber.onSubscribe(mockSubscription);
    var event = new DefaultEvent(new TestEvent("test"));
    Mockito.when(mockMethod.invoke(receiver, event.source()))
        .thenThrow(new InvocationTargetException(new Exception("error")));
    Assertions.assertThrows(RuntimeException.class, () -> subscriber.onNext(event));
  }
}
