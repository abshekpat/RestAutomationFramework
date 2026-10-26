# RestAutomationFramework

A Maven-based REST API automation framework built with **REST Assured** and **TestNG**. It demonstrates a layered design — tests → service helpers → request models — configured through an external properties file so the target API can be swapped without touching code.

The included example suite runs against the public [Swagger Petstore](https://petstore.swagger.io) API.

## Tech Stack

| Library | Purpose |
| --- | --- |
| [REST Assured](https://rest-assured.io/) 4.5.1 | HTTP client and response assertions |
| [TestNG](https://testng.org/) 7.10.2 | Test runner, assertions, listeners, reporting |
| [Jackson](https://github.com/FasterXML/jackson) 2.17.1 | JSON serialization/deserialization |
| [Lombok](https://projectlombok.org/) 1.18.24 | Boilerplate reduction (builders, getters) |
| [Owner](https://github.com/aeonbits/owner) 1.0.12 | Typed configuration from `config.properties` |
| [Java Faker](https://github.com/DiUS/java-faker) 1.0.2 | Random test data generation |
| [Podam](https://github.com/jemos/podam) 7.2.9 | POJO test data generation |
| JSON Schema Validator 3.0.0 | Response schema validation |
| json-simple 1.1.1 | Lightweight JSON parsing |

## Prerequisites

- JDK 11 or newer
- Maven 3.6+ (no Maven wrapper is included)
- Network access to the configured API (`https://petstore.swagger.io` by default)

## Project Structure

```
RestAutomationFramework/
├── pom.xml
├── testng.xml                        # TestNG suite definition
└── src/
    ├── main/java/com/api/
    │   ├── helpers/
    │   │   ├── BaseService.java      # Shared request spec, GET/POST wrappers
    │   │   └── UserService.java      # User endpoints
    │   ├── models/request/
    │   │   └── CreateUserRequest.java
    │   └── utils/
    │       ├── FrameworkConfig.java  # Owner config interface
    │       └── TestListeners.java    # TestNG ITestListener
    └── test/
        ├── java/com/api/tests/
        │   └── UserTest.java         # Example test class
        └── resources/
            └── config.properties     # Base API URL
```

### Design

- **`BaseService`** builds a reusable `RequestSpecification` with the base URI from config and exposes protected `getRequest` / `postRequest` helpers.
- **`UserService`** extends `BaseService` and maps business operations (`createWithList`, `getUser`) to endpoints.
- **Tests** call services only, then assert on the returned `Response`.
- **`FrameworkConfig`** is an Owner interface bound to `src/test/resources/config.properties`, resolved relative to `${user.dir}`.

## Configuration

`src/test/resources/config.properties`:

```properties
url = https://petstore.swagger.io/v2/
```

Change `url` to point the framework at a different environment. Because it is read through Owner, additional properties can be added to the interface and the file as needed.

## Running the Tests

Run the full suite defined in `testng.xml`:

```bash
mvn clean test
```

Run from an IDE by executing `testng.xml` or the `UserTest` class directly.

### Test Coverage

| Test | Endpoint | Description |
| --- | --- | --- |
| `createUserTest` | `POST /user/createWithList` | Creates a user with Faker-generated data and asserts `200 OK` |
| `getUserNameTest` | `GET /user/{username}` | Fetches a user and asserts `200 OK` and the returned username |

> `getUserNameTest` currently asserts against a fixed user (`abhi` → `user1`); adjust it to match data available in your target environment.

## Reports

- **TestNG HTML report:** `target/surefire-reports/index.html`
- **Emailable report:** `emailable-report.html` (generated via `EmailableReporter2` in `testng.xml`)

## Adding a New Test

1. Create a request model under `com.api.models.request`.
2. Add a method to a service class (or a new service extending `BaseService`) that calls `getRequest` / `postRequest`.
3. Write a TestNG test class under `src/test/java/com/api/tests`.
4. Register the class or method in `testng.xml` if it should run with the suite.

## Notes

- `testFailureIgnore` is set to `true` in the Surefire configuration, so the Maven build will not fail on test failures. Set it to `false` in CI if build status should reflect test results.
- `TestListeners` logs test start and execution status to the TestNG report and console.
