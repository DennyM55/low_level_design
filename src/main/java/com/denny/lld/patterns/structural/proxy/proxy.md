
# Proxy Design Pattern

**Category:** Structural Design Pattern  
**Language:** Java 17+  
**Level:** LLD / Senior Java Backend Interview

## 1. What is Proxy Pattern?

Proxy is a structural design pattern in which one object acts as a substitute for another object.

The proxy controls access to the real object.

**Remember:** Client → Proxy → Real Object

## 2. Why Do We Need It?

Imagine a banking application.

Initially, a customer directly calls the bank service to withdraw money.

Later, we need to check whether the request is allowed before performing the withdrawal.

We could add this checking logic inside the bank service.

However, this mixes two responsibilities:

1. Checking the request.
2. Performing the withdrawal.

A better solution is to create a separate object that checks the request before calling the real bank service.

This object is called a **Proxy**.

## 3. Solution

The proxy and real object implement the same interface.

The client uses the proxy instead of directly using the real object.

The proxy decides whether to forward the request.

## 4. Main Components

| Component | Responsibility |
|---|---|
| Subject | Common interface |
| Real Subject | Performs the actual work |
| Proxy | Controls access to the real object |
| Client | Calls the service through the interface |

## 5. Design Diagram

```text
             Client
                |
                v
        BankService
         (Interface)
           ^     ^
           |     |
           |     |
    RealBankService   BankServiceProxy
           ^                |
           |                |
           +--- delegates --+
```

Both RealBankService and BankServiceProxy implement BankService.

The proxy holds a reference to the real service.

## 6. Java Implementation

Package:

`com.denny.lld.patterns.structural.proxy`

### BankService.java

```java
public interface BankService {
    void withdraw(int amount);
}
```

This is the common contract.

### RealBankService.java

```java
public class RealBankService implements BankService {

    @Override
    public void withdraw(int amount) {
        System.out.println("Withdrawn: Rs. " + amount);
    }
}
```

This class performs the actual work.

### BankServiceProxy.java

```java
public class BankServiceProxy implements BankService {

    private final BankService realBank;

    public BankServiceProxy(BankService realBank) {
        this.realBank = realBank;
    }

    @Override
    public void withdraw(int amount) {

        if (amount <= 0) {
            System.out.println("Invalid amount");
            return;
        }

        System.out.println("Checking withdrawal request");
        realBank.withdraw(amount);
    }
}
```

The proxy checks the amount before forwarding the request.

Note: This is input validation, not real user authorization.

### ProxyDemo.java

```java
public class ProxyDemo {

    public static void main(String[] args) {

        BankService bank =
                new BankServiceProxy(new RealBankService());

        bank.withdraw(5000);
        bank.withdraw(-100);
    }
}
```

### Output

```text
Checking withdrawal request
Withdrawn: Rs. 5000
Invalid amount
```

## 7. How the Code Works

1. The client creates a RealBankService.
2. It wraps the real service inside BankServiceProxy.
3. The client calls withdraw() on the proxy.
4. The proxy checks the amount.
5. For a valid amount, it calls the real service.
6. For an invalid amount, it rejects the request.

**Key point:** The client works with the BankService interface.

## 8. Types of Proxy

| Type | Purpose |
|---|---|
| Protection Proxy | Restricts access |
| Virtual Proxy | Delays expensive object creation |
| Remote Proxy | Represents a remote object |
| Caching Proxy | Reuses stored results |
| Logging Proxy | Records operations |

## 9. Proxy in Spring Boot

Spring can create proxies around Spring-managed objects.

These proxies add features such as caching and transaction management without placing that logic inside every business method.

### Example from commerce-backend

```java
@Cacheable(value = "products-v2", key = "#id")
@Transactional(readOnly = true)
public ProductResponse getProductResponseById(Long id) {
    Product product = getProductByID(id);
    // Build ProductResponse
}
```

### Cache Miss

```text
Controller
    |
    v
Spring Proxy
    |
    v
Check Cache
    |
    | Not Found
    v
ProductServiceImpl
    |
    v
Database
    |
    v
Store Result in Cache
    |
    v
Return Response
```

### Cache Hit

