

<aside>
💡

Builder solves two core problems when a class has many attributes: (1) constructors become unreadable/error‑prone (too many parameters, order matters, same-type mixups), and (2) you want validation to happen *before* a valid object is created.

The notes walk through why common alternatives fail (huge parameterized constructors, constructor overloading/telescoping, and a `HashMap<String,Object>` approach that lacks compile-time safety), then build up the Builder idea: use a typed helper/builder object to collect named values with type safety, validate them, and finally construct the real object.

Final recommended flow:

- `Student.getStudentBuilder()` returns a builder (implemented as a **static inner class** so it can call a **private** `Student` constructor).
- Builder uses **fluent method chaining** (`setX()` returns `this`) and ends with `build()`.
- Validation can live in `build()` (cleaner) or the constructor; on failure, throw an exception and avoid creating the `Student`.
- Result: readable object creation, scalable to many attributes, avoids constructor explosion, and prevents parameter-order mistakes.
</aside>

<aside>
💡

Here is the exact skeleton of the modern, standard **"Effective Java" Builder Pattern**.

You can use this as a template. Notice how the outer class is locked down (immutable and private), and the inner `Builder` class does all the heavy lifting.

```java
public class TargetClass {

    // 1. All fields are private and final (Immutable)
    private final String fieldOne;
    private final int fieldTwo;

    // 2. Private constructor: Forces you to use the Builder
    // It takes the Builder as an argument and copies the values over
    private TargetClass(Builder builder) {
        this.fieldOne = builder.fieldOne;
        this.fieldTwo = builder.fieldTwo;
    }

    // 3. Only Getters are provided (No Setters, because it is final)
    public String getFieldOne() {
        return fieldOne;
    }

    public int getFieldTwo() {
        return fieldTwo;
    }

    // 4. A static method to easily create a new Builder
    public static Builder builder() {
        return new Builder();
    }

    // ==========================================
    // 5. The Static Inner Builder Class
    // ==========================================
    public static class Builder {

        // Fields mirror the outer class, but are NOT final
        private String fieldOne;
        private int fieldTwo;

        // Private constructor for the builder itself
        private Builder() {}

        // 6. Chainable setter methods returning 'this'
        public Builder fieldOne(String fieldOne) {
            this.fieldOne = fieldOne;
            return this; // Returns the builder so you can chain
        }

        public Builder fieldTwo(int fieldTwo) {
            this.fieldTwo = fieldTwo;
            return this;
        }

        // 7. The final build() method
        // This is where we finally create the outer class
        public TargetClass build() {
            // Pass 'this' (the builder itself) to the outer class's constructor
            return new TargetClass(this);
        }
    }
}
```

### Quick checklist of what happens here:

1. **`final` fields on top:** Once `TargetClass` is built, its data can never be changed.
2. **`private` constructor:** Nobody can type `new TargetClass(...)` by accident.
3. **`public static class Builder`:** The builder lives inside the target class but doesn't require an instance of the target class to exist first.
4. **`return this`:** Every configuration method in the builder returns itself, which is what allows the `.method().method()` chaining.
5. **`build()` method:** Takes all the temporary data sitting in the builder, passes it to that private constructor, and returns the finished, unchangeable `TargetClass`.
</aside>

Yes — you need **lecture-flow notes**, not textbook notes: same order, same examples, and wording kept close to how the instructor explains it, with only fillers and repeated classroom talk removed.

## Builder Design Pattern — Instructor-Style Notes

### 1. Introduction

In the previous class, we discussed the Singleton design pattern.

Singleton falls under the category of **creational design patterns**.

Similarly, Builder design pattern also falls under the category of creational design patterns.

Builder is a very interesting design pattern which solves a very interesting use case.

First, we will try to understand it theoretically. We will try to understand the logic behind it. Once everyone is comfortable with the logic and problem statement, then we will implement the Builder design pattern.

The first time you try to implement it, you might feel a little bit weird. But once you get into the habit of using the Builder design pattern, it becomes super easy.

### 2. Use case — a class with a lot of attributes

Assume that I have a `Student` class.

What different attributes can be present in a Student class?

```java
class Student {
    String name;
    int age;
    String studentId;
    String email;
    String password;
    String batch;
    double psp;
    double attendance;
    String universityName;
    int graduationYear;
    String phone;
    String city;
    String country;
    String pinCode;
}
```

We can literally add 50 attributes to the Student class.

