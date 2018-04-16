# Workshop 1


## Java Basics

### Syntax

```java
public class HelloWorld {
    public static void main(String []args) {
        System.out.println("Hello World");
    }
}
```

Features:
* C like syntax
* "everything is a class" (except primitive types)
* *Object* is the root of the class hierarchy
* classes have fields (state) and methods (behavior)
* methods have a return type, parameters and a block of statements (code)
* blocks of statements are enclosed in curly braces '{}'
* *if*-*else*, *switch*, *while*, *do*-*while*, *for*
* classes have constructors
* *new* instantiates objects using the constructors
* classes, fields and methods have access modifiers: *private*, *public*, *protected*, default
* methods can be *abstract* (unimplemented)
* fields and methods can belong to an instance (default), or can belong to the class as a whole (*static* keyword)
* *this* keyword identifies the instance in the current context

### OOP

#### Inheritance 

```java
public class DerivedClass extends BaseClass {
}
```

The DerivedClass inherits the implementation of BaseClass and has the possibility of modifying it.

**A class can only extend a single super class.**

#### Interfaces 

```java
public interface Animal {
    public abstract void breathe();
    public abstract void move();
}

public class Dog implements Animal {
    public void breathe() {...}
    public void move() {...}
}
```

Classes with abstract methods only (but not really since java 8).

A class can implement multiple interfaces.

#### Overriding methods

```java
public interface Animal {
    public abstract void breathe();
    public abstract void move();
}

public class Dog implements Animal {
    public void breathe() {...}
    public void move() {...}
}

public static void main(String []args) {
    Animal animal = new Dog();
	  
    animal.move();
}

```

#### Overloading 

```java
public class Dog() {
    public void move() {...}
    public void move(int distanceInMeters) {...}
}
```

#### Generics

In a nutshell, generics enable types (classes and interfaces) to be parameters when defining classes, interfaces and 
methods. By using generics, programmers can implement generic algorithms that work on collections of different 
types, can be customized, and are type safe and easier to read.

You could bypass generics and just use *Object* in your containers, but the compiler can no longer enforce type 
safety on you. In fact, the compiler does just that, it casts back and forth between *Object* (or the common 
ancestor) and your declared type, so you don't have access to the type at runtime.

##### Generic method

```java
public static < E > void printArray( E[] inputArray ) {
  // Display array elements
  for(E element : inputArray) {
     System.out.printf("%s ", element);
  }
  System.out.println();
}

public static void main(String args[]) {
     // Create arrays of Integer, Double and Character
     Integer[] intArray = { 1, 2, 3, 4, 5 };
     Double[] doubleArray = { 1.1, 2.2, 3.3, 4.4 };
     Character[] charArray = { 'H', 'E', 'L', 'L', 'O' };

     System.out.println("Array integerArray contains:");
     printArray(intArray);   // pass an Integer array

     System.out.println("\nArray doubleArray contains:");
     printArray(doubleArray);   // pass a Double array

     System.out.println("\nArray characterArray contains:");
     printArray(charArray);   // pass a Character array
  }
```

##### Generic class

```java
public class Box<T> {
   private T t;

   public void add(T t) {
      this.t = t;
   }

   public T get() {
      return t;
   }

   public static void main(String[] args) {
      Box<Integer> integerBox = new Box<Integer>();
      Box<String> stringBox = new Box<String>();
    
      integerBox.add(new Integer(10));
      stringBox.add(new String("Hello World"));

      System.out.printf("Integer Value :%d\n\n", integerBox.get());
      System.out.printf("String Value :%s\n", stringBox.get());
   }
}
```

#### Collections 

The Java collections framework gives the programmer access to prepackaged data structures as well as to algorithms for manipulating them.

A collection is an object that can hold references to other objects. The collection interfaces declare the operations that can be performed on each type of collection.

The classes and interfaces of the collections framework are in package java.util.

