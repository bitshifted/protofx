/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.ui.loader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import co.bitshifted.protofx.core.l10n.ObservableResourceBundle;
import java.util.concurrent.ExecutorService;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
class LoaderAwareComponentBuilderTest {

  @Mock private ExecutorService mockExecutorService;
  @Mock private ObservableResourceBundle mockResourceBundle;

  private Task<String> dummyTask;
  private Node content;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    dummyTask = new DummyTask();
    content = new TextField("test");
  }

  @Test
  void testBuilderCreatesInstance() {
    assertNotNull(LoaderAwareComponentBuilder.builder());
  }

  @Test
  void testWithExecutorService() {
    LoaderAwareComponentBuilder<Void> builder = LoaderAwareComponentBuilder.builder();
    builder.withExecutorService(mockExecutorService);
    assertNotNull(builder); // Just checking if the builder is returned
  }

  @Test
  void testWithExecutorServiceThrowsNPEWhenNull() {
    LoaderAwareComponentBuilder<Void> builder = LoaderAwareComponentBuilder.builder();
    assertThrows(NullPointerException.class, () -> builder.withExecutorService(null));
  }

  @Test
  void testWithTask() {
    LoaderAwareComponentBuilder<String> builder = LoaderAwareComponentBuilder.builder();
    builder.withTask(dummyTask);
    assertNotNull(builder);
  }

  @Test
  void testWithTaskThrowsNPEWhenNull() {
    LoaderAwareComponentBuilder<Void> builder = LoaderAwareComponentBuilder.builder();
    assertThrows(NullPointerException.class, () -> builder.withTask(null));
  }

  @Test
  void testWithResources() {
    LoaderAwareComponentBuilder<Void> builder = LoaderAwareComponentBuilder.builder();
    builder.withResources(mockResourceBundle, "key", null);
    assertNotNull(builder);
  }

  @Test
  void testWithResourcesThrowsNPEWhenResourceBundleNull() {
    LoaderAwareComponentBuilder<Void> builder = LoaderAwareComponentBuilder.builder();
    assertThrows(NullPointerException.class, () -> builder.withResources(null, "key", null));
  }

  @Test
  void testWithResourcesThrowsNPEWhenLoaderTextKeyNull() {
    LoaderAwareComponentBuilder<Void> builder = LoaderAwareComponentBuilder.builder();
    assertThrows(
        NullPointerException.class, () -> builder.withResources(mockResourceBundle, null, null));
  }

  @Test
  void testWithLoaderText() {
    LoaderAwareComponentBuilder<Void> builder = LoaderAwareComponentBuilder.builder();
    builder.withLoaderText("Custom Loading...");
    assertNotNull(builder);
  }

  @Test
  void testWithContent() {
    LoaderAwareComponentBuilder<Void> builder = LoaderAwareComponentBuilder.builder();
    builder.withContent(content);
    assertNotNull(builder);
  }

  @Test
  void testWithContentThrowsNPEWhenNull() {
    LoaderAwareComponentBuilder<Void> builder = LoaderAwareComponentBuilder.builder();
    assertThrows(NullPointerException.class, () -> builder.withContent(null));
  }

  @Test
  void testBuildWithDefaultLoaderText() {
    LoaderAwareComponent<Void> component =
        LoaderAwareComponentBuilder.<Void>builder()
            .withExecutorService(mockExecutorService)
            .withTask(dummyTask)
            .withContent(content)
            .build();

    assertNotNull(component);
    // Further assertions could be made if LoaderAwareComponent had public getters for these fields
  }

  @Test
  void testBuildWithCustomLoaderText() {
    String customText = "Please wait...";
    LoaderAwareComponent<Void> component =
        LoaderAwareComponentBuilder.<Void>builder()
            .withExecutorService(mockExecutorService)
            .withTask(dummyTask)
            .withContent(content)
            .withLoaderText(customText)
            .build();

    assertNotNull(component);
  }

  @Test
  void testBuildWithResources() {
    String key = "loader.text";
    when(mockResourceBundle.getStringBinding(anyString()))
        .thenReturn(Bindings.createStringBinding(() -> "Loading from resources..."));
    LoaderAwareComponent<Void> component =
        LoaderAwareComponentBuilder.<Void>builder()
            .withExecutorService(mockExecutorService)
            .withTask(dummyTask)
            .withContent(content)
            .withResources(mockResourceBundle, key, null)
            .build();

    assertNotNull(component);
  }

  @Test
  void testBuildThrowsNPEWhenExecutorServiceIsNull() {
    LoaderAwareComponentBuilder<Void> builder =
        LoaderAwareComponentBuilder.<Void>builder().withTask(dummyTask).withContent(content);
    assertThrows(NullPointerException.class, builder::build);
  }

  @Test
  void testBuildThrowsNPEWhenTaskIsNull() {
    LoaderAwareComponentBuilder<Void> builder =
        LoaderAwareComponentBuilder.<Void>builder()
            .withExecutorService(mockExecutorService)
            .withContent(content);
    assertThrows(NullPointerException.class, builder::build);
  }

  @Test
  void testBuildThrowsNPEWhenContentIsNull() {
    LoaderAwareComponentBuilder<Void> builder =
        LoaderAwareComponentBuilder.<Void>builder()
            .withExecutorService(mockExecutorService)
            .withTask(dummyTask);
    assertThrows(NullPointerException.class, builder::build);
  }

  @Test
  void testWithNoTaskOrTaskSupplierThrowsNPE() {
    var builder =
        LoaderAwareComponentBuilder.builder()
            .withExecutorService(mockExecutorService)
            .withContent(content);
    assertThrows(NullPointerException.class, builder::build);
  }
}
