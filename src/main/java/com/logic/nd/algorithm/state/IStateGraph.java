package com.logic.nd.algorithm.state;

import com.logic.api.IFormula;

import java.util.Set;

public interface IStateGraph {
    StateNode getInitialState();

    Set<IFormula> getPremises();

    StateEdge getEdge(StateNode node);

    void build();

    boolean isSolvable();
}
