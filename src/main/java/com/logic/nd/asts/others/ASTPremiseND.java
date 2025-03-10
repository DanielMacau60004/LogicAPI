package com.logic.nd.asts.others;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.asts.AASTND;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public class ASTPremiseND extends AASTND implements IASTND {

    private final IASTExp premise;
    private final int m;

    public ASTPremiseND(IASTExp hyp, int m) {
        this.premise = hyp;
        this.m = m;
    }

    public int getM() {
        return m;
    }

    public IASTExp getConclusion() {
        return premise;
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return "[P, " + m + "] [" + premise + ".]";
    }
}
