package com.logic.nd.asts.binary;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.ERule;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public class ASTIConj extends AASTBinaryND {

    public ASTIConj(IASTND hypothesis1, IASTND hypothesis2, IASTExp conclusion) {
        super(hypothesis1, hypothesis2, conclusion);
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return "[" + ERule.INTRO_CONJUNCTION + "] " + super.toString();
    }

}