All these attributes can be proper and valid attributes of a Student.

Now, if I ask you to create the object of this Student class, how will you do it?

A very simple way is:

```java
Student s1 = new Student();
```

But if you create the Student object using an empty constructor, will these attributes hold any value?

Apart from their default values, there will be no proper values.

Therefore, you will have to manually set each and every attribute:

```java
Student s1 = new Student();

s1.setName("Anshuman");
s1.setAge(30);
s1.setBatch("Morning");
s1.setPsp(85);
```

Now you can tell me that instead of doing this, we can create a parameterised constructor which takes all the values.

### 3. Parameterised constructor

We can create a constructor like this:

```java
Student(
    String name,
    int age,
    String batch,
    double psp,
    String universityName,
    int graduationYear,
    String phone
) {
    this.name = name;
    this.age = age;
    this.batch = batch;
    this.psp = psp;
    this.universityName = universityName;
    this.graduationYear = graduationYear;
    this.phone = phone;
}
```

Then the object can be created like this:

```java
Student s1 = new Student(
    "Anshuman",
    30,
    "Morning",
    85,
    "Scaler University",
    2025,
    "9876543210"
);
```

But there is a problem.

As of now, we only have around 10 attributes. Tomorrow, in a realistic way, we can have 50 attributes.

If I create a constructor with 50 parameters, is it going to be an easy task while creating the object?

No.

It is going to be very hard to remember.

You will have to check the constructor declaration again and again.

### 4. The order in which parameters are passed matters

The order in which the parameters are passed to the constructor matters.

For example:

```java
Student(
    String name,
    String email,
    String password
) {
    this.name = name;
    this.email = email;
    this.password = password;
}
```

All these three parameters have the same data type: `String`.

Now, while creating the Student object, can I pass email, name and password in the wrong order and still get no compilation error?

Yes.

```java
Student s1 = new Student(
    "anshuman@gmail.com",  // passed as name
    "Anshuman",            // passed as email
    "password123"
);
```

This code will compile because all three values are Strings.

But logically, this is wrong.

The first argument expected the name, but we sent the email address.

So, it is possible that I can accidentally pass a wrong parameter into the constructor as the first parameter.

The compiler will not identify the problem because the data type is still correct.

Therefore, creating a parameterised constructor is not impossible, but it is not an easy task.

**Issues with this approach**

1. Difficult to understand.
2. Prone to errors because we have to look at the constructor declaration again and again.
3. Order of the parameters matters.
4. Parameters with the same data type can accidentally be interchanged.
5. With 50 attributes, the constructor becomes very difficult to use.

So, problem number one is: **There are a lot of attributes.**

### 5. Validate the object before creation

There is a second requirement.

While creating the Student object, I want to validate the values.

For example:

```
Graduation year should be greater than 2020.
Phone number should be valid.
Name should not be empty.
Email should not be empty.
Password should not be empty.
Age should satisfy the required condition.
```

The important requirement is: **No object of Student should be created if any validation fails.**

For example:

```java
Student s1 = new Student("", "", "");
```

Technically, the constructor can create this object and assign empty values.

But this is wrong because:

- A Student cannot have an empty name.
- A Student cannot have an empty email.
- A Student cannot have an empty password.

If the name is empty or the name is less than two characters, I do not want to create the Student object.

**Can we validate after creating the object?**

One approach can be:

```java
Student s1 = new Student("", "", "");

if (s1.getName().isEmpty()) {
    throw new RuntimeException();
}

if (s1.getEmail().isEmpty()) {
    throw new RuntimeException();
}
```

But what is the problem here?

The problem statement says: If the name is empty, I do not want to create the object in the first place.

But here, we first created the object, allocated the memory and then performed the validation.

These are two different things.

We want to validate the input before a valid Student object is returned.

### 6. Validation inside the constructor

We can put the validations inside the constructor:

```java
Student(
    String name,
    int age,
    String batch,
    double psp,
    String universityName,
    int graduationYear,
    String phone
) {
    if (graduationYear <= 2020) {
        throw new RuntimeException();
    }

    if (phone == null || phone.isEmpty()) {
        throw new RuntimeException();
    }

    if (name == null || name.isEmpty()) {
        throw new RuntimeException();
    }

    this.name = name;
    this.age = age;
    this.batch = batch;
    this.psp = psp;
    this.universityName = universityName;
    this.graduationYear = graduationYear;
    this.phone = phone;
}
```

