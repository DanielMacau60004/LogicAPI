package com.logic.nd.asts.unary;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.ERule;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public class ASTELConjND extends AASTUnaryND {

    public ASTELConjND(IASTND hypothesis, IASTExp conclusion) {
        super(hypothesis, conclusion);
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return "[" + ERule.ELIM_CONJUNCTION_LEFT + "] " + super.toString();
    }

}
