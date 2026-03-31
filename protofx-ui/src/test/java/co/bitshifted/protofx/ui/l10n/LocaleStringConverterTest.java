/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.ui.l10n;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import co.bitshifted.protofx.core.di.ApplicationConfig;
import co.bitshifted.protofx.core.l10n.DefaultLocaleManager;
import co.bitshifted.protofx.core.l10n.LocaleManager;
import co.bitshifted.protofx.core.prefs.PreferenceManager;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocaleStringConverterTest {

  private LocaleManager localeManager;

  @BeforeEach
  void setup() {
    localeManager = mock(LocaleManager.class);
  }

  @Test
  void shouldReturnCorrectSupportedLocale() {
    when(localeManager.getSupportedLocales())
        .thenReturn(List.of(new Locale("en", "US"), new Locale("fr", "FR")));
    var converter = new LocaleStringConverter(localeManager);
    var result = converter.toString(Locale.forLanguageTag("en-US"));
    assertEquals("English (United States)", result);

    result = converter.toString(Locale.CHINESE);
    assertEquals("Chinese", result);
  }

  @Test
  void shouldReturnCorrectLocaleForString() {
    var prefMngr = mock(PreferenceManager.class);
    var appConfig = mock(ApplicationConfig.class);
    var manager = new DefaultLocaleManager(prefMngr, appConfig);
    var converter = new LocaleStringConverter(manager);
    var result = converter.fromString("en_US");
    assertEquals(new Locale("en", "US"), result);
  }
}
