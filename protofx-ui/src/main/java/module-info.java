/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
module co.bitshifted.protofx.ui {
  exports co.bitshifted.protofx.ui.di;
  exports co.bitshifted.protofx.ui.l10n;

    requires co.bitshifted.protofx.core;
  requires java.base;
  requires javafx.controls;
  requires jakarta.inject;
  requires com.google.guice;
  requires org.slf4j;
}
u