
# Anki Cards for Java Streams Mastery

## Level 1 - Stream Basics

**Q: How do you filter all odd numbers from a list using streams?**
```java
List<Integer> oddNumbers = numbers.stream()
    .filter(n -> n % 2 != 0)
    .collect(Collectors.toList());
```

**Q: How do you filter all even numbers from a list using streams?**
```java
List<Integer> evenNumbers = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());
```

**Q: How do you count elements in a stream?**
```java
long count = numbers.stream().count();
// or
long count = numbers.stream().collect(Collectors.counting());
```

**Q: How do you find the first element in a stream?**
```java
Optional<Integer> first = numbers.stream().findFirst();
```

**Q: How do you find any element in a stream?**
```java
Optional<Integer> any = numbers.stream().findAny();
```

**Q: How do you check if a list is empty using streams?**
```java
boolean isEmpty = list.stream().count() == 0;
// or
boolean isEmpty = list.stream().findAny().isEmpty();
```

**Q: How do you convert a List to a Stream?**
```java
Stream<String> stream = list.stream();
```

**Q: How do you convert a Stream to a List?**
```java
List<String> list = stream.collect(Collectors.toList());
// or Java 16+:
List<String> list = stream.toList();
```

**Q: What are all possible ways to create streams?**
```java
// From values
Stream<String> stream1 = Stream.of("A", "B", "C");

// From collection
Stream<String> stream2 = list.stream();

// From array
Stream<String> stream3 = Arrays.stream(array);

// Infinite stream with iterate
Stream<Integer> stream4 = Stream.iterate(0, n -> n + 2);

// Infinite stream with generate
Stream<Double> stream5 = Stream.generate(Math::random);
```

**Q: Difference between Collection and Stream?**
- Collection stores data, Stream doesn't
- Collection can be modified, Stream is immutable
- Collection can be traversed multiple times, Stream only once
- Collection is eager, Stream is lazy
- Collection is about data storage, Stream is about data processing

## Level 2 - filter()

**Q: How do you filter strings starting with "A"?**
```java
List<String> result = strings.stream()
    .filter(s -> s.startsWith("A"))
    .collect(Collectors.toList());
```

**Q: How do you filter strings ending with "x"?**
```java
List<String> result = strings.stream()
    .filter(s -> s.endsWith("x"))
    .collect(Collectors.toList());
```

**Q: How do you filter numbers greater than 100?**
```java
List<Integer> result = numbers.stream()
    .filter(n -> n > 100)
    .collect(Collectors.toList());
```

**Q: How do you filter numbers between two values?**
```java
List<Integer> result = numbers.stream()
    .filter(n -> n >= min && n <= max)
    .collect(Collectors.toList());
```

**Q: How do you filter prime numbers?**
```java
List<Integer> primes = numbers.stream()
    .filter(this::isPrime)
    .collect(Collectors.toList());

// Helper method
private boolean isPrime(int n) {
    if (n <= 1) return false;
    return IntStream.range(2, (int) Math.sqrt(n) + 1)
        .noneMatch(i -> n % i == 0);
}
```

**Q: How do you filter palindrome strings?**
```java
List<String> palindromes = strings.stream()
    .filter(s -> new StringBuilder(s).reverse().toString().equals(s))
    .collect(Collectors.toList());
```

**Q: How do you remove null values from a stream?**
```java
List<String> nonNull = list.stream()
    .filter(Objects::nonNull)
    .collect(Collectors.toList());
```

**Q: How do you remove empty strings from a stream?**
```java
List<String> nonEmpty = strings.stream()
    .filter(s -> !s.isEmpty())
    .collect(Collectors.toList());
```

**Q: How do you filter employees whose salary > 50000?**
```java
List<Employee> highEarners = employees.stream()
    .filter(e -> e.getSalary() > 50000)
    .collect(Collectors.toList());
```

**Q: How do you filter employees from IT department?**
```java
List<Employee> itEmployees = employees.stream()
    .filter(e -> "IT".equals(e.getDepartment()))
    .collect(Collectors.toList());
```

## Level 3 - map()

**Q: How do you square every number in a list?**
```java
List<Integer> squares = numbers.stream()
    .map(n -> n * n)
    .collect(Collectors.toList());
```

**Q: How do you cube every number in a list?**
```java
List<Integer> cubes = numbers.stream()
    .map(n -> n * n * n)
    .collect(Collectors.toList());
```

**Q: How do you convert names to uppercase?**
```java
List<String> upperCase = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

**Q: How do you convert names to lowercase?**
```java
List<String> lowerCase = names.stream()
    .map(String::toLowerCase)
    .collect(Collectors.toList());
