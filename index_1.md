# Copart Intern Interview Prep — Complete Guide
**Call: Tomorrow 10 AM | Duration: 30 min**

> **Strategy for 30-min call:** Expect ~5 min intro/behavioral, ~20 min technical (Java + Spring Boot + Multithreading are TOP priority), ~5 min your questions. They WILL ask multithreading — JD says "must". Master Sections 1, 2, 3 first.

---

## ⭐ PRIORITY ORDER (study in this order if running out of time)
1. **Core Java** (OOP, Collections, Exceptions)
2. **Multithreading** — JD says "MUST"
3. **Spring Boot & Spring MVC**
4. **JUnit + Mockito**
5. **REST APIs**
6. **SQL basics**
7. **JavaScript / React basics**
8. **Agile**
9. **Behavioral**

---

# 1. CORE JAVA

## 1.1 OOP — 4 Pillars
- **Encapsulation**: Wrapping data + methods together; using `private` fields with `public` getters/setters. Hides internal state.
- **Inheritance**: A class acquires properties of another via `extends`. Promotes code reuse. Java supports single inheritance (one parent class) but multiple inheritance via interfaces.
- **Polymorphism**: Same method behaves differently.
  - **Compile-time (Method Overloading)**: same name, different parameters.
  - **Runtime (Method Overriding)**: subclass redefines parent's method.
- **Abstraction**: Hiding implementation, showing only essentials. Achieved via `abstract class` and `interface`.

## 1.2 Abstract Class vs Interface
| Abstract Class | Interface |
|---|---|
| Can have abstract + concrete methods | All methods abstract by default (Java 8+ allows default & static) |
| Variables can be any type | Variables are public static final (constants) |
| `extends` (single) | `implements` (multiple) |
| Use when classes share common code | Use to define a contract/capability |

## 1.3 == vs .equals()
- `==` compares **references** (memory address) for objects, values for primitives.
- `.equals()` compares **content** (when overridden, e.g., in String).
- If you override `equals()`, **always override `hashCode()`** too (HashMap contract).

## 1.4 String, StringBuilder, StringBuffer
- **String** → immutable. Every modification creates new object.
- **StringBuilder** → mutable, NOT thread-safe, faster.
- **StringBuffer** → mutable, thread-safe (synchronized), slower.

## 1.5 Collections Framework
- **List** (ordered, allows duplicates): `ArrayList` (dynamic array, fast read), `LinkedList` (doubly linked, fast insert/delete).
- **Set** (no duplicates): `HashSet` (unordered), `LinkedHashSet` (insertion order), `TreeSet` (sorted).
- **Map** (key-value): `HashMap` (unordered, allows one null key), `LinkedHashMap` (insertion order), `TreeMap` (sorted by key), `ConcurrentHashMap` (thread-safe).
- **HashMap vs Hashtable**: Hashtable is synchronized (legacy, slower); HashMap is not. Use `ConcurrentHashMap` for thread-safe modern code.

## 1.6 Exception Handling
- **Checked** (compile-time) — must handle: `IOException`, `SQLException`.
- **Unchecked** (runtime) — `NullPointerException`, `ArrayIndexOutOfBoundsException`, `ArithmeticException`.
- `try-catch-finally` — `finally` always executes (except `System.exit()`).
- `throw` (throw an exception) vs `throws` (declare in method signature).
- **try-with-resources** — auto-closes resources implementing `AutoCloseable`.

## 1.7 final, finally, finalize
- `final` — keyword: final class (can't extend), final method (can't override), final variable (constant).
- `finally` — block that always executes after try-catch.
- `finalize()` — method called by GC before object is destroyed (deprecated).

## 1.8 JVM, JRE, JDK
- **JDK** = Development Kit (compiler `javac` + JRE).
- **JRE** = Runtime Environment (JVM + libraries).
- **JVM** = Java Virtual Machine (executes bytecode).

## 1.9 Garbage Collection
- Automatic memory management. Removes objects with no references.
- You can request GC via `System.gc()` but can't force it.
- Generations: Young (Eden, Survivor) → Old (Tenured) → Metaspace.

## 1.10 Java 8 Features (often asked!)
- **Lambda**: `(a, b) -> a + b`
- **Stream API**: `list.stream().filter(x -> x > 5).map(x -> x*2).collect(Collectors.toList())`
- **Functional interfaces**: `Predicate`, `Function`, `Consumer`, `Supplier`.
- **Optional**: avoid NullPointerException.
- **Default methods** in interfaces.

---

# 2. MULTITHREADING ⭐ (MUST KNOW — JD says required)

