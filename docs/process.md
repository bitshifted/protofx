# Process Execution

The `co.bitshifted.protofx.core.process` package provides a robust and easy-to-use API for executing external processes from within a JavaFX application. It simplifies the complexities of Java's `ProcessBuilder` and provides a clean, modern interface for handling process execution, including long-running tasks and launching default system applications.

## Introduction

The core components of this package are:

*   **`ProcessExecutor`**: An interface that defines the contract for executing external processes.
*   **`DefaultProcessExecutor`**: The default, concrete implementation of the `ProcessExecutor` interface. It uses a `java.util.concurrent.ExecutorService` to run tasks asynchronously.
*   **`ProcessExecutionResult`**: A simple record that encapsulates the result of a completed process, including its exit code, standard output (`stdout`), and standard error (`stderr`).
*   **`SystemUtils`**: A utility class to determine the host operating system.

## `ProcessExecutor`

The `ProcessExecutor` is the primary entry point for all process-related tasks. To get an instance, you would typically inject it into your classes using a dependency injection framework.

```java
@Inject
private ProcessExecutor processExecutor;
```

The interface provides several key methods:

### Executing a Command and Getting a Result

To run a command and wait for its completion, use the `executeExternalProcess` method. This method returns a `Future<ProcessExecutionResult>`, which will complete when the external process terminates.

```java
List<String> command = List.of("ls", "-l", "/home/user");
File workingDir = new File("/home/user");
Map<String, String> environment = Map.of(); // Optional environment variables

Future<ProcessExecutionResult> futureResult = processExecutor.executeExternalProcess(command, workingDir, environment);

// This will block until the process is finished
ProcessExecutionResult result = futureResult.get();

if (result.exitCode() == 0) {
    System.out.println("Command succeeded!");
    System.out.println("Output:\n" + result.stdOut());
} else {
    System.err.println("Command failed with exit code " + result.exitCode());
    System.err.println("Error Output:\n" + result.stdErr());
}
```

### Executing Long-Running Processes

For tasks that need to run in the background for an extended period (like a server), you can use the `executeAndWait` method. This returns a `Future<Process>`, giving you direct access to the `Process` object itself, which you can use to manage the process's lifecycle (e.g., destroy it).

```java
List<String> serverCommand = List.of("java", "-jar", "my-server.jar");
Future<Process> futureProcess = processExecutor.executeAndWait(serverCommand, new File("."), Map.of());

// The process is now running in the background
Process serverProcess = futureProcess.get();

// ... later, when you need to shut down the server
serverProcess.destroy();
```

### Launching Default Applications

The `ProcessExecutor` also provides a convenient way to open files or URIs with the default application registered by the operating system.

**To open a file:**

```java
try {
    Path pdfPath = Path.of("/path/to/my/document.pdf");
    processExecutor.launchDefaultApplication(pdfPath);
} catch (ProcessExecutionException e) {
    // Handle cases where the file can't be opened
    e.printStackTrace();
}
```

**To open a URL in the default web browser:**

```java
try {
    URI website = new URI("https://bitshifted.co");
    processExecutor.launchDefaultApplication(website);
} catch (ProcessExecutionException | URISyntaxException e) {
    // Handle errors
    e.printStackTrace();
}
```

This functionality is cross-platform and will automatically use the appropriate system command (`xdg-open` on Linux, `open` on macOS, and `start` on Windows).