```

**Q: How do you find the length of every string?**
```java
List<Integer> lengths = strings.stream()
    .map(String::length)
    .collect(Collectors.toList());
```

**Q: How do you extract employee names from a list of employees?**
```java
List<String> names = employees.stream()
    .map(Employee::getName)
    .collect(Collectors.toList());
```

**Q: How do you extract employee salaries?**
```java
List<Double> salaries = employees.stream()
    .map(Employee::getSalary)
    .collect(Collectors.toList());
```

**Q: How do you convert Integer to String?**
```java
List<String> strings = integers.stream()
    .map(String::valueOf)
    .collect(Collectors.toList());
```

**Q: How do you trim all strings?**
```java
List<String> trimmed = strings.stream()
    .map(String::trim)
    .collect(Collectors.toList());
```

**Q: How do you add a prefix to every string?**
```java
List<String> prefixed = strings.stream()
    .map(s -> "prefix_" + s)
    .collect(Collectors.toList());
```

## Level 4 - sorted()

**Q: How do you sort numbers in ascending order?**
```java
List<Integer> sorted = numbers.stream()
    .sorted()
    .collect(Collectors.toList());
```

**Q: How do you sort numbers in descending order?**
```java
List<Integer> descending = numbers.stream()
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.toList());
```

**Q: How do you sort strings alphabetically?**
```java
List<String> sorted = strings.stream()
    .sorted()
    .collect(Collectors.toList());
```

**Q: How do you sort strings by length?**
```java
List<String> byLength = strings.stream()
    .sorted(Comparator.comparingInt(String::length))
    .collect(Collectors.toList());
```

**Q: How do you sort employees by salary?**
```java
List<Employee> bySalary = employees.stream()
    .sorted(Comparator.comparing(Employee::getSalary))
    .collect(Collectors.toList());
```

**Q: How do you sort employees by age?**
```java
List<Employee> byAge = employees.stream()
    .sorted(Comparator.comparing(Employee::getAge))
    .collect(Collectors.toList());
```

**Q: How do you perform multi-level sorting?**
```java
List<Employee> multiSort = employees.stream()
    .sorted(Comparator.comparing(Employee::getDepartment)
        .thenComparing(Employee::getSalary))
    .collect(Collectors.toList());
```

**Q: How do you sort in reverse order?**
```java
List<String> reverse = strings.stream()
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.toList());
```

**Q: How do you use a custom Comparator?**
```java
List<Employee> customSort = employees.stream()
    .sorted((e1, e2) -> {
        int deptCompare = e1.getDepartment().compareTo(e2.getDepartment());
        return deptCompare != 0 ? deptCompare : e1.getSalary().compareTo(e2.getSalary());
    })
    .collect(Collectors.toList());
```

**Q: What is stable sorting in streams?**
Stable sorting maintains the relative order of elements with equal sort keys. Java's stream sorting is stable for all sorting algorithms.

## Level 5 - distinct()

**Q: How do you remove duplicate numbers?**
```java
List<Integer> unique = numbers.stream()
    .distinct()
    .collect(Collectors.toList());
```

**Q: How do you remove duplicate strings?**
```java
List<String> unique = strings.stream()
    .distinct()
    .collect(Collectors.toList());
```

**Q: How do you find distinct employee departments?**
```java
List<String> departments = employees.stream()
    .map(Employee::getDepartment)
    .distinct()
    .collect(Collectors.toList());
```

**Q: How does distinct() work with custom objects?**
`distinct()` uses `equals()` and `hashCode()` methods to determine uniqueness. Ensure these are properly implemented in your custom class.

## Level 6 - limit() and skip()

**Q: How do you get the first 5 numbers?**
```java
List<Integer> firstFive = numbers.stream()
    .limit(5)
    .collect(Collectors.toList());
```

**Q: How do you skip the first 5 numbers?**
```java
List<Integer> afterFive = numbers.stream()
    .skip(5)
    .collect(Collectors.toList());
```

**Q: How do you implement pagination with streams?**
```java
List<Employee> page = employees.stream()
    .skip(pageNumber * pageSize)
    .limit(pageSize)
    .collect(Collectors.toList());
```

**Q: How do you find the top 10 salaries?**
```java
List<Double> top10 = employees.stream()
    .map(Employee::getSalary)
    .sorted(Comparator.reverseOrder())
    .limit(10)
    .collect(Collectors.toList());
