/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.view;

import static org.junit.jupiter.api.Assertions.*;

import co.bitshifted.protofx.core.l10n.ObservableResourceBundle;
import co.bitshifted.protofx.core.l10n.ResourceBundleManager;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

  @Test
  void shouldLoadAnnotatedView() {
    var view = new SimpleAnnotatedView();
    var node = viewLoader.loadView(view);
    assertNotNull(node);
    assertTrue(node instanceof VBox);
  }

  @Test
  void shouldLoadFxmlView() {
    Mockito.when(resourceBundleManager.loadResourceBundle("test"))
        .thenReturn(new ObservableResourceBundle(ResourceBundle.getBundle("test")));
    var view = new SimpleFxmlView();
    var node = viewLoader.loadView(view);
    assertNotNull(node);
    assertTrue(node instanceof HBox);
    var label = (Label) ((HBox) node).getChildren().get(0);
    assertEquals("Hello", label.getText());
  }
}
