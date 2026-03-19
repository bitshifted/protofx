/*
 * Copyright © 2024-2025, Bitshift-ED <https://bitshifted.co>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package co.bitshifted.protofx.core.process;

/**
 * Represents the result of process execution.
 *
 * @param exitCode process exit code
 * @param stdout standard output content
 * @param stdErr standard error content
 */
public record ProcessExecutionResult(int exitCode, String stdout, String stdErr) {}
