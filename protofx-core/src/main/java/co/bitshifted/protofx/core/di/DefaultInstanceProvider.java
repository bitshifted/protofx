/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.di;

import co.bitshifted.protofx.core.view.FxViewLoader;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultInstanceProvider<T extends Node> implements InstanceProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultInstanceProvider.class);

  private final Injector injector;
  private final FxViewLoader viewLoader;
  private final Map<String, T> cache;

  @Inject
  public DefaultInstanceProvider(Injector injector, FxViewLoader viewLoader) {
    this.injector = injector;
    this.viewLoader = viewLoader;
    this.cache = new HashMap<>();
  }

  @Override
  public <T> T getModelDataInstance(String name, Class<T> type) {
    return injector.getInstance(Key.get(type, Names.named(name)));
  }

  @Override
  public <T> T getViewInstance(Class<T> viewClass) {
    return injector.getInstance(viewClass);
  }

  @Override
  public T getViewRootNode(Class viewClass) {
    var className = viewClass.getName();
    if (cache.containsKey(className)) {
      LOGGER.debug("Returning cached view for class {}", className);
      return cache.get(className);
    }
    if (Node.class.isAssignableFrom(viewClass)) {
      var instance = (T) injector.getInstance(viewClass);
      cache.put(className, instance);
      LOGGER.debug("Adding view to cache for class {}", className);
      return instance;
    }
    var node = (T) viewLoader.loadView(injector.getInstance(viewClass));
    cache.put(className, node);
    LOGGER.debug("Adding view to cache for class {}", className);
    return node;
  }
}
