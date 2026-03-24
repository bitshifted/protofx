/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.view;

import static javafx.application.Platform.runLater;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import co.bitshifted.protofx.core.l10n.ObservableResourceBundle;
import co.bitshifted.protofx.core.l10n.ResourceBundleManager;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;

class DialogBuilderTest {

  // Initializes JavaFX Toolkit
  @BeforeAll
  static void initToolkit() throws Exception {
    FxToolkit.registerPrimaryStage();
  }

  @Test
  void withTitle_shouldSetDialogTitle() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    runLater(
        () -> {
          try {
            String title = "Test Title";
            Dialog<Void> dialog = DialogBuilder.newBuilder().withTitle(title).build();
            assertEquals(title, dialog.getTitle());
          } finally {
            latch.countDown();
          }
        });
    latch.await();
  }

  @Test
  void withContent_shouldSetDialogContent() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    runLater(
        () -> {
          try {
            Label content = new Label("Test Content");
            Dialog<Void> dialog = DialogBuilder.newBuilder().withContent(content).build();
            assertEquals(content, dialog.getDialogPane().getContent());
          } finally {
            latch.countDown();
          }
        });
    latch.await();
  }

  @Test
  void withButtonTypes_shouldAddButtonsToDialog() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    runLater(
        () -> {
          try {
            Dialog<Void> dialog =
                DialogBuilder.newBuilder()
                    .withButtonTypes(ButtonType.OK, ButtonType.CANCEL)
                    .build();
            assertEquals(2, dialog.getDialogPane().getButtonTypes().size());
            assertTrue(dialog.getDialogPane().getButtonTypes().contains(ButtonType.OK));
            assertTrue(dialog.getDialogPane().getButtonTypes().contains(ButtonType.CANCEL));
          } finally {
            latch.countDown();
          }
        });
    latch.await();
  }

  @Test
  void withResultConverter_shouldSetConverter() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    runLater(
        () -> {
          try {
            Dialog<String> dialog =
                DialogBuilder.newBuilder(String.class)
                    .withButtonTypes(ButtonType.OK)
                    .withResultConverter(
                        buttonType -> {
                          if (buttonType == ButtonType.OK) {
                            return "OK";
                          }
                          return null;
                        })
                    .build();

            String result = dialog.getResultConverter().call(ButtonType.OK);
            assertEquals("OK", result);
          } finally {
            latch.countDown();
          }
        });
    latch.await();
  }

  @Test
  void withResourceBundle_shouldSetTitleFromKey() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    runLater(
        () -> {
          try {
            // Given
            String bundleName = "test.bundle";
            String titleKey = "dialog.title";
            String expectedTitle = "Test Dialog Title";

            ResourceBundle resourceBundle =
                new ListResourceBundle() {
                  @Override
                  protected Object[][] getContents() {
                    return new Object[][] {{titleKey, expectedTitle}};
                  }
                };

            ObservableResourceBundle observableResourceBundle =
                new ObservableResourceBundle(resourceBundle);
            ResourceBundleManager mockManager = mock(ResourceBundleManager.class);
            when(mockManager.loadResourceBundle(bundleName)).thenReturn(observableResourceBundle);

            // When
            Dialog<Void> dialog =
                DialogBuilder.newBuilder()
                    .withResourceBundleManager(mockManager)
                    .withResourceBundleName(bundleName)
                    .withTitleKey(titleKey)
                    .build();

            // Then
            assertEquals(expectedTitle, dialog.getTitle());
          } finally {
            latch.countDown();
          }
        });
    latch.await();
  }

  @Test
  void withButtonTypeActionHandler_shouldAddActionHandlerToButton() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    final AtomicBoolean handlerCalled = new AtomicBoolean(false);
    runLater(
        () -> {
          try {
            // Given
            ButtonType testButton = new ButtonType("Test");

            Dialog<Void> dialog =
                DialogBuilder.newBuilder()
                    .withButtonTypes(testButton)
                    .withButtonTypeActionHandler(
                        testButton,
                        event -> {
                          handlerCalled.set(true);
                        })
                    .build();

            // When
            // This simulates the button being clicked
            dialog.getDialogPane().lookupButton(testButton).fireEvent(new ActionEvent());

            // Then
            assertTrue(
                handlerCalled.get(),
                "Action handler should have been called inside Platform.runLater");
          } finally {
            latch.countDown();
          }
        });
    latch.await();
    assertTrue(handlerCalled.get(), "Action handler should have been called after waiting");
  }
}
