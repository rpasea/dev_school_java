# Workshop 4

## Hibernate

Hibernate is an Object/Relational Mapping framework and persistence service. It should handle all of your 
interactions with the database and provide an OOP way of dealing with persistence.

 ![hibernate](https://www.tutorialspoint.com/hibernate/images/hibernate_architecture.jpg)
 
 ### Important classes
 
 #### SessionFactory Object
Configuration object is used to create a SessionFactory object which in turn configures Hibernate for the 
application using the supplied configuration file and allows for a Session object to be instantiated. The 
SessionFactory is a thread safe object and used by all the threads of an application.
 
The SessionFactory is a heavyweight object; it is usually created during application start up and kept for later 
use. You would need one SessionFactory object per database using a separate configuration file. So, if you are 
using multiple databases, then you would have to create multiple SessionFactory objects.
 
 #### Session Object
A Session is used to get a physical connection with a database. The Session object is lightweight and designed to 
be instantiated each time an interaction is needed with the database. Persistent objects are saved and retrieved 
through a Session object.
 
The session objects should not be kept open for a long time because they are not usually thread safe and they should
be created and destroyed them as needed.
 
#### Transaction Object
A Transaction represents a unit of work with the database and most of the RDBMS supports transaction functionality. 
Transactions in Hibernate are handled by an underlying transaction manager and transaction (from JDBC or JTA).
 
This is an optional object and Hibernate applications may choose not to use this interface, instead managing 
transactions in their own application code.
 
#### Query Object
Query objects use SQL or Hibernate Query Language (HQL) string to retrieve data from the database and create objects.
A Query instance is used to bind query parameters, limit the number of results returned by the query, and finally to
execute the query.
 
#### Criteria Object
Criteria objects are used to create and execute object oriented criteria queries to retrieve objects.


### Mappings

You can create your mappings from simple POJO classes using the Hibernate annotations:

```java
import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {
   @Id @GeneratedValue
   @Column(name = "id")
   private int id;

   @Column(name = "first_name")
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "salary")
   private int salary;  

   public Employee() {}
   
   public int getId() {
      return id;
   }
   
   public void setId( int id ) {
      this.id = id;
   }
   
   public String getFirstName() {
      return firstName;
   }
   
   public void setFirstName( String first_name ) {
      this.firstName = first_name;
   }
   
   public String getLastName() {
      return lastName;
   }
   
   public void setLastName( String last_name ) {
      this.lastName = last_name;
   }
   
   public int getSalary() {
      return salary;
   }
   
   public void setSalary( int salary ) {
      this.salary = salary;
   }
}
```

### Interacting with the database

A Session is used to get a physical connection with a database. The Session object is lightweight and designed to be
instantiated each time an interaction is needed with the database. Persistent objects are saved and retrieved 
through a Session object.

Instances can exist in three states:
* **transient** − A new instance of a persistent class, which is not associated with a Session and has no 
representation in the database and no identifier value is considered transient by Hibernate
* **persistent** - You can make a transient instance persistent by associating it with a Session. A persistent
instance has a representation in the database, an identifier value and is associated with a Session
* **detached** - Once we close the Hibernate Session, the persistent instance will become a detached instance.

Transaction example:
```java
Session session = factory.openSession();
Transaction tx = null;

try {
   tx = session.beginTransaction();
   // do some work
   ...
   tx.commit();
}

catch (Exception e) {
   if (tx!=null) tx.rollback();
   e.printStackTrace(); 
} finally {
   session.close();
}
```

Session javadoc: https://docs.jboss.org/hibernate/orm/current/javadocs/org/hibernate/Session.html

### Notes

Hibernate has been started more than 15 years ago and tackles the database layer, which is a very complex area. There is
a great level of detail which can be controlled (transaction isolation level, locking strategy, etc). Basically, 
Hibernate is meant to work with most sane database configurations out there, so you should be able to find any 
configuration you are comfortable with and apply it in Hibernate.


## Spring Security

Spring applications are not secured by default. To provide required authentication and authorization facilities you 
need to either create them from the scratch or use existing security framework. Of course, in production you most 
likely won't implement your own security, so enter Spring Security.

Spring Security offers authentication and authorization (access control) features to web applications. It implements 
most of the authentication protocols (CAS, openid, oAuth2) and provides ways to add them to your services. Since 
security is a cross cutting concern, it can add a lot of noise to the business logic when done in an imperative 
fashion, which is why Spring Security relies on Aspect Oriented Programming to separate security code from business 
logic.

The security logic resides in a series of filters outside the business logic and can be accessed through dependency 
injection and annotations.

![security](https://github.com/spring-guides/top-spring-security-architecture/raw/master/images/security-filters.png)


### Authentication

The main strategy interface for authentication is *AuthenticationManager*:
```java
public interface AuthenticationManager {

  Authentication authenticate(Authentication authentication)
    throws AuthenticationException;

}
```

The most commonly used implementation of *AuthenticationManager* is *ProviderManager* which delegates to a chain of 
*AuthenticationProvider* instances:
```java
public interface AuthenticationProvider {

	Authentication authenticate(Authentication authentication)
			throws AuthenticationException;

	boolean supports(Class<?> authentication);

}
```

The *Authentication* interface encapsulates authentication data (such as credentials).

### Authorization

Once authentication is successful, we can move on to authorization, and the core strategy here is 
*AccessDecisionManager*. There are three implementations provided by the framework and all three delegate to a chain 
of *AccessDecisionVoter*, a bit like the *ProviderManager* delegates to *AuthenticationProviders*.

An *AccessDecisionVoter* considers an *Authentication* (representing a principal) and a secure Object which as been 
decorated with *ConfigAttributes*:

```java
boolean supports(ConfigAttribute attribute);

boolean supports(Class<?> clazz);

int vote(Authentication authentication, S object,
        Collection<ConfigAttribute> attributes);
```

The default manager is generally used where all voters should accept in order for the permission to be granted. 

### Web security

In order to secure your API you need to customize the *WebSecurityConfigurerAdapter*:

```java
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class ApplicationConfigurerAdapter extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/foo/**")
      .authorizeRequests()
        .antMatchers("/foo/bar").hasRole("BAR")
        .antMatchers("/foo/spam").hasRole("SPAM")
        .anyRequest().isAuthenticated();
  }
}
```

The default fallback filter chain in a Spring Boot app (the one with the /** request matcher) has a predefined order 
of *SecurityProperties.BASIC_AUTH_ORDER*. You can switch it off completely by setting *security.basic.enabled=false*, 
or you can use it as a fallback and just define other rules with a lower order. To do that just add a *@Bean* of type 
*WebSecurityConfigurerAdapter* (or *WebSecurityConfigurer*) and decorate the class with *@Order*.

Security is enforced with *ConfigAttributes*:

```java
@Service
public class MyService {

  @Secured("ROLE_USER")
  public String secure() {
    return "Hello Security";
  }

}
```

### Session management

HTTP session related functionality is handled by a combination of the *SessionManagementFilter* and the 
*SessionAuthenticationStrategy* interface, which the filter delegates to. Typical usage includes session-fixation 
protection attack prevention, detection of session timeouts and restrictions on how many sessions an authenticated 
user may have open concurrently.

By default, spring stores sessions in memory and sets them in thread-local variables in the authentication filter. 
This way they can be accessed in the business code.

## Exercises

We will be reimplementing our wannabe twitter with hibernate and we will add some simple authentication to it.

### 1 Hibernate

We need to reimplement the repositories using Hibernate now. Have a look over the UserDAO and implement the TweetDAO 
class.

Since we have a more complex application (not), we will add a Service layer. The service layer only contains business
logic and must use the repositories. A service can use multiple repositories, which introduces a problem: what do we 
do with transactions?

So transactions belong to the repository layer, but we can't add them to the DAO objects since a transaction can span
multiple DAOs. This means that Service objects must start and close transactions. We have the 
*ProgrammaticTransactionManager* which encapsulates transactions and must be used by services. Have a look over it.

Using the UserService as an example, implement the TweetService.

### 2 Security

We want to protect our API with a simple security scheme:
* users can log in on a /login endpoint with a (user, pass) combination and receive a session ID
* all other endpoints return 401 if not called with a valid session
* the session cookie is called *JSESSIONID*.

We will be implementing this manually at first, and then see how spring security can help us:
* set the *security.enabled* config to *manual*
* we have a *SessionInterceptor* class which is called before our resources and checks if there is a valid session 
set up
* you have to implement the LoginResource where the user provides username+password credentials, these are checked 
against the database and, if valid, a session is created and added to the session map. For unique identifiers use the
 UUID class.

