package me.andremueller.logassert;

import ch.qos.logback.classic.Level;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assume.assumeTrue;

public class LogAssertTest {
    private static final String ASSUMPTION_DEBUG_LOG_LEVEL = "Log level has to be set to DEBUG";

    private Logger testLogger = LoggerFactory.getLogger(LogAssertTest.class);
    @Rule
    public LogRule unit = new LogRule();

    @Before
    public void setUp(){
        ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.DEBUG);
    }

    @Test
    public void itAssertsDebugMessageExistsIfLogLevelIsSetToDebug(){
        assumeTrue(ASSUMPTION_DEBUG_LOG_LEVEL, testLogger.isDebugEnabled());
        testLogger.debug("test");
        assertThatCode(() -> LogAssert.assertThat(unit).hasDebugMessageContaining("test"))
                .doesNotThrowAnyException();
    }

    @Test
    public void itAssertsDebugMessageDoesNotExistIfLogLevelIsSetToDebug(){
        assumeTrue(ASSUMPTION_DEBUG_LOG_LEVEL, testLogger.isDebugEnabled());
        assertThatThrownBy(() -> LogAssert.assertThat(unit).hasDebugMessageContaining("test"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expecting DEBUG log");
    }

}