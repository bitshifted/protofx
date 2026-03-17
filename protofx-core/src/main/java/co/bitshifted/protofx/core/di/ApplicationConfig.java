/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.di;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

/** Defines methods used for application configuration. */
public interface ApplicationConfig {

  /**
   * Returns the name of default root node for preferences. This node will be used if method in
   * {@link co.bitshifted.protofx.core.prefs.PreferenceManager} does not take root node name. <code>
   *     // assumes that preference "my-pref" is under default root noe
   *     var node = manager.getIntegerPreference("my-pref", 1);
   *     // root node is specified explicitely
   *     var expNode = manager.getIntegerPreference("root-node", "my-pref", 1);
   * </code>
   *
   * @return name of the default root node for preferences
   */
  String defaultPreferenceRootNode();

  /**
   * Returns preferred locale for the application.
   *
   * @return preferred locale in standard Java locale format
   */
  String preferredLocale();

  /**
   * Returns list of locales supported by application
   *
   * @return list of supported locales
   */
  List<Locale> supportedLocales();

  /**
   * Whether to enable built-in event bus or not
   *
   * @return {@code true} if event bus is enabled, {@code false}
   */
  boolean eventBusEnabled();

  /**
   * Whether to enable built-in executor service
   *
   * @return {@code true} if executor service is enabled, {@code false}
   */
  boolean executorServiceEnabled();

  /**
   * Returns custom defined executor service.
   *
   * @return executor service
   */
  ExecutorService executorService();
}
