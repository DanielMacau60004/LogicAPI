package com.logic.nd.asts.binary;

import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public class ASTIConjND extends AASTBinaryND {

    public ASTIConjND(IASTND hypothesis1, IASTND hypothesis2, IASTND conclusion) {
        super(hypothesis1, hypothesis2, conclusion);
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return null;
    }
}
