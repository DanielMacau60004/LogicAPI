package com.logic.nd.algorithm;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.exps.asts.IASTExp;
import com.logic.nd.algorithm.state.StateNode;

import java.util.HashSet;
import java.util.Set;

public class AlgoProofStateBuilder {

    private final IFormula state;
    private final Set<IASTExp> hypotheses;
    private int height;

    //TODO include variables that cannot be closed
    public AlgoProofStateBuilder(IFormula state) {
        this.state = state;
        this.height = 0;
        this.hypotheses = new HashSet<>();
    }

    public AlgoProofStateBuilder addHypothesis(IFOLFormula hypothesis) {
        this.hypotheses.add(hypothesis.getFormula());
        return this;
    }

    public AlgoProofStateBuilder addHypotheses(Set<IFOLFormula> hypotheses) {
        this.hypotheses.addAll(hypotheses.stream().map(IFormula::getFormula).toList());
        return this;
    }

    public AlgoProofStateBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public StateNode build(Set<IFormula> premises) {
        return new StateNode(state.getFormula(), premises, hypotheses, height);
    }

}
