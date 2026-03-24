/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.di;

import com.google.inject.Guice;
import com.google.inject.Key;
import com.google.inject.name.Names;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestApp {

  @Test
  void shouldCreateGuiceBindings() {
    var injector = Guice.createInjector(new TestModule(new DefaultApplicationConfig()));
    var modelData = injector.getInstance(Key.get(SimpleStringProperty.class, Names.named("text")));
    Assertions.assertNotNull(modelData);
  }
}
