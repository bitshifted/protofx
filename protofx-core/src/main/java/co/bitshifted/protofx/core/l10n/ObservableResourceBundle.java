/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.l10n;

import java.util.ResourceBundle;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Observable resource bundle is a wrapper around standard Java resource bundle, but with the
 * ability to react to change. This allows applications to immediately update application UI when
 * locale changes.
 */
public class ObservableResourceBundle {

  private final SimpleObjectProperty<ResourceBundle> resourceBundleProperty;

  /**
   * Creates new instance of observable resource bundle based on specified bundle.
   *
   * @param source underlying respource bundle
   */
  public ObservableResourceBundle(ResourceBundle source) {
    resourceBundleProperty = new SimpleObjectProperty<>(source);
  }

  /**
   * Returns underlying resource bundle
   *
   * @return resource bundle
   */
  public ResourceBundle getResourceBundle() {
    return resourceBundleProperty.getValue();
  }

  /**
   * Sets new underlying resource bundle..
   *
   * @param bundle resource bundle to set
   */
  public void setResourceBundle(ResourceBundle bundle) {
    resourceBundleProperty.set(bundle);
  }

  /**
   * Returns string binding for the specified resource key.
   *
   * @param key resource key
   * @return string binding
   */
  public StringBinding getStringBinding(String key) {
    return new StringBinding() {
      {
        bind(resourceBundleProperty);
      }

      @Override
      protected String computeValue() {
        return getResourceBundle().getString(key);
      }
    };
  }
}
