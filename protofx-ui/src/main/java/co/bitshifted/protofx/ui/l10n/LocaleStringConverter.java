/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.ui.l10n;

import co.bitshifted.protofx.core.l10n.LocaleManager;
import co.bitshifted.protofx.core.l10n.LocaleUtils;
import java.util.Locale;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocaleStringConverter extends StringConverter<Locale> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocaleStringConverter.class);

  private final LocaleManager localeManager;

  public LocaleStringConverter(LocaleManager localeManager) {
    this.localeManager = localeManager;
  }

  @Override
  public String toString(Locale locale) {
    LOGGER.debug("Converting locale {} to string", locale);
    return LocaleUtils.localeDisplayString(locale, localeManager.getSupportedLocales());
  }

  @Override
  public Locale fromString(String s) {
    LOGGER.debug("Converting string '{}' to locale", s);
    return localeManager.localeFromString(s);
  }
}
