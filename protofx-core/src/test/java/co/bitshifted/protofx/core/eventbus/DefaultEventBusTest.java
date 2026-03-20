/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.eventbus;

import co.bitshifted.protofx.core.eventbus.internal.DefaultEventBus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultEventBusTest {

  private DefaultEventBus eventBus = new DefaultEventBus();

  @Test
  void eventProcessingSuccess() throws Exception {
    var handler = new EventHandler();
    eventBus.subscribe(handler, TestEvent.class);
    eventBus.publishEvent(new TestEvent("test"));
    Thread.sleep(500);
    Assertions.assertEquals(1, handler.getCount());
  }

  void multipleEventHandlersTest() throws Exception {
    var handler = new EventHandler();
    eventBus.subscribe(handler, TestEvent.class);
    eventBus.subscribe(handler, FooEvent.class);
    eventBus.publishEvent(new TestEvent("test"));
    eventBus.publishEvent(new FooEvent("bar"));
    Thread.sleep(500);
    Assertions.assertEquals(1, handler.getCount());
    Assertions.assertEquals(1, handler.getFooCount());
  }
}
