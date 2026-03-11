# Guidance for coding agents

Use this file as general guidelines for generated code.

## Coding style
* Use idiomatic Java code with generally accepted conventions
* Prefer modern Java features over older ones (strams, lambdas, functional programming)
* Project uses Google-style Java code conventions. It is enforced using Maven Spotless plugin
** to check correct code formatting, run `mvn spotless:check`
** to fix any formatting errors, run `mvn spotless:apply`
* All generated .java files must contain license header. To add header, run `mvn license:format`

## Comments
* avoid unnecessary comments inside generated code
* all public methods must have correct Javadoc comments. These must nbe kept in sync with any code changes
* package-level javadoc must also be generated and kept in sync

## Testing
* all public methods must be covered with unit tests
* tests must cover valid and invalid inputs and any edge cases
