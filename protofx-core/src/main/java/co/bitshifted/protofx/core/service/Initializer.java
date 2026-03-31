/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.service;

import co.bitshifted.protofx.core.error.InitializationException;

/**
 * Defines class invoked at application startup to initialize required functionality. Client
 * application is responsible to define correct implementation for it's intended purposes.
 */
public interface Initializer {

  /**
   * Called to initialize application.
   *
   * @throws InitializationException if an error occurs
   */
  void initialize() throws InitializationException;
}
