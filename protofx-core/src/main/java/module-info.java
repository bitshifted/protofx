/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
module co.bitshifted.protofx.core {
  exports co.bitshifted.protofx.core.annotations;
  exports co.bitshifted.protofx.core.di;
  exports co.bitshifted.protofx.core.error;
  exports co.bitshifted.protofx.core.eventbus;
  exports co.bitshifted.protofx.core.l10n;
  exports co.bitshifted.protofx.core.prefs;
  exports co.bitshifted.protofx.core.process;
  exports co.bitshifted.protofx.core.property;
  exports co.bitshifted.protofx.core.service;
  exports co.bitshifted.protofx.core.view;

  requires java.prefs;
  requires javafx.base;
  requires javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;
  requires jakarta.inject;
  requires com.google.guice;
  requires org.slf4j;
    requires org.junit.jupiter.api;
}
