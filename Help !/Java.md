## What is Java?

Java is a popular programming language, created in 1995.

It is owned by Oracle, and more than **3 billion** devices run Java.

It is used for:

- Mobile applications (specially Android apps)
- Desktop applications
- Web applications
- Web servers and application servers
- Games
- Database connection
- And much, much more!

---

## Why Use Java?

- Java works on different platforms (Windows, Mac, Linux, Raspberry Pi, etc.)
- It is one of the most popular programming language in the world
- It has a large demand in the current job market
- It is easy to learn and simple to use
- It is open-source and free
- It is secure, fast and powerful
- It has a huge community support (tens of millions of developers)
- Java is an object oriented language which gives a clear structure to programs and allows code to be reused, lowering development costs
- As Java is close to [C++](https://www.w3schools.com/cpp/default.asp) and [C#](https://www.w3schools.com/cs/default.asp), it makes it easy for programmers to switch to Java or vice versa

---

## Java Quickstart

In Java, every application begins with a class name, and that class must match the filename.
Let's create our first Java file, called Main.java, which can be done in any text editor (like Notepad).
The file should contain a "Hello World" message, which is written with the following code:

```java
public class Main {
  public static void main(String[] args) {
    System.out.println("Hello World");
  }
}
```

---

## The main Method

The `main()` method is required and you will see it in every Java program:

```java
public static void main(String[] args)
```

Any code inside the `main()` method will be executed. Don't worry about the keywords before and after main. You will get to know them bit by bit while reading this tutorial.

For now, just remember that every Java program has a `class` name which must match the filename, and that every program must contain the `main()` method.

---

## System.out.println()

Inside the `main()` method, we can use the `println()` method to print a line of text to the screen:

```java
public static void main(String[] args) {
  System.out.println("Hello World");
}
```

---

## Double Quotes

When you are working with text, it must be wrapped inside double quotations marks `""`.

If you forget the double quotes, an error occurs:

```java
System.out.println("This sentence will work!");
```

```java
System.out.println(This sentence will produce an error);
```

---

## The Print() Method

There is also a `print()` method, which is similar to `println()`.

The only difference is that it does not insert a new line at the end of the output:

```java
System.out.print("Hello World! ");
System.out.print("I will print on the same line.");
```

---

## Print Numbers

You can also use the `println()` method to print numbers.

However, unlike text, we don't put numbers inside double quotes:

```java
System.out.println(3);
System.out.println(358);
System.out.println(50000);
```

```java
System.out.println(3 + 3);
System.out.println(2 * 5);
```

---

## Java Variables

Variables are containers for storing data values.

In Java, there are different **types** of variables, for example:

- `String` - stores text, such as "Hello". String values are surrounded by double quotes
- `int` - stores integers (whole numbers), without decimals, such as 123 or -123
- `float` - stores floating point numbers, with decimals, such as 19.99 or -19.99
- `char` - stores single characters, such as 'a' or 'B'. Char values are surrounded by single quotes
- `boolean` - stores values with two states: true or false

---

## Declaring (Creating) Variables

To create a variable, you must specify the type and assign it a value:

```java
type variableName = value;
```

Where _type_ is one of Java's types (such as `int` or `String`), and _variableName_ is the name of the variable (such as **x** or **name**). The **equal sign** is used to assign values to the variable.

To create a variable that should store text, look at the following example:

```java
String name = "John";
System.out.println(name);
```

To create a variable that should store a number, look at the following example:

```java
int myNum = 15;
System.out.println(myNum);
```

You can also declare a variable without assigning the value, and assign the value later:

```java
int myNum;
myNum = 15;
System.out.println(myNum);
```

Note that if you assign a new value to an existing variable, it will overwrite the previous value:

```java
int myNum = 15;
myNum = 20;  // myNum is now 20
System.out.println(myNum);
```

## Final Variables

If you don't want others (or yourself) to overwrite existing values, use the `final` keyword (this will declare the variable as "final" or "constant", which means unchangeable and read-only):

```java
final int myNum = 15;
myNum = 20;  // will generate an error: cannot assign a value to a final variable
```

---

## Other Types

A demonstration of how to declare variables of other types:

```java
int myNum = 5;
float myFloatNum = 5.99f;
char myLetter = 'D';
boolean myBool = true;
String myText = "Hello";
```

---

## Display Variables

The `println()` method is often used to display variables.

To combine both text and a variable, use the `+` character:

```java
String name = "John";
System.out.println("Hello " + name);
```

You can also use the `+` character to add a variable to another variable:

```java
String firstName = "John ";
String lastName = "Doe";
String fullName = firstName + lastName;
System.out.println(fullName);
```

For numeric values, the `+` character works as a mathematical operator (notice that we use `int` (integer) variables here):

```java
int x = 5;
int y = 6;
System.out.println(x + y); // Print the value of x + y
```

---

## Declare Many Variables

To declare more than one variable of the **same type**, you can use a comma-separated list:

Instead of writing:

```java
int x = 5;
int y = 6;
int z = 50;
System.out.println(x + y + z);
```

You can simply write:

```java
int x = 5, y = 6, z = 50;
System.out.println(x + y + z);
```

---

## One Value to Multiple Variables

You can also assign the **same value** to multiple variables in one line:

```java
int x, y, z;
x = y = z = 50;
System.out.println(x + y + z);
```

---

## Data Types

Data types are divided into two groups:

- Primitive data types - includes `byte`, `short`, `int`, `long`, `float`, `double`, `boolean` and `char`
- Non-primitive data types - such as `[String](https://www.w3schools.com/java/java_strings.asp)`, `Arrays` and `Classes` (you will learn more about these in a later chapter)

A primitive data type specifies the size and type of variable values, and it has no additional methods.

There are eight primitive data types in Java:

|Data Type|Size|Description|
|---|---|---|
|`byte`|1 byte|Stores whole numbers from -128 to 127|
|`short`|2 bytes|Stores whole numbers from -32,768 to 32,767|
|`int`|4 bytes|Stores whole numbers from -2,147,483,648 to 2,147,483,647|
|`long`|8 bytes|Stores whole numbers from -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807|
|`float`|4 bytes|Stores fractional numbers. Sufficient for storing 6 to 7 decimal digits|
|`double`|8 bytes|Stores fractional numbers. Sufficient for storing 15 decimal digits|
|`boolean`|1 bit|Stores true or false values|
|`char`|2 bytes|Stores a single character/letter or ASCII values|

---

## Floating Point Types

You should use a floating point type whenever you need a number with a decimal, such as 9.99 or 3.14515.

The `float` and `double` data types can store fractional numbers. Note that you should end the value with an "f" for floats and "d" for doubles:

```java
float myNum = 5.75f;
System.out.println(myNum);
```

```java
double myNum = 19.99d;
System.out.println(myNum);
```

---

## Arithmetic Operators

|Operator|Name|Description|Example|
|---|---|---|---|
|+|Addition|Adds together two values|x + y| 
|-|Subtraction|Subtracts one value from another|x - y|
|*|Multiplication|Multiplies two values|x * y|
|/|Division|Divides one value by another|x / y|
|%|Modulus|Returns the division remainder|x % y|
|++|Increment|Increases the value of a variable by 1|++x|
|--|Decrement|Decreases the value of a variable by 1|--x|

---

## Java Assignment Operators

|Operator|Example|Same As|
|---|---|---|
|=|x = 5|x = 5|
|+=|x += 3|x = x + 3|
|-=|x -= 3|x = x - 3|
|*=|x *= 3|x = x * 3|
|/=|x /= 3|x = x / 3|
|%=|x %= 3|x = x % 3|
|&=|x &= 3|x = x & 3|
|\|=|x \|= 3|x = x \| 3|
|^=|x ^= 3|x = x ^ 3|
|>>=|x >>= 3|x = x >> 3|
|<<=|x <<= 3|x = x << 3|

---

## Java Comparison Operators

|Operator|Name|Example|
|---|---|---|
|==|Equal to|x == y|
|!=|Not equal|x != y|
|>|Greater than|x > y|
|<|Less than|x < y|
|>=|Greater than or equal to|x >= y|
|<=|Less than or equal to|x <= y|

---

## Java Logical Operators

|Operator|Name|Description|Example|
|---|---|---|---|
|&&|Logical and|Returns true if both statements are true|x < 5 &&  x < 10|
|\||Logical or|Returns true if one of the statements is true|x < 5 \| x < 4|
|!|Logical not|Reverse the result, returns false if the result is true|!(x < 5 && x < 10)|

