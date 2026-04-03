/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.ui.loader;

import co.bitshifted.protofx.core.l10n.ObservableResourceBundle;
import java.util.concurrent.ExecutorService;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component used to implement progress indicator for another component being loaded or set up. It
 * consists of 2 components:
 *
 * <ol>
 *   <li>{@link LoaderView} which is displayed on top, and provides visual feedback on loading
 *   <li>actual content component which is kept in background and made visible once loading is
 *       finished
 * </ol>
 *
 * Loading logic runs in background thread. When it completes, top-level {@link LoaderView} is
 * removed and {@code content} component is made visible. In case the loading fails, an error
 * message is displayed in {@link LoaderView},a nd {@code content} is not shown
 */
public class LoaderAwareComponent<T> extends StackPane {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoaderAwareComponent.class);

  private final ExecutorService executorService;
  private final LoaderView loaderView;

  private Task<T> loadingTask;

  public LoaderAwareComponent(
      ObservableResourceBundle resourceBundle,
      String loaderTextKey,
      ExecutorService executorService) {
    this.loaderView = new LoaderView(resourceBundle, loaderTextKey);
    this.executorService = executorService;
    setAlignment(Pos.CENTER);
    getChildren().addAll(loaderView);
  }

  public LoaderAwareComponent(String loaderText, ExecutorService executorService) {
    this.loaderView = new LoaderView(loaderText);
    this.executorService = executorService;
    setAlignment(Pos.CENTER);
    getChildren().addAll(loaderView);
  }

  public void setContent(Node content) {
    getChildren().add(content);
  }

  public void setLoadingTask(Task<T> loadingTask) {
    this.loadingTask = loadingTask;
    this.loadingTask.setOnSucceeded(
        e -> {
          getChildren().remove(loaderView);
        });
    this.loadingTask.setOnFailed(
        _ -> LOGGER.error("Loading task failed", loadingTask.getException()));
  }

  public Task<T> getLoadingTask() {
    return loadingTask;
  }

  public void startTask() {
    executorService.submit(loadingTask);
  }
}
