package com.logic.nd.algorithm.state.strategies;

import com.logic.nd.algorithm.state.*;
import com.logic.nd.algorithm.transition.ITransitionGraph;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

public interface IBuildStrategy {

    void build(StateNode initialNode, ITransitionGraph transitionGraph, StateGraphSettings settings);

    Map<StateNode, Set<StateEdge>> getGraph();

    Map<StateNode, Set<StateNode>> getInvertedGraph();

    Queue<StateNode> getClosedNodes();

}
