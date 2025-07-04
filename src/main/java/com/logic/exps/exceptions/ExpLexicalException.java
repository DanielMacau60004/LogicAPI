package com.logic.exps.exceptions;

import com.logic.exps.asts.IASTExp;
import com.logic.parser.TokenMgrError;

public class ExpLexicalException extends ExpException {

    protected TokenMgrError exception;

    public ExpLexicalException(IASTExp exp, TokenMgrError exception) {
        super(exp);

        this.exception = exception;
    }

    public TokenMgrError getException() {
        return exception;
    }

    @Override
    public String getMessage() {
        return exception.getMessage();
    }
}
