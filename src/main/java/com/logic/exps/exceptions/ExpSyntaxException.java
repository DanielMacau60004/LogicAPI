package com.logic.exps.exceptions;

import com.logic.exps.asts.IASTExp;
import com.logic.parser.ParseException;

public class ExpSyntaxException extends ExpException {

    private final ParseException exception;

    public ExpSyntaxException(IASTExp exp, ParseException exception) {
        super(exp);

        this.exception = exception;
    }

    public ParseException getException() {
        return exception;
    }

    @Override
    public String getMessage() {
        return exception.getMessage();
    }
}
