/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class-level annotation that marks the class as a "view" (as in MVC "View"). This is equivalent to
 * a controller class set for FXML defined views.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FxView {

  /**
   * Name of the view. Should be unique within application.
   *
   * @return view name
   */
  String name();

  /**
   * Name of the resource bundle containing resources for the view (strings, images etc.)
   *
   * @return resource bundle name
   */
  String resourceBundle() default "";

  /**
   * URL of the FXML file holding view definition (optional).
   *
   * @return FXML URL
   */
  String fxmlUrl() default "";

  /**
   * Character set for the view text. Defaults to {@code UTF-8}.
   *
   * @return character encoding string
   */
  String charset() default "UTF-8";
}
