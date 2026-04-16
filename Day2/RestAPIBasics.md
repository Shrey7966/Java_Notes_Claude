# SPRING BOOT RESTful Web Services — Interview Notes
### Built module-by-module, basic → advanced. Each module picks up where the last one ended.

---
---

# MODULE 1: PROJECT SETUP & FOUNDATIONS

Before writing any code, you create a Spring Boot project (via start.spring.io) and select dependencies. This module covers what Spring Boot is, what each dependency does, and what happens when you hit "Run."

---

## 1.1 What is Spring Boot?

Spring is a Java framework for building enterprise apps. But raw Spring requires tons of XML/Java configuration. Spring Boot sits on top of Spring and **auto-configures everything** — embedded server, component scanning, JSON conversion — so you write business logic, not plumbing.

**Interview line:**
> "Spring Boot is an opinionated framework on top of Spring that uses auto-configuration, starter dependencies, and an embedded server to eliminate boilerplate setup."

---

## 1.2 Dependencies (pom.xml)

When you create the project, you pick these four dependencies. Each one is a "starter" — a single entry in `pom.xml` that pulls in many JARs transitively.

```
┌─────────────────────────────────────────────────────────────────────┐
│  pom.xml                                                            │
│                                                                     │
│  spring-boot-starter-web ──────► Spring MVC + Tomcat + Jackson      │
│  spring-boot-starter-data-jpa ─► JPA + Hibernate + Spring Data      │
│  h2 ──────────────────────────► In-memory database (dev/testing)    │
│  spring-boot-devtools ────────► Auto-restart on code changes        │
└─────────────────────────────────────────────────────────────────────┘
```

**spring-boot-starter-web** — This is the most important one for REST APIs. It gives you:
- **Spring MVC** — the framework for handling HTTP requests
- **Embedded Tomcat** — a web server baked into your JAR (no external server needed)
- **Jackson** — converts Java objects ↔ JSON automatically

**spring-boot-starter-data-jpa** — For database access. Gives you JPA (Java Persistence API) and Hibernate (the ORM that maps Java objects to database tables). You won't use this immediately — it becomes essential in Module 7 when you swap the in-memory list for a real database.

**h2** — A lightweight in-memory database. Data resets on every restart. Perfect for development; you'd swap it for MySQL/PostgreSQL in production.

**spring-boot-devtools** — Quality-of-life tool. When you save a file, the server auto-restarts. No manual stop → start cycle.

**Interview line (starter projects):**
> "Starter projects are curated dependency descriptors. Adding one starter transitively pulls in all the libraries needed for that capability. For example, `spring-boot-starter-web` brings in Spring MVC, embedded Tomcat, Jackson, and their transitive dependencies."

---

## 1.3 The Entry Point — @SpringBootApplication

Every Spring Boot app has one main class:

```java
@SpringBootApplication
public class FirstSpringProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(FirstSpringProjectApplication.class, args);
    }
}
```

`@SpringBootApplication` is a shortcut that combines three annotations:

```
@SpringBootApplication
    ├── @Configuration          → "This class can define beans"
    ├── @EnableAutoConfiguration → "Auto-configure based on JARs on classpath"
    └── @ComponentScan          → "Scan this package + sub-packages for @Component classes"
```

When `SpringApplication.run()` executes, Spring Boot:
1. Starts the embedded Tomcat server
2. Scans for components (@Component, @RestController, etc.)
3. Auto-configures everything (DispatcherServlet, Jackson, error handling, etc.)
4. Registers all discovered beans in the Application Context (the IoC container)

**Interview line:**
> "@SpringBootApplication is a meta-annotation combining @Configuration, @EnableAutoConfiguration, and @ComponentScan. It marks the entry point and triggers component scanning and auto-configuration."

---

## 1.4 What Happens When You Hit Run — The Background Magic

Add this to `application.properties` to see it yourself:
```
logging.level.org.springframework=debug
```

