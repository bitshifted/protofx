/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.eventbus;

/** Basic interface defining events that are published to Event Bus. */
public interface Event {

  /**
   * Event source, ie. payload with information relevant for the event.
   *
   * @return payload with information relevant for the event
   */
  Object source();
}
