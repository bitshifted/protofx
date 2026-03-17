/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.l10n;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;

public class LocaleUtilsTest {

  @Test
  void testLocaleDisplayString_languageOnly() {
    Locale locale = new Locale("en");
    List<Locale> supported = List.of(new Locale("en"));
    String expected = "English";
    assertEquals(expected, LocaleUtils.localeDisplayString(locale, supported));
  }

  @Test
  void testLocaleDisplayString_languageAndSupportedCountry() {
    Locale locale = new Locale("en", "US");
    List<Locale> supported = List.of(new Locale("en", "US"));
    String expected = "English (United States)";
    assertEquals(expected, LocaleUtils.localeDisplayString(locale, supported));
  }

  @Test
  void testLocaleDisplayString_languageAndUnsupportedCountry() {
    Locale locale = new Locale("en", "US");
    List<Locale> supported = List.of(new Locale("en", "GB")); // Supported only GB
    String expected = "English"; // Country should be omitted
    assertEquals(expected, LocaleUtils.localeDisplayString(locale, supported));
  }

  @Test
  void testLocaleDisplayString_languageCountryAndSupportedVariant() {
    Locale locale = new Locale("en", "US", "POSIX");
    List<Locale> supported = List.of(new Locale("en", "US", "POSIX"));
    String expected = "English (United States) (Computer)";
    assertEquals(expected, LocaleUtils.localeDisplayString(locale, supported));
  }

  @Test
  void testLocaleDisplayString_languageCountryAndUnsupportedVariant() {
    Locale locale = new Locale("en", "US", "POSIX");
    List<Locale> supported = List.of(new Locale("en", "US")); // Supported only language and country
    String expected = "English (United States)"; // Variant should be omitted
    assertEquals(expected, LocaleUtils.localeDisplayString(locale, supported));
  }

  @Test
  void testLocaleDisplayString_emptySupportedList() {
    Locale locale = new Locale("en", "US");
    List<Locale> supported = List.of();
    String expected = "English"; // Country and variant should be omitted
    assertEquals(expected, LocaleUtils.localeDisplayString(locale, supported));
  }

  @Test
  void testLocaleDisplayString_nullSupportedList() {
    Locale locale = new Locale("en", "US");
    // Expect NullPointerException because the stream will be called on a null list
    assertThrows(NullPointerException.class, () -> LocaleUtils.localeDisplayString(locale, null));
  }

  @Test
  void testLocaleDisplayString_rootLocale() {
    Locale locale = Locale.ROOT;
    List<Locale> supported = List.of(Locale.ROOT);
    String expected = ""; // Root locale has empty display language, country, variant
    assertEquals(expected, LocaleUtils.localeDisplayString(locale, supported));
  }

  @Test
  void testLocaleDisplayString_differentLanguageSameCountry() {
    Locale locale = new Locale("en", "US");
    List<Locale> supported = List.of(new Locale("es", "US")); // Different language, same country
    String expected = "English"; // Country should be omitted because language doesn't match
    assertEquals(expected, LocaleUtils.localeDisplayString(locale, supported));
  }

  @Test
  void testLocaleDisplayString_multipleSupportedLocales() {
    Locale locale = new Locale("en", "US");
    List<Locale> supported =
        List.of(new Locale("fr"), new Locale("en", "US"), new Locale("de", "DE"));
    String expected = "English (United States)";
    assertEquals(expected, LocaleUtils.localeDisplayString(locale, supported));
  }
}