Here's what Spring Boot auto-configures behind the scenes:

**Question 1: How are requests handled?**
→ `DispatcherServlet` — configured by `DispatcherServletAutoConfiguration`
→ It maps to `urls=[/]` — catches ALL incoming requests
→ It's the **Front Controller** (a design pattern — one central servlet routes to the right controller)

**Question 2: How do Java objects become JSON?**
→ `@ResponseBody` (part of `@RestController`) signals "serialize the return value"
→ Jackson's `JacksonHttpMessageConverters` does the actual conversion
→ Configured by `JacksonHttpMessageConvertersConfiguration`

**Question 3: Who handles error pages?**
→ `ErrorMvcAutoConfiguration` — gives you the Whitelabel Error Page

**Question 4: How are all JARs available (Spring MVC, Jackson, Tomcat)?**
→ **Starter Projects** — one starter pulls in everything transitively

```
                         THE REQUEST LIFECYCLE
                         =====================

    Browser / Postman
          │
          │  HTTP Request (e.g. GET /hello-world)
          ▼
    ┌───────────────┐
    │ Embedded       │   Provided by spring-boot-starter-web
    │ Tomcat :8080   │
    └───────┬───────┘
            ▼
    ┌───────────────┐
    │ Dispatcher     │   Auto-configured by Spring Boot
    │ Servlet        │   Front Controller Pattern — catches ALL requests
    │                │   Consults Handler Mapping to find the right method
    └───────┬───────┘
            ▼
    ┌───────────────┐
    │ Your           │   The @RestController method you wrote
    │ Controller     │   e.g. helloWorld() returns "Hello World"
    │ Method         │
    └───────┬───────┘
            ▼
    ┌───────────────┐
    │ Jackson        │   Converts return value → JSON
    │ Message        │   (if returning an object)
    │ Converter      │
    └───────┬───────┘
            ▼
       HTTP Response
       200 OK + body
```

**Interview line:**
> "All requests go through the DispatcherServlet, which is the front controller. It consults handler mappings to route each request to the correct controller method. Spring Boot auto-configures the DispatcherServlet, Jackson message converters, and error handling through its auto-configuration classes."

---
---

# MODULE 2: YOUR FIRST REST ENDPOINT — HelloWorldController

Now you have a running app. In this module you create your first REST controller, expose a simple GET endpoint, then evolve it to return a Java object and use path variables.

---

## 2.1 Creating the Controller

**File:** `src/main/java/.../helloworld/HelloWorldController.java`

```java
@RestController
public class HelloWorldController { ... }
```

### @RestController — what it means

```
@RestController
    ├── @Controller    → Registers this class as a Spring MVC controller
    └── @ResponseBody  → "Write return values directly to the HTTP response
                          body (as JSON/XML), NOT as a view/template name"
```

**Key distinction:**
- `@Controller` → returns a **view name** (e.g., an HTML page rendered by Thymeleaf)
- `@RestController` → returns **data** (JSON/XML) directly to the client

**Interview line:**
> "@RestController combines @Controller and @ResponseBody. Every method returns data serialized directly to the response body — typically as JSON — rather than a view name."

---

## 2.2 Endpoint v1 — Return a String

```java
@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
public String helloWorld() {
    return "Hello World";
}
```

### @RequestMapping

The **general-purpose** mapping annotation. You specify the HTTP method and path explicitly.

**Interview line:**
> "@RequestMapping is the most flexible request mapping annotation — you can specify method, path, headers, produces, and consumes. The shortcut annotations (@GetMapping, etc.) are derived from it."

### Refactored with @GetMapping

```java
@GetMapping(path = "/hello-world")
public String helloWorld() {
    return "Hello World";
}
```

`@GetMapping` is just a shortcut for `@RequestMapping(method = RequestMethod.GET)`. Cleaner and more readable.

