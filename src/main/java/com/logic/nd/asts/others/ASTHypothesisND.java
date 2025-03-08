package com.logic.nd.asts.others;

import com.logic.nd.asts.AASTND;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public abstract class ASTHypothesisND extends AASTND implements IASTND {

    protected final IASTND hyp;

    public ASTHypothesisND(IASTND hyp) {this.hyp = hyp;}

    public IASTND getHyp() {
        return hyp;
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

}
