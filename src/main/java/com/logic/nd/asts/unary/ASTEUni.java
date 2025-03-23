package com.logic.nd.asts.unary;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.nd.ERule;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public class ASTEUni extends AASTUnaryND {

    private AASTTerm mapping;

    public ASTEUni(IASTND hypothesis, IASTExp conclusion) {
        super(hypothesis, conclusion);
    }

    public void setMapping(AASTTerm mapping) {
        this.mapping = mapping;
    }

    public AASTTerm getMapping() {
        return mapping;
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return "[" + ERule.ELIM_UNIVERSAL + "] " + super.toString();
    }

}
