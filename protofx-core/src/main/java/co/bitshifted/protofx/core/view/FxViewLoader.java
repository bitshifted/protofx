/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.view;

import co.bitshifted.protofx.core.error.ViewLoadException;
import javafx.scene.Node;

/** Loads views from FXML files. */
public interface FxViewLoader {

  /**
   * Loads view defined in {@code view} interface and returns JavaFX {@code Node} representing view
   * root.
   *
   * @param view view interface
   * @return root {@code Node} of the view
   * @throws ViewLoadException if an error occurs
   */
  Node loadView(FxViewAware view) throws ViewLoadException;

  /**
   * Loads view defined in {@code viewObject} class and returns JavaFX {@code Node} representing
   * view root. View can be defined using {@code @FxView} annotation or by implementing {@code
   * FxViewAware} interface.
   *
   * @param viewObject view object
   * @return root {@code Node} of the view
   * @throws ViewLoadException if an error occurs
   */
  Node loadView(Object viewObject) throws ViewLoadException;

  /**
   * Loads view defined in {@code viewObject} class and returns JavaFX {@code Node} representing
   * view root. Return type van be any subtype of {@code Node}.
   *
   * @param viewObject view object
   * @param viewType type of view root node
   * @return root {@code Node} of the view
   * @param <T> root node type
   * @throws ViewLoadException if an error occurs
   */
  <T extends Node> T loadView(Object viewObject, Class<T> viewType) throws ViewLoadException;
}