```

**Q: How do you find the second highest salary?**
```java
OptionalDouble secondHighest = employees.stream()
    .mapToDouble(Employee::getSalary)
    .sorted()
    .distinct()
    .skip(employees.size() - 2)
    .findFirst();
```

## Level 7 - peek()

**Q: How do you debug a stream pipeline?**
```java
List<String> result = strings.stream()
    .filter(s -> s.startsWith("A"))
    .peek(System.out::println)  // Debug point
    .map(String::toUpperCase)
    .peek(System.out::println)  // Another debug point
    .collect(Collectors.toList());
```

**Q: How do you print values between operations?**
```java
numbers.stream()
    .filter(n -> n % 2 == 0)
    .peek(n -> System.out.println("Filtered: " + n))
    .map(n -> n * n)
    .forEach(System.out::println);
```

## Level 8 - Matching Operations

**Q: How does allMatch() work?**
```java
boolean allPositive = numbers.stream()
    .allMatch(n -> n > 0);
```

**Q: How does anyMatch() work?**
```java
boolean hasNegative = numbers.stream()
    .anyMatch(n -> n < 0);
```

**Q: How does noneMatch() work?**
```java
boolean noneZero = numbers.stream()
    .noneMatch(n -> n == 0);
```

**Q: How do you check if all salaries > 10000?**
```java
boolean allAbove10k = employees.stream()
    .allMatch(e -> e.getSalary() > 10000);
```

**Q: How do you check if any employee is HR?**
```java
boolean hasHR = employees.stream()
    .anyMatch(e -> "HR".equals(e.getDepartment()));
```

## Level 9 - findFirst() and findAny()

**Q: How do you find the first employee?**
```java
Optional<Employee> first = employees.stream().findFirst();
```

**Q: How do you find the first odd number?**
```java
Optional<Integer> firstOdd = numbers.stream()
    .filter(n -> n % 2 != 0)
    .findFirst();
```

**Q: How do you find any prime number?**
```java
Optional<Integer> anyPrime = numbers.stream()
    .filter(this::isPrime)
    .findAny();
```

**Q: What's the difference between findFirst() and findAny()?**
- `findFirst()` always returns the first element in encounter order
- `findAny()` may return any element (useful in parallel streams)
- Both return Optional to handle empty cases

## Level 10 - min(), max()

**Q: How do you find the largest number?**
```java
Optional<Integer> max = numbers.stream()
    .max(Integer::compareTo);
```

**Q: How do you find the smallest number?**
```java
Optional<Integer> min = numbers.stream()
    .min(Integer::compareTo);
```

**Q: How do you find the longest string?**
```java
Optional<String> longest = strings.stream()
    .max(Comparator.comparingInt(String::length));
```

**Q: How do you find the shortest string?**
```java
Optional<String> shortest = strings.stream()
    .min(Comparator.comparingInt(String::length));
```

**Q: How do you find the employee with highest salary?**
```java
Optional<Employee> highestPaid = employees.stream()
    .max(Comparator.comparing(Employee::getSalary));
```

**Q: How do you find the youngest employee?**
```java
Optional<Employee> youngest = employees.stream()
    .min(Comparator.comparing(Employee::getAge));
```

## Level 11 - count()

**Q: How do you count employees?**
```java
long employeeCount = employees.stream().count();
```

**Q: How do you count even numbers?**
```java
long evenCount = numbers.stream()
    .filter(n -> n % 2 == 0)
    .count();
```

**Q: How do you count strings starting with "A"?**
```java
long countA = strings.stream()
    .filter(s -> s.startsWith("A"))
    .count();
```

## Level 12 - reduce()

**Q: How do you sum numbers using reduce()?**
```java
Optional<Integer> sum = numbers.stream()
    .reduce((a, b) -> a + b);
// or with identity:
int sum = numbers.stream()
    .reduce(0, Integer::sum);
```

**Q: How do you find the product of numbers?**
```java
Optional<Integer> product = numbers.stream()
    .reduce((a, b) -> a * b);
```

**Q: How do you find the maximum using reduce()?**
```java
Optional<Integer> max = numbers.stream()
    .reduce(Integer::max);
```

**Q: How do you find the minimum using reduce()?**
```java
Optional<Integer> min = numbers.stream()
    .reduce(Integer::min);
```

**Q: How do you concatenate strings using reduce()?**
```java
Optional<String> concatenated = strings.stream()
    .reduce((a, b) -> a + b);
```

**Q: How do you calculate factorial using reduce()?**
```java
int factorial = IntStream.rangeClosed(1, n)
    .reduce(1, (a, b) -> a * b);