## 2.1 Process vs Thread
- **Process**: independent program with its own memory.
- **Thread**: lightweight unit within a process; shares memory with other threads of same process.

## 2.2 Creating Threads — 2 ways
1. **Extend Thread class**:
   ```java
   class MyThread extends Thread {
       public void run() { System.out.println("Running"); }
   }
   new MyThread().start();
   ```
2. **Implement Runnable** (preferred):
   ```java
   class MyTask implements Runnable {
       public void run() { System.out.println("Running"); }
   }
   new Thread(new MyTask()).start();
   ```
3. **Callable** (returns a value, can throw exception) — used with ExecutorService.

## 2.3 Thread Lifecycle
**NEW → RUNNABLE → RUNNING → (BLOCKED / WAITING / TIMED_WAITING) → TERMINATED**

## 2.4 start() vs run()
- `start()` — creates a new thread and calls `run()` on it.
- `run()` — just a normal method call, runs on current thread (no new thread).

## 2.5 Synchronization
- Prevents multiple threads from accessing critical section simultaneously.
- `synchronized` keyword on method or block:
  ```java
  public synchronized void increment() { count++; }
  // OR
  synchronized(this) { count++; }
  ```
- Uses intrinsic lock (monitor) of the object.

## 2.6 volatile vs synchronized
- **volatile** — guarantees visibility of variable changes across threads (read directly from main memory, not CPU cache). Doesn't ensure atomicity.
- **synchronized** — ensures both visibility AND atomicity (mutual exclusion).

## 2.7 Deadlock
- Two threads each holding a lock the other needs → both wait forever.
- **Prevention**: acquire locks in same order, use `tryLock()` with timeout, avoid nested locks.

## 2.8 wait(), notify(), notifyAll()
- Used for inter-thread communication. Must be called inside synchronized block.
- `wait()` — releases lock, thread waits.
- `notify()` — wakes ONE waiting thread.
- `notifyAll()` — wakes ALL waiting threads.

## 2.9 sleep() vs wait()
| sleep() | wait() |
|---|---|
| Static method of Thread | Instance method of Object |
| Doesn't release lock | Releases lock |
| Doesn't need synchronized block | Must be inside synchronized |

## 2.10 ExecutorService & Thread Pool
- Better than creating threads manually (reuses threads).
- ```java
  ExecutorService executor = Executors.newFixedThreadPool(5);
  executor.submit(() -> System.out.println("Task"));
  executor.shutdown();
  ```
- Types: `newFixedThreadPool`, `newCachedThreadPool`, `newSingleThreadExecutor`, `newScheduledThreadPool`.

## 2.11 Concurrent Collections
- `ConcurrentHashMap` — thread-safe HashMap, locks only buckets.
- `CopyOnWriteArrayList` — thread-safe list, creates new copy on every write (good for read-heavy).
- `BlockingQueue` — used in producer-consumer.

## 2.12 Atomic Classes
- `AtomicInteger`, `AtomicLong`, `AtomicReference` — lock-free thread-safe operations using CAS (Compare-And-Swap).

## 2.13 Producer-Consumer
- Use `BlockingQueue` (e.g., `ArrayBlockingQueue`) — `put()` blocks if full, `take()` blocks if empty. No manual sync needed.

## 2.14 Race Condition
- When two threads access shared data and final result depends on timing. Fix with synchronization or atomic operations.

---

# 3. SPRING BOOT & SPRING MVC ⭐

## 3.1 What is Spring?
Open-source Java framework for building enterprise apps. Provides **Dependency Injection (DI)** and **Inversion of Control (IoC)**.

## 3.2 Spring vs Spring Boot
- **Spring** — needs lots of XML/Java configuration.
- **Spring Boot** — auto-configuration, embedded server (Tomcat), starter dependencies, no XML. "Convention over configuration."

## 3.3 IoC and Dependency Injection
- **IoC**: control of object creation handed over to framework instead of you using `new`.
- **DI**: Framework injects required dependencies.
- 3 types: **Constructor injection** (preferred), Setter injection, Field injection (`@Autowired` on field — discouraged).

