/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.annotations;

import java.lang.annotation.*;

/**
 * Marks the root element of the view. Used when view is defined directly in code, without using
 * FXML. Example:
 *
 * <pre>
 *     <code>
 *         class CustomView {
 *
 *              @ViewRootNode
 *             private VBox rootNode;
 *
 *             public CustomView() {
 *                 rootNode = new VBox();
 *                 rooteNode.getChildren().add(new Label("Hello World!"));
 *             }
 *         }
 *     </code>
 * </pre>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ViewRootNode {}