Any parameter which needs to be checked can be validated using `if-else`.

If a validation fails, throw an exception.

Only after all validations pass do we assign the values.

So, now we have two major problems:

1. Too many attributes.
2. Validation before object creation.

### 7. Can constructor overloading solve the problem?

The first solution that comes to mind is to create different constructors.

For example:

```java
Student(String name, int age) {
    this.name = name;
    this.age = age;
}

Student(String name) {
    this.name = name;
}

Student(String name, String batch) {
    this.name = name;
    this.batch = batch;
}

Student(String name, String phone) {
    this.name = name;
    this.phone = phone;
}
```

The idea is that the client can choose whichever constructor contains the required attributes.

But this approach has problems.

**Problem 1: Too many constructors**

If a class has `N` attributes, there can be a very large number of possible combinations.

The slide represents it as:

```
N attributes → potentially 2^N combinations of constructors
```

We cannot realistically create one constructor for every possible combination.

**Problem 2: Some constructors are not even possible**

Look at these two constructors:

```java
Student(String name, String batch) {
}

Student(String name, String phone) {
}
```

For Java, both constructors have the same signature:

```
Student(String, String)
```

Java does not care about the parameter names while checking method overloading.

It only checks the number, type and order of parameters.

Therefore, these constructors are not possible together.

Similarly:

```java
Student(String name) {
}

Student(String email) {
}
```

are both:

```
Student(String)
```

This will cause a compile-time issue because both have the same method signature.

**Problem 3: A lot of code duplication**

Every constructor will repeat assignments:

```java
Student(String name) {
    this.name = name;
}

Student(String name, int age) {
    this.name = name;
    this.age = age;
}

Student(String name, int age, double psp) {
    this.name = name;
    this.age = age;
    this.psp = psp;
}
```

The same code is being repeated again and again.

**Problems with the constructor-overloading approach**

1. Too many constructors.
2. Some constructors might not even be possible.
3. A lot of code duplication.

### 8. Telescoping constructors

One way to reduce code duplication is constructor chaining.

```java
Student(String name) {
    this.name = name;
}

Student(String name, int age) {
    this(name);
    this.age = age;
}

Student(String name, int age, double psp) {
    this(name, age);
    this.psp = psp;
}
```

One constructor calls another constructor.

This is called **telescoping constructors**.

The constructors keep growing like a telescope.

This reduces some duplication, but it does not solve the major problem.

We still have:

- Too many constructors.
- Order-based parameters.
- Same-data-type issues.
- Different combinations that may not be possible.
- Difficulty understanding the object-creation statement.

### 9. Requirement — pass one parameter containing multiple named values

The requirement is something like:

```java
Student(params);
```

We want some data structure that allows us to pass multiple values along with their names.

For example:

```
name  : "Anshuman"
age   : 30
psp   : 85
batch : "Morning"
```

This looks like a key-value structure.

The name of the attribute can be the key.

The actual value can be the value.

So, the first data structure that comes to mind is a `Map`.

### 10. Using a HashMap

We can use:

```java
HashMap<String, Object>
```

Why is the key a `String`?

Because the keys will be attribute names:

```
"name"
"age"
"psp"
"batch"
```

Why is the value an `Object`?

Because different attributes can have different data types:

```
name  → String
age   → int
psp   → double
batch → String
```

Therefore:

```java
HashMap<String, Object> map = new HashMap<>();
```

The client can provide values like this:

```java
map.put("name", "Anshuman");
map.put("age", 25);
map.put("batch", "Morning");
map.put("psp", 85.0);
```

The Student object can then be created using one constructor parameter:

```java
Student student = new Student(map);
```

The constructor can extract values:

```java
Student(HashMap<String, Object> map) {
    String name = (String) map.get("name");
    int age = (int) map.get("age");
    String batch = (String) map.get("batch");
    double psp = (double) map.get("psp");

    this.name = name;
    this.age = age;
    this.batch = batch;
    this.psp = psp;
}
```

At least now the constructor has only one parameter.

The map can store any number of key-value pairs.

### 11. Problems with HashMap

The HashMap solution appears flexible, but it is error-prone.

**Problem 1: Typo in the key name**

The constructor expects:

```java
map.get("name");
```

But the client might write:

```java
map.put("nama", "Anshuman");
```

There is a typo in the key.

The compiler will not give an error because `"nama"` is still a valid String.

Later:

```java
map.get("name");
```

will return `null`.

Therefore, there is no compile-time check for key names.

**Problem 2: No compile-time safety for the data type of values**

The map accepts `Object` as its value.

Therefore, the client can write:

```java
map.put("age", "Scaler");
```

This will compile because `"Scaler"` is an Object.

But the constructor expects:

```java
int age = (int) map.get("age");
```

Now the code will fail during type casting.

This is a runtime failure.

The compiler cannot guarantee that the value associated with `"age"` will always be an integer.

**Problem 3: Manual casting**

We have to manually cast every value:

```java
String name = (String) map.get("name");
int age = (int) map.get("age");
double psp = (double) map.get("psp");
```

If the wrong value is passed, the problem is identified only at runtime.

**Problem 4: Missing key**

If a key is not present:

```java
map.get("name");
```

returns `null`.

Now the constructor has to separately handle whether every required key is present.

### 12. Requirements from the HashMap discussion

We need something that satisfies all three requirements.

**Requirement 1**

Something which is like a map.

That means it should allow us to store multiple values having multiple data types.

**Requirement 2**

It should have a compile-time check for the key names.

For example, this should not be allowed:

```java
helper.nama = "Anshuman";
```

if there is no field called `nama`.

**Requirement 3**

It should have a compile-time check for the data type of values.

For example, this should not be allowed:

```java
helper.age = "Scaler";
```

if `age` is an integer.

### 13. What is the most basic type-safe structure in Java?

What is the most basic thing that we have learned in Java?

A class.

A class is a blueprint of an object.

A class can also be considered like a map, but it gives us type safety.

For example:

```java
class Helper {
    String name;
    int age;
    String batch;
    double psp;
    String universityName;
    int graduationYear;
    String phone;
}
```

Now:

```java
Helper helper = new Helper();

helper.name = "Anshuman";
helper.age = 25;
```

The compiler checks the key name.

This is invalid:

```java
helper.nama = "Anshuman";
```

because `nama` is not a field.

The compiler also checks the data type.

This is invalid:

```java
helper.age = "Scaler";
```

because `age` is an integer.

Therefore, a class provides:

- Named attributes.
- Multiple data types.
- Compile-time checking of attribute names.
- Compile-time checking of value types.

### 14. Create a Student helper class

We can create a helper class:

```java
class StudentHelper {
    String name;
    int age;
    String batch;
    double psp;
    String universityName;
    int graduationYear;
    String phone;
}
```

The Student constructor can now take only one parameter:

```java
class Student {
    String name;
    int age;
    String batch;
    double psp;
    String universityName;
    int graduationYear;
    String phone;

    Student(StudentHelper helper) {
        // validations

        this.name = helper.name;
        this.age = helper.age;
        this.batch = helper.batch;
        this.psp = helper.psp;
        this.universityName = helper.universityName;
        this.graduationYear = helper.graduationYear;
        this.phone = helper.phone;
    }
}
```

The client can do:

```java
StudentHelper helper = new StudentHelper();

helper.name = "Anshuman";
helper.age = 25;
helper.batch = "Morning";
helper.psp = 85.0;

Student student = new Student(helper);
```

### 15. Why create a helper object?

The requirement says: Do not create the Student object if validation fails.

The requirement does not say that we cannot create a helper object.

So, we can always create the helper object.

The helper object is a very plain object.

It simply stores values.

The actual Student object is created only after the validations pass.

For example:

```java
Student(StudentHelper helper) {
    if (helper.graduationYear <= 2020) {
        throw new RuntimeException();
    }

    if (helper.phone == null || helper.phone.isEmpty()) {
        throw new RuntimeException();
    }

    this.name = helper.name;
    this.age = helper.age;
    this.batch = helper.batch;
    this.psp = helper.psp;
    this.universityName = helper.universityName;
    this.graduationYear = helper.graduationYear;
    this.phone = helper.phone;
}
```

If any validation fails, the Student object is not successfully created.

But the helper object can still exist.

### 16. Helper does not necessarily need all 50 attributes

Suppose the Student class has 50 attributes.

While creating the Student object, we may not need all 50 attributes.

Probably we need only 5, 7 or 10 attributes.

The helper class can contain only the meaningful attributes required during object creation.

For example:

```java
class StudentHelper {
    int age;
    String name;
    String phone;
    String batchId;
    String email;
}
```

We may not need:

```
address
city
country
pin code
```