## 3.4 Important Annotations
| Annotation | Purpose |
|---|---|
| `@SpringBootApplication` | Main class — combines @Configuration + @EnableAutoConfiguration + @ComponentScan |
| `@Component` | Generic Spring-managed bean |
| `@Service` | Service layer (business logic) |
| `@Repository` | DAO layer (DB access) |
| `@Controller` | MVC controller (returns view) |
| `@RestController` | @Controller + @ResponseBody (returns JSON) |
| `@Autowired` | Inject dependency |
| `@Qualifier` | Resolve ambiguity when multiple beans |
| `@RequestMapping`, `@GetMapping`, `@PostMapping` | Map HTTP requests |
| `@PathVariable` | Extract value from URL `/users/{id}` |
| `@RequestParam` | Extract query param `?name=abc` |
| `@RequestBody` | Bind JSON body to object |
| `@Configuration` | Class containing bean definitions |
| `@Bean` | Method-level — registers return value as Spring bean |
| `@Value` | Inject from properties file |

## 3.5 Spring MVC Flow
1. Request → **DispatcherServlet** (front controller)
2. DispatcherServlet → **HandlerMapping** to find right Controller
3. Controller processes → returns ModelAndView
4. **ViewResolver** resolves view name → renders view
5. Response sent back

## 3.6 Bean Scopes
- **singleton** (default) — one instance per Spring container.
- **prototype** — new instance every request.
- **request, session, application** — web scopes.

## 3.7 Bean Lifecycle (high-level)
Instantiation → Populate properties → Aware interfaces → `@PostConstruct` → ready → `@PreDestroy` → destroyed.

## 3.8 Spring Boot Starters
Pre-packaged dependencies, e.g., `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-test`.

## 3.9 application.properties / application.yml
Configuration: `server.port=8080`, `spring.datasource.url=...`. Profiles via `spring.profiles.active=dev`.

## 3.10 Spring Boot Actuator
Production-ready endpoints: `/actuator/health`, `/actuator/metrics`, `/actuator/info`.

## 3.11 Exception Handling in Spring
- `@ControllerAdvice` + `@ExceptionHandler` — global exception handler.

## 3.12 Spring Data JPA (if asked)
- `JpaRepository<Entity, ID>` — gives CRUD methods out of the box.
- `@Entity`, `@Id`, `@GeneratedValue`, `@Column`.

---

# 4. JUnit & Mockito ⭐

## 4.1 JUnit Annotations (JUnit 5)
- `@Test` — marks a test method.
- `@BeforeEach` / `@AfterEach` — runs before/after each test.
- `@BeforeAll` / `@AfterAll` — runs once before/after all tests (must be static).
- `@Disabled` — skip a test.
- `@ParameterizedTest` — run same test with different inputs.

## 4.2 Common Assertions
- `assertEquals(expected, actual)`
- `assertTrue(condition)`, `assertFalse(condition)`
- `assertNull(obj)`, `assertNotNull(obj)`
- `assertThrows(Exception.class, () -> code())`

## 4.3 Mockito Basics
- **Mocking** = creating fake objects to isolate the unit under test (e.g., mock the DB so you test only the service logic).
- ```java
  @Mock UserRepository repo;
  @InjectMocks UserService service;
  
  when(repo.findById(1)).thenReturn(Optional.of(user));
  verify(repo, times(1)).findById(1);
  ```
- `@Mock` — creates mock object.
- `@InjectMocks` — injects mocks into the class being tested.
- `when().thenReturn()` — stub a method's return.
- `verify()` — confirm a method was called.

## 4.4 AAA Pattern
- **Arrange** — set up data and mocks.
- **Act** — call the method.
- **Assert** — verify the result.

## 4.5 Spring Boot Test Annotations
- `@SpringBootTest` — loads full app context (integration test).
- `@WebMvcTest` — only web layer.
- `@DataJpaTest` — only JPA layer.
- `@MockBean` — adds a mock to Spring context.

---

# 5. REST APIs

## 5.1 What is REST?
**Representational State Transfer** — architectural style using HTTP. Stateless, client-server, uses standard HTTP methods.

## 5.2 HTTP Methods
- **GET** — retrieve (idempotent, safe).
- **POST** — create new resource.
- **PUT** — full update (idempotent).
- **PATCH** — partial update.
- **DELETE** — remove (idempotent).

## 5.3 HTTP Status Codes
- **200** OK · **201** Created · **204** No Content
- **400** Bad Request · **401** Unauthorized · **403** Forbidden · **404** Not Found
- **500** Internal Server Error · **503** Service Unavailable

## 5.4 REST Best Practices
- Use nouns, not verbs in URLs (`/users/123`, NOT `/getUser?id=123`).
- Use plural resources.
- Versioning: `/api/v1/users`.
- Return appropriate status codes & JSON.

## 5.5 REST vs SOAP
- REST → lightweight, JSON, uses HTTP. SOAP → XML-based, heavier, has built-in standards.

## 5.6 Idempotency
An operation is idempotent if calling it multiple times has the same result as calling it once. GET, PUT, DELETE = idempotent; POST = not.

