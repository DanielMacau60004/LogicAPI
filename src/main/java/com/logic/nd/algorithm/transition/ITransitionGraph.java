package com.logic.nd.algorithm.transition;

import com.logic.api.IFormula;
import com.logic.exps.asts.IASTExp;

import java.util.Set;

public interface ITransitionGraph {

    void build();

    Set<TransitionEdge> getEdges(IASTExp exp);
}