during the initial object creation.

The helper class is taking some responsibility away from the Student class.

### 17. Core intuition of Builder

This is not yet the final implementation.

We are creating the intuition of the solution.

The Builder design pattern says: If there is a class with many attributes and you want to have some validation rules, delegate some responsibility to a helper class.

The Student class says: I do not want to deal with many attributes.

I do not want to deal with different constructors.

I will deal only with the builder.

Whatever values the builder gives me, I will take those values, validate them and create the object.

The Student Builder says: I will deal with the client.

I will collect the input values.

I will give the final values to the Student class.

The helper class is more for object creation than validation.

Initially, validation can still happen inside the Student constructor.

The helper class is mainly there to talk with the client, take whatever values the client gives and pass the proper values to the main class.

This is the core foundation of the Builder design pattern.

### 18. Initial implementation with getters and setters

```java
class StudentBuilder {
    private int age;
    private String name;
    private String phone;
    private String batchId;
    private String email;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

The Student constructor can validate the builder:

```java
class Student {
    private int age;
    private String name;
    private String phone;
    private String batchId;
    private String email;

    public Student(StudentBuilder builder) {
        if (builder.getAge() < 18) {
            throw new RuntimeException(
                "Age should be greater than 18"
            );
        }

        if (
            builder.getBatchId() == null ||
            builder.getBatchId().length() == 0
        ) {
            throw new RuntimeException(
                "Length of batch should be greater than zero"
            );
        }

        this.age = builder.getAge();
        this.name = builder.getName();
        this.phone = builder.getPhone();
        this.batchId = builder.getBatchId();
        this.email = builder.getEmail();
    }
}
```

The client does:

```java
StudentBuilder builder = new StudentBuilder();

builder.setAge(10);
builder.setBatchId("MWF");
builder.setEmail("someone@gmail.com");
builder.setName("Yash");
builder.setPhone("9876543210");

Student student = new Student(builder);
```

Here, the Student Builder object gets created.

But because the age is less than 18, the Student object does not get created successfully.

If we change the age:

```java
builder.setAge(20);
```

then the Student object will be created successfully.

### 19. Problem with this initial Builder implementation

The basic requirement of the client was: I want to create an object of Student.

But in order to create a Student, the client has now been asked to learn about another class called `StudentBuilder`.

The client has to do:

```java
StudentBuilder builder = new StudentBuilder();
```

Why should the client be responsible for creating the Student Builder object?

The Student Builder is an internal implementation detail of the Student class.

The client will say: I am not responsible for creating StudentBuilder.

Whatever you want to do internally, you do it.

Do not give your internal problems to me.

Therefore, the best person to create the Student Builder object is the Student class itself.

### 20. Student should provide the Builder

Inside the Student class, we can provide a method:

```java
public static StudentBuilder getStudentBuilder() {
    return new StudentBuilder();
}
```

Why is this method `static`?

Because we want to obtain the Builder before a Student object exists.

If it is not static, we will need a Student object to call it.

But the Builder is needed to create the Student object itself.

Therefore:

```java
StudentBuilder builder =
        Student.getStudentBuilder();
```

Now the client does not need to know how the Builder object is created.

The client simply has faith that calling:

```java
Student.getStudentBuilder();
```

will return the Builder for Student.

### 21. Current object-creation flow

The flow is:

**Step 1: Get the Student Builder**

```java
StudentBuilder builder =
        Student.getStudentBuilder();
```

**Step 2: Set values inside the Builder**

```java
builder.setAge(20);
builder.setBatchId("MWF");
builder.setEmail("someone@gmail.com");
builder.setName("Yash");
builder.setPhone("9876543210");
```

**Step 3: Create the Student object**

```java
Student student = new Student(builder);
```

This works, but the code can still be made more beautiful.

### 22. Method chaining

It would look better if we could write:

```java
Student student = Student.getStudentBuilder()
        .setAge(20)
        .setName("Yash")
        .setEmail("someone@gmail.com")
        .setBatchId("MWF")
        .setPhone("9876543210")
        .build();
```

Set the age, set the name, set the email and finally call `build()`.

But currently, the setter methods return `void`.

For example:

```java
public void setAge(int age) {
    this.age = age;
}
```

If we want to call another method after `setAge()`, the setter should return the Student Builder.

```java
public StudentBuilder setAge(int age) {
    this.age = age;
    return this;
}
```

Similarly:

```java
public StudentBuilder setName(String name) {
    this.name = name;
    return this;
}

