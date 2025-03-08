package com.logic.nd.asts.unary;

import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public class ASTILDisND extends AASTUnaryND {

    public ASTILDisND(IASTND hypothesis, IASTND conclusion) {
        super(hypothesis, conclusion);
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }
    
}
