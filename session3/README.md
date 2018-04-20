# Workshop 3

## Java File I/O

### Streams

Reading and writing to files is done with I/O streams, just like sockets (with the File streams variant). All the
high level constructs like Readers and Writers work on files the same way they work on sockets.

### File class

The Java **File** class represents the files and directory pathnames in an abstract manner.
This class is used for creation of files and directories, file searching, file deletion, etc.

JavaDoc: https://docs.oracle.com/javase/8/docs/api/java/io/File.html

The **Files** class was added in Java 8 and is an utility class which provides portable implementations of
various common file operations.

JavaDoc: https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html

### Path manipulation

Path manipulation is a pain in the ass when working on different OSs, Java is meant to provide portability and the
java platform offers the **Path** class just for this. It is meant to complement the **File** class.

JavaDoc: https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html

## Spring 

The *Spring Framework* provides a large array of functionality to enable Java enterprise applications. It is built
in a highly modular manner, allowing you to choose which functionality you need. The core component on which the
framework is built is the *Spring Inversion of Control (IoC)* container.

### Spring container

The Spring container is at the core of the Spring Framework. The container will create the objects, wire them 
together, configure them, and manage their complete life cycle from creation till destruction. The Spring container uses
*Dependency Injection* to manage the components that make up an application. These objects are called Spring Beans.

The container gets its instructions on what objects to instantiate, configure, and assemble by reading the configuration
metadata provided. The configuration metadata can be represented either by XML, Java annotations, or Java code.
Configuring the container with annotations is the most common way and it is done through reflection using a classpath
scanner which has to be run on container startup.

![spring_container](https://www.tutorialspoint.com/spring/images/spring_ioc_container.jpg)

### Beans

A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container. Bean definitions
contains the *configuration metadata* which tell the container:
* how to create a bean
* its dependencies
* its lifecycle details.

There are multiple available bean scopes (and more can be added):
* singleton - a single instance per Spring container
* prototype - create a new bean each time one is needed (using the same bean definition)
* request - a single bean per HTTP request (only valid in the context of a web-aware Spring ApplicationContext)
* session - a single bean per HTTP session (only valid in the context of a web-aware Spring ApplicationContext)
* etc.

Beans are declared with the **@Bean** annotation. You can attach init and destroy callbacks to beans and thus control
their lifecycle.

Classes annotated with **@Configuration** are used by the Spring container as sources of beans.

Classes annotated with **@Component** and scanned with the component scanner are another valid way of providing bean
definitions

### Dependency Injection

DI is a way of writing applications so that class dependencies are explicitly via constructors or setters. This enables
loose coupling of classes and reusability.

Spring injects dependencies with the **@Autowired** annotation, but in some cases Spring can inject beans without the
annotation being present. When the context is initialized, all the bean definitions are loaded and a dependency graph
is created between all the beans, providing an order of bean construction.

The Spring container has 2 ways of resolving dependencies:
* by bean name, if specified
* by type, if there is a single bean implementing the required type in the container.

Injection can happen:
* in the constructor
* in setters
* in fields.

## Exercises
