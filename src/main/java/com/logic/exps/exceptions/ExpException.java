package com.logic.exps.exceptions;

import com.logic.exps.asts.IASTExp;

public abstract class ExpException extends RuntimeException {

    protected IASTExp exp;
    public ExpException(IASTExp exp) {
        this.exp = exp;
    }

    public IASTExp getExp() {
        return exp;
    }
}
