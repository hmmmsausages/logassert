package me.andremueller.logassert;

import ch.qos.logback.classic.Level;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.internal.Failures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static ch.qos.logback.classic.Level.*;
import static me.andremueller.logassert.ShouldContainLogMessage.shouldContainLogMessage;

public class LogAssert extends AbstractAssert<LogAssert, LogRule> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAssert.class);
    private static final Failures FAILURES = Failures.instance();

    private LogAssert(LogRule actual){
        super(actual, LogAssert.class);
    }

    public static LogAssert assertThat(LogRule actual) {
        return new LogAssert(actual);
    }

    public LogAssert hasDebugMessageContaining(String expectedMessage){
        if(LOGGER.isDebugEnabled()){
            hasMessageWithLogLevelContaining(expectedMessage, DEBUG);
        }
        return this;
    }

    public LogAssert hasErrorMessageContaining(String expectedMessage) {
        if(LOGGER.isErrorEnabled()){
            hasMessageWithLogLevelContaining(expectedMessage, ERROR);
        }
        return this;
    }

    public LogAssert hasInfoMessageContaining(String expectedMessage) {
        if(LOGGER.isInfoEnabled()){
            hasMessageWithLogLevelContaining(expectedMessage, INFO);
        }
        return this;
    }

    public LogAssert hasWarnMessageContaining(String expectedMessage) {
        if(LOGGER.isWarnEnabled()){
            hasMessageWithLogLevelContaining(expectedMessage, WARN);
        }
        return this;
    }

    private void hasMessageWithLogLevelContaining(String expectedMessage, Level logLevel) {
        List<String> messages = actual.getMessages(logLevel);
        boolean matches = messages.stream().anyMatch(message -> message.contains(expectedMessage));

        if (!matches) {
            throw FAILURES.failure(info, shouldContainLogMessage(logLevel, messages, expectedMessage, expectedMessage));
        }
    }
}
