# Java Identifiers & Reserved Words — Complete Study Notes

---

## 1. Java Identifiers

### What Is an Identifier?

An **identifier** is any name used in a Java program to identify a variable, method, class, interface, package, or label. Every user-defined name you write in Java source code is an identifier.

### Example — Counting Identifiers

```java
class Test {
    public static void main(String[] args) {
        int x = 10;
    }
}
```

| Identifier | What It Identifies |
|---|---|
| `Test` | Class name (user-defined) |
| `main` | Method name |
| `String` | Pre-defined class from `java.lang` |
| `args` | Parameter / variable name |
| `x` | Local variable name |

> **Total identifiers = 5.**
> Keywords such as `class`, `public`, `static`, `void`, and `int` are **not** identifiers — they are reserved words.

---

### Rules for Naming Identifiers

#### Rule 1 — Allowed Characters

An identifier may contain **only** the following characters:

- Lowercase letters: `a` – `z`
- Uppercase letters: `A` – `Z`
- Digits: `0` – `9`
- Dollar sign: `$`
- Underscore: `_`

Any other character (e.g., `#`, `@`, `!`, `-`, space) causes a **compile-time error**.

#### Rule 2 — Cannot Start with a Digit

An identifier **must not begin with a digit** (`0`–`9`). It can start with a letter, `$`, or `_`.

| Example | Valid? | Reason |
|---|---|---|
| `total123` | ✅ Valid | Starts with a letter |
| `123Total` | ❌ Invalid | Starts with a digit |
| `_count` | ✅ Valid | Starts with `_` |
| `$amount` | ✅ Valid | Starts with `$` |
| `total#` | ❌ Invalid | Contains `#` (illegal character) |

#### Rule 3 — Case Sensitivity

Java is a **case-sensitive** language. Identifiers that differ only in case are treated as completely different names.

```java
class Test {
    int number = 10;   // valid — unique identifier
    int Number = 20;   // valid — different from 'number'
    int NUMBER = 30;   // valid — different from both above
}
```

> **Interview Tip:** This is why `String` and `string` are not the same thing in Java — `String` is a class, while `string` (lowercase) has no built-in meaning.

#### Rule 4 — No Length Limit (But Keep It Reasonable)

Technically, there is **no maximum length** for an identifier. However, excessively long names reduce readability. Follow conventions: meaningful, concise names like `employeeSalary` rather than `theCurrentMonthlySalaryOfTheEmployee`.

#### Rule 5 — Reserved Words Cannot Be Used as Identifiers

You **cannot** use any of Java's 53 reserved words as an identifier.

```java
int x = 20;    // ✅ Valid
int if = 80;   // ❌ Compile-time error — 'if' is a reserved keyword
```

#### Rule 6 — Pre-defined Class / Interface Names *Can* Be Used (But Shouldn't)

Java allows you to use pre-defined class names (like `String`, `Integer`) and interface names (like `Runnable`) as identifiers. The code will compile and run, but this is **strongly discouraged** because it destroys readability and causes confusion.

```java
class Test {
    public static void main(String[] args) {
        int String = 10;
        System.out.println(String);     // prints 10 — compiles fine

        int Runnable = 999;
        System.out.println(Runnable);   // prints 999 — compiles fine
    }
}
```

> **Why does this work?** The compiler resolves the name based on context. When `String` is declared as a local `int`, the local variable shadows the `java.lang.String` class within that scope. It compiles, but you lose access to the actual `String` class in that scope — a recipe for bugs.

---

### Quick Practice — Valid or Invalid?

| Identifier | Valid? | Explanation |
|---|---|---|
| `total_number` | ✅ Valid | Letters and underscore only |
| `total#` | ❌ Invalid | `#` is an illegal character |
| `123total` | ❌ Invalid | Starts with a digit |
| `total123` | ✅ Valid | Digit at end is fine |
| `Ca$h` | ✅ Valid | `$` is allowed |
| `_$_$_$_` | ✅ Valid | Only `_` and `$` — perfectly legal |
| `all@hands` | ❌ Invalid | `@` is an illegal character |
| `Java2Share` | ✅ Valid | Mix of letters and digits |
| `Integer` | ✅ Valid | Legal but bad practice (pre-defined class name) |
| `Int` | ✅ Valid | Legal but bad practice (misleading, resembles a keyword) |
| `int` | ❌ Invalid | Reserved keyword |

---

## 2. Reserved Words in Java

### Overview

Java has **53 reserved words** in total. None of them can be used as identifiers. They break down as follows:

```
Reserved Words (53)
├── Keywords (50)
│   ├── Used Keywords (48)  — actively used in the language
│   └── Unused Keywords (2) — goto, const (reserved but have no function)
└── Reserved Literals (3)
    ├── true
    ├── false
    └── null
```

