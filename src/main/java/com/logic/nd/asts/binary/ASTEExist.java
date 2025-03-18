package com.logic.nd.asts.binary;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.nd.ERule;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;

public class ASTEExist extends AASTBinaryND {

    private final int m;
    private ASTVariable mapping;

    private IASTExp generatedHypothesis;

    public ASTEExist(IASTND hypothesis1, IASTND hypothesis2, IASTExp conclusion, int m) {
        super(hypothesis1, hypothesis2, conclusion);
        this.m = m;
    }

    public int getM() {
        return m;
    }

    public void setMapping(ASTVariable mapping) {
        this.mapping = mapping;
    }

    public ASTVariable getMapping() {
        return mapping;
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
        return "[" + ERule.ELIM_EXISTENTIAL + ", " + m + "] " + super.toString();
    }

}