```
Shortcut annotations (all derive from @RequestMapping):

    @GetMapping     →  method = GET
    @PostMapping    →  method = POST
    @PutMapping     →  method = PUT
    @DeleteMapping  →  method = DELETE
    @PatchMapping   →  method = PATCH
```

**Test it:** `GET http://localhost:8080/hello-world` → returns `"Hello World"` (plain text)

---

## 2.3 Endpoint v2 — Return a Java Object (→ JSON)

What if you want to return structured data, not just a string?

**Step 1:** Create a `HelloWorldBean` class with a constructor, getter, setter, and toString:
```java
public class HelloWorldBean {
    private String message;
    public HelloWorldBean(String message) { this.message = message; }
    public String getMessage() { return message; }   // ← Jackson uses THIS
    public void setMessage(String m) { this.message = m; }
    public String toString() { return "HelloWorldBean [message=" + message + "]"; }
}
```

**Step 2:** Return it from a new endpoint:
```java
@GetMapping(path = "/hello-world-bean")
public HelloWorldBean helloWorldBean() {
    return new HelloWorldBean("Hello World");
}
```

**What happens behind the scenes:**
```
Your method returns → HelloWorldBean object
                          │
                          ▼
Jackson sees getMessage() → extracts "message" field
                          │
                          ▼
Client receives →  { "message": "Hello World" }
```

**Critical point:** Jackson serializes by calling **getter methods**. If your class has `getMessage()`, the JSON will have a `"message"` field. No getter = no field in JSON.

**Test it:** `GET http://localhost:8080/hello-world-bean` → returns `{"message":"Hello World"}`

---

## 2.4 Endpoint v3 — Path Variables

You want dynamic URLs like `/hello-world/path-variable/Ranga` where the name changes.

```java
@GetMapping(path = "/hello-world/path-variable/{name}")
public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
    return new HelloWorldBean(String.format("Hello World, %s", name));
}
```

### @PathVariable

Extracts a value from the URL path and binds it to a method parameter.

```
URL pattern:    /hello-world/path-variable/{name}
Actual URL:     /hello-world/path-variable/Ranga
                                           ─────
                                             │
                @PathVariable String name ◄──┘   name = "Ranga"
```

Another example:
```
URL:      /users/42
Pattern:  /users/{id}
                  ↓
@PathVariable int id  →  id = 42
```

**Test it:** `GET http://localhost:8080/hello-world/path-variable/Ranga` → `{"message":"Hello World, Ranga"}`

**Interview line:**
> "@PathVariable binds a URI template variable to a method parameter. The variable name in the path template must match the parameter name — or you can use @PathVariable('name') to map explicitly."

---

**What you've built so far:**
```
HelloWorldController
    ├── GET /hello-world              → "Hello World" (String)
    ├── GET /hello-world-bean         → {"message":"Hello World"} (JSON)
    └── GET /hello-world/path-variable/{name} → {"message":"Hello World, Ranga"}
```

---
---

# MODULE 3: BUILDING A REAL REST API — Users Resource

You've proven you can expose endpoints. Now you build a proper REST API for a social media app. This module introduces the layered architecture (Controller → DAO), the User model, and CRUD operations.

---

## 3.1 REST API Design — Resources & Methods

First, plan your API. In REST, you think in **resources** (nouns), not actions (verbs).

```
HTTP Methods (the verbs of REST):

┌──────────┬───────────────────────────────┬──────────┐
│ METHOD   │ PURPOSE                       │ CRUD     │
├──────────┼───────────────────────────────┼──────────┤
│ GET      │ Retrieve a resource           │ READ     │
│ POST     │ Create a new resource         │ CREATE   │
│ PUT      │ Replace entire resource       │ UPDATE   │
│ PATCH    │ Update part of a resource     │ UPDATE   │
│ DELETE   │ Remove a resource             │ DELETE   │
└──────────┴───────────────────────────────┴──────────┘
```

