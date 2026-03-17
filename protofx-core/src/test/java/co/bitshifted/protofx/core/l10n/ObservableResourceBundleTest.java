/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.l10n;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObservableResourceBundleTest {

  private ObservableResourceBundle observableResourceBundle;
  private ResourceBundle resourceBundleEn;
  private ResourceBundle resourceBundleFr;

  @BeforeEach
  void setUp() {
    resourceBundleEn = ResourceBundle.getBundle("test", new Locale("en"));
    resourceBundleFr = ResourceBundle.getBundle("test", new Locale("fr"));
    observableResourceBundle = new ObservableResourceBundle(resourceBundleEn);
  }

  @Test
  void testGetStringBinding_localeChange() {
    String key = "greeting";
    var binding = observableResourceBundle.getStringBinding(key);

    // Initial value
    assertEquals("Hello", binding.get());

    // Change locale
    observableResourceBundle.setResourceBundle(resourceBundleFr);

    // Value should be updated
    assertEquals("Bonjour", binding.get());
  }
}
