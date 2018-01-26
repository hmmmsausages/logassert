package me.andremueller.logassert;

import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;
import org.assertj.core.internal.StandardComparisonStrategy;

public class ShouldContainLogMessage extends BasicErrorMessageFactory {
    public static ErrorMessageFactory shouldContainDebugLogMessage(Object actual, Object expected, Object notFound){
        return new ShouldContainLogMessage("%nExpecting DEBUG log:%n <%s>%nto contain debug message:%n <%s>%nbut could not find:%n <%s>%n%s", actual, expected, notFound);
    }

    private ShouldContainLogMessage(String errorMessage, Object actual, Object expected, Object notFound){
        super(errorMessage, actual, expected, notFound, StandardComparisonStrategy.instance());
    }
}