You can find more details here: https://www.tutorialspoint.com/java/java_collections.htm

#### Reflection

Reflection is commonly used by programs which require the ability to examine or modify the runtime behavior of 
applications running in the Java virtual machine. This is a relatively advanced feature and should be used only by 
developers who have a strong grasp of the fundamentals of the language. With that caveat in mind, reflection is a 
powerful technique and can enable applications to perform operations which would otherwise be impossible.

```java
Method[] methods = MyObject.class.getMethods();

for(Method method : methods){
    System.out.println("method = " + method.getName());
}
```

The class object:
```java
Class myObjectClass = MyObject.class;
```

Methods and fields:
```java
Class myObjectClass = MyObject.class;

Method[] methods = myObjectClass.getMethods();

Field[] fields   = myObjectClass.getFields();
```

### Exceptions

Exceptions are thrown:

```java
public void deposit(double amount) throws RemoteException {
      // Method implementation
      throw new RemoteException();
   }
```

And then are caught:

```java
try {
    // Protected code
} catch (ExceptionClass1 e1) {
    // Catch block
} catch (ExceptionClass2 e2) {
    // Catch block
}
```

Otherwise they terminate the program.

## Maven


Maven is a build and dependency management system which is built with the tenet
of **convention over configuration** at heart. The way this is achieved is by
providing a reusable, maintainable and simple declarative model for projects
which can be manipulated by a collection of plugins.

### The Model

A project contains:
* identifiers and version
* project dependencies
* plugins
* goals
* build profiles
* developers
* mailing list

All of these are specified in the **pom.xml** file which has to exist in every
maven project. Most of the project configuration comes with sensible defaults.

### Lifecycle

When building a project, maven goes sequentially through a number of phases:
* validate - validate the project is correct and all necessary information is available
* compile - compile the source code of the project
* test - test the compiled source code using a suitable unit testing framework. These tests should not require the 
code be packaged or deployed
* package - take the compiled code and package it in its distributable format, such as a JAR.
* verify - run any checks on results of integration tests to ensure quality criteria are met
* install - install the package into the local repository, for use as a dependency in other projects locally
* deploy - done in the build environment, copies the final package to the remote repository for sharing with other 
developers and projects. 

A phase is invoked like this:
```sh
mvn package
```

Plugins can declare goals and attach them to phases. If goals are not attached,
they must be explicitly invoked. Usually, the most common plugins are included
by default and have their goals attached to phases in a sensible way.

Here is more documentation regarding the lifecycle:
https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html.

### Dependencies

Maven dependencies reside in repositories which are:
* local
* remote (central or third-party).

Before a maven project can run, all dependencies must be brought into the local
repository (usually in ~/.m2).

Remote repositories and credentials can be specified in the **settings.xml** file.

### Archetypes

Maven archetypes provide quick initialization for common types of projects.
They are a great way to start your project.

```sh
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart
```

## Exercises

### 1. Environment setup

#### 1.1 Install jdk

#### 1.2 Install maven

#### 1.3 Create a quickstart project

### 2. The json parser

We will be implementing a json parser which can transform Map objects and turn them to/from JSON strings. You have a 
skeleton project with some TODOs and a couple of unit tests. Have alook around.

#### 2.1 Serialization
 
Serialization is the easy part. Have a look in the **MapSerializer** class and implement the missing methods. Then
run the *givenMapWhenSerializeThenReturnCorrectJsonString* and make sure it passes.

#### 2.2 Deserialization

Deserialization is trickier. You have a parser implementation in the **MapDeserializer** class. It uses a stack to 
keep track of the encountered JSON elements. You can use that class and implement de missing **Element** classes.

Or you can just roll your own parser. Try implementing a JSON deserializer which passes the remaining unit test.

#### [BONUS] Deserialization with reflection

Try writing a parser which deserializes java objects (not just maps). You need to iterate recursively through the 
fields of the given object with reflection and serialize them to JSON.