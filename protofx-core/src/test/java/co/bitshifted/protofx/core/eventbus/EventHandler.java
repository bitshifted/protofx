/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.eventbus;

import co.bitshifted.protofx.core.annotations.EventBusSubscriptionHandler;

public class EventHandler {

  private int count = 0;
  private int fooCount = 0;

  @EventBusSubscriptionHandler
  public String eventReceived(TestEvent event) {
    System.out.println("Receiving event");
    count++;
    return event.getName();
  }

  @EventBusSubscriptionHandler
  public void fooEventReceived(FooEvent event) {
    fooCount++;
  }

  public int getCount() {
    return count;
  }

  public int getFooCount() {
    return fooCount;
  }
}
