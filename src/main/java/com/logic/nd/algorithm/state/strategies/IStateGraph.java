package com.logic.nd.algorithm.state.strategies;

import com.logic.api.IFormula;
import com.logic.nd.algorithm.state.StateEdge;
import com.logic.nd.algorithm.state.StateNode;

import java.util.Set;

public interface IStateGraph {
    StateNode getInitialState();

    Set<IFormula> getPremises();

    StateEdge getEdge(StateNode node);

    void build();

    boolean isSolvable();
}
