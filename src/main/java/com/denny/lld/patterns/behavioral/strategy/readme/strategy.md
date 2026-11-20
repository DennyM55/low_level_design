
# Strategy Design Pattern
**Category:** Behavioral Design Pattern

## 1. What is Strategy?

Strategy is a design pattern that allows us to define different ways of performing the same task and switch between them without changing the main class.

Each way of performing the task is called a **Strategy**.

**Strategy = Same Task + Different Algorithms**

## 2. Why do we need it?

Suppose we are building a navigation application.

Users can travel by Car, Bike or Walk. Each mode requires a different route calculation.

We could write multiple `if-else` conditions inside `Navigator` to handle each mode.

But whenever we add a new travel mode, we would need to modify `Navigator`.

Instead, we move each navigation algorithm into its own class and give them a common interface.

Now `Navigator` simply uses whichever Strategy is selected.

This keeps the navigation logic separate and makes it easy to add or switch algorithms.

## 3. How does it work?

```text
                Navigator
                    |
                    v
           NavigationStrategy
                (Interface)
                    |
          +---------+---------+
          |         |         |
          v         v         v
         Car       Bike      Walk
       Navigation Navigation Navigation
```

Our Java implementation has three main components:

- **Strategy:** `NavigationStrategy` — Defines the common `navigate()` method.
- **Concrete Strategies:** `CarNavigation`, `BikeNavigation`, `WalkNavigation` — Each provides its own navigation logic.
- **Context:** `Navigator` — Holds and uses the selected Strategy.

`Point` represents a location using `x` and `y` coordinates.

## 4. Java Implementation

All navigation strategies follow the same interface:

```java
interface NavigationStrategy {
    void navigate(Point from, Point to);
}
```

Each class implements its own behavior:

```java
class CarNavigation implements NavigationStrategy {
    public void navigate(Point from, Point to) {
        System.out.println("Calculating car route");
    }
}
```

`BikeNavigation` and `WalkNavigation` implement the same method with their own behavior.

The `Navigator` holds a Strategy and delegates the work to it.

```java
class Navigator {
    private NavigationStrategy strategy;

    public Navigator(NavigationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(NavigationStrategy strategy) {
        this.strategy = strategy;
    }

    public void navigate(Point from, Point to) {
        strategy.navigate(from, to);
    }
}
```

We can switch the Strategy while the application is running:

```java
Navigator navigator = new Navigator(new CarNavigation());

navigator.navigate(from, to);

navigator.setStrategy(new BikeNavigation());
navigator.navigate(from, to);
```

The `Navigator` remains the same. Only the selected algorithm changes.

This is called **runtime strategy switching**.

## 5. Where is Strategy used?

**Navigation:** Choose Car, Bike or Walking routes.

**Payment processing:** Choose different payment-processing algorithms.

**Discount calculation:** Apply different discount rules based on customer type.

**Sorting:** Choose different sorting algorithms depending on the requirement.

**Retry logic:** Use different retry policies for different operations.

Use Strategy when multiple algorithms perform the same task but their implementations differ.

## 6. Strategy in Spring Boot

In Spring Boot, each Strategy can be a separate Spring-managed bean using `@Component`.

A service can receive the required Strategy through dependency injection.

For example, different discount calculation classes can implement a common `DiscountStrategy` interface.

The application selects the appropriate implementation and calls the same method.

This avoids putting all discount rules into one large `if-else` block.

## 7. Key Points

- Strategy separates different algorithms into individual classes.
- All Strategies implement a common interface.
- The Context holds a reference to the selected Strategy.
- The Context delegates the actual work to that Strategy.
- Strategies can be switched at runtime.
- New algorithms can be added without modifying the Context.
- It supports OCP and DIP by using interfaces instead of concrete implementations.
- It avoids large conditional blocks but introduces additional classes.
- Strategy is useful when algorithms change independently of the class using them.

**Strategy = Common Interface + Multiple Algorithms + Runtime Switching**
