/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.ui.loader;

import co.bitshifted.protofx.core.l10n.ObservableResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;

/**
 * View shown during long-running tasks. It contains indeterminate spinner and text label. Text on
 * label can be customizable.
 */
public class LoaderView extends VBox {

  private static final String DEFAULT_STYLE = "-fx-font-size: 2.0em;-fx-font-weight: bold;";

  private final String labelStyle;

  /**
   * Creates new {@code LoaderView} with text label fetched from specified resource bundle and with
   * specified property key. Use this constructor when client app is internationalized, or strings
   * are stored in resource bundle.
   *
   * @param resourceBundle resource bundle containing text property
   * @param loaderTextKey property key for loader label text
   */
  public LoaderView(ObservableResourceBundle resourceBundle, String loaderTextKey) {
    super();
    this.labelStyle = DEFAULT_STYLE;
    setAlignment(Pos.CENTER);
    var loader = new ProgressIndicator();
    loader.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    var label = new Label();
    label.textProperty().bind(resourceBundle.getStringBinding(loaderTextKey));
    label.setStyle(labelStyle);
    getChildren().addAll(loader, label);
  }

  /**
   * Creates new {@code LoaderView} with specified text for a label. Use this constructor when raw
   * string is to be applied to a label, not using resource bundles.
   *
   * @param loaderText label text
   */
  public LoaderView(String loaderText) {
    super();
    this.labelStyle = DEFAULT_STYLE;
    setAlignment(Pos.CENTER);
    var loader = new ProgressIndicator();
    loader.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    var label = new Label();
    label.setText(loaderText);
    label.setStyle(labelStyle);
    getChildren().addAll(loader, label);
  }
}