> **Why 53 and not 50?** The 50 keywords plus 3 reserved literals (`true`, `false`, `null`) together make 53 reserved words. Some sources count only the 50 keywords — exam questions may use either count, so know both.

---

### 2.1 Keywords for Data Types (8)

These define primitive data types:

| Keyword | Size | Description |
|---|---|---|
| `byte` | 1 byte | Signed integer (−128 to 127) |
| `short` | 2 bytes | Signed integer (−32,768 to 32,767) |
| `int` | 4 bytes | Signed integer (~±2.1 billion) |
| `long` | 8 bytes | Signed integer (very large range) |
| `float` | 4 bytes | Single-precision floating point |
| `double` | 8 bytes | Double-precision floating point |
| `char` | 2 bytes | Single Unicode character |
| `boolean` | JVM-dependent | `true` or `false` only |

---

### 2.2 Keywords for Flow Control (11)

| Keyword | Purpose |
|---|---|
| `if` | Conditional branching |
| `else` | Alternate branch of `if` |
| `switch` | Multi-way branching |
| `case` | A branch inside `switch` |
| `default` | Fallback branch in `switch` |
| `while` | Loop with pre-condition check |
| `do` | Loop with post-condition check (runs at least once) |
| `for` | Counted / iterable loop |
| `break` | Exit loop or switch block |
| `continue` | Skip to next iteration of a loop |
| `return` | Exit a method and optionally return a value |

---

### 2.3 Keywords for Modifiers (12)

| Keyword | Purpose |
|---|---|
| `public` | Accessible from anywhere |
| `private` | Accessible only within the same class |
| `protected` | Accessible within the same package + subclasses |
| `static` | Belongs to the class, not to instances |
| `final` | Cannot be changed / overridden / extended |
| `abstract` | Incomplete — must be implemented by subclass |
| `synchronized` | Thread-safe; only one thread executes at a time |
| `native` | Method implemented in platform-dependent code (e.g., C/C++) |
| `strictfp` | Ensures consistent floating-point calculations across platforms |
| `transient` | Field excluded from serialization |
| `volatile` | Field always read from main memory (visibility guarantee across threads) |

> **Spelling matters for exams:** It is `strictfp` (all lowercase), **not** `strictFp`. It is `synchronized`, **not** `synchronize`.

---

### 2.4 Keywords for Exception Handling (6)

| Keyword | Purpose |
|---|---|
| `try` | Encloses code that might throw an exception |
| `catch` | Handles a specific exception type |
| `finally` | Block that always executes (cleanup code) |
| `throw` | Explicitly throws an exception object |
| `throws` | Declares that a method may throw exception(s) |
| `assert` | Tests assumptions during development (enabled with `-ea` flag) |

> **Common trap:** `finalize` is a **method** of the `Object` class (used by the garbage collector), not a keyword. Similarly, `thrown` does not exist in Java.

---

### 2.5 Keywords Related to Classes & Interfaces (6)

| Keyword | Purpose |
|---|---|
| `class` | Declares a class |
| `interface` | Declares an interface |
| `extends` | Inherits from a class (or extends an interface) |
| `implements` | A class adopts an interface contract |
| `package` | Declares the package a class belongs to |
| `import` | Brings other packages / classes into scope |

> **Spelling matters:** `extends` (not `extend`), `implements` (not `implement`), `import` (not `imports`).

---

### 2.6 Keywords Related to Objects (4)

| Keyword | Purpose |
|---|---|
| `new` | Creates a new object instance on the heap |
| `instanceof` | Tests whether an object is an instance of a given type |
| `super` | Refers to the parent class (constructor or members) |
| `this` | Refers to the current object instance |

> **Spelling matters:** `instanceof` is all lowercase — **not** `instanceOf`.

---

### 2.7 Return Type Keyword (1)

| Keyword | Purpose |
|---|---|
| `void` | Indicates that a method does not return a value |

> In Java, specifying a return type for every method is **mandatory**. If the method returns nothing, you must explicitly write `void`.

---

### 2.8 Unused Keywords (2)

| Keyword | Notes |
|---|---|
| `goto` | Reserved but **not implemented**. Java uses labeled `break` / `continue` instead. |
| `const` | Reserved but **not implemented**. Java uses `final` for constants instead. |

If you try to use `goto` or `const` in your code, the compiler will throw an error — they are reserved even though they have no functionality.

> **Why reserve unused keywords?** They were reserved to prevent developers from using them as identifiers, in case Java ever implements them in a future version. This avoids breaking existing code.