```

**Q: How do you sum salaries using reduce()?**
```java
OptionalDouble totalSalary = employees.stream()
    .mapToDouble(Employee::getSalary)
    .reduce((a, b) -> a + b);
```

## Level 13 - Collectors

**Q: How do you collect to a List?**
```java
List<String> list = stream.collect(Collectors.toList());
// or Java 16+:
List<String> list = stream.toList();
```

**Q: How do you collect to a Set?**
```java
Set<String> set = stream.collect(Collectors.toSet());
```

**Q: How do you collect to a Map?**
```java
Map<Integer, String> map = employees.stream()
    .collect(Collectors.toMap(Employee::getId, Employee::getName));
```

**Q: How do you use joining() collector?**
```java
String joined = strings.stream()
    .collect(Collectors.joining(", "));
```

**Q: How do you use counting() collector?**
```java
Long count = stream.collect(Collectors.counting());
```

**Q: How do you use averagingInt() collector?**
```java
Double average = employees.stream()
    .collect(Collectors.averagingInt(Employee::getAge));
```

**Q: How do you use averagingDouble() collector?**
```java
Double avgSalary = employees.stream()
    .collect(Collectors.averagingDouble(Employee::getSalary));
```

**Q: How do you use summingInt() collector?**
```java
Integer totalAge = employees.stream()
    .collect(Collectors.summingInt(Employee::getAge));
```

**Q: How do you use summarizingInt() collector?**
```java
IntSummaryStatistics stats = employees.stream()
    .collect(Collectors.summarizingInt(Employee::getAge));
```

**Q: How do you use groupingBy() collector?**
```java
Map<String, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDepartment));
```

**Q: How do you use partitioningBy() collector?**
```java
Map<Boolean, List<Employee>> partitioned = employees.stream()
    .collect(Collectors.partitioningBy(e -> e.getSalary() > 50000));
```

**Q: How do you use mapping() collector?**
```java
Set<String> departments = employees.stream()
    .collect(Collectors.mapping(
        Employee::getDepartment, 
        Collectors.toSet()
    ));
```

**Q: How do you use collectingAndThen() collector?**
```java
List<String> unmodifiableList = strings.stream()
    .collect(Collectors.collectingAndThen(
        Collectors.toList(),
        Collections::unmodifiableList
    ));
```

**Q: How do you use toUnmodifiableList() collector?**
```java
List<String> unmodifiable = strings.stream()
    .collect(Collectors.toUnmodifiableList());
```

## Level 14 - groupingBy()

**Q: How do you group employees by department?**
```java
Map<String, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDepartment));
```

**Q: How do you group students by grade?**
```java
Map<String, List<Student>> byGrade = students.stream()
    .collect(Collectors.groupingBy(Student::getGrade));
```

**Q: How do you group products by category?**
```java
Map<String, List<Product>> byCategory = products.stream()
    .collect(Collectors.groupingBy(Product::getCategory));
```

**Q: How do you group by age?**
```java
Map<Integer, List<Person>> byAge = people.stream()
    .collect(Collectors.groupingBy(Person::getAge));
```

**Q: How do you count employees per department?**
```java
Map<String, Long> countByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment, 
        Collectors.counting()
    ));
```

**Q: How do you find the highest salary per department?**
```java
Map<String, Optional<Employee>> highestByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.maxBy(Comparator.comparing(Employee::getSalary))
    ));
```

**Q: How do you find the average salary per department?**
```java
Map<String, Double> avgSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.averagingDouble(Employee::getSalary)
    ));
```

## Level 15 - partitioningBy()

**Q: How do you partition numbers into even/odd?**
```java
Map<Boolean, List<Integer>> evenOdd = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));
```

**Q: How do you partition students into passed/failed?**
```java
Map<Boolean, List<Student>> passedFailed = students.stream()
    .collect(Collectors.partitioningBy(s -> s.getGrade() >= 60));
```

**Q: How do you partition people into adult/minor?**
```java
Map<Boolean, List<Person>> adultMinor = people.stream()
    .collect(Collectors.partitioningBy(p -> p.getAge() >= 18));
```

**Q: How do you partition by salary > 50000?**
```java
Map<Boolean, List<Employee>> salaryPartition = employees.stream()
    .collect(Collectors.partitioningBy(e -> e.getSalary() > 50000));
```

## Level 16 - flatMap()

**Q: How do you flatten a list of lists?**
```java
List<Integer> flattened = listOfLists.stream()
    .flatMap(List::stream)
    .collect(Collectors.toList());
```

**Q: How do you flatten employee skills?**
```java
List<String> allSkills = employees.stream()
    .flatMap(e -> e.getSkills().stream())
    .distinct()
    .collect(Collectors.toList());
