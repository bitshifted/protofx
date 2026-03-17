/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.l10n;

import java.util.List;
import java.util.Locale;

/** Utility functions for working with locales. */
public final class LocaleUtils {

  private LocaleUtils() {}

  /**
   * Returns a string representing locale in human-friendly format, ie: {@code English (United
   * States)}. If the locale's country or variant is not included in the list of supported locales,
   * it will be omitted from the display string.
   *
   * @param locale locale to display
   * @param supported list of locales supported by application
   * @return string representing locale (eg. {@code English (UK)})
   */
  public static String localeDisplayString(Locale locale, List<Locale> supported) {
    var sb = new StringBuilder();
    sb.append(locale.getDisplayLanguage());
    var country = locale.getDisplayCountry();
    if (!country.isEmpty() && includeCountry(locale, supported)) {
      sb.append(" (").append(country).append(")");
    }
    var variant = locale.getDisplayVariant();
    if (!variant.isEmpty() && includeVariant(locale, supported)) {
      sb.append(" (").append(variant).append(")");
    }
    return sb.toString();
  }

  private static boolean includeCountry(Locale locale, List<Locale> supported) {
    return supported.stream()
        .anyMatch(
            loc ->
                loc.getLanguage().equals(locale.getLanguage())
                    && loc.getCountry().equals(locale.getCountry()));
  }

  private static boolean includeVariant(Locale locale, List<Locale> supported) {
    return supported.stream()
        .anyMatch(
            loc ->
                loc.getLanguage().equals(locale.getLanguage())
                    && loc.getCountry().equals(locale.getCountry())
                    && loc.getVariant().equals(locale.getVariant()));
  }
}
