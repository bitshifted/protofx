/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.l10n;

import static co.bitshifted.protofx.core.l10n.LocaleManager.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import co.bitshifted.protofx.core.di.ApplicationConfig;
import co.bitshifted.protofx.core.prefs.PreferenceManager;
import co.bitshifted.protofx.core.prefs.StringPreferenceEntry;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultLocaleManagerTest {

  private LocaleManager localeManager;
  private ApplicationConfig applicationConfig;
  private PreferenceManager preferenceManager;

  @BeforeEach
  void setUp() {
    applicationConfig = mock(ApplicationConfig.class);
    preferenceManager = mock(PreferenceManager.class);
    localeManager = new DefaultLocaleManager(preferenceManager, applicationConfig);
  }

  @Test
  void shouldReturnDefaultLocaleAsCurrent() {
    var defaultLocale = Locale.getDefault();
    when(preferenceManager.getStringPreferenceEntry(
            eq(CURRENT_LOCALE_PREFERENCE_NAME), anyString()))
        .thenReturn(
            new StringPreferenceEntry(
                "root", CURRENT_LOCALE_PREFERENCE_NAME, defaultLocale.toString()));
    var localeString = localeManager.getCurrentLocale().getValue();
    assertEquals(defaultLocale.toString(), localeString);
  }

  @Test
  void shouldReturnSupportedLocales() {
    when(applicationConfig.supportedLocales())
        .thenReturn(List.of(new Locale("en"), new Locale("fr")));
    var supportedLocales = localeManager.getSupportedLocales();
    assertEquals(2, supportedLocales.size());
    assertTrue(supportedLocales.contains(new Locale("en")));
    assertTrue(supportedLocales.contains(new Locale("fr")));
  }

  @Test
  void shouldReturnCorrectLocaleFromString() {
    var langString = "en";
    var out = localeManager.localeFromString(langString);
    assertEquals(new Locale("en"), out);
    var langCountryString = "en_UK";
    out = localeManager.localeFromString(langCountryString);
    assertEquals(new Locale("en", "UK"), out);
    var langCountryVariantString = "en_UK_POSIX";
    out = localeManager.localeFromString(langCountryVariantString);
    assertEquals(new Locale("en", "UK", "POSIX"), out);
  }
}
