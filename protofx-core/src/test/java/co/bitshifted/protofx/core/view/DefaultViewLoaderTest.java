/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.view;

import static org.junit.jupiter.api.Assertions.*;

import co.bitshifted.protofx.core.l10n.ResourceBundleManager;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class DefaultViewLoaderTest {

  private FxViewLoader viewLoader;
  private ResourceBundleManager resourceBundleManager;

  @BeforeEach
  public void setup() {
    resourceBundleManager = Mockito.mock(ResourceBundleManager.class);
    viewLoader = new DefaultFxViewLoader(resourceBundleManager);
  }

  @Test
  void shouldLoadViewAwareTestView() {
    var view = new SimpleTestView();
    var node = viewLoader.loadView(view);
    assertNotNull(node);
    assertTrue(node instanceof VBox);
  }
}