public StudentBuilder setEmail(String email) {
    this.email = email;
    return this;
}

public StudentBuilder setBatchId(String batchId) {
    this.batchId = batchId;
    return this;
}

public StudentBuilder setPhone(String phone) {
    this.phone = phone;
    return this;
}
```

Why are we returning `this`?

Because after setting the value, we return the same Builder object.

Therefore, the next setter can be called on the same object.

This allows:

```java
builder
    .setAge(20)
    .setName("Yash")
    .setEmail("someone@gmail.com");
```

### 23. The `build()` method

As of now, there is no method called `build()`.

If we want to write:

```java
builder.build();
```

we need to add a `build()` method inside the Builder.

What should the responsibility of the `build()` method be?

It should create the Student object and return it.

```java
public Student build() {
    return new Student(this);
}
```

Here, `this` refers to the current Student Builder object.

The Student constructor accepts a Student Builder, so we pass the current Builder to the Student constructor.

Now:

```java
Student student = builder.build();
```

returns the final Student object.

### 24. Complete fluent flow

```java
Student student = Student.getStudentBuilder()
        .setAge(20)
        .setName("Yash")
        .setEmail("someone@gmail.com")
        .setBatchId("MWF")
        .setPhone("9876543210")
        .build();
```

Now the code tells us exactly what is happening:

1. Get the Student Builder.
2. Set the age.
3. Set the name.
4. Set the email.
5. Set the batch.
6. Set the phone.
7. Build the Student object.

The code becomes more readable and maintainable.

Even if there are 50 values, this will still be cleaner than passing 50 positional arguments to a constructor.

### 25. Should Student be created without the Builder?

Now tell me one thing.

Should we allow somebody to create the Student object without using the Builder?

For example:

```java
new Student(builder);
```

If the constructor is public, anybody can call it directly.

But we want Builder to be the only supported way of creating the Student object.

Therefore, the Student constructor should be private:

```java
private Student(StudentBuilder builder) {
    this.age = builder.getAge();
    this.name = builder.getName();
    this.email = builder.getEmail();
    this.batchId = builder.getBatchId();
    this.phone = builder.getPhone();
}
```

But now there is another problem.

If `StudentBuilder` is a separate class outside `Student`, it cannot call the private Student constructor.

Private members can only be accessed from inside the same class.

### 26. Move Student Builder inside Student

Because the Builder needs to access the private Student constructor, move the Builder class inside the Student class.

```java
class Student {

    private Student(StudentBuilder builder) {
        // assignments
    }

    static class StudentBuilder {
        // builder fields and methods
    }
}
```

Now `StudentBuilder` is an inner class of `Student`.

Anything present inside the Student class can access the private members of Student.

Therefore, this is now allowed:

```java
public Student build() {
    return new Student(this);
}
```

### 27. Why should the inner Builder be static?

The method:

```java
public static StudentBuilder getStudentBuilder() {
    return new StudentBuilder();
}
```

is static.

It is trying to create an object of the inner Student Builder class.

If the Student Builder is non-static, normally it requires an outer Student object.

But we do not have a Student object yet.

We are creating the Builder in order to create the Student.

Therefore, the inner Builder class should also be static:

```java
public static class StudentBuilder {
}
```

The main problem was that the Student constructor had to be private.

So, we moved the Builder class inside the Student class and made it static.

### 28. Moving validation to the `build()` method

As of now, the Student constructor is taking care of two things:

1. Validation.
2. Creating and assigning the Student object.

Can we remove validation from the Student constructor?

Where should we keep it?

Where are we actually building the Student object?

Inside the `build()` method.

Therefore, validation can be moved to `build()`:

```java
public Student build() {
    if (age < 18) {
        throw new RuntimeException(
            "Age should be greater than 18"
        );
    }

    if (batchId == null || batchId.length() == 0) {
        throw new RuntimeException(
            "Length of batch should be greater than zero"
        );
    }

    return new Student(this);
}
```

This is an optimisation.

You may keep validation inside the constructor or move it to `build()`.

But moving it to `build()` makes the constructor cleaner.

The Builder now:

- Stores the parameters.
- Validates the parameters.
- Calls the private constructor.
- Returns the final Student object.

### 29. Final implementation

```java
public class Student {

    private int age;
    private String name;
    private String phone;
    private String batchId;
    private String email;

