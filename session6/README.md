# Workshop 6

## Lambda Expressions 

Java 8 added the *@FunctionalInterface* which can be used to decorate interfaces with exactly one abstract method. 
These interfaces (with or without the annotation) can be transformed by the compiler into lambda expressions.

You can find here a list of FunctionalInterfaces available in the jdk:
https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html

A lambda expression has the following syntax:
```
parameter -> expression body
```

Example:
```java
public class Java8Tester {

   public static void main(String args[]) {
      Java8Tester tester = new Java8Tester();
		
      //with type declaration
      MathOperation addition = (int a, int b) -> a + b;
		
      //with out type declaration
      MathOperation subtraction = (a, b) -> a - b;
		
      //with return statement along with curly braces
      MathOperation multiplication = (int a, int b) -> { return a * b; };
		
      //without return statement and without curly braces
      MathOperation division = (int a, int b) -> a / b;
		
      System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
      System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
      System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
      System.out.println("10 / 5 = " + tester.operate(10, 5, division));
		
      //without parenthesis
      GreetingService greetService1 = message ->
      System.out.println("Hello " + message);
		
      //with parenthesis
      GreetingService greetService2 = (message) ->
      System.out.println("Hello " + message);
		
      greetService1.sayMessage("Mahesh");
      greetService2.sayMessage("Suresh");
   }
	
   interface MathOperation {
      int operation(int a, int b);
   }
	
   interface GreetingService {
      void sayMessage(String message);
   }
	
   private int operate(int a, int b, MathOperation mathOperation) {
      return mathOperation.operation(a, b);
   }
}
```

Lambda expressions are equivalent to instantiating an anonymous class implementing the functional interface with the 
lambda code.

## Optional

Optional is a container object used to contain not-null objects. It provides methods for dealing with nulls in a 
fluent manner.

javadoc: https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html

```java
import java.util.Optional;

public class Java8Tester {

   public static void main(String args[]) {
      Java8Tester java8Tester = new Java8Tester();
      Integer value1 = null;
      Integer value2 = new Integer(10);
		
      //Optional.ofNullable - allows passed parameter to be null.
      Optional<Integer> a = Optional.ofNullable(value1);
		
      //Optional.of - throws NullPointerException if passed parameter is null
      Optional<Integer> b = Optional.of(value2);
      System.out.println(java8Tester.sum(a,b));
   }
	
   public Integer sum(Optional<Integer> a, Optional<Integer> b) {
      //Optional.isPresent - checks the value is present or not
		
      System.out.println("First parameter is present: " + a.isPresent());
      System.out.println("Second parameter is present: " + b.isPresent());
		
      //Optional.orElse - returns the value if present otherwise returns
      //the default value passed.
      Integer value1 = a.orElse(new Integer(0));
		
      //Optional.get - gets the value, value should be present
      Integer value2 = b.get();
      return value1 + value2;
   }
}
```

## CompletableFuture

The CompletableFuture class represents a framework of dealing with asynchronous computation in Java.
It provides numerous methods for composing, combining and executing async tasks.

Javadoc: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html

```java
CompletableFuture<String> future
  = CompletableFuture.supplyAsync(() -> "Hello");
 
assertEquals("Hello", future.get());
```

### Chaining

```java
CompletableFuture<String> completableFuture
  = CompletableFuture.supplyAsync(() -> "Hello");
 
CompletableFuture<String> future = completableFuture
  .thenApply(s -> s + " World");
 
assertEquals("Hello World", future.get());
```

### Composing

But what happens if the next stage is also an async task and you don't want to block the running thread?
You return a CompletableFuture, but you would get CompletableFuture<CompletableFuture<...>>. Luckily, we have 
thenCompose():

```java
CompletableFuture<String> completableFuture 
  = CompletableFuture.supplyAsync(() -> "Hello")
    .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
 
assertEquals("Hello World", completableFuture.get());
```

### Exceptions

With handle():

```java
CompletableFuture<String> completableFuture  
  =  CompletableFuture.supplyAsync(() -> {
      if (name == null) {
          throw new RuntimeException("Computation error!");
      }
      return "Hello, " + name;
  })}).handle((s, t) -> s != null ? s : "Hello, Stranger!");
```

With completeExceptionally() :

```java

CompletableFuture<String> completableFuture = new CompletableFuture<>();

CompletableFuture<String> exceptionalFuture = completableFuture.exceptionally(ex -> "oh noes!");

completableFuture.completeExceptionally(
  new RuntimeException("Calculation failed!"));
 
 
completableFuture.get(); // ExecutionException
System.out.println(exceptionalFuture.get());
```

### Async execution 

Each of the chaining methods in CompletableFuture have three forms:

```java
completableFuture.thenAccept(consumer)
completableFuture.thenAcceptAsync(consumer)
completableFuture.thenAcceptAssync(consumer, executor)
```

The async forms are similar, with the difference that without specifying the executor, the *ForkJoinPool.commonPool()* 
will be chosen.

In the non-async form we only have 2 participating threads:
* the one which calls *thenAccept* on the future
* the one which completes the future.

The chained future can be executed by any of these 2 threads:
* if the future is completed at the moment of chaining, the thread which performs the chaining will execute the next 
stage
* otherwise, the thread which completes the first stage will execute the second one.

The asyn form makes sure that the next thread will be executed on a thread belonging to the specified *Executor*.

## Streams

Stream represents a sequence of objects from a source, which supports aggregate operations. 

A stream can be created from multiple objects, an array or a collection:

```java
String[] arr = new String[]{"a", "b", "c"};
Stream<String> stream = Arrays.stream(arr);
stream = Stream.of("a", "b", "c");
stream = list.stream();
```

### Operations

There are 2 types of stream operations:
* intermediate, which return a Stream<T>
* terminal, which return a definitive result (or Void).

Stream operation do not modify the original stream. Processing is done only on the final operations.

#### Iterating

```java
list.stream().forEach(el -> System.out.println(el));
```

#### Filtering

```java
Stream<String> stream = list.stream().filter(el -> el.contains("aString"));
```

#### Mapping

```java
List<String> uris = new ArrayList<>();
uris.add("C:\\My.txt");
Stream<Path> stream = uris.stream().map(uri -> Paths.get(uri));
```

For dealing with functions which return collections of elements use flatmap

```java
List<Detail> details = new ArrayList<>();
details.add(new Detail());
Stream<String> stream = details.stream().flatMap(detail -> detail.getParts().stream());
```

#### Reducing

```java
List<Integer> integers = Arrays.asList(1, 1, 1);
Integer reduced = integers.stream().reduce(23, (a, b) -> a + b);
```

#### Collecting

```java
List<String> resultList = list.stream().map(element -> element.toUpperCase()).collect(Collectors.toList());
```

#### Parallel execution

```java
list.parallelStream().forEach(element -> doWork(element));
```



