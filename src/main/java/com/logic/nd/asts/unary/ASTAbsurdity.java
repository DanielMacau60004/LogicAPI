package com.logic.nd.asts.unary;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.ERule;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public class ASTAbsurdity extends AASTUnaryND {

    private final int m;
    private IASTExp generatedHypothesis;

    public ASTAbsurdity(IASTND hypothesis, IASTExp conclusion, int m) {
        super(hypothesis, conclusion);
        this.m = m;
    }

    public int getM() {
        return m;
    }

    public void setGeneratedHypothesis(IASTExp generatedHypothesis) {
        this.generatedHypothesis = generatedHypothesis;
    }

    public IASTExp getGeneratedHypothesis() {
        return generatedHypothesis;
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return "[" + ERule.ABSURDITY + ", " + m + "] " + super.toString();
    }

}
