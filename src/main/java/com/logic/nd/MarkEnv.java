package com.logic.nd;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.asts.IASTND;

public class MarkEnv {
    private final IASTND rule;
    private final IASTExp exp;

    private IASTExp closed;

    public MarkEnv(IASTND rule, IASTExp exp) {
        this.rule = rule;
        this.exp = exp;
        this.closed = exp;
    }

    public IASTExp getClosed() {
        return closed;
    }

    public void setClosed(IASTExp closed) {
        this.closed = closed;
    }

    public IASTND getRule() {
        return rule;
    }

    public IASTExp getExp() {
        return exp;
    }
}
