package com.logic.nd.algorithm;

import com.logic.api.IPLFormula;
import com.logic.nd.algorithm.state.BitGraphHandler;
import com.logic.nd.algorithm.state.StateNode;

import java.util.HashSet;
import java.util.Set;

public class AlgoProofPLStateBuilder {

    protected final IPLFormula state;
    protected final Set<IPLFormula> hypotheses;
    protected int height;

    public AlgoProofPLStateBuilder(IPLFormula state) {
        this.state = state;
        this.height = 0;
        this.hypotheses = new HashSet<>();
    }

    public AlgoProofPLStateBuilder addHypothesis(IPLFormula hypothesis) {
        this.hypotheses.add(hypothesis);
        return this;
    }

    public AlgoProofPLStateBuilder addHypotheses(Set<IPLFormula> hypotheses) {
        this.hypotheses.addAll(hypotheses);
        return this;
    }

    public AlgoProofPLStateBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public StateNode build(BitGraphHandler handler) {
        return new StateNode(handler.getIndex(state), handler.toBitSet(new HashSet<>(hypotheses)),
                height, null, handler);
    }

}
