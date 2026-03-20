/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.view;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import javafx.scene.Node;

public interface FxViewAware {

  String viewName();

  ResourceBundle resourceBundle();

  URL fxmlUrl();

  Node viewRoot();

  default Charset charset() {
    return StandardCharsets.UTF_8;
  }
}
