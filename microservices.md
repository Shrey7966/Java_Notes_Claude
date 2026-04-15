# SPRING BOOT — RESTful Web Services | Interview Preparation Notes

---

## TABLE OF CONTENTS

1. [Spring Framework — The Big Picture](#1-spring-framework--the-big-picture)
2. [Dependencies — What & Why](#2-dependencies--what--why)
3. [Annotations — Definitions & Interview Explanations](#3-annotations--definitions--interview-explanations)
4. [The Request Flow — How Spring Handles a Request](#4-the-request-flow--how-spring-handles-a-request)
5. [REST API Concepts](#5-rest-api-concepts)
6. [Code Walkthrough — HelloWorldController](#6-code-walkthrough--helloworldcontroller)
7. [Code Walkthrough — DAO Layer & UserController](#7-code-walkthrough--dao-layer--usercontroller)
8. [Key Java Concepts Used](#8-key-java-concepts-used)
9. [Quick Interview Cheat Sheet](#9-quick-interview-cheat-sheet)

---

---

## 1. SPRING FRAMEWORK — The Big Picture

**What is Spring Framework?**
Spring is a Java framework that provides infrastructure support for building enterprise applications. It handles the plumbing (object creation, wiring, configuration) so you focus on business logic.

**What is Spring Boot?**
Spring Boot is an **opinionated layer on top of Spring** that auto-configures everything. No manual XML configs, no manual server setup — it just works.

**How to explain in interview:**
> "Spring Boot is a framework that removes boilerplate configuration from Spring applications. It uses auto-configuration, starter dependencies, and an embedded server so I can focus on writing business logic instead of infrastructure code."

---

---

## 2. DEPENDENCIES — What & Why

Each dependency you add in `pom.xml` (Maven) pulls in a set of JARs. Here's what each one does:

### 2.1 Spring Web
```
spring-boot-starter-web
```
- **What it gives you:** Spring MVC, REST support, embedded Tomcat server, Jackson (JSON converter)
- **Why you need it:** Without this, you can't create REST endpoints or handle HTTP requests
- **Interview line:** "Spring Web starter bundles Spring MVC, an embedded Tomcat server, and Jackson for JSON serialization — everything needed to build REST APIs."

### 2.2 Spring Data JPA
```
spring-boot-starter-data-jpa
```
- **What it gives you:** JPA (Java Persistence API), Hibernate (ORM), Spring Data repositories
- **Why you need it:** To interact with databases using Java objects instead of raw SQL
- **Interview line:** "Spring Data JPA provides an abstraction over Hibernate and JPA, allowing me to perform database operations through repository interfaces without writing SQL."

### 2.3 H2 Database
```
h2
```
- **What it gives you:** An in-memory relational database
- **Why you need it:** Quick prototyping/testing without installing MySQL or PostgreSQL
- **Interview line:** "H2 is an in-memory database useful during development. It resets on every restart, which makes it ideal for rapid prototyping and testing."

### 2.4 Spring Boot DevTools
```
spring-boot-devtools
```
- **What it gives you:** Automatic restart on code changes, LiveReload, relaxed caching
- **Why you need it:** Faster development cycle — no manual restarts
- **Interview line:** "DevTools enables hot-reload during development so the server automatically restarts when I save code changes."

### How all JARs become available — Starter Projects

```
You add ONE starter → it pulls MANY JARs

spring-boot-starter-web
    ├── spring-webmvc
    ├── spring-web
    ├── spring-boot-starter-tomcat
    ├── spring-boot-starter-json (Jackson)
    └── ... (transitive dependencies)
```

**Interview line:** "Starter projects are curated dependency descriptors. Adding `spring-boot-starter-web` transitively pulls in Spring MVC, Tomcat, Jackson, and all related libraries — I don't have to manage individual JARs."

---

---

## 3. ANNOTATIONS — Definitions & Interview Explanations

### 3.1 @SpringBootApplication

```java
@SpringBootApplication
public class FirstSpringProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(FirstSpringProjectApplication.class, args);
    }
}
```

**Definition:** A convenience annotation that combines THREE annotations:

```
@SpringBootApplication
    ├── @Configuration       → This class can define beans
    ├── @EnableAutoConfiguration → Auto-configure based on JARs on classpath
    └── @ComponentScan       → Scan this package & sub-packages for components
```

**Interview line:** "@SpringBootApplication is a meta-annotation combining @Configuration, @EnableAutoConfiguration, and @ComponentScan. It marks the entry point of the application and triggers classpath scanning and auto-configuration."

---

### 3.2 @RestController

```java
@RestController
public class HelloWorldController { ... }
```

**Definition:** Marks a class as a REST API controller. It is a combination of TWO annotations:

```
@RestController
    ├── @Controller      → Marks class as a Spring MVC controller
    └── @ResponseBody    → Every method's return value is written directly
                           to the HTTP response body (as JSON/XML),
                           NOT resolved as a view/template
```

**Interview line:** "@RestController combines @Controller and @ResponseBody. It tells Spring that every method in this class returns data directly in the response body — typically serialized as JSON — rather than a view name."

**Key difference:**
- `@Controller` → returns a **view name** (HTML page via Thymeleaf, JSP, etc.)
- `@RestController` → returns **data** (JSON/XML) directly

---

### 3.3 @RequestMapping

```java
@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
public String helloWorld() {
    return "Hello World";
}
```

**Definition:** The general-purpose annotation for mapping HTTP requests to handler methods. You specify the HTTP method and URL path.

**Interview line:** "@RequestMapping is the most flexible mapping annotation — it lets me specify the HTTP method, path, headers, and media types. It's the parent annotation from which shortcut annotations like @GetMapping are derived."

---

### 3.4 @GetMapping

```java
@GetMapping(path = "/hello-world")
public String helloWorld() {
    return "Hello World";
}
```

**Definition:** A shortcut for `@RequestMapping(method = RequestMethod.GET)`. Cleaner, more readable.

```
@GetMapping("/path")    ← shortcut
@PostMapping("/path")   ← shortcut for POST
@PutMapping("/path")    ← shortcut for PUT
@DeleteMapping("/path") ← shortcut for DELETE
@PatchMapping("/path")  ← shortcut for PATCH
```

**Interview line:** "@GetMapping is a composed annotation — a shorthand for @RequestMapping with method=GET. It improves readability. Similarly, @PostMapping, @PutMapping, and @DeleteMapping exist for their respective HTTP methods."

---

### 3.5 @PathVariable

```java
@GetMapping("/hello-world/path-variable/{name}")
public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
    return new HelloWorldBean(String.format("Hello World, %s", name));
}
```

**Definition:** Extracts a value from the **URL path** and binds it to a method parameter.

```
URL:    /users/42
Mapping: /users/{id}
                 ↓
@PathVariable int id  →  id = 42
```

**Interview line:** "@PathVariable binds a URI template variable to a method parameter. For example, in `/users/{id}`, the {id} segment gets extracted and injected into the method argument."

---

### 3.6 @Component

```java
@Component
public class UserDaoService { ... }
```

**Definition:** Tells Spring to **create and manage an instance (bean)** of this class. Spring's component scanner detects it and adds it to the Application Context (the IoC container).

```
Spring Stereotype Annotations (all are specializations of @Component):

@Component         → Generic Spring-managed bean
    ├── @Service       → Business logic layer
    ├── @Repository    → Data access layer (adds DB exception translation)
    └── @Controller    → Web/presentation layer
```

**Interview line:** "@Component is the generic stereotype annotation that registers a class as a Spring bean. @Service, @Repository, and @Controller are specializations — they all do what @Component does but add semantic clarity about the layer the class belongs to."

**Why @Component on UserDaoService?** Because we need Spring to manage its lifecycle so it can be injected (via @Autowired) into the controller.

---

### 3.7 @RequestBody

```java
@PostMapping("/users")
public ResponseEntity<Object> createUser(@RequestBody User user) { ... }
```

**Definition:** Tells Spring to **deserialize the HTTP request body** (usually JSON) into a Java object.

```
POST /users
Content-Type: application/json

{                              @RequestBody
  "name": "Shrey",        ──────────────►   User object
  "birthDate": "1994-01-15"                  name = "Shrey"
}                                            birthDate = 1994-01-15
```

**How does JSON → Java Object conversion happen?**
Jackson's `JacksonHttpMessageConverters` (auto-configured by Spring Boot) handles it.

**Interview line:** "@RequestBody binds the HTTP request body to a method parameter. Spring uses Jackson's HttpMessageConverter to deserialize JSON into the specified Java type automatically."

---

### 3.8 ResponseEntity

```java
return ResponseEntity.created(location).build();
```

**Definition:** `ResponseEntity` is a class (NOT an annotation) that represents the **entire HTTP response**: status code + headers + body.

```
ResponseEntity gives you full control:

ResponseEntity.ok(body)              → 200 OK + body
ResponseEntity.created(uri).build()  → 201 Created + Location header
ResponseEntity.noContent().build()   → 204 No Content
ResponseEntity.notFound().build()    → 404 Not Found
ResponseEntity.badRequest().body(x)  → 400 Bad Request + body
```

**Interview line:** "ResponseEntity is a wrapper that lets me control the HTTP status code, headers, and body of the response. For example, after creating a resource, I return ResponseEntity.created() with a Location header pointing to the new resource's URI — which follows REST best practices."

---

### 3.9 ResponseEntity.created(location).build()

Let's break this down piece by piece:

```java
URI location = ServletUriComponentsBuilder
    .fromCurrentRequest()       // starts from: http://localhost:8080/users
    .path("/{id}")              // appends:     /{id}
    .buildAndExpand(savedUser.getId())  // replaces {id} with actual ID, e.g. 3
    .toUri();                   // result: http://localhost:8080/users/3

return ResponseEntity
    .created(location)   // Sets status = 201 CREATED
                         // Sets Location header = http://localhost:8080/users/3
    .build();            // Builds the response (no body)
```

**What the client receives:**

```
HTTP/1.1 201 Created
Location: http://localhost:8080/users/3
```

**Why this matters for REST:** The `Location` header tells the client exactly where to find the newly created resource. This is a REST best practice.

**Interview line:** "After a successful POST, I return a 201 Created status with a Location header built using ServletUriComponentsBuilder. This tells the client the URI of the newly created resource, following RESTful conventions."

---

### 3.10 @Autowired

```java
@Autowired
private UserDaoService service;
```

**Definition:** Tells Spring to **inject a matching bean** from the Application Context into this field. This is Dependency Injection (DI).

```
Without @Autowired (tight coupling):
    UserDaoService service = new UserDaoService();  // YOU create it

With @Autowired (loose coupling):
    Spring creates UserDaoService (because of @Component)
    Spring injects it into the controller automatically
```

**Interview line:** "@Autowired enables dependency injection — Spring automatically resolves and injects the required bean. This promotes loose coupling because the controller doesn't instantiate its dependencies directly."

> **Note:** In modern Spring, if a class has only ONE constructor, @Autowired is optional — constructor injection happens automatically.

---

---

## 4. THE REQUEST FLOW — How Spring Handles a Request

This is a **critical interview topic**. Here's the complete flow:

```
CLIENT (Browser / Postman)
    │
    │  GET /hello-world
    ▼
┌─────────────────────────────┐
│     EMBEDDED TOMCAT         │  ← Provided by spring-boot-starter-web
│     (Port 8080)             │
└─────────────┬───────────────┘
              │
              ▼
┌─────────────────────────────┐
│     DISPATCHER SERVLET      │  ← Front Controller Pattern
│                             │
│  • Receives ALL requests    │
│  • Consults Handler Mapping │
│  • Routes to right controller│
│  • Configured at url = [/]  │
└─────────────┬───────────────┘
              │  Finds: HelloWorldController.helloWorld()
              ▼
┌─────────────────────────────┐
│     CONTROLLER METHOD       │
│                             │
│  @GetMapping("/hello-world")│
│  return "Hello World";      │
└─────────────┬───────────────┘
              │
              ▼
┌─────────────────────────────┐
│  JACKSON MESSAGE CONVERTER  │  ← Converts return value to JSON
│  (HttpMessageConverter)     │
└─────────────┬───────────────┘
              │
              ▼
         HTTP RESPONSE
         200 OK
         "Hello World"
```

### Key Background Concepts:

**1. DispatcherServlet — The Front Controller**
- **What:** A single servlet that intercepts ALL incoming HTTP requests
- **Pattern:** Front Controller Design Pattern
- **Mapping:** `dispatcherServlet urls=[/]` (catches everything)
- **Configured by:** `DispatcherServletAutoConfiguration` (auto-config by Spring Boot)
- **Interview line:** "DispatcherServlet is the front controller in Spring MVC. It intercepts all requests, consults the handler mapping to find the right controller method, invokes it, and returns the response. Spring Boot auto-configures it through DispatcherServletAutoConfiguration."

**2. JSON Conversion — How HelloWorldBean becomes JSON**
- `@ResponseBody` (included in `@RestController`) tells Spring to serialize the return value
- Jackson's `JacksonHttpMessageConverters` does the actual conversion
- **Configured by:** `JacksonHttpMessageConvertersConfiguration` (auto-config)

**3. Error Handling**
- If you hit a wrong URL → Spring returns a Whitelabel Error Page
- **Configured by:** `ErrorMvcAutoConfiguration` (auto-config)

**4. How to see all this yourself**
Add to `application.properties`:
```
logging.level.org.springframework=debug
```
This enables debug logging so you can see DispatcherServlet mapping, auto-configuration reports, and bean creation.

> **WARNING:** Debug logs change frequently between Spring Boot versions. Don't memorize specific log lines.

---

---

## 5. REST API Concepts

### 5.1 What is REST?

**REST** = **RE**presentational **S**tate **T**ransfer

It's an architectural style for designing networked APIs. Resources are identified by URIs, and actions are performed using standard HTTP methods.

**Interview line:** "REST is an architectural style where resources are identified by URIs and manipulated using standard HTTP methods. It's stateless, meaning each request contains all the information the server needs."

### 5.2 HTTP Methods (CRUD Operations)

```
┌──────────┬──────────────────────────────┬─────────────┐
│  METHOD  │  PURPOSE                     │  CRUD       │
├──────────┼──────────────────────────────┼─────────────┤
│  GET     │  Retrieve details of a       │  READ       │
│          │  resource                    │             │
├──────────┼──────────────────────────────┼─────────────┤
│  POST    │  Create a new resource       │  CREATE     │
├──────────┼──────────────────────────────┼─────────────┤
│  PUT     │  Update an existing resource │  UPDATE     │
│          │  (full replacement)          │  (full)     │
├──────────┼──────────────────────────────┼─────────────┤
│  PATCH   │  Update part of a resource   │  UPDATE     │
│          │  (partial update)            │  (partial)  │
├──────────┼──────────────────────────────┼─────────────┤
│  DELETE  │  Delete a resource           │  DELETE     │
└──────────┴──────────────────────────────┴─────────────┘
```

**PUT vs PATCH — Interview favorite:**
- **PUT:** Sends the COMPLETE updated resource. If you omit a field, it gets set to null.
- **PATCH:** Sends ONLY the fields you want to change. Other fields remain untouched.

### 5.3 Social Media App — Resources & Methods

```
USERS REST API
─────────────────────────────────────────────────
Retrieve all Users     │  GET     /users
Create a User          │  POST    /users
Retrieve one User      │  GET     /users/{id}      → e.g. /users/1
Delete a User          │  DELETE  /users/{id}      → e.g. /users/1

POSTS REST API (nested resource — posts belong to a user)
─────────────────────────────────────────────────
Retrieve all posts     │  GET     /users/{id}/posts
for a User             │
Create a post          │  POST    /users/{id}/posts
for a User             │
Retrieve one post      │  GET     /users/{id}/posts/{post_id}
```

**Why nested URIs?** Posts belong to a user, so the URI structure reflects that relationship: `/users/2/posts/100` means "post 100 by user 2."

**Sample JSON response for GET /users:**
```json
[
  { "id": 1, "name": "Adam",  "birthDate": "2017-07-18T09:33:30.034+0000" },
  { "id": 2, "name": "Eve",   "birthDate": "2017-07-18T09:33:30.034+0000" },
  { "id": 3, "name": "Jack",  "birthDate": "2017-07-18T09:33:30.035+0000" }
]
```

---

---

## 6. CODE WALKTHROUGH — HelloWorldController

### The class:

```java
@RestController
public class HelloWorldController {

    // ── Endpoint 1: Returns a plain String ──
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    // ── Endpoint 2: Returns an Object (→ auto-converted to JSON) ──
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    // ── Endpoint 3: Uses Path Variable ──
    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
```

### What happens at each endpoint:

```
GET /hello-world
    → Returns plain string: "Hello World"
    → Response Content-Type: text/plain

GET /hello-world-bean
    → Returns HelloWorldBean object
    → Jackson converts it to: { "message": "Hello World" }
    → Response Content-Type: application/json

GET /hello-world/path-variable/Ranga
    → {name} = "Ranga"
    → Returns: { "message": "Hello World, Ranga" }
```

### HelloWorldBean class (what it needs):

```java
public class HelloWorldBean {
    private String message;

    public HelloWorldBean(String message) {   // Constructor
        this.message = message;
    }

    public String getMessage() { ... }        // Getter  ← Jackson uses this!
    public void setMessage(String msg) { ... }// Setter
    public String toString() { ... }          // toString
}
```

**Why does Jackson need the getter?** Jackson serializes by calling getter methods. No `getMessage()` → no `"message"` field in the JSON output.

---

---

## 7. CODE WALKTHROUGH — DAO Layer & UserController

### 7.1 Why Do We Need a DAO Layer?

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│  CONTROLLER  │────►│   DAO / SERVICE   │────►│  DATABASE    │
│  (REST API)  │     │   (Data Access)   │     │  (H2 / MySQL)│
│              │◄────│                   │◄────│              │
└──────────────┘     └──────────────┘     └──────────────┘
   Handles HTTP        Business logic        Stores data
   requests            + data operations
```

**DAO** = **D**ata **A**ccess **O**bject

**Why separate layers?**
- **Separation of Concerns:** Controller handles HTTP; DAO handles data
- **Reusability:** Multiple controllers can use the same DAO
- **Testability:** You can test DAO independently, mock it in controller tests
- **Swappability:** Switch from in-memory list to MySQL without changing the controller

**Interview line:** "The DAO layer abstracts data access logic from the controller. This separation of concerns makes the code more testable, maintainable, and allows me to swap data sources — say from an in-memory list to a database — without modifying the controller."

---

### 7.2 UserDaoService — Line by Line

```java
@Component  // ← Registers this as a Spring bean so it can be @Autowired
public class UserDaoService {

    // ── In-memory "database" ──
    private static List<User> users = new ArrayList<User>();
    private static int userId;
```

#### Explaining: `private static List<User> users = new ArrayList<User>();`

```
private   → Only this class can access it
static    → Shared across ALL instances (belongs to the class, not an object)
List<User>→ A list that holds User objects
users     → Variable name
= new ArrayList<User>()  → Creates a resizable array implementation of List

WHY STATIC?
Spring creates ONE instance of UserDaoService (@Component = singleton by default).
But even if multiple instances existed, static ensures they ALL share the same
list — simulating a shared database.

Think of it as: a temporary in-memory database that lives as long as the app runs.
```

**Interview line:** "This is a static ArrayList acting as an in-memory data store. It's static so the data is shared at the class level. In a real application, this would be replaced by a JPA Repository backed by an actual database."

#### Static initializer block:

```java
    static {
        users.add(new User(userId++, "Shrey", LocalDate.now().minusYears(30)));
        users.add(new User(userId++, "Khushi", LocalDate.now().minusYears(26)));
        users.add(new User(userId++, "Gahan", LocalDate.now().minusYears(2)));
    }
```

This runs ONCE when the class is loaded. It pre-populates the list with sample data. `userId++` assigns the current value then increments (0, 1, 2...).

#### findAll() — Retrieve all users:

```java
    public List<User> findAll() {
        return users;  // Returns the entire list
    }
```

#### findOne(id) — Retrieve one user using Java Streams:

```java
    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId() == id;
        return users.stream().filter(predicate).findFirst().orElse(null);
    }
```

```
Breaking it down:

users.stream()          → Convert list to a stream (for functional operations)
    .filter(predicate)  → Keep only users where getId() == id
    .findFirst()        → Get the first match (returns Optional<User>)
    .orElse(null)       → If no match found, return null

Predicate<? super User> → A functional interface that takes a User
                           and returns true/false
user -> user.getId() == id  → Lambda: "does this user's ID match?"
```

#### saveUser(user) — Create a new user:

```java
    public User saveUser(User user) {
        user.setId(userId++);  // Assign next available ID
        users.add(user);       // Add to the in-memory list
        return user;           // Return saved user (now has an ID)
    }
```

---

### 7.3 UserController — Line by Line

```java
@RestController
public class UserController {

    @Autowired
    private UserDaoService service;  // Spring injects the DAO bean here
```

#### GET /users — Retrieve all users:

```java
    @GetMapping("/users")
    public List<User> retreieveAllUsers() {
        return service.findAll();
    }
```

```
Request:   GET http://localhost:8080/users
Response:  200 OK
           [
             { "id": 0, "name": "Shrey", "birthDate": "..." },
             { "id": 1, "name": "Khushi", "birthDate": "..." },
             { "id": 2, "name": "Gahan", "birthDate": "..." }
           ]
```

#### GET /users/{id} — Retrieve one user:

```java
    @GetMapping("/users/{id}")
    public User retreieveOneUser(@PathVariable int id) {
        return service.findOne(id);
    }
```

```
Request:   GET http://localhost:8080/users/1
Response:  200 OK
           { "id": 1, "name": "Khushi", "birthDate": "..." }
```

#### POST /users — Create a user:

```java
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User savedUser = service.saveUser(user);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()               // http://localhost:8080/users
            .path("/{id}")                      // http://localhost:8080/users/{id}
            .buildAndExpand(savedUser.getId())  // http://localhost:8080/users/3
            .toUri();

        return ResponseEntity.created(location).build();
    }
```

```
Request:   POST http://localhost:8080/users
           Content-Type: application/json
           Body: { "name": "Rohan", "birthDate": "1995-05-20" }

Response:  201 Created
           Location: http://localhost:8080/users/3
           (no body)

Flow:
  1. @RequestBody deserializes JSON → User object
  2. service.saveUser() assigns ID and adds to list
  3. ServletUriComponentsBuilder builds the URI for the new resource
  4. ResponseEntity.created(location) sets status 201 + Location header
  5. .build() sends the response with no body
```

---

---

## 8. KEY JAVA CONCEPTS USED

### 8.1 Predicate (Functional Interface)

```java
Predicate<User> p = user -> user.getId() == id;
```

A Predicate takes one argument and returns a boolean. Used with `.filter()` in streams.

### 8.2 Java Streams

```java
users.stream().filter(...).findFirst().orElse(null)
```

Streams provide a functional way to process collections: filter, map, reduce, collect — without writing loops.

### 8.3 Static Keyword

```
static field   → Belongs to the CLASS, not to any instance
static method  → Can be called without creating an object
static block   → Runs once when the class is first loaded
```

### 8.4 Optional

`findFirst()` returns `Optional<User>` — a container that may or may not hold a value. `.orElse(null)` unwraps it, returning null if empty.

---

---

## 9. QUICK INTERVIEW CHEAT SHEET

```
┌─────────────────────────────────────────────────────────────────┐
│                    RAPID FIRE ANSWERS                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  @RestController = @Controller + @ResponseBody                  │
│                                                                 │
│  @GetMapping = @RequestMapping(method=GET)                      │
│                                                                 │
│  @SpringBootApplication = @Configuration                        │
│                         + @EnableAutoConfiguration               │
│                         + @ComponentScan                         │
│                                                                 │
│  DispatcherServlet = Front Controller (routes all requests)     │
│                                                                 │
│  Jackson = JSON ↔ Java conversion library                       │
│                                                                 │
│  @Component = "Spring, manage this class as a bean"             │
│  @Autowired = "Spring, inject the matching bean here"           │
│                                                                 │
│  @PathVariable = Extract value from URL path                    │
│  @RequestBody  = Deserialize request body to Java object        │
│                                                                 │
│  ResponseEntity = Full HTTP response (status + headers + body)  │
│  201 Created + Location header = REST best practice for POST    │
│                                                                 │
│  DAO = Data Access Object (separates data logic from controller)│
│                                                                 │
│  PUT = full update  |  PATCH = partial update                   │
│                                                                 │
│  Static list = in-memory DB substitute for prototyping          │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

*Notes will continue building as you progress through the course. Next topics to cover: Exception Handling, Validation, HATEOAS, JPA Integration, Versioning, Content Negotiation, Swagger/OpenAPI, Security, and Filtering.*
