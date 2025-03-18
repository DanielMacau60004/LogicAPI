package com.logic.nd.asts.others;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.asts.AASTND;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public class ASTHypothesis extends AASTND implements IASTND {

    private final IASTExp hyp;
    private final int m;

    public ASTHypothesis(IASTExp hyp, int m) {
        this.hyp = hyp;
        this.m = m;
    }

    public int getM() {
        return m;
    }

    public IASTExp getConclusion() {
        return hyp;
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return "[H, " + m + "] [" + hyp + ".]";
    }
}
