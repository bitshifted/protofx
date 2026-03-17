/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.l10n;

import co.bitshifted.protofx.core.prefs.StringPreferenceEntry;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultResourceBundleManager implements ResourceBundleManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultResourceBundleManager.class);

  private final LocaleManager localeManager;
  private final Map<String, ObservableResourceBundle> cache;
  private final StringPreferenceEntry localePreference;

  @Inject
  public DefaultResourceBundleManager(LocaleManager localeManager) {
    this.localeManager = localeManager;
    this.cache = new HashMap<>();
    this.localePreference = localeManager.getCurrentLocale();
    localePreference
        .getProperty()
        .addListener(
            (observableValue, s, t1) -> {
              var newLocale = localeManager.localeFromString(t1);
              LOGGER.trace("Locale changed to {}", newLocale);
              cache
                  .entrySet()
                  .forEach(
                      entry -> {
                        var newBundle = ResourceBundle.getBundle(entry.getKey(), newLocale);
                        entry.getValue().setResourceBundle(newBundle);
                      });
            });
  }

  @Override
  public ObservableResourceBundle loadResourceBundle(String name) {
    var localeString = localePreference.getValue();
    var locale = localeManager.localeFromString(localeString);
    var bundle = ResourceBundle.getBundle(name, locale);
    var observableBundle = cache.get(name);
    if (observableBundle != null) {
      observableBundle.setResourceBundle(bundle);
    } else {
      observableBundle = new ObservableResourceBundle(bundle);
    }
    cache.put(name, observableBundle);
    return observableBundle;
  }
}