---

# 6. SQL

## 6.1 Basic Queries
```sql
SELECT name, age FROM users WHERE age > 25 ORDER BY name ASC LIMIT 10;
INSERT INTO users (name, age) VALUES ('John', 30);
UPDATE users SET age = 31 WHERE id = 1;
DELETE FROM users WHERE id = 1;
```

## 6.2 Joins
- **INNER JOIN** — only matching rows in both tables.
- **LEFT JOIN** — all from left + matching from right (NULL if no match).
- **RIGHT JOIN** — all from right + matching from left.
- **FULL OUTER JOIN** — all from both.

```sql
SELECT u.name, o.total
FROM users u
INNER JOIN orders o ON u.id = o.user_id;
```

## 6.3 Aggregate Functions
`COUNT()`, `SUM()`, `AVG()`, `MIN()`, `MAX()`, used with `GROUP BY` and filtered with `HAVING`.

```sql
SELECT department, COUNT(*) FROM employees GROUP BY department HAVING COUNT(*) > 5;
```

## 6.4 Primary Key vs Foreign Key
- **PK** — uniquely identifies row, NOT NULL, unique.
- **FK** — references PK of another table (referential integrity).

## 6.5 Normalization (high-level)
- **1NF** — atomic values (no repeating groups).
- **2NF** — 1NF + no partial dependency on PK.
- **3NF** — 2NF + no transitive dependency.

## 6.6 Index
Speeds up reads, slows writes. Created on columns frequently used in WHERE/JOIN.

## 6.7 Stored Procedure
Pre-compiled SQL stored in DB; called by name. Improves performance, encapsulates logic.

## 6.8 ACID
- **Atomicity** — all or nothing.
- **Consistency** — DB stays valid.
- **Isolation** — concurrent transactions don't interfere.
- **Durability** — committed data persists.

## 6.9 WHERE vs HAVING
- WHERE — filters rows BEFORE grouping.
- HAVING — filters AFTER grouping (used with GROUP BY).

---

# 7. JavaScript, HTML, CSS, jQuery, AJAX, React

## 7.1 JavaScript Basics
- **var** (function-scoped, hoisted), **let** (block-scoped), **const** (block-scoped, immutable reference).
- **==** (loose, type coercion) vs **===** (strict). Always use `===`.
- **Hoisting** — `var` and function declarations moved to top of scope.
- **Closure** — function that remembers variables from its outer scope.
- **Promise / async-await** — for asynchronous operations.
  ```javascript
  async function getData() {
    const res = await fetch('/api');
    return res.json();
  }
  ```

## 7.2 AJAX
**Asynchronous JavaScript and XML** — making HTTP requests without reloading page.
```javascript
$.ajax({ url: '/api/users', method: 'GET', success: data => console.log(data) });
// Modern way:
fetch('/api/users').then(r => r.json()).then(data => console.log(data));
```

## 7.3 jQuery (briefly)
JS library that simplifies DOM manipulation. `$('#id').click(...)`, `$('.class').hide()`. Largely replaced by modern frameworks.

## 7.4 React Basics
- **Component-based** UI library by Facebook.
- **JSX** — HTML-like syntax in JS.
- **Props** — read-only data passed parent → child.
- **State** — internal mutable data of component (`useState` hook).
- **Virtual DOM** — React keeps a virtual copy; on change, diffs against real DOM and updates only what changed.
- **Hooks**:
  - `useState` — manage state.
  - `useEffect` — side effects (e.g., API calls), runs after render.
  - `useContext` — access context.
- **One-way data flow** — data flows from parent to child via props.
- **Class vs Functional components** — functional with hooks is the modern standard.

## 7.5 HTML5 / CSS3 quick points
- HTML5 semantic tags: `<header>`, `<footer>`, `<nav>`, `<section>`, `<article>`.
- CSS box model: content → padding → border → margin.
- Flexbox & Grid for layouts.

---

# 8. AGILE & TOOLS

## 8.1 Agile Methodology
Iterative, incremental development. Working software over documentation. Welcomes change.

## 8.2 Scrum
- **Roles**: Product Owner, Scrum Master, Development Team.
- **Ceremonies**: Sprint Planning, Daily Standup, Sprint Review, Retrospective.
- **Artifacts**: Product Backlog, Sprint Backlog, Increment.
- **Sprint** — 2-4 week development cycle.

## 8.3 Daily Standup — 3 Questions
1. What did I do yesterday? 2. What will I do today? 3. Any blockers?

