/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.l10n;

import co.bitshifted.protofx.core.prefs.StringPreferenceEntry;
import java.util.List;
import java.util.Locale;

/** Defines methods for managing supported {@code Locale}s for the application. */
public interface LocaleManager {

  /** Preference name for the current user-selected locale. */
  String CURRENT_LOCALE_PREFERENCE_NAME = "current-locale";

  /**
   * Returns current user-selected locale for application.
   *
   * @return current locale preference
   */
  StringPreferenceEntry getCurrentLocale();

  /**
   * Returns a list of locales supported byt the application.
   *
   * @return list of supported locales
   */
  List<Locale> getSupportedLocales();

  /**
   * Returns {@code Locale} based onm supplied string representation.
   *
   * @param input string representing locale. Must conform to standard Java locale format.
   * @return {@code Locale} represented by string
   */
  Locale localeFromString(String input);
}