**PUT vs PATCH (common interview question):**
- PUT sends the COMPLETE object. Missing fields become null.
- PATCH sends ONLY the changed fields. Everything else stays.

**Social Media App — Full API Design:**
```
USERS
    GET    /users           → Retrieve all users
    POST   /users           → Create a user
    GET    /users/{id}      → Retrieve one user       (e.g. /users/1)
    DELETE /users/{id}      → Delete a user            (e.g. /users/1)

POSTS (nested under users — a post belongs to a user)
    GET    /users/{id}/posts            → All posts for a user
    POST   /users/{id}/posts            → Create a post for a user
    GET    /users/{id}/posts/{post_id}  → One specific post
```

---

## 3.2 The Layered Architecture

You don't put everything in one file. Spring apps follow a layered structure:

```
┌──────────────────────┐
│     CONTROLLER       │  Handles HTTP requests/responses
│  (UserController)    │  Annotations: @RestController, @GetMapping, etc.
│                      │  Returns: ResponseEntity, JSON objects
└──────────┬───────────┘
           │  calls methods on
           ▼
┌──────────────────────┐
│     DAO / SERVICE    │  Business logic + data access
│  (UserDaoService)    │  Annotation: @Component (or @Service)
│                      │  Contains: findAll(), findOne(), saveUser()
└──────────┬───────────┘
           │  reads/writes to
           ▼
┌──────────────────────┐
│     DATA STORE       │  Currently: static ArrayList (in-memory)
│                      │  Later: H2 Database via JPA (Module 7)
└──────────────────────┘
```

**Why separate layers?**
- **Separation of concerns** — Controller handles HTTP, DAO handles data
- **Reusability** — Multiple controllers can share the same DAO
- **Testability** — You can mock the DAO when testing the controller
- **Swappability** — Switch from in-memory list to MySQL without touching the controller

**Interview line:**
> "I follow a layered architecture — controllers handle HTTP concerns, service/DAO handles business logic and data access. This separation makes the code testable, reusable, and allows swapping data sources without changing the controller."

---

## 3.3 The User Model

Create a `User` class inside a `users` package:

```java
public class User {
    private Integer id;
    private String name;
    private LocalDate birthDate;

    // Constructor, getters, setters, toString
}
```

This is a **POJO** (Plain Old Java Object). Jackson will serialize/deserialize it to/from JSON.

---

## 3.4 The DAO Layer — UserDaoService

**File:** `src/main/java/.../dao/UserDaoService.java`

```java
@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<User>();
    private static int userId;

    static {
        users.add(new User(userId++, "Shrey", LocalDate.now().minusYears(30)));
        users.add(new User(userId++, "Khushi", LocalDate.now().minusYears(26)));
        users.add(new User(userId++, "Gahan", LocalDate.now().minusYears(2)));
    }

    public List<User> findAll()    { return users; }
    public User findOne(int id)    { ... }
    public User saveUser(User user){ ... }
}
```

### @Component — making Spring manage this class

`@Component` tells Spring: "Create an instance of this class and put it in the Application Context." This makes it available for injection.

```
Stereotype annotations (all specializations of @Component):

@Component     → Generic bean (any layer)
    ├── @Service     → Business/service layer (semantic — no extra behavior)
    ├── @Repository  → Data access layer (adds automatic exception translation)
    └── @Controller  → Web layer (pairs with @RequestMapping)
```

**Why @Component on UserDaoService?** Because UserController needs it injected via @Autowired. Without @Component, Spring doesn't know this class exists.

**Interview line:**
> "@Component is the generic stereotype annotation that registers a class as a Spring-managed bean. @Service, @Repository, and @Controller are specializations that add semantic clarity. All of them trigger component scanning and bean registration."

### `private static List<User> users = new ArrayList<User>();` — Explained

```
private    → Only accessible within this class
static     → Belongs to the CLASS itself, not to any single instance
             (all instances share this same list)
List<User> → A list holding User objects
new ArrayList<User>() → A resizable array-backed implementation of List
```

