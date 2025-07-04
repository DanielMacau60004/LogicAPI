package com.logic.exps.exceptions;

import com.logic.exps.asts.IASTExp;

public class AmbiguousException extends ExpException {

    private final IASTExp after;

    public AmbiguousException(IASTExp before, IASTExp after) {
        super(before);

        this.after = after;
    }

    public IASTExp getBefore() {
        return exp;
    }

    public IASTExp getAfter() {
        return after;
    }

    @Override
    public String getMessage() {
        return "Ambiguous expression, consider adding parentheses around " + exp + "!";
    }
}


