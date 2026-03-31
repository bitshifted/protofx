/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.ui.di;

import co.bitshifted.protofx.ui.l10n.LocaleComboBoxInitializer;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import java.util.Locale;
import java.util.function.Consumer;
import javafx.scene.control.ComboBox;

public class ProtoFXUIGuiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(new TypeLiteral<Consumer<ComboBox<Locale>>>() {})
        .annotatedWith(Names.named(NamedValues.LOCALE_COMBO_INITIALIZER))
        .to(LocaleComboBoxInitializer.class);
  }
}
