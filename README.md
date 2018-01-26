# LogAssert

Allows log testing and log message assertions with the help of `jUnit` and `AssertJ`.

LogAssert uses and extends [AssertJ][https://github.com/joel-costigliola/assertj-core] intuitive and user-friendly assertions to enable log specific tests.


## Installation

LogAssert is currently not available as a dependency on Maven Central. You can however, download and embed the source code to make it work for now.

In your test resources create a new file titled `logback-test.xml` and add a new log appender for the LogRule in it. This will cause every future log message that is output to be stored inside the `LogRule`, which will then make it possible to test them as part of the tests.

Example of `src/test/resources/logback-test.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="logAssert" class="me.andremueller.logassert.LogRule"/>

    <root level="debug">
        <appender-ref ref="logAssert"/>
    </root>
</configuration>
```

## Usage

Simply add the `LogRule` jUnit test rule to your test class and assert on it with the custom AssertJ assertions provided via the `LogAssert` class.
```
public class LoggingAppTest {
    @Rule
    public LogRule log = new LogRule();

    @Test
    public void itLogsSomething(){
        LoggingApp unit = new LoggingApp();
        unit.logSomething();
        assertThat(log).hasDebugMessageContaining("hello");
    }
}
```

