/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.ui.loader;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutorService;
import javafx.scene.control.Label;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class LoaderAwareComponentTest {

  @Mock private ExecutorService executorService;

  @Test
  void shouldCreateCorrectLoaderComponent() {
    var component = new LoaderAwareComponent("Loader", executorService);
    var content = new Label("Content");
    component.setContent(content);
    var children = component.getChildren();
    assertEquals(2, children.size());
    assertTrue(children.get(0) instanceof LoaderView);
    assertTrue(children.get(1) instanceof Label);
  }
}
