/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.view;

import co.bitshifted.protofx.core.annotations.FxView;
import co.bitshifted.protofx.core.annotations.ViewRootNode;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

@FxView(name = "annotated-view", resourceBundle = "test")
public class SimpleAnnotatedView {

  @ViewRootNode private VBox root;

  public SimpleAnnotatedView() {
    this.root = new VBox();
    root.getChildren().add(new Label("test label"));
  }
}
