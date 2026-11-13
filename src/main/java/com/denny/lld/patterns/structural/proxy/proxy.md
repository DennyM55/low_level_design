
# Proxy Design Pattern
**Category:** Structural Design Pattern

## 1. What is Proxy?

A Proxy is an object that stands between the client and the real object.

Instead of accessing the real object directly, the client calls the Proxy. The Proxy controls access and forwards the request when needed.

**Client → Proxy → Real Object**

## 2. Why do we need it?

Suppose `BankService` handles money withdrawals.

Now we need to check requests before allowing withdrawals.

Instead of mixing checking logic with withdrawal logic, we create `BankServiceProxy`.

The Proxy checks the request, while `RealBankService` performs the actual withdrawal.

This keeps the responsibilities separate.

## 3. How does it work?

```text
Client
  |
  v
BankServiceProxy
  |  Checks request
  v
RealBankService
     Performs withdrawal
```

Our Java implementation has four parts:

- `BankService` — Common interface defining `withdraw()`.
- `RealBankService` — Performs the actual withdrawal.
- `BankServiceProxy` — Checks the request and forwards it.
- `ProxyDemo` — Creates and uses the Proxy.

Both the Proxy and Real Service implement `BankService`.

```java
BankService bank =
    new BankServiceProxy(new RealBankService());

bank.withdraw(5000);
```

The Proxy holds a reference to the real object (**composition**) and calls it when required (**delegation**).

## 4. Where is Proxy used?

**Protection Proxy:** Checks whether access is allowed.

**Caching Proxy:** Returns saved results instead of repeating expensive operations.

**Virtual Proxy:** Creates an expensive object only when required.

**Remote Proxy:** Represents an object running on another machine.

## 5. Proxy in Spring Boot

Spring can create proxies around Spring-managed objects to provide features such as caching and transaction management.

Example from `commerce-backend`:

```java
@Cacheable(value = "products-v2", key = "#id")
@Transactional(readOnly = true)
public ProductResponse getProductResponseById(Long id) {
    // Fetch product
}
```

**Caching flow:**

```text
Controller → Spring Proxy → Cache
                            |
                 +----------+----------+
                 |                     |
              Cache Hit            Cache Miss
                 |                     |
           Return saved          Execute method
              result                   |
                                  Cache result
                                       |
                                  Return result
```

- `@Cacheable` — Returns cached data if available; otherwise executes the method and caches the result.
- `@CacheEvict` — Removes cached entries when data changes.
- `@Transactional` — Manages transaction boundaries around method execution.

## 6. Self-Invocation

Spring's default proxy mechanism works when a method call passes through the Proxy.

When one method directly calls another method inside the same object, the call bypasses the Proxy.

Therefore, proxy-based features such as `@Cacheable` and `@Transactional` are not applied to that internal call.

**Solution:** Move the annotated method to another Spring-managed service and call it through that service.

## 7. Key Points

- Proxy controls access to the real object.
- Proxy and Real Object usually implement the same interface.
- Proxy uses composition and delegation.
- A Proxy can forward, delay or reject requests.
- It can return cached results without calling the real object.
- It keeps access-related logic separate from business logic.
- It introduces an additional layer, which can increase complexity.

**Proxy = Same Interface + Controlled Access + Delegation**