```text
Controller
    |
    v
Spring Proxy
    |
    v
Cache Hit
    |
    v
Return Cached Response
```

The real service method is not executed on a cache hit.

### @CacheEvict

The updateProduct() and deleteProduct() methods use:

```java
@CacheEvict(value = "products-v2", key = "#id")
```

This removes the matching cached entry after successful method execution by default.

The next read can load fresh data.

### @Transactional

Spring uses transaction advice to manage transaction boundaries around eligible method calls.

For a new transaction, it generally:

1. Begins the transaction.
2. Executes the service method.
3. Commits on success.
4. Rolls back when rollback rules require it.

## 10. Self-Invocation Problem

This is an important Spring Boot interview question.

### Problem

```java
@Service
class ProductService {

    public void process(Long id) {
        getProduct(id);
    }

    @Cacheable("products")
    public String getProduct(Long id) {
        return "Product " + id;
    }
}
```

The process() method calls getProduct() inside the same object.

This internal call does not go through the Spring proxy.

Therefore, the caching advice is bypassed.

The same issue can occur with @Transactional.

### Solution

Move the annotated method into a separate Spring-managed service and call it through the injected bean.

**Remember:** External call through proxy = advice can run.

Internal call using this = proxy bypassed.

## 11. Proxy vs Other Patterns

| Pattern | Main Purpose |
|---|---|
| Proxy | Control access to an object |
| Adapter | Connect incompatible interfaces |
| Decorator | Add responsibilities to an object |
| Strategy | Choose between algorithms |

Proxy and Decorator can look similar in code.

The main difference is their intention.

## 12. SOLID Principles

**SRP:** Separates access checks from business logic.

**OCP:** Allows proxy behavior to be added without changing the real service.

**DIP:** The client can depend on a common interface.

These benefits depend on how the pattern is implemented.

## 13. Advantages

1. Controls access to real objects.
2. Separates additional logic from business logic.
3. Supports caching and lazy loading.
4. Helps with logging and monitoring.
5. Can reduce unnecessary expensive operations.

## 14. Disadvantages

1. Adds more classes.
2. Introduces another layer of execution.
3. Can make debugging difficult.
4. Proxy-based frameworks have limitations such as self-invocation.

## 15. Interview Questions

### Q1. Why is Proxy a structural pattern?

Because it changes how the client accesses an object by placing another object between them.

### Q2. Does Proxy always call the real object?

No. It may return a cached result or reject a request.

### Q3. JDK Proxy vs CGLIB?

JDK dynamic proxies use interfaces.

CGLIB creates class-based proxies using subclassing.

### Q4. Why can @Transactional fail during self-invocation?

Because the internal method call bypasses Spring's proxy.

### Q5. Difference between @Cacheable and @CachePut?

@Cacheable can skip method execution on a cache hit.

@CachePut always executes the method and stores its result.

### Q6. Is Proxy always required?

No. Use it when controlling access or adding an interception layer provides a clear benefit.

## 16. Interview Answer — 30 Seconds

Proxy is a structural design pattern where a substitute object controls access to a real object.

The proxy and real object usually share the same interface.

The proxy can perform checks, caching, or logging before calling the real object.

Spring Boot uses proxy-based AOP for features such as @Cacheable and @Transactional.

One important limitation is self-invocation, where internal calls bypass the proxy.

## 17. Key Points to Remember

- Category: Structural.
- Purpose: Control access.
- Components: Subject, Real Subject, Proxy, Client.
- Design: Client → Proxy → Real Object.
- Java: Common interface + delegation.
- Spring: Caching and transactions.
- Interview trap: Self-invocation.
- Related patterns: Adapter, Decorator, Strategy.

## 18. Sources

### Scaler Course
The combined Scaler LLD transcript briefly mentions Proxy Pattern but does not provide a complete Proxy implementation.

### Project Code
- DennyM55/low_level_design
- DennyM55/commerce-backend
- ProductServiceImpl.java
- ProductCacheTest.java

### Official Spring Documentation
- https://docs.spring.io/spring-framework/reference/core/aop/proxying.html
- https://docs.spring.io/spring-framework/reference/integration/cache/annotations.html
- https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html

The banking example and detailed Spring explanations are supplementary material beyond the Scaler transcript.
