/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.di;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

public interface ApplicationConfig {

    String defaultPreferenceRootNode();
    String preferredLocale();
    List<Locale> supportedLocales();
    boolean eventBusEnabled();
    boolean executorServiceEnabled();
    ExecutorService executorService();
}