```

**Q: How do you flatten nested collections?**
```java
List<String> flattened = nestedLists.stream()
    .flatMap(Collection::stream)
    .collect(Collectors.toList());
```

**Q: How do you flatten Optional?**
```java
List<String> values = optionals.stream()
    .flatMap(Optional::stream)
    .collect(Collectors.toList());
```

## Level 17 - Optional

**Q: How do you avoid NullPointerException with Optional?**
```java
Optional<String> optional = Optional.ofNullable(getString());
String value = optional.orElse("default");
```

**Q: How does orElse() work?**
```java
String result = optional.orElse("default"); // Always evaluated
```

**Q: How does orElseGet() work?**
```java
String result = optional.orElseGet(() -> computeDefault()); // Lazy evaluation
```

**Q: How does orElseThrow() work?**
```java
String result = optional.orElseThrow(() -> new IllegalStateException("Value missing"));
```

**Q: How does ifPresent() work?**
```java
optional.ifPresent(value -> System.out.println("Value: " + value));
```

**Q: How does map() work with Optional?**
```java
Optional<Integer> length = optional.map(String::length);
```

**Q: How does flatMap() work with Optional?**
```java
Optional<String> upper = optional.flatMap(s -> s.isEmpty() ? Optional.empty() : Optional.of(s.toUpperCase()));
```

**Q: How does filter() work with Optional?**
```java
Optional<String> filtered = optional.filter(s -> s.startsWith("A"));
```

**Q: How does Optional.stream() work?**
```java
List<String> list = optional.stream().collect(Collectors.toList());
```

## Level 18 - Primitive Streams

**Q: What is IntStream?**
A specialized stream for primitive int values that avoids boxing overhead and provides numeric operations like sum(), average(), etc.

**Q: What is LongStream?**
A specialized stream for primitive long values with similar benefits to IntStream.

**Q: What is DoubleStream?**
A specialized stream for primitive double values.

**Q: How do you use range() with primitive streams?**
```java
IntStream range = IntStream.range(0, 10); // 0 to 9
```

**Q: How do you use rangeClosed() with primitive streams?**
```java
IntStream rangeClosed = IntStream.rangeClosed(0, 10); // 0 to 10
```

**Q: How do you use mapToInt()?**
```java
int[] lengths = strings.stream()
    .mapToInt(String::length)
    .toArray();
```

**Q: How do you use boxed() with primitive streams?**
```java
Stream<Integer> boxed = IntStream.of(1, 2, 3).boxed();
```

**Q: How do you use summaryStatistics()?**
```java
IntSummaryStatistics stats = IntStream.of(1, 2, 3, 4, 5).summaryStatistics();
System.out.println("Max: " + stats.getMax());
System.out.println("Min: " + stats.getMin());
System.out.println("Average: " + stats.getAverage());
```

## Level 19 - Method References

**Q: How do you use System.out::println?**
```java
list.forEach(System.out::println);
```

**Q: How do you use String::length?**
```java
List<Integer> lengths = strings.stream()
    .map(String::length)
    .collect(Collectors.toList());
```

**Q: How do you use Integer::parseInt?**
```java
List<Integer> numbers = stringNumbers.stream()
    .map(Integer::parseInt)
    .collect(Collectors.toList());
```

**Q: How do you use Employee::getSalary?**
```java
List<Double> salaries = employees.stream()
    .map(Employee::getSalary)
    .collect(Collectors.toList());
```

**Q: How do you use constructor reference (Employee::new)?**
```java
List<Employee> employees = ids.stream()
    .map(Employee::new)
    .collect(Collectors.toList());
