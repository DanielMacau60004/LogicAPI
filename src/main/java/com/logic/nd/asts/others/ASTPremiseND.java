package com.logic.nd.asts.others;

import com.logic.nd.asts.AASTND;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public abstract class ASTPremiseND extends AASTND implements IASTND {

    protected final IASTND premise;

    public ASTPremiseND(IASTND hyp) {
        this.premise = hyp;
    }

    public IASTND getPremise() {return premise;}

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }
}
