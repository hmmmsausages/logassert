package me.andremueller.logassert;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class LogRule extends AppenderBase<LoggingEvent> implements TestRule {
    private static List<LoggingEvent> loggingEvents = new ArrayList<LoggingEvent>();

    @Override
    protected void append(LoggingEvent loggingEvent) {
        loggingEvents.add(loggingEvent);
    }

    public Statement apply(final Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                loggingEvents.clear();
                statement.evaluate();
            }
        };
    }

    public List<String> getMessages(final Level loggingLevel){
        return loggingEvents.stream()
                .filter(loggingEvent -> loggingEvent.getLevel().equals(loggingLevel))
                .map(LoggingEvent::getFormattedMessage)
                .collect(toList());
    }

}