**Why static?** This simulates a shared database. Even though Spring creates only one instance of UserDaoService (singleton scope by default), `static` makes the intent explicit — this data belongs to the class, not the instance.

**Think of it as:** A temporary in-memory database that lives as long as the application runs. It resets on every restart. In Module 7 you'll replace this with a real database.

### The `static { }` initializer block

```java
static {
    users.add(new User(userId++, "Shrey", ...));
    users.add(new User(userId++, "Khushi", ...));
    users.add(new User(userId++, "Gahan", ...));
}
```

Runs **once** when the class is first loaded into memory. Pre-populates the list with sample data.

`userId++` = post-increment. Assigns the current value (0, 1, 2) THEN increments.

### findOne() — Java Streams breakdown

```java
public User findOne(int id) {
    Predicate<? super User> predicate = user -> user.getId() == id;
    return users.stream().filter(predicate).findFirst().orElse(null);
}
```

```
Step by step:

users.stream()          → Converts the ArrayList to a Stream
  .filter(predicate)    → Keeps only elements where predicate returns true
  .findFirst()          → Returns Optional<User> — the first match
  .orElse(null)         → Unwraps the Optional; returns null if empty

Predicate<? super User> → A functional interface: takes User → returns boolean
user -> user.getId() == id → Lambda expression: "does this user's ID match?"
```

---

## 3.5 The Controller — UserController

```java
@RestController
public class UserController {

    @Autowired
    private UserDaoService service;
```

### @Autowired — Dependency Injection

```
WITHOUT @Autowired (tight coupling — you create it yourself):
    UserDaoService service = new UserDaoService();

WITH @Autowired (loose coupling — Spring creates and injects it):
    Spring sees @Component on UserDaoService → creates the bean
    Spring sees @Autowired on this field → injects the bean
```

**Interview line:**
> "@Autowired enables dependency injection. Spring resolves the required bean from the Application Context and injects it automatically. This promotes loose coupling — the controller doesn't know or care how the DAO is constructed."

> **Modern note:** If a class has only ONE constructor, @Autowired is optional — Spring does constructor injection automatically.

### GET /users — Retrieve all users

```java
@GetMapping("/users")
public List<User> retreieveAllUsers() {
    return service.findAll();
}
```

```
Request:  GET http://localhost:8080/users
Response: 200 OK
[
  { "id": 0, "name": "Shrey",  "birthDate": "1996-04-15" },
  { "id": 1, "name": "Khushi", "birthDate": "2000-04-15" },
  { "id": 2, "name": "Gahan",  "birthDate": "2024-04-15" }
]
```

### GET /users/{id} — Retrieve one user

```java
@GetMapping("/users/{id}")
public User retreieveOneUser(@PathVariable int id) {
    return service.findOne(id);
}
```

```
Request:  GET http://localhost:8080/users/1
Response: 200 OK
{ "id": 1, "name": "Khushi", "birthDate": "2000-04-15" }
```

### POST /users — Create a user

```java
@PostMapping("/users")
public ResponseEntity<Object> createUser(@RequestBody User user) {
    User savedUser = service.saveUser(user);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedUser.getId())
        .toUri();
    return ResponseEntity.created(location).build();
}
```

This introduces three new concepts: `@RequestBody`, `ResponseEntity`, and `ServletUriComponentsBuilder`.

### @RequestBody

Tells Spring to take the HTTP request body (JSON) and convert it into a Java object.

```
POST /users
Content-Type: application/json
                                         @RequestBody
{                                    ──────────────────►   User object
  "name": "Rohan",                                         name = "Rohan"
  "birthDate": "1995-05-20"                                birthDate = 1995-05-20
}
```

Jackson does the conversion using setter methods on the User class.

**Interview line:**
> "@RequestBody binds the HTTP request body to a method parameter. Spring uses Jackson's HttpMessageConverter to deserialize JSON into the target Java type."

