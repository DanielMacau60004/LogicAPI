package com.logic.nd.asts.unary;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.ERule;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;
import com.logic.parser.ParserConstants;

public class ASTINegND extends AASTUnaryND {

    private final int m;

    public ASTINegND(IASTND hypothesis, IASTExp conclusion, int m) {
        super(hypothesis, conclusion);
        this.m = m;
    }

    public int getM() {
        return m;
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return "[" + ERule.INTRO_NEGATION + ", " + m + "] " + super.toString();
    }

}