## 8.4 Git Basics
- `git clone`, `git pull`, `git add .`, `git commit -m "msg"`, `git push`.
- `git branch feature-x`, `git checkout feature-x`, `git merge`.
- **Merge vs Rebase** — merge creates a merge commit; rebase moves commits onto another base (cleaner history).
- **Pull request** — proposing changes to be merged, with code review.

## 8.5 Maven
Build & dependency management tool. `pom.xml` defines dependencies. Common: `mvn clean install`, `mvn test`, `mvn package`.

## 8.6 Jenkins
CI/CD automation server — automates build, test, deploy on every commit.

## 8.7 N-tier Architecture
- **Presentation tier** — UI (React, JSP).
- **Application/Business tier** — Spring services (logic).
- **Data tier** — Database (MySQL).
- Separation = scalability, maintainability.

---

# 9. UNIX / LINUX (basics — nice to have)
- `ls`, `cd`, `pwd`, `mkdir`, `rm`, `cp`, `mv`
- `cat`, `grep`, `find`, `head`, `tail`, `less`
- `chmod 755 file`, `chown`, `ps`, `kill -9 PID`, `top`
- `|` (pipe), `>` (redirect), `>>` (append)

---

# 10. BEHAVIORAL QUESTIONS

> Use **STAR** method: **Situation, Task, Action, Result.**

## 10.1 Tell me about yourself
- 60-90 seconds. Format: *Background → Skills/Projects → Why this role*.
- Example: *"I'm a final-year CS student. I've worked on projects using Java, Spring Boot, and React — built [project] which does [X]. I enjoy backend development and problem-solving, and I'm excited about Copart's intern role because it aligns with my Java/Spring skills and gives me exposure to real-time, large-scale applications."*

## 10.2 Why Copart?
Mention: global expansion, opportunity to work on real-time apps, agile environment, exposure to enterprise-grade Java/Spring.

## 10.3 Why should we hire you?
Skills match (Java, Spring, JS), can-do attitude, fast learner, willing to take ownership.

## 10.4 Common Behavioral Questions
1. **Tell me about a challenging project.** — Project, technical hurdle, what you did, the outcome.
2. **Tell me about a time you worked in a team.** — Group project; your role; how conflict was resolved.
3. **A time you faced a conflict in a team — how did you handle it?** — Stay neutral, listen, find common ground.
4. **A time you had to learn a new technology quickly.** — Show curiosity & learning approach.
5. **Tell me about a time you failed.** — Pick a real but recoverable failure; emphasize the lesson.
6. **How do you handle tight deadlines?** — Prioritize, break down tasks, communicate early.
7. **How do you handle feedback/criticism?** — Welcome it, take notes, act on it.
8. **Describe a time you debugged a difficult problem.**
9. **Strengths and weaknesses?** — Strength: pick one with example. Weakness: real but improving (e.g., "I used to over-engineer; now I write minimum viable code first and iterate").
10. **Where do you see yourself in 5 years?** — Growth into a strong engineer, contributing meaningfully.

## 10.5 Questions YOU should ask (always ask 1-2!)
1. *What does a typical day for an intern look like at Copart?*
2. *What tech stack will I be working on day-to-day?*
3. *How are interns evaluated, and what does success look like in the first 3 months?*
4. *Could you tell me about the team I'd be joining?*
5. *What are the biggest technical challenges the team is currently solving?*

---

# 🔥 LAST-MINUTE QUICK CHECKLIST (read 30 min before call)

- [ ] OOP 4 pillars
- [ ] HashMap vs Hashtable vs ConcurrentHashMap
- [ ] Multithreading: Runnable, synchronized, volatile, deadlock, ExecutorService
- [ ] Spring Boot vs Spring; @RestController, @Autowired, @Service
- [ ] DispatcherServlet flow
- [ ] JUnit + Mockito basics (@Mock, when().thenReturn(), verify())
- [ ] HTTP methods + status codes
- [ ] SQL JOINs
- [ ] React: state, props, virtual DOM, hooks
- [ ] Agile/Scrum ceremonies
- [ ] "Tell me about yourself" — practice ALOUD 3x
- [ ] 2 questions to ask interviewer

---

# 💡 INTERVIEW DAY TIPS
- Be in a quiet place 10 min early. Test mic/camera.
- Keep water nearby. Have a pen + paper.
- If you don't know an answer: **"I haven't worked with that hands-on, but my understanding is X. I'd love to learn more about it."** Honesty > faking.
- Speak slowly. Take a 2-second pause before answering — it's fine.
- Smile (audible in voice). Be enthusiastic.

**You've got this. Good luck! 🚀**
