/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SimpleTestView implements FxViewAware {

  private VBox vbox;

  public SimpleTestView() {
    vbox = new VBox();
    vbox.getChildren().add(new Label("Hello World!"));
  }

  @Override
  public String viewName() {
    return "simple-test-view";
  }

  @Override
  public ResourceBundle resourceBundle() {
    return null;
  }

  @Override
  public URL fxmlUrl() {
    return null;
  }

  @Override
  public Node viewRoot() {
    return vbox;
  }
}
