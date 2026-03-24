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
import java.util.concurrent.Executors;

/**
 * Default implementation of {@link ApplicationConfig}. It provided sensible default configuration,
 * and is intended to be overriden by application.
 */
public class DefaultApplicationConfig implements ApplicationConfig {

  public static final String DEFAULT_LOCALE = "en";

  @Override
  public String defaultPreferenceRootNode() {
    return this.getClass().getPackageName();
  }

  @Override
  public String preferredLocale() {
    return DEFAULT_LOCALE;
  }

  @Override
  public List<Locale> supportedLocales() {
    return List.of(Locale.ENGLISH);
  }

  @Override
  public boolean eventBusEnabled() {
    return false;
  }

  @Override
  public boolean executorServiceEnabled() {
    return false;
  }

  @Override
  public ExecutorService executorService() {
    return Executors.newWorkStealingPool();
  }
}
