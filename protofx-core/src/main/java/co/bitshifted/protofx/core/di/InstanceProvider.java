/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.di;

import javafx.scene.Node;

/** Provides managed instances present in dependency injection */
public interface InstanceProvider {

  /**
   * Returns an instance based on it's name and class.
   *
   * @param name name of the instance (similar to using {@code Named} annotation)
   * @param type type of the instance
   * @return instance associated with name and type. If not found, throws exception
   * @param <T> expected type of the instance
   */
  <T> T getModelDataInstance(String name, Class<T> type);

  <T> T getViewInstance(Class<T> viewClass);

  <T extends Node> T getViewRootNode(Class viewClass);
}
