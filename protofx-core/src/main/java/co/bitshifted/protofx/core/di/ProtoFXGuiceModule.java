/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.di;

import co.bitshifted.protofx.core.annotations.EventBusSubscriptionHandler;
import co.bitshifted.protofx.core.annotations.ModelData;
import co.bitshifted.protofx.core.error.ConfigurationException;
import co.bitshifted.protofx.core.error.ModelLoadException;
import co.bitshifted.protofx.core.eventbus.EventBus;
import co.bitshifted.protofx.core.eventbus.internal.DefaultEventBus;
import co.bitshifted.protofx.core.l10n.DefaultLocaleManager;
import co.bitshifted.protofx.core.l10n.DefaultResourceBundleManager;
import co.bitshifted.protofx.core.l10n.LocaleManager;
import co.bitshifted.protofx.core.l10n.ResourceBundleManager;
import co.bitshifted.protofx.core.prefs.DefaultPreferenceManager;
import co.bitshifted.protofx.core.prefs.PreferenceManager;
import co.bitshifted.protofx.core.process.DefaultProcessExecutor;
import co.bitshifted.protofx.core.process.ProcessExecutor;
import co.bitshifted.protofx.core.view.DefaultFxViewLoader;
import co.bitshifted.protofx.core.view.FxViewLoader;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtoFXGuiceModule extends AbstractModule {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProtoFXGuiceModule.class);

  private final ApplicationConfig applicationConfig;

  protected ProtoFXGuiceModule(ApplicationConfig applicationConfig) {
    this.applicationConfig = applicationConfig;
  }

  @Override
  protected void configure() {
    bind(ApplicationConfig.class).toInstance(applicationConfig);
    bind(PreferenceManager.class).to(DefaultPreferenceManager.class).in(Scopes.SINGLETON);
    bind(FxViewLoader.class).to(DefaultFxViewLoader.class).in(Scopes.SINGLETON);
    bind(LocaleManager.class).to(DefaultLocaleManager.class).in(Scopes.SINGLETON);
    bind(ResourceBundleManager.class).to(DefaultResourceBundleManager.class).in(Scopes.SINGLETON);
    bind(InstanceProvider.class).to(DefaultInstanceProvider.class).in(Scopes.SINGLETON);
    if (applicationConfig.eventBusEnabled()) {
      bind(EventBus.class).to(DefaultEventBus.class).in(Scopes.SINGLETON);
    }
    if (applicationConfig.executorServiceEnabled()) {
      bind(ProcessExecutor.class).to(DefaultProcessExecutor.class).in(Scopes.SINGLETON);
      LOGGER.debug("Binding ExecutorService to instance provided by application config");
      bind(ExecutorService.class).toInstance(applicationConfig.executorService());
    }
    // bind model
    models().forEach(m -> bindModelData(m));
    // bind even bus handlers
    if (applicationConfig.eventBusEnabled()) {
      bindEventBusSubscriptionHandlers();
    }

    // register any custom bindings
    customBindings();
  }

  protected List<Object> models() {
    return List.of();
  }

  protected List<Class> eventBusSubscriptionHandlers() {
    return List.of();
  }

  protected void customBindings() {}

  public ApplicationConfig applicationConfig() {
    return applicationConfig;
  }

  private void bindModelData(Object model) {
    var fields = model.getClass().getDeclaredFields();
    for (Field f : fields) {
      f.setAccessible(true);
      var annotation = f.getAnnotation(ModelData.class);
      if (annotation == null) {
        continue;
      }
      var name = annotation.name();
      var fieldType = f.getType();
      try {
        var fieldValue = f.get(model);
        bind((Class<Object>) fieldType).annotatedWith(Names.named(name)).toInstance(fieldValue);
        LOGGER.debug("Bound model data with name {} and type {}", name, fieldType.getName());
      } catch (Throwable th) {
        throw new ModelLoadException(th);
      }
    }
  }

  private void bindEventBusSubscriptionHandlers() {
    var handlers = eventBusSubscriptionHandlers();
    handlers.forEach(
        h -> {
          var annotation =
              (EventBusSubscriptionHandler) h.getAnnotation(EventBusSubscriptionHandler.class);
          boolean isSingleton = false;
          if (annotation != null) {
            isSingleton = annotation.singleton();
          } else {
            throw new ConfigurationException(
                "No @EventBusSubscriptionHandler annotation found for handler class "
                    + h.getName());
          }
          if (isSingleton) {
            bind(h).in(Scopes.SINGLETON);
          } else {
            bind(h);
          }
        });
  }
}
