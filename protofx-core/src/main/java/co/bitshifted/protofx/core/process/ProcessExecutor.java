/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.process;

import co.bitshifted.protofx.core.error.ProcessExecutionException;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/** Defines methods for executing external processes. */
public interface ProcessExecutor {

  int DEFAULT_SUCCESS_EXIT_CODE = 0;
  int DEFAULT_FAILURE_EXIT_CODE = 1;

  /**
   * Executes external process and returns when execution is complete.
   *
   * @param cmdLine command to execute the process
   * @param workingDirectory process working directory
   * @param environment process environment
   * @return {@code Future} representing the result of process execution
   */
  Future<ProcessExecutionResult> executeExternalProcess(
      List<String> cmdLine, File workingDirectory, Map<String, String> environment);

  /**
   * Executes external process and waits until process exists. This method is used to execute
   * long-running processes such as servers
   *
   * @param cmdLine command to execute process
   * @param workingDirectory process working directory
   * @param environment process environment
   * @return {@code Future} representing the {@code Process} class
   */
  Future<Process> executeAndWait(
      List<String> cmdLine, File workingDirectory, Map<String, String> environment);

  /**
   * Launches default application associated with the file at specified path
   *
   * @param filePath path to the file
   * @throws ProcessExecutionException if an error occurs while launching the application
   */
  void launchDefaultApplication(Path filePath) throws ProcessExecutionException;

  /**
   * Launches default application for specified URL.
   *
   * @param uri URL to open
   * @throws ProcessExecutionException if an error occurs while launching the application
   */
  void launchDefaultApplication(URI uri) throws ProcessExecutionException;
}
