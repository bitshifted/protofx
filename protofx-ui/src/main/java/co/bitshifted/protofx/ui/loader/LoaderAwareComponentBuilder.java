/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.ui.loader;

import co.bitshifted.protofx.core.l10n.ObservableResourceBundle;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;
import javafx.concurrent.Task;
import javafx.scene.Node;

/**
 * Builder for {@link LoaderAwareComponent}. This is convenience class to create new instance of
 * {@link LoaderAwareComponent}.
 *
 * @param <T> type of the background task
 */
public class LoaderAwareComponentBuilder<T> {

  private String loaderText = "Loading...";
  private ObservableResourceBundle resourceBundle;
  private String loaderTextKey;
  private ExecutorService executorService;
  private Task<T> task;
  private Node content;

  private Supplier<T> taskSupplier;

  private LoaderAwareComponentBuilder() {}

  public static LoaderAwareComponentBuilder builder() {
    return new LoaderAwareComponentBuilder();
  }

  public LoaderAwareComponentBuilder withExecutorService(ExecutorService executorService) {
    Objects.requireNonNull(executorService, "Executer service cannot be null");
    this.executorService = executorService;
    return this;
  }

  public LoaderAwareComponentBuilder withTask(Task<T> task) {
    Objects.requireNonNull(task, "Task cannot be null");
    this.task = task;
    return this;
  }

  public LoaderAwareComponentBuilder withResources(
      ObservableResourceBundle resourceBundle, String loaderTextKey) {
    Objects.requireNonNull(resourceBundle);
    Objects.requireNonNull(loaderTextKey);
    this.resourceBundle = resourceBundle;
    this.loaderTextKey = loaderTextKey;
    return this;
  }

  public LoaderAwareComponentBuilder withLoaderText(String loaderText) {
    this.loaderText = loaderText;
    return this;
  }

  public LoaderAwareComponentBuilder withContent(Node content) {
    Objects.requireNonNull(content);
    this.content = content;
    return this;
  }

  public LoaderAwareComponentBuilder withTaskSupplier(Supplier<T> taskSupplier) {
    Objects.requireNonNull(taskSupplier, "Task supplier cannot be null");
    this.taskSupplier = taskSupplier;
    return this;
  }

  public LoaderAwareComponent<T> build() {
    Objects.requireNonNull(executorService, "Executor service cannot be null");
    Objects.requireNonNull(content, "Content cannot be null");
    if (task == null) {
      Objects.requireNonNull(taskSupplier, "Task supplier cannot be null");
      task = new DefaultLoaderTask<T>(taskSupplier);
    }
    LoaderAwareComponent<T> component;
    if (resourceBundle != null && loaderTextKey != null) {
      component = new LoaderAwareComponent<T>(resourceBundle, loaderTextKey, executorService);
    } else {
      component = new LoaderAwareComponent<T>(loaderText, executorService);
    }
    component.setContent(content);
    component.setLoadingTask(task);
    return component;
  }
}
