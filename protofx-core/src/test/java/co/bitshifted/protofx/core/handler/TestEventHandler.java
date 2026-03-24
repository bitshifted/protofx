/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.handler;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCombination;

public class TestEventHandler extends DefaultActionEventHandler {

  public TestEventHandler() {
    super(KeyCombination.keyCombination("Ctrl+T"));
  }

  @Override
  public void handle(ActionEvent event) {
    disabledProperty().set(true);
  }
}
