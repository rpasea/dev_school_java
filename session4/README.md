# Workshop 4

## Spring Boot

Spring Boot's goal is to simplify the configuration of Java applications. It is built on numerous conventions which 
greatly reduce boilerplate needed to start new applications.

Some features:
* has a spring-boot-starter parent which sets up the pom.xml in a commonly used configuration
* uses special spring-boot-starter dependencies which handle commonly used dependenciesS
* comes with new auto-configuration mechanism which are used to set up some commonly used beans in sensible ways 
(DataSources, HttpServers, JSONSerialization etc)
* creates and configures beans based on properties defined in config files.

Basically, Spring Boot is a usability layer over Spring Framework. You can always disable the Spring Boot 
auto-configuration of certain beans and create them in the traditional way.

### Spring Initializr

Bootstrap a new Spring Boot project:
https://start.spring.io/

## JDBC (Java Database Connectivity)

The ODBC implementation in Java is called JDBC. It is the lowest level on which we can interact with a database.

![jdbc](https://www.tutorialspoint.com/jdbc/images/jdbc-architecture.jpg)

JDBC components:
* DriverManager: This class manages a list of database drivers. Matches connection requests from the java application 
with the proper database driver using communication sub protocol. The first driver that recognizes a certain subprotocol
under JDBC will be used to establish a database Connection.
* Driver: This interface handles the communications with the database server. You will interact directly with Driver 
objects very rarely. Instead, you use DriverManager objects, which manages objects of this type. It also abstracts the
details associated with working with Driver objects.
* Connection: This interface with all methods for contacting a database. The connection object represents 
communication context, i.e., all communication with database is through connection object only.
* Statement: You use objects created from this interface to submit the SQL statements to the database. Some derived 
interfaces accept parameters in addition to executing stored procedures.
* ResultSet: These objects hold data retrieved from a database after you execute an SQL query using Statement objects.
 It acts as an iterator to allow you to move through its data.
* SQLException: This class handles any errors that occur in a database application.

### DataSource

The **DataSource** object is the standard method of connecting to a db (instead of using the DriverManager):
https://docs.oracle.com/javase/8/docs/api/javax/sql/DataSource.html

Objects instantiated by classes that implement the DataSource represent a particular DBMS or some other data source,
such as a file. A DataSource object represents a particular DBMS or some other data source, such as a file. If a company
uses more than one data source, it will deploy a separate DataSource object for each of them. The DataSource 
interface is implemented by a driver vendor.

You can find more information about **DataSources** here:
https://docs.oracle.com/javase/tutorial/jdbc/basics/sqldatasources.html

### The Spring JdbcTemplate

The **JdbcTemplate** class is a utility wrapper over the JDBC API:
* it provides standard templates of opening and closing resources when dealing with databases
* it implements exception handling on database logic
* provides uniform way of dealing with transactions.

```sql
create table employee(  
id number(10),  
name varchar2(100),  
salary number(10)  
);  
```

```java
public int saveEmployee(Employee e){  
    String query="insert into employee values(  
    '"+e.getId()+"','"+e.getName()+"','"+e.getSalary()+"')";  
    return jdbcTemplate.update(query);  
}  
public int updateEmployee(Employee e){  
    String query="update employee set   
    name='"+e.getName()+"',salary='"+e.getSalary()+"' where id='"+e.getId()+"' ";  
    return jdbcTemplate.update(query);  
}  
public int deleteEmployee(Employee e){  
    String query="delete from employee where id='"+e.getId()+"' ";  
    return jdbcTemplate.update(query);  
}  
```

Of course, we don't want SQL injection in our services. The JDBC way of dealing with this is to use 
**PreparedStatement**:

```java
public Boolean saveEmployeeByPreparedStatement(final Employee e){  
    String query="insert into employee values(?,?,?)";  
    return jdbcTemplate.execute(query,new PreparedStatementCallback<Boolean>(){  
    @Override  
    public Boolean doInPreparedStatement(PreparedStatement ps)  
            throws SQLException, DataAccessException {  
              
        ps.setInt(1,e.getId());  
        ps.setString(2,e.getName());  
        ps.setFloat(3,e.getSalary());  
              
        return ps.execute();  
              
    }  
    });  
}  
```

A simpler way of doing inserts is to use the **SimpleJdbcInsert** class:
https://docs.spring.io/autorepo/docs/spring-framework/5.0.5.RELEASE/javadoc-api/

Full javadoc: https://docs.spring.io/autorepo/docs/spring-framework/5.0.5.RELEASE/javadoc-api/

### The Data Access Object pattern

The DAO Design Pattern is used to separate the data persistence logic in a separate layer, so that low level operations
are completely transparent to the higher application layers.

 ![dao](https://www.tutorialspoint.com/design_pattern/images/dao_pattern_uml_diagram.jpg)
 
You can find here a detailed example:
https://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm


### Spring stereotypes 

Besides the **@Component** annotation, there are other stereotypes available:
* **@Repository** - separate the database layer
* **@Service** - separate the business layer
* **@Controller** - separate the controller layer.

It is useful to use the appropriate annotations for your beans, mainly for implementing aspects
(AOP - https://en.wikipedia.org/wiki/Aspect-oriented_programming). **@Repository** also comes with other perks, such as
automated persistence exception translation.

## Spring Web-MVC

The Spring Web MVC framework provides Model-View-Controller (MVC) architecture and ready components that can be used to
develop flexible and loosely coupled web applications. The MVC pattern results in separating the different aspects of
the application (input logic, business logic, and UI logic), while providing a loose coupling between these elements.
* The Model encapsulates the application data and in general they will consist of POJO.
* The View is responsible for rendering the model data and in general it generates HTML output that the client's 
browser can interpret.
* The Controller is responsible for processing user requests and building an appropriate model and passes it to the 
view for rendering.

Of course, since we are building REST services, we are not really interested in the view part.

### spring-boot-starter-web

This dependency adds multiple stuff to your application, like:
* a HTTP server (by default Tomcat)
* the Spring Web-MVC framework
* the Jackson JSON serialization library
* Java bean validation api.

### The REST controller

These classes define the endpoints of your service and have to be annotated with the **RestController** stereotype 
(which is a combination of **@Controller** and **ResponseBody** - which tells Spring MVC not to return a html view).

You can map endpoints (URLs) to methods using the **@RequestMapping** annotation and its variants: **GetMapping**, 
**PostMapping** etc.

Path params are defined like this:
```java
@GetMapping("/{id}")
public MyResource getMyResource(@PathVariable Long id)
```

Query params are defined with the **@RequestParam** annotation.

Body params are defined with the  **@RequestBody** annotation.

### Parameter validation

The *javax.validation.validation-api* contains the standard validation APIs. The reference implementation is 
*org.hibernate.validator* which is configured by Spring Boot.

#### Annotation based validation

You can use the various annotations from the *javax.validation.constraints* package:
* @NotNull 
* @Size
* @Email
* etc

In order to apply the validations in a rest controller you have to decorate the parameters with the *@Valid* annotation.

## Spring Test

In order to test some beans it is necessary to construct parts of the application context and start the dependency 
injection container. The Spring Test framework provides ways to do this and the context management mechanism which 
allows for having different contexts for different tests. Of course, since booting up the application context is 
serious work, Spring Test is usually suited to integration tests.

In order to run the spring test runner you need to annotate your classes with *@RunWith(SpringRunner.class)*. This 
way you can now benefit from the spring test features. The *@SpringBootTest* annotation provides the same feature for
tests that Spring Boot provides to normal applications: a conventional setup of beans and configurations useful for 
testing. These features are brought by the *spring-boot-starter-test* dependency.

By default, Spring boot loads the entire application context by scanning the root package of the application. In order
to use a different context for a given test, you need to annotate it with the *@ContextConfiguration* annotation.

The *@MockBean* annotation injects mock objects into the context, the same as Mockito mocks. You can use the Mockito 
API on them.

In order to test your web layer you can annotate your test with *@WebMvcTest*. This will only load the web layer 
(controllers, validation, etc) and provide you (through injection) a **MockMvc** object which you can use to perform 
HTTP calls.

## Excercises

We will start the implementation of something like a twitter clone. We have users and tweets, each tweet belonging to
 a user and containing an 140 character long message.
 
 We will first implement the data layer and then the web layer.

### 1 The Data Layer

You need to implement the model class for Tweet. Have a look in the *schema.sql* resource file to see the 
resource. The model classes should be simple data classes, so don't add behaviour to them.

Next you need to implement the data access object for the Tweet class. Complete the code in the TweetDAO class.

In order to test your implementation you need to write an integration test for the TweetDAO class. Use 
UserDAOIT as a starting point and implement an integration test for the TweetDAO class. Test all the functionality in
 TweetDAO.
 
 
### 2 The Web Layer

#### 2.1 API

We need to create a Web API over our tweets. Have a look over the user API in UsersResource and implement the 
TweetsResource class.

#### 2.2 Validation

Make sure that the tweets have an owner and that their message doesn't exceed 140 chars (hint: use javax.validation 
annotations)

#### 2.3 Integration Tests

Have a look over the TweetsResourceIT class and implement the rest of the tests.

