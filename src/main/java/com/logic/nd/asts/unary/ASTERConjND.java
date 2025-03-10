package com.logic.nd.asts.unary;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;
import com.logic.parser.ParserConstants;

public class ASTERConjND extends AASTUnaryND {

    public ASTERConjND(IASTND hypothesis, IASTExp conclusion) {
        super(hypothesis, conclusion);
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return "[" + getToken(ParserConstants.ERAND) + "] " + super.toString();
    }
}
