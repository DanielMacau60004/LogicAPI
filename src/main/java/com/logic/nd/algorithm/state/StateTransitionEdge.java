package com.logic.nd.algorithm.state;

import com.logic.api.IFormula;

public class StateTransitionEdge {

    private final StateNode node;
    private final IFormula produces;

    public StateTransitionEdge(StateNode transition, IFormula produces) {
        this.node = transition;
        this.produces = produces;
    }

    public IFormula getProduces() {
        return produces;
    }

    public StateNode getNode() {
        return node;
    }
}
