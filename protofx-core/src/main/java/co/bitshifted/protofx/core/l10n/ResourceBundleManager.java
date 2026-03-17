/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.l10n;

/** Defines methods for managing resource bundles. */
public interface ResourceBundleManager {

  /**
   * Loads resource bundle based on a name.
   *
   * @param name resource bundle name
   * @return observable resource bundle
   */
  ObservableResourceBundle loadResourceBundle(String name);
}
