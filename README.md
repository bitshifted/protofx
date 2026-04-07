# ProtoFX

ProtoFX is a framework for rapid development of JavaFX applications. The aim of the project is to provide common structure and boilerplate
frequently found in JavaFX projects. Main focus is on JavaFX desktop applications.

High-level principles of the project:
* Inversion of control
* Separation of concerns
* Focus on MVC architecture

## Getting started

Project consists of several modules:

* `protofx-core` - core classes for the framework.All other modules include this module
* `protofx-ui` - common ready-made UI components for use in client applications

To include ProtoFX in your applications, use the following Maven coordinates:

```xml
<dependencies>
    <dependency>
        <groupId>co.bitshifted.protofx</groupId>
        <artifactId>protofx-core</artifactId>
        <version>latest version</version>
    </dependency>
    <dependency>
        <groupId>co.bitshifted.protofx</groupId>
        <artifactId>protofx-ui</artifactId>
        <version>latest version</version>
    </dependency>
</dependencies>
```

## Documentation

Detailed documentation on how to use the project can be found in 

## License

Project is published under Mozilla Public License 2.0 (MPL-2.0)