---

### 2.9 Reserved Literals (3)

| Literal | Purpose |
|---|---|
| `true` | Boolean literal — one of two possible `boolean` values |
| `false` | Boolean literal — the other `boolean` value |
| `null` | The default value for all object reference types (means "no object") |

> These are **not keywords** technically — they are **literals**. But they are still reserved and cannot be used as identifiers.

---

### 2.10 The `enum` Keyword (Added in Java 5)

`enum` is used to define a **fixed set of named constants**.

```java
enum Month {
    JAN, FEB, MAR, APR, MAY, JUN,
    JUL, AUG, SEP, OCT, NOV, DEC
}
```

> Under the hood, each `enum` constant is a `public static final` instance of the enum type. Enums can have constructors, fields, and methods — they are much more powerful than simple integer constants.

---

## 3. Key Conclusions & Exam Tips

### All Reserved Words Are Lowercase

Every single one of Java's 53 reserved words contains **only lowercase letters**. If you see a capitalized version on an exam (`Int`, `Null`, `Boolean`), it is **not** a reserved word — it is a valid (though potentially confusing) identifier.

### There Is No `delete` Keyword

Java has `new` but **no `delete`**. Memory deallocation is handled entirely by the **Garbage Collector (GC)**. You cannot manually free an object — you can only remove all references to it and let the GC reclaim it.

### Common Spelling Traps

| Correct Keyword | Common Wrong Version |
|---|---|
| `strictfp` | ~~strictFp~~ |
| `instanceof` | ~~instanceOf~~ |
| `synchronized` | ~~synchronize~~ |
| `extends` | ~~extend~~ |
| `implements` | ~~implement~~ |
| `import` | ~~imports~~ |
| `const` | ~~constant~~ |

### Not Keywords — Common Tricks

These look like they could be keywords but are actually **methods or classes**:

| Name | What It Actually Is |
|---|---|
| `delete` | Does not exist in Java |
| `exit` | A method: `System.exit()` |
| `finalize` | A method of `Object` class |
| `thrown` | Does not exist (the keyword is `throw` / `throws`) |
| `notify` | A method of `Object` class |
| `notifyAll` | A method of `Object` class |
| `sizeOf` | Does not exist in Java (exists in C/C++) |

---

## 4. Quiz — Which Set Contains *Only* Reserved Keywords?

Test yourself by checking each option:

| # | Option | Answer | Explanation |
|---|---|---|---|
| 1 | `new`, `delete` | ❌ | `delete` does not exist in Java |
| 2 | `goto`, `constant` | ❌ | Should be `const`, not `constant` |
| 3 | `break`, `continue`, `return`, `exit` | ❌ | `exit` is a method (`System.exit()`), not a keyword |
| 4 | `final`, `finally`, `finalize` | ❌ | `finalize` is a method in the `Object` class |
| 5 | `throw`, `throws`, `thrown` | ❌ | `thrown` does not exist in Java |
| 6 | `notify`, `notifyAll` | ❌ | Both are methods of the `Object` class |
| 7 | `implements`, `extends`, `imports` | ❌ | Should be `import`, not `imports` |
| 8 | `sizeOf`, `instanceof` | ❌ | `sizeOf` does not exist in Java (C/C++ concept) |
| 9 | `instanceOf`, `strictFp` | ❌ | Incorrect casing — must be `instanceof` and `strictfp` |
| 10 | `byte`, `short`, `Int` | ❌ | `Int` (capital I) is not a keyword — the keyword is `int` |

> **Takeaway:** None of the above sets are fully correct. Exam questions like these test your knowledge of exact spelling, casing, and the distinction between keywords vs. methods vs. non-existent terms.

---

## 5. Quick-Reference Cheat Sheet

```
DATA TYPES (8)     : byte  short  int  long  float  double  char  boolean
FLOW CONTROL (11)  : if  else  switch  case  default  while  do  for  break  continue  return
MODIFIERS (12)     : public  private  protected  static  final  abstract  synchronized  native  strictfp  transient  volatile
EXCEPTIONS (6)     : try  catch  finally  throw  throws  assert
CLASS-RELATED (6)  : class  interface  extends  implements  package  import
OBJECT-RELATED (4) : new  instanceof  super  this
RETURN TYPE (1)    : void
UNUSED (2)         : goto  const
ENUM (1)           : enum   [added in Java 5]
─────────────────────────────────
TOTAL KEYWORDS     : 50  (48 used + 2 unused)
RESERVED LITERALS  : true  false  null
─────────────────────────────────
GRAND TOTAL        : 53 reserved words
```

---

*These notes are structured for quick revision and interview preparation. Good luck, Shreyas!*