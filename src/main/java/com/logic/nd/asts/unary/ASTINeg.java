package com.logic.nd.asts.unary;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.ERule;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public class ASTINeg extends AASTUnaryND {

    private final int m;
    private IASTExp generatedHypothesis;

    public ASTINeg(IASTND hypothesis, IASTExp conclusion, int m) {
        super(hypothesis, conclusion);
        this.m = m;
    }

    public void setGeneratedHypothesis(IASTExp generatedHypothesis) {
        this.generatedHypothesis = generatedHypothesis;
    }

    public IASTExp getGeneratedHypothesis() {
        return generatedHypothesis;
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
