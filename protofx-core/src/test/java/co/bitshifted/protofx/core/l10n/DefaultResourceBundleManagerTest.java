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

import co.bitshifted.protofx.core.prefs.StringPreferenceEntry;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultResourceBundleManagerTest {

  private ResourceBundleManager resourceBundleManager;
  private LocaleManager localeManager;

  @BeforeEach
  void setup() {
    localeManager = mock(LocaleManager.class);
    when(localeManager.localeFromString(anyString())).thenReturn(new Locale("en"));
    when(localeManager.getCurrentLocale())
        .thenReturn(new StringPreferenceEntry("root", CURRENT_LOCALE_PREFERENCE_NAME, "en"));
    resourceBundleManager = new DefaultResourceBundleManager(localeManager);
  }

  @Test
  void shouldLoadResourceBundle() {
    var out = resourceBundleManager.loadResourceBundle("test");
    assertNotNull(out);
    assertEquals("Hello", out.getResourceBundle().getString("greeting"));
  }
}