### ResponseEntity

A class (NOT an annotation) that gives you full control over the HTTP response: status code, headers, and body.

```
Common factory methods:

ResponseEntity.ok(body)               → 200 OK + body
ResponseEntity.created(uri).build()   → 201 Created + Location header (no body)
ResponseEntity.noContent().build()    → 204 No Content
ResponseEntity.notFound().build()     → 404 Not Found
ResponseEntity.badRequest().body(err) → 400 Bad Request + error body
```

### ServletUriComponentsBuilder — building the Location header

After creating a resource via POST, REST best practice says: return status 201 and a `Location` header pointing to the new resource.

```java
URI location = ServletUriComponentsBuilder
    .fromCurrentRequest()               // http://localhost:8080/users
    .path("/{id}")                      // appends /{id} to the path
    .buildAndExpand(savedUser.getId())  // replaces {id} with actual value (e.g. 3)
    .toUri();                           // → http://localhost:8080/users/3
```

```
Client sends:   POST /users  { "name": "Rohan", "birthDate": "1995-05-20" }

Server returns: HTTP/1.1 201 Created
                Location: http://localhost:8080/users/3
                (no body)
```

**Interview line:**
> "After a successful POST, I return 201 Created with a Location header built using ServletUriComponentsBuilder. This header tells the client the URI of the newly created resource, following REST conventions."

---

**What you've built so far:**
```
HelloWorldController
    ├── GET /hello-world
    ├── GET /hello-world-bean
    └── GET /hello-world/path-variable/{name}

UserController + UserDaoService (in-memory list)
    ├── GET  /users          → List all
    ├── GET  /users/{id}     → Get one
    └── POST /users          → Create (201 + Location header)
```

---
---

# MODULE 4: EXCEPTION HANDLING

Right now, if you request `GET /users/999`, the DAO returns `null` and the client gets a `200 OK` with an empty body. That's wrong — it should be `404 Not Found`. This module fixes that.

---

## 4.1 The Problem

```
GET /users/999

Current behavior:   200 OK, empty body      ← WRONG
Expected behavior:  404 Not Found + message  ← CORRECT
```

---

## 4.2 Custom Exception Class

Create a class that extends `RuntimeException`:

```java
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
```

### @ResponseStatus(code = HttpStatus.NOT_FOUND)

This annotation tells Spring: "Whenever this exception is thrown, return HTTP status **404**."

Without it, any unhandled exception defaults to `500 Internal Server Error`.

```
@ResponseStatus maps exceptions to HTTP status codes:

HttpStatus.NOT_FOUND             → 404
HttpStatus.BAD_REQUEST           → 400
HttpStatus.INTERNAL_SERVER_ERROR → 500
HttpStatus.FORBIDDEN             → 403
HttpStatus.UNAUTHORIZED          → 401
```

**Interview line:**
> "@ResponseStatus maps an exception to a specific HTTP status code. By annotating my UserNotFoundException with @ResponseStatus(HttpStatus.NOT_FOUND), Spring automatically returns 404 whenever this exception is thrown — no try-catch needed in the controller."

---

## 4.3 Throwing the Exception in the Controller

Update the controller to check for null:

```java
@GetMapping("/users/{id}")
public User retreieveOneUser(@PathVariable int id) {
    User user = service.findOne(id);
    if (user == null)
        throw new UserNotFoundException("id: " + id);
    return user;
}
```

```
GET /users/1    → User found    → 200 OK + user JSON
GET /users/999  → User is null  → throws UserNotFoundException
                                → @ResponseStatus kicks in
                                → 404 Not Found + error message
```

---

