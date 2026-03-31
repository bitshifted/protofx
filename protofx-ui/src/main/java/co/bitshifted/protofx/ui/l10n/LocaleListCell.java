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
import javafx.scene.control.ListCell;

public class LocaleListCell extends ListCell<Locale> {

  private final LocaleManager localeManager;

  public LocaleListCell(LocaleManager localeManager) {
    this.localeManager = localeManager;
  }

  @Override
  protected void updateItem(Locale locale, boolean empty) {
    super.updateItem(locale, empty);
    if (locale != null && !empty) {
      setText(LocaleUtils.localeDisplayString(locale, localeManager.getSupportedLocales()));
    } else {
      setText(null);
    }
  }
}