```

## Level 20 - Functional Interfaces

**Q: What is a Predicate?**
A functional interface that takes one argument and returns a boolean. Used for filtering operations.
```java
Predicate<String> startsWithA = s -> s.startsWith("A");
```

**Q: What is a BiPredicate?**
A functional interface that takes two arguments and returns a boolean.
```java
BiPredicate<String, Integer> lengthCheck = (s, length) -> s.length() == length;
```

**Q: What is a Function?**
A functional interface that takes one argument and produces a result. Used for transformation operations.
```java
Function<String, Integer> lengthFunction = String::length;
```

**Q: What is a BiFunction?**
A functional interface that takes two arguments and produces a result.
```java
BiFunction<String, String, String> concat = (s1, s2) -> s1 + s2;
```

**Q: What is a Consumer?**
A functional interface that takes one argument and returns no result. Used for terminal operations.
```java
Consumer<String> printer = System.out::println;
```

**Q: What is a BiConsumer?**
A functional interface that takes two arguments and returns no result.
```java
BiConsumer<String, Integer> printer = (s, i) -> System.out.println(s + i);
```

**Q: What is a Supplier?**
A functional interface that takes no arguments but produces a result. Used for generating values.
```java
Supplier<Double> randomSupplier = Math::random;
```

**Q: What is a UnaryOperator?**
A functional interface that takes one argument and returns a result of the same type.
```java
UnaryOperator<String> upperCase = String::toUpperCase;
```

**Q: What is a BinaryOperator?**
A functional interface that takes two arguments of the same type and returns a result of that type.
```java
BinaryOperator<Integer> add = Integer::sum;
```

**Q: How do you create a custom functional interface?**
```java
@FunctionalInterface
interface StringOperation {
    String apply(String s);
}

StringOperation reverse = s -> new StringBuilder(s).reverse().toString();
```

## Level 21 - Parallel Streams

**Q: How do you use parallelStream()?**
```java
List<String> result = list.parallelStream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

**Q: What's the difference between stream() and parallelStream()?**
- `stream()` processes sequentially in a single thread
- `parallelStream()` processes across multiple threads using the common ForkJoinPool
- Parallel streams can improve performance for large datasets and CPU-intensive operations

**Q: When should you NOT use parallel streams?**
- Small datasets where overhead outweighs benefits
- Operations with shared mutable state
- I/O-bound operations
- Ordered operations where order matters
- When thread safety is a concern

**Q: How do you ensure thread safety with parallel streams?**
- Avoid shared mutable state
- Use thread-safe collectors
- Ensure operations are stateless and associative
- Consider using concurrent collections when needed

**Q: How do you compare performance between sequential and parallel streams?**
```java
long sequentialTime = measureTime(() -> list.stream().filter(...).collect(...));
long parallelTime = measureTime(() -> list.parallelStream().filter(...).collect(...));
```

## Level 22 - Real Interview Questions

**Q: How do you find the second highest salary?**
```java
OptionalDouble secondHighest = employees.stream()
    .mapToDouble(Employee::getSalary)
    .distinct()
    .sorted()
    .skip(employees.size() - 2)
    .findFirst();
```

**Q: How do you find the second lowest salary?**
```java
OptionalDouble secondLowest = employees.stream()
    .mapToDouble(Employee::getSalary)
    .distinct()
    .sorted()
    .skip(1)
    .findFirst();
```

**Q: How do you find duplicate elements?**
```java
Set<String> duplicates = list.stream()
    .filter(e -> Collections.frequency(list, e) > 1)
    .collect(Collectors.toSet());
```

**Q: How do you find the first non-repeated character?**
```java
Optional<Character> firstNonRepeated = str.chars()
    .mapToObj(c -> (char) c)
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
    .entrySet().stream()
    .filter(e -> e.getValue() == 1)
    .map(Map.Entry::getKey)
    .findFirst();
```

**Q: How do you find the frequency of characters?**
```java
Map<Character, Long> frequency = str.chars()
    .mapToObj(c -> (char) c)
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
```

**Q: How do you find the frequency of words?**
```java
Map<String, Long> wordFrequency = Arrays.stream(text.split("\\s+"))
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
```

**Q: How do you find the longest string?**
```java
Optional<String> longest = strings.stream()
    .max(Comparator.comparingInt(String::length));
```

**Q: How do you find the shortest string?**
```java
Optional<String> shortest = strings.stream()
    .min(Comparator.comparingInt(String::length));
```

**Q: How do you find the most frequent element?**
```java
Optional<String> mostFrequent = list.stream()
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
    .entrySet().stream()
    .max(Map.Entry.comparingByValue())
    .map(Map.Entry::getKey);
```

**Q: How do you find the top 3 highest salaries?**
```java
List<Double> top3 = employees.stream()
    .map(Employee::getSalary)
    .sorted(Comparator.reverseOrder())
    .limit(3)
    .collect(Collectors.toList());
```

**Q: How do you group employees by department?**
```java
Map<String, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDepartment));
```

**Q: How do you find the highest paid employee in each department?**
```java
Map<String, Optional<Employee>> highestByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.maxBy(Comparator.comparing(Employee::getSalary))
    ));
```

**Q: How do you remove duplicate employees?**
```java
List<Employee> unique = employees.stream()
    .distinct()
    .collect(Collectors.toList());
```

