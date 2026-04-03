/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.ui.loader;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import javafx.scene.control.TextField;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class LoaderAwareComponentIntegrationTest {

  private ExecutorService executorService;

  @BeforeEach
  void setup() {
    executorService =
        new ThreadPoolExecutor(
            1,
            1,
            0L,
            java.util.concurrent.TimeUnit.MILLISECONDS,
            new java.util.concurrent.LinkedBlockingQueue<>());
  }

  @Test
  void shouldShowLoaderAwareComponent() throws Exception {
    var task = new LongRunningTask();
    var content = new TextField("test");
    var component =
        LoaderAwareComponentBuilder.builder()
            .withExecutorService(executorService)
            .withTask(task)
            .withContent(content)
            .build();
    // verify that loader view is on top
    assertTrue(component.getChildren().get(0) instanceof LoaderView);
    component.startTask();
    Awaitility.await().atMost(Duration.ofSeconds(10)).until(task::isDone);
    assertEquals(1, component.getChildren().size());
    assertTrue(component.getChildren().get(0) instanceof TextField);
  }

  @Test
  void shouldShowLoaderAwareComponentWithtaskSupplier() throws Exception {
    var content = new TextField("test");
    var component =
        LoaderAwareComponentBuilder.builder()
            .withExecutorService(executorService)
            .withContent(content)
            .withTaskSupplier(
                () -> {
                  try {
                    Thread.sleep(5000);
                    return "test";
                  } catch (Exception ex) {
                    throw new RuntimeException(ex);
                  }
                })
            .build();
    // verify that loader view is on top
    assertTrue(component.getChildren().get(0) instanceof LoaderView);
    component.startTask();
    Awaitility.await().atMost(Duration.ofSeconds(10)).until(component.getLoadingTask()::isDone);
    assertEquals(1, component.getChildren().size());
    assertTrue(component.getChildren().get(0) instanceof TextField);
  }
}
