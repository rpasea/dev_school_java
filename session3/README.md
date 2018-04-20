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

We will be trying to implement an HTTP server based on the TCP server we completed in the previous session.

Have a look at the pom.xml, you ca see there the tcpserver dependency. In order for maven to be able to resolve it, 
you need to put it into your local repo. Go to the project, fix any compilation errors you might have, fix the tests 
(add the *@Ignore* annotation to the failing ones) and run the **maven install** phase, either from the cmdline or 
with your IDE. Now maven should be able to resolve the dependency in the httpserver project.

We will be implementing the server in 2 steps: first we will be dealing with the protocol and then with the file 
operations.

### 1 HTTP protocol 

[Request](https://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html)

[Response](https://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html#sec6)

In order to implement the protocol we need to handle de request/response encoding and implement a handler. For the 
first step, use a simple handler which returns a response containing the "hello world!" string for http GET.

You have the model implemented in the *HttpRequest* and *HttpResponse* classes. You need to implement:
* a *HttpRequestCodec* which **decodes** a HttpRequest from a String
* a *HttpResponseCodec* which **encodes** a HttpResponse into a String.

The CodecPipeline is already set up and you don't have to deal with the *encode* of the HttpRequestCodec or the 
*decode* of the HttpResponseCodec. Just return null.

The response encoder is simple, just follow the protocol.

For Decoding requests, there are 3 phases:
* the request line which ends with *CRLF*
* the header part which has a list of headers, each ending with *CRLF*. A *CRLF* on an empty line signifies the end 
of the header list.
* the body, whose length is specified by the *Content-length* header.

You will need to deal with segmentation in this codec (using a buffer), because the length of a http request depends 
on the protocol, meaning you can't use a splitting codec without actually parsing the requests.

You have the loop which navigates between the phases implemented, just implement the following private methods:
* parseRequestLine
* parseHeaders
* parseBody.

The convention is that these methods will consume from the buffer the usable bytes and will return a boolean 
signifying if the phase is complete or not (you encountered the header list terminator or you processed the whole 
body length).

Test the server by connecting to localhost:8080 from your browser.

### 2 Files

Our server will be started in the root specified by the **server.root** config in application properties. We will try
 to implement as much of the functionality of a normal http server:
* GET will return the contents of a file or, if called on a directory, a list of the folder's children with anchor 
HTML elements (*<a>*) for navigation
* PUT will create the file with the given content at the given path (create any missing parent)
* DELETE will delete the resource at the given path.

You have hints in the code for the GET functionality.

For testing, you can use the browser for GET and an HTTP client for the others (e.g. : postman, curl, the IDEA 
integrated REST client).