**Progress so far:**
```
Module 1: Project setup, dependencies, @SpringBootApplication
Module 2: HelloWorldController — GET endpoints, @RestController, @GetMapping, @PathVariable
Module 3: UserController + DAO — layered architecture, @Component, @Autowired,
          @RequestBody, ResponseEntity, POST with 201 + Location
Module 4: Exception handling — custom exception + @ResponseStatus(404)
    └── GET  /users/{id} → now returns 404 if user not found
```

---
---

# MODULE 5: VALIDATION

Users can currently POST anything — empty names, future birth dates. This module adds validation so the server rejects bad data with `400 Bad Request`.

---

## 5.1 Validation Annotations (on the User model)

You add constraints directly to the model fields:

```java
public class User {
    private Integer id;

    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    @Past(message = "Birth date should be in the past")
    private LocalDate birthDate;
}
```

### @Size(min = 2, message = "Name,....")

Validates that a string's length meets the constraint.

```
@Size(min = 2)                     → name must be at least 2 characters
@Size(max = 100)                   → at most 100 characters
@Size(min = 2, max = 100)          → between 2 and 100
message = "..."                    → custom error message returned to client
```

Part of `jakarta.validation.constraints` (Bean Validation API).

**Interview line:**
> "@Size validates string length. I use it on the name field with min=2 to ensure users can't submit empty or single-character names. The custom message attribute provides a meaningful error response."

### @Past

Validates that a date is **in the past**.

```
@Past            → Date must be before today          (birthDate ✓)
@PastOrPresent   → Before or equal to today
@Future          → Date must be after today            (expiryDate ✓)
@FutureOrPresent → After or equal to today
```

**Interview line:**
> "@Past ensures a date field is in the past. For a birth date, this prevents clients from submitting future dates."

---

## 5.2 Triggering Validation — @Valid

Adding `@Size` and `@Past` to the model is **not enough by itself**. You must also tell Spring to actually validate the incoming object. That's what `@Valid` does.

```java
@PostMapping("/users")
public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
    //                                    ^^^^^^
    //                                    THIS triggers validation
    ...
}
```

### @Valid

Placed before `@RequestBody`, it tells Spring: "Before calling my method, validate this object using all the constraint annotations on its fields."

```
Without @Valid:
    POST { "name": "" }  →  Saved successfully     ← BAD!

With @Valid:
    POST { "name": "" }  →  400 Bad Request         ← CORRECT
                             + validation errors
```

If validation fails, Spring throws `MethodArgumentNotValidException` → returns 400.

**Interview line:**
> "@Valid triggers Bean Validation on the annotated parameter. Without it, constraint annotations like @Size and @Past are ignored. When validation fails, Spring automatically returns 400 Bad Request."

---

## 5.3 Nullable

`Nullable` (from `jakarta.annotation`) indicates that a field **can be null**. It's documentation-level — it doesn't enforce anything at runtime like @NotNull does, but it communicates intent to other developers and tools.

```
Annotation       │ Purpose                          │ Enforced?
─────────────────┼──────────────────────────────────┼──────────
@Nullable        │ "This field may be null"         │ No (informational)
@NotNull         │ "This field must NOT be null"    │ Yes (validated)
@NotBlank        │ "Not null + not empty + not      │ Yes
                 │  just whitespace"                │
@NotEmpty        │ "Not null + not empty"           │ Yes
```

---

**Progress so far:**
```
Module 1: Setup → Dependencies, @SpringBootApplication, auto-config
Module 2: HelloWorldController → @RestController, @GetMapping, @PathVariable
Module 3: Users REST API → DAO layer, @Component, @Autowired, @RequestBody,
                            ResponseEntity, POST → 201 + Location
Module 4: Exception Handling → @ResponseStatus(404), custom exception
Module 5: Validation → @Size, @Past, @Valid → 400 Bad Request
```

---
---

# MODULE 6: CUSTOMIZING JSON OUTPUT — @JsonProperty

By default, Jackson uses getter method names to determine JSON field names. `@JsonProperty` lets you override that.

---

## 6.1 @JsonProperty

