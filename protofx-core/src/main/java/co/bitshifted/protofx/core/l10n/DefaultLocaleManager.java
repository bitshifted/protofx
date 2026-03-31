/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.l10n;

import co.bitshifted.protofx.core.di.ApplicationConfig;
import co.bitshifted.protofx.core.prefs.PreferenceManager;
import co.bitshifted.protofx.core.prefs.StringPreferenceEntry;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of {@link LocaleManager}. It uses {@link ApplicationConfig} to manage the
 * list of supported locales and {@link PreferenceManager} to manage user's locale preferences.
 */
public class DefaultLocaleManager implements LocaleManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLocaleManager.class);

  private final PreferenceManager preferenceManager;
  private final ApplicationConfig applicationConfig;

  @Inject
  public DefaultLocaleManager(PreferenceManager preferenceManager, ApplicationConfig appConfig) {
    this.preferenceManager = preferenceManager;
    this.applicationConfig = appConfig;
  }

  @Override
  public StringPreferenceEntry getCurrentLocale() {
    var defaultLocale = Locale.getDefault();
    var sb = new StringBuilder(defaultLocale.getLanguage());
    if (!defaultLocale.getCountry().isEmpty()) {
      sb.append("_").append(defaultLocale.getCountry());
    }
    if (!defaultLocale.getVariant().isEmpty()) {
      sb.append("_").append(defaultLocale.getVariant());
    }
    var localeString = sb.toString();
    LOGGER.debug("Default locale string: {}", localeString);
    return preferenceManager.getStringPreferenceEntry(CURRENT_LOCALE_PREFERENCE_NAME, localeString);
  }

  @Override
  public List<Locale> getSupportedLocales() {
    return applicationConfig.supportedLocales();
  }

  @Override
  public Locale localeFromString(String input) {
    var parts = input.split("_");
    return switch (parts.length) {
        case 1 -> new Locale(parts[0]);
        case 2 -> new Locale(parts[0], parts[1]);
        case 3 -> new Locale(parts[0], parts[1], parts[2]);
        default ->  throw new IllegalArgumentException("Invalid locale string: " + input);
    };
  }
}
