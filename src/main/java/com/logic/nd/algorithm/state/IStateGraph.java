package com.logic.nd.algorithm.state;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;

import java.util.Set;

public interface IStateGraph {
    StateNode getRootState();

    StateNode getInitialState();

    Set<IFormula> getPremises();

    StateGraphSettings getSettings();

    StateEdge getEdge(StateNode node);

    void build();

    boolean isSolvable();
}
