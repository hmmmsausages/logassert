package me.andremueller.logassert;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.internal.Failures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static ch.qos.logback.classic.Level.DEBUG;
import static me.andremueller.logassert.ShouldContainLogMessage.shouldContainDebugLogMessage;

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
            List<String> debugMessages = actual.getMessages(DEBUG);
            boolean matches = debugMessages.stream().anyMatch(debugMessage -> debugMessage.contains(expectedMessage));

            if(!matches){
                throw FAILURES.failure(info, shouldContainDebugLogMessage(debugMessages, expectedMessage, expectedMessage));
            }
        }
        return this;
    }
}
