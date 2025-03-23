package com.logic.nd.asts.unary;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.nd.ERule;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public class ASTIUni extends AASTUnaryND {

    private ASTVariable mapping;

    public ASTIUni(IASTND hypothesis, IASTExp conclusion) {
        super(hypothesis, conclusion);
    }


    public void setMapping(ASTVariable mapping) {
        this.mapping = mapping;
    }

    public ASTVariable getMapping() {
        return mapping;
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return "[" + ERule.INTRO_UNIVERSAL + "] " + super.toString();
    }

}
