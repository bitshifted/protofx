/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.ui.loader;

import java.util.function.Supplier;
import javafx.concurrent.Task;

/**
 * Convenience implementation of {@code Task}. It only requires the user to supply task code,
 * without the need to actually exyend the class
 *
 * @param <T> task return type
 */
public class DefaultLoaderTask<T> extends Task<T> {

  private Supplier<T> taskSupplier;

  /**
   * Creates new instance of the task. Parameter {@code taskSupplier} is a lambda that contains the
   * code to be executed in background thread. It should return the result of the task.
   *
   * @param taskSupplier supplier to be executed in background thread
   */
  public DefaultLoaderTask(Supplier<T> taskSupplier) {
    this.taskSupplier = taskSupplier;
  }

  @Override
  protected T call() throws Exception {
    return taskSupplier.get();
  }
}
