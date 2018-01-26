package me.andremueller.logassert;

import ch.qos.logback.classic.Level;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static me.andremueller.logassert.LogAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assume.assumeTrue;

public class LogAssertTest {
    private static final String ASSUMPTION_DEBUG_LOG_LEVEL = "Log level has to be set to DEBUG";
    private static final String ASSUMPTION_ERROR_LOG_LEVEL = "Log level has to be set to ERROR";
    private static final String ASSUMPTION_INFO_LOG_LEVEL = "Log level has to be set to INFO";
    private static final String ASSUMPTION_WARN_LOG_LEVEL = "Log level has to be set to WARN";

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
        assertThatCode(() -> assertThat(unit).hasDebugMessageContaining("test"))
                .doesNotThrowAnyException();
    }

    @Test
    public void itAssertsDebugMessageDoesNotExistIfLogLevelIsSetToDebug(){
        assumeTrue(ASSUMPTION_DEBUG_LOG_LEVEL, testLogger.isDebugEnabled());
        assertThatThrownBy(() -> assertThat(unit).hasDebugMessageContaining("test"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expecting DEBUG log");
    }

    @Test
    public void itAssertsErrorMessageExistsIfLogLevelIsSetToError(){
        assumeTrue(ASSUMPTION_ERROR_LOG_LEVEL, testLogger.isErrorEnabled());
        testLogger.error("test");
        assertThatCode(() -> assertThat(unit).hasErrorMessageContaining("test"))
                .doesNotThrowAnyException();
    }

    @Test
    public void itAssertsErrorMessageDoesNotExistIfLogLevelIsSetToError(){
        assumeTrue(ASSUMPTION_ERROR_LOG_LEVEL, testLogger.isErrorEnabled());
        assertThatThrownBy(() -> assertThat(unit).hasErrorMessageContaining("test"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expecting ERROR log");
    }

    @Test
    public void itAssertsInfoMessageExistsIfLogLevelIsSetToInfo(){
        assumeTrue(ASSUMPTION_INFO_LOG_LEVEL, testLogger.isInfoEnabled());
        testLogger.info("test");
        assertThatCode(() -> assertThat(unit).hasInfoMessageContaining("test"))
                .doesNotThrowAnyException();
    }

    @Test
    public void itAssertsInfoMessageDoesNotExistIfLogLevelIsSetToInfo(){
        assumeTrue(ASSUMPTION_INFO_LOG_LEVEL, testLogger.isInfoEnabled());
        assertThatThrownBy(() -> assertThat(unit).hasInfoMessageContaining("test"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expecting INFO log");
    }

    @Test
    public void itAssertsWarnMessageExistsIfLogLevelIsSetToWarn(){
        assumeTrue(ASSUMPTION_WARN_LOG_LEVEL, testLogger.isWarnEnabled());
        testLogger.warn("test");
        assertThatCode(() -> assertThat(unit).hasWarnMessageContaining("test"))
                .doesNotThrowAnyException();
    }

    @Test
    public void itAssertsWarnMessageDoesNotExistIfLogLevelIsSetToWarn(){
        assumeTrue(ASSUMPTION_WARN_LOG_LEVEL, testLogger.isWarnEnabled());
        assertThatThrownBy(() -> assertThat(unit).hasWarnMessageContaining("test"))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expecting WARN log");
    }
}