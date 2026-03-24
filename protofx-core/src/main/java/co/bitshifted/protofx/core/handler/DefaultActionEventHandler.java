/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.handler;

import java.util.Optional;
import javafx.beans.binding.Binding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCombination;

/**
 * Implementation of JavaFX {@code EventHandler<ActionEvent>} that provides a disabled property,
 * graphics and accelerator key. The disabled property can be bound to a JavaFX {@code Binding} to
 * control when the action should be disabled. The graphics can be used to provide an icon for the
 * action, and the accelerator key can be used to provide a keyboard shortcut for the action.
 */
public abstract class DefaultActionEventHandler implements EventHandler<ActionEvent> {

  private final SimpleBooleanProperty disabledProperty;
  private final Node graphics;
  private final KeyCombination acceleratorKey;

  /** Creates new handler instance with no binding, graphics or accelerator. */
  protected DefaultActionEventHandler() {
    this(null, null, null);
  }

  /**
   * Creates new handler instance with specified disabled binding.
   *
   * @param disabledBinding disable property binding
   */
  protected DefaultActionEventHandler(Binding disabledBinding) {
    this(disabledBinding, null, null);
  }

  /**
   * Creates new handler instance with specified graphics as icon.
   *
   * @param graphics icon graphics
   */
  protected DefaultActionEventHandler(Node graphics) {
    this(null, graphics, null);
  }

  /**
   * Creates new handler with specified accelerator key.
   *
   * @param acceleratorKey accelerator key
   */
  protected DefaultActionEventHandler(KeyCombination acceleratorKey) {
    this(null, null, acceleratorKey);
  }

  /**
   * Creates new instance with specified properties.
   *
   * @param disabledBinding disable property binding
   * @param graphics icon graphics
   * @param acceleratorKey keyboard accelerator
   */
  protected DefaultActionEventHandler(
      Binding disabledBinding, Node graphics, KeyCombination acceleratorKey) {
    this.disabledProperty = new SimpleBooleanProperty();
    this.graphics = graphics;
    this.acceleratorKey = acceleratorKey;
    calculateDisabledProperty(Optional.ofNullable(disabledBinding));
  }

  private void calculateDisabledProperty(Optional<Binding> binding) {
    binding.ifPresent(b -> disabledProperty.bind(b));
  }

  /**
   * Returns icon graphics
   *
   * @return
   */
  public Node getGraphics() {
    return graphics;
  }

  public BooleanProperty disabledProperty() {
    return disabledProperty;
  }

  public KeyCombination getAcceleratorKey() {
    return acceleratorKey;
  }
}