```java
public class User {
    private Integer id;

    @JsonProperty("user_name")
    private String name;

    private LocalDate birthDate;
}
```

**Before @JsonProperty:**
```json
{ "id": 1, "name": "Shrey", "birthDate": "1996-04-15" }
```

**After @JsonProperty("user_name"):**
```json
{ "id": 1, "user_name": "Shrey", "birthDate": "1996-04-15" }
```

### When you'd use this:

- Your Java convention is camelCase (`birthDate`) but the API contract requires snake_case (`birth_date`)
- You want to hide internal field names from the client
- You're integrating with a third-party API that expects specific JSON keys

**Interview line:**
> "@JsonProperty customizes the JSON field name during serialization and deserialization. It's useful when your Java naming conventions differ from the API contract — for example, mapping camelCase fields to snake_case JSON keys."

---

**Progress so far:**
```
Module 1: Setup → Dependencies, @SpringBootApplication, auto-config
Module 2: HelloWorldController → @RestController, @GetMapping, @PathVariable
Module 3: Users REST API → DAO layer, @Component, @Autowired, @RequestBody,
                            ResponseEntity, POST → 201 + Location
Module 4: Exception Handling → @ResponseStatus(404), custom exception
Module 5: Validation → @Size, @Past, @Valid → 400 Bad Request
Module 6: JSON Customization → @JsonProperty
```

---
---

# APPENDIX A: Java Concepts Reference

These Java features appear throughout the modules. Quick reference for interviews.

**Predicate (Functional Interface):**
```java
Predicate<User> p = user -> user.getId() == id;  // Takes User → returns boolean
```

**Streams:**
```java
users.stream().filter(p).findFirst().orElse(null);
// stream() → filter() → findFirst() → orElse()
// Functional way to search/transform collections without loops
```

**Static keyword:**
```
static field  → Belongs to the class, shared by all instances
static method → Called on the class, not on an instance
static block  → Runs once when the class is loaded
```

**Optional:**
`findFirst()` returns `Optional<User>` — a container that may or may not contain a value.
`.orElse(null)` unwraps it, returning null if empty.

---
---

# APPENDIX B: Quick Interview Cheat Sheet

```
┌──────────────────────────────────────────────────────────────────────┐
│                       RAPID FIRE ANSWERS                             │
├──────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  @RestController       = @Controller + @ResponseBody                 │
│  @GetMapping           = @RequestMapping(method=GET)                 │
│  @SpringBootApplication= @Configuration + @EnableAutoConfiguration   │
│                        + @ComponentScan                              │
│                                                                      │
│  DispatcherServlet     = Front Controller (routes all HTTP requests) │
│  Jackson               = JSON ↔ Java Object conversion               │
│                                                                      │
│  @Component            = "Spring, create and manage this bean"       │
│  @Autowired            = "Spring, inject the matching bean here"     │
│  @PathVariable         = Extract value from URL path                 │
│  @RequestBody          = Deserialize HTTP body → Java object         │
│  @ResponseStatus       = Map exception → HTTP status code            │
│  @Valid                = Trigger bean validation                      │
│  @Size                 = Validate string length                       │
│  @Past                 = Validate date is in the past                │
│  @JsonProperty         = Customize JSON field name                   │
│  @Nullable             = Documents that a field can be null          │
│                                                                      │
│  ResponseEntity        = Full HTTP response (status+headers+body)    │
│  201 + Location header = REST best practice after POST               │
│  404 + custom exception= REST best practice for not found            │
│                                                                      │
│  PUT = full update  │  PATCH = partial update                        │
│  DAO = Data Access Object (separates data from controller)           │
└──────────────────────────────────────────────────────────────────────┘
```

---

*These notes continue building. Upcoming modules as you progress: DELETE endpoint, JPA & Database Integration, HATEOAS, Content Negotiation, Filtering, Versioning, Swagger/OpenAPI, Spring Security, Actuator.*
