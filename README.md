# api-challenge
_reqres.in_

### Preconditions
1. Java JDK (SDK used during development is `14.0.1`)
2. Maven (Version used during development is `3.5.3`)

## How to run tests

To run tests, navigate to the project's root directory and execute following command:
```
mvn test
```

Dependencies will be automatically dowloaded and tests will execute.

## Additional information
- Default configuration is set to `local` environment. Additional environment configurations can be added through Maven Profiles.
- Target URL can be changed inside `/src/main/resources/properties/local.properties`.
- Tests are configured to run in parallel. To change that behavior, open `src/main/resources/junit-platform.properties` and set `junit.jupiter.execution.parallel.enabled` to `false`.
- There is a known issue with `BeanUtils` library that outputs an error in the console (https://issues.apache.org/jira/browse/BEANUTILS-477). This does not affect the test execution in any way. 