**Q: How do you find a missing number in an array?**
```java
int n = array.length + 1;
int expectedSum = n * (n + 1) / 2;
int actualSum = Arrays.stream(array).sum();
int missing = expectedSum - actualSum;
```

**Q: How do you merge two lists?**
```java
List<String> merged = Stream.concat(list1.stream(), list2.stream())
    .collect(Collectors.toList());
```

**Q: How do you find common elements?**
```java
List<String> common = list1.stream()
    .filter(list2::contains)
    .collect(Collectors.toList());
```

**Q: How do you find the union of lists?**
```java
List<String> union = Stream.concat(list1.stream(), list2.stream())
    .distinct()
    .collect(Collectors.toList());
```

**Q: How do you find the intersection of lists?**
```java
List<String> intersection = list1.stream()
    .filter(list2::contains)
    .distinct()
    .collect(Collectors.toList());
```

**Q: How do you find the difference of lists?**
```java
List<String> difference = list1.stream()
    .filter(e -> !list2.contains(e))
    .collect(Collectors.toList());
```

**Q: How do you sort employees by multiple fields?**
```java
List<Employee> sorted = employees.stream()
    .sorted(Comparator.comparing(Employee::getDepartment)
        .thenComparing(Employee::getSalary)
        .thenComparing(Employee::getName))
    .collect(Collectors.toList());
```

**Q: How do you calculate average salary by department?**
```java
Map<String, Double> avgSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.averagingDouble(Employee::getSalary)
    ));
```

**Q: How do you calculate total salary by department?**
```java
Map<String, Double> totalSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.summingDouble(Employee::getSalary)
    ));
```

**Q: How do you find duplicate words?**
```java
Set<String> duplicates = Arrays.stream(text.split("\\s+"))
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
    .entrySet().stream()
    .filter(e -> e.getValue() > 1)
    .map(Map.Entry::getKey)
    .collect(Collectors.toSet());
```

**Q: How do you reverse every word?**
```java
String reversed = Arrays.stream(text.split("\\s+"))
    .map(word -> new StringBuilder(word).reverse().toString())
    .collect(Collectors.joining(" "));
```

**Q: How do you reverse a sentence using streams?**
```java
String reversed = Arrays.stream(text.split("\\s+"))
    .collect(Collectors.collectingAndThen(
        Collectors.toList(),
        list -> {
            Collections.reverse(list);
            return String.join(" ", list);
        }
    ));
```

**Q: How do you count vowels?**
```java
long vowelCount = str.chars()
    .filter(c -> "AEIOUaeiou".indexOf(c) != -1)
    .count();
```

**Q: How do you count consonants?**
```java
long consonantCount = str.chars()
    .filter(Character::isLetter)
    .filter(c -> "AEIOUaeiou".indexOf(c) == -1)
    .count();
```

**Q: How do you find palindromes?**
```java
List<String> palindromes = strings.stream()
    .filter(s -> new StringBuilder(s).reverse().toString().equals(s))
    .collect(Collectors.toList());
```

**Q: How do you find anagrams?**
```java
Map<String, List<String>> anagrams = words.stream()
    .collect(Collectors.groupingBy(
        word -> word.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString()
    ));
```

**Q: How do you find top N records?**
```java
List<Employee> topN = employees.stream()
    .sorted(Comparator.comparing(Employee::getSalary).reversed())
    .limit(n)
    .collect(Collectors.toList());
```

**Q: How do you implement pagination?**
```java
List<Employee> page = employees.stream()
    .skip(pageNumber * pageSize)
    .limit(pageSize)
    .collect(Collectors.toList());
```

**Q: How do you flatten employee hierarchy?**
```java
List<Employee> flattened = rootEmployees.stream()
    .flatMap(this::flattenEmployee)
    .collect(Collectors.toList());

private Stream<Employee> flattenEmployee(Employee employee) {
    return Stream.concat(
        Stream.of(employee),
        employee.getSubordinates().stream().flatMap(this::flattenEmployee)
    );
}
```

**Q: How do you convert a list to a map?**
```java
Map<Integer, Employee> employeeMap = employees.stream()
    .collect(Collectors.toMap(Employee::getId, Function.identity()));
```

**Q: How do you handle duplicate keys in toMap()?**
```java
Map<String, Employee> employeeMap = employees.stream()
    .collect(Collectors.toMap(
        Employee::getEmail,
        Function.identity(),
        (existing, replacement) -> existing  // Keep existing on duplicate
    ));
```

**Q: How do you find the kth largest element?**
```java
Optional<Integer> kthLargest = numbers.stream()
    .sorted(Comparator.reverseOrder())
    .skip(k - 1)
    .findFirst();
```