    private Student(StudentBuilder builder) {
        this.age = builder.age;
        this.name = builder.name;
        this.phone = builder.phone;
        this.batchId = builder.batchId;
        this.email = builder.email;
    }

    public static StudentBuilder getStudentBuilder() {
        return new StudentBuilder();
    }

    public static class StudentBuilder {

        private int age;
        private String name;
        private String phone;
        private String batchId;
        private String email;

        public StudentBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public StudentBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public StudentBuilder setBatchId(String batchId) {
            this.batchId = batchId;
            return this;
        }

        public StudentBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Student build() {
            if (age < 18) {
                throw new RuntimeException(
                    "Age should be greater than 18"
                );
            }

            if (batchId == null || batchId.length() == 0) {
                throw new RuntimeException(
                    "Length of batch should be greater than zero"
                );
            }

            return new Student(this);
        }
    }
}
```

Client code:

```java
Student student = Student.getStudentBuilder()
        .setAge(20)
        .setName("Yash")
        .setEmail("someone@gmail.com")
        .setBatchId("MWF")
        .setPhone("9876543210")
        .build();
```

This is the only way the Student object can be created because the Student constructor is private.

### 30. What exactly is Builder doing?

Builder is taking care of an extra class which handles the parameters.

It can take care of validation.

It calls the Student constructor privately.

The Builder remains an inner class of Student.

The client does not directly deal with constructor overloading.

The client does not need to remember the parameter order.

The client does not need to pass 50 values into one constructor.

The name of every setter tells us what value is being provided.

```java
.setAge(20)
.setName("Yash")
.setEmail("someone@gmail.com")
```

Therefore, there is no confusion regarding which value represents which attribute.

### 31. Real-world usage

Builder is used across the industry.

The instructor gives the example of Firebase APIs.

Firebase has entities which can contain many attributes.

Instead of asking you to directly create a complicated object, it can ask you to first create a Builder, configure that Builder and finally call `build()`.

For example, Firebase advertising APIs contain classes such as an `AdLoader.Builder`.

The idea is the same:

```
Create a Builder for constructing the final object.
Set the required values.
Call build().
```

The same way we created:

```
StudentBuilder for constructing a Student object.
```

### 32. When should we use Builder?

When will you opt for the Builder design pattern?

When:

- A class has too many attributes.
- You need to validate the attributes before returning the object.
- Different combinations of attributes may be required.
- Constructor overloading becomes difficult.
- Constructor parameters become difficult to remember.
- Several parameters have the same data type.
- You want to create a complex object step by step.

The slide summarises Builder as: It allows us to create an object of a class with the following constraints:

1. Validate the object before its creation.
2. Too many attributes.

### 33. Step-by-step summary

Builder creates complex objects in a step-by-step way.

**Step 1**

Create or obtain the Builder object.

```java
Student.getStudentBuilder()
```

**Step 2**

Set the attributes.

```java
.setAge(20)
.setName("Yash")
.setEmail("someone@gmail.com")
```

**Step 3**

Create the final Student object.

```java
.build()
```

So:

```java
Student student = Student.getStudentBuilder()
        .setAge(20)
        .setName("Yash")
        .setEmail("someone@gmail.com")
        .build();
```

### 34. Final core idea in the instructor’s wording

I do not want to deal with many attributes.

I do not want to deal with different constructors.

I will deal only with the Builder.

The Builder will deal with all the input.

Whatever values the Builder gives me, I will validate them and create the object.

The Builder is responsible for dealing with the object-creation parameters.

The main class can continue focusing on its actual business logic.

### 35. Doubts discussed

**Can validation remain in the constructor?**

Yes.

Validation may remain inside the private Student constructor.

Moving it to `build()` is an optimisation which makes the constructor cleaner.

**Can there be more than one helper class?**

After using the final design, one Builder class is normally sufficient to solve the problem.

**Can the constructor be protected instead of private?**

It can be implemented in other ways, but making it private ensures that the client has to use the Builder.

**Is validation mandatory for Builder?**

No.

Sometimes validation is needed and sometimes it is not.

Builder is also about organising complicated object-creation code.

**Can Lombok create the Builder?**

Lombok provides:

```java
@Builder
```

It can generate Builder-related code automatically.

But the instructor does not recommend using it before understanding the internal flow because then you will never understand how Builder is actually implemented.

First implement Builder yourself. After understanding the complete flow, an annotation may be used where appropriate.
