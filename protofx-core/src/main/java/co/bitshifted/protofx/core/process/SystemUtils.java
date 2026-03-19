/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.process;

/** Utility methods used for system-related tasks. */
public final class SystemUtils {

  private SystemUtils() {}

  /**
   * Determines operating system the application tuns on.
   *
   * @return unrlying operating system
   */
  public static OperatingSystem operatingSystem() {
    var value = System.getProperty("os.name").toLowerCase();
    if (value.contains("win")) {
      return OperatingSystem.WINDOWS;
    } else if (value.contains("nix") || value.contains("nux") || value.contains("aix")) {
      return OperatingSystem.LINUX;
    } else if (value.contains("mac")) {
      return OperatingSystem.MAC;
    }
    throw new IllegalStateException("Unable to determine operating system from value: " + value);
  }
}
