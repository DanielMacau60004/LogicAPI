package com.logic.nd.algorithm.state;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.ERule;

public class StateTransitionEdge {

    private final StateNode transition;
    private final IASTExp produces;

    public StateTransitionEdge(StateNode transition, IASTExp produces) {
        this.transition = transition;
        this.produces = produces;
    }

    public IASTExp getProduces() {
        return produces;
    }

    public StateNode getTransition() {
        return transition;
    }
}
