package com.logic.nd.asts.unary;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.asts.AASTND;
import com.logic.nd.asts.IASTND;

public abstract class AASTUnaryND extends AASTND implements IASTND {

    protected final IASTND hyp;
    protected final IASTExp conclusion;

    public AASTUnaryND(IASTND hyp, IASTExp conclusion) {
        this.hyp = hyp;
        this.conclusion = conclusion;
    }

    public IASTND getHyp() {
        return hyp;
    }

    public IASTExp getConclusion() {
        return conclusion;
    }

    @Override
    public String toString() {
        return "["+ conclusion + ". " + hyp + "]";
    }
}
