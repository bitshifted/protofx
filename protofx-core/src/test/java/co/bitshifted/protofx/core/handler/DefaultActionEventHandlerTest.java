/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.handler;

import javafx.event.ActionEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class DefaultActionEventHandlerTest {

  private DefaultActionEventHandler handler;

  @BeforeEach
  void setup() {
    handler = new TestEventHandler();
  }

  @Test
  void shouldProcessEventSuccesfully() {
    handler.handle(new ActionEvent());
    Assertions.assertTrue(handler.disabledProperty().get());
  }
}