**Q: How do you find the kth smallest element?**
```java
Optional<Integer> kthSmallest = numbers.stream()
    .sorted()
    .skip(k - 1)
    .findFirst();
```

**Q: How do you build a custom collector?**
```java
Collector<String, ?, List<String>> customCollector = Collector.of(
    ArrayList::new,  // Supplier
    List::add,       // Accumulator
    (list1, list2) -> {  // Combiner
        list1.addAll(list2);
        return list1;
    },
    Characteristics.IDENTITY_FINISH  // Characteristics
);
```

## Interview Theory Questions

**Q: What is a Stream?**
A sequence of elements that supports sequential and parallel aggregate operations. It's not a data structure but a view of data that can be processed.

**Q: Difference between Stream and Collection?**
- Collection stores data, Stream doesn't
- Collection can be modified, Stream is immutable
- Collection can be traversed multiple times, Stream only once
- Collection is eager, Stream is lazy
- Collection is about data storage, Stream is about data processing

**Q: Difference between Intermediate and Terminal operations?**
- Intermediate: return a new stream, are lazy (filter, map, sorted)
- Terminal: consume the stream and produce a result (collect, forEach, reduce)

**Q: What is Lazy Evaluation in Streams?**
Operations are not performed until a terminal operation is invoked. This allows for optimization like short-circuiting.

**Q: What are Short Circuiting operations?**
Operations that can terminate processing without examining all elements (limit, findFirst, anyMatch, etc.).

**Q: Difference between Stateful and Stateless operations?**
- Stateless: process each element independently (filter, map)
- Stateful: need to maintain state across elements (sorted, distinct)

**Q: Why Streams cannot be reused?**
Once a terminal operation is called, the stream is consumed. To reuse, you must create a new stream from the source.

**Q: What is a Stream Pipeline?**
A sequence of stream operations starting with a source, followed by zero or more intermediate operations, and ending with a terminal operation.

**Q: Difference between Internal and External Iteration?**
- External: you manage the iteration (for loop)
- Internal: the stream manages the iteration (forEach)

**Q: Difference between Predicate, Function, Consumer, and Supplier?**
- Predicate: takes input, returns boolean
- Function: takes input, returns output
- Consumer: takes input, returns nothing
- Supplier: takes nothing, returns output

**Q: Difference between Method Reference and Lambda?**
Method references are compact forms of lambda expressions for methods that already have a name. They're more readable when the lambda expression just calls an existing method.

**Q: What are Optional best practices?**
- Never return null from Optional
- Use Optional as return type, not as field type
- Avoid Optional in collections or arrays
- Use orElseGet instead of orElse for expensive operations
- Use isPresent only when necessary, prefer map/filter/flatMap

**Q: Difference between map() and flatMap()?**
- map: transforms one element to one element
- flatMap: transforms one element to zero or more elements and flattens the result

**Q: Difference between forEach() and peek()?**
- forEach: terminal operation, used for final processing
- peek: intermediate operation, used for debugging without modifying the stream

**Q: Difference between sorted() and Comparator?**
- sorted(): sorts using natural order or a provided Comparator
- Comparator: defines the sorting logic that can be used with sorted()

**Q: Difference between reduce() and collect()?**
- reduce: combines elements into a single result
- collect: transforms elements into a container (List, Set, Map, etc.)

**Q: Difference between Collectors.toList() and Stream.toList()?**
- Collectors.toList(): returns a mutable list
- Stream.toList() (Java 16+): returns an unmodifiable list

**Q: Difference between findFirst() and findAny()?**
- findFirst: returns the first element in encounter order
- findAny: may return any element (useful in parallel streams)

**Q: Difference between groupingBy() and partitioningBy()?**
- groupingBy: groups by key function with multiple categories
- partitioningBy: special case of groupingBy with boolean predicate (two categories)

**Q: How do Parallel Stream internals work?**
Parallel streams use the ForkJoinPool.commonPool() to split the work across multiple threads, using Spliterator to divide the data.

**Q: What are Spliterator basics?**
Spliterator is an iterator for traversing and partitioning elements of a source. It supports both sequential and parallel processing.

**Q: When do Streams hurt performance?**
- For small collections where overhead outweighs benefits
- For operations with boxing/unboxing (use primitive streams)
- For operations that are better done with traditional loops
- When misusing parallel streams

**Q: What are common Stream mistakes and anti-patterns?**
- Modifying shared state in operations
- Using forEach for transformations instead of map
- Overusing parallel streams
- Not closing streams when using resources
- Excessive boxing/unboxing with primitive streams