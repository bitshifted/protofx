/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.process;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DefaultProcessExecutorTest {

  @Test
  @Disabled
  void executeProcessSuccess() throws Exception {
    var mockExecutor = mock(ExecutorService.class);
    var processExecutor = new DefaultProcessExecutor(mockExecutor);
    var success =
        new ProcessExecutionResult(ProcessExecutor.DEFAULT_SUCCESS_EXIT_CODE, "stdout", "stderr");
    var mockFuture = mock(Future.class);
    when(mockFuture.get()).thenReturn(success);
    when(mockExecutor.submit(any(Callable.class))).thenReturn(mockFuture);

    var result =
        processExecutor.executeExternalProcess(
            List.of("ls", "-l"), Path.of("/").toFile(), Map.of());
    assertEquals(result.get().exitCode(), ProcessExecutor.DEFAULT_SUCCESS_EXIT_CODE);
    assertEquals(result.get().stdout(), "stdout");
    assertEquals(result.get().stdErr(), "stderr");
  }
}
