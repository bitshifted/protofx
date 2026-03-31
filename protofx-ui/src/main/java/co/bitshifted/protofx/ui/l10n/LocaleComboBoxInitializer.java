/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.ui.l10n;

import co.bitshifted.protofx.core.l10n.LocaleManager;
import jakarta.inject.Inject;
import java.util.Locale;
import java.util.function.Consumer;
import javafx.scene.control.ComboBox;

public class LocaleComboBoxInitializer implements Consumer<ComboBox<Locale>> {

  private final LocaleManager localeManager;

  @Inject
  public LocaleComboBoxInitializer(LocaleManager localeManager) {
    this.localeManager = localeManager;
  }

  @Override
  public void accept(ComboBox<Locale> localeComboBox) {
    localeComboBox.setButtonCell(new LocaleListCell(localeManager));
    localeComboBox.setCellFactory(localeListView -> new LocaleListCell(localeManager));
    localeComboBox.setConverter(new LocaleStringConverter(localeManager));
    var currentLocale = localeManager.getCurrentLocale();
    var supportedLocales = localeManager.getSupportedLocales();
    localeComboBox.getItems().addAll(supportedLocales);
    localeComboBox.setValue(localeManager.localeFromString(currentLocale.getValue()));
    localeComboBox
        .valueProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              currentLocale.save(newValue.toString());
            });
  }
}
