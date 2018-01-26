package me.andremueller.logassert;

import ch.qos.logback.classic.Level;
import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;
import org.assertj.core.internal.StandardComparisonStrategy;


class ShouldContainLogMessage extends BasicErrorMessageFactory {
    static ErrorMessageFactory shouldContainLogMessage(Level level, Object actual, Object expected, Object notFound){
        return new ShouldContainLogMessage("%nExpecting %s log:%n <%s>%nto contain message:%n <%s>%nbut could not find:%n <%s>%n%s", level, actual, expected, notFound);
    }

    private ShouldContainLogMessage(String errorMessage, Level level, Object actual, Object expected, Object notFound){
        super(errorMessage, level, actual, expected, notFound, StandardComparisonStrategy.instance());
    }
}
