/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.error;

public class ProcessExecutionException extends Exception {

  public ProcessExecutionException(Throwable cause) {
    super(cause);
  }

  public ProcessExecutionException(String message) {
    super(message);
  }
}
