package com.logic.nd.algorithm.state.strategies;

import com.logic.nd.algorithm.state.StateEdge;
import com.logic.nd.algorithm.state.StateGraphSettings;
import com.logic.nd.algorithm.state.StateNode;
import com.logic.nd.algorithm.transition.ITransitionGraph;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

public interface IBuildStrategy {

    void build(ITransitionGraph transitionGraph, StateGraphSettings settings);

    Map<StateNode, Set<StateEdge>> getGraph();
    Map<StateNode, Set<StateEdge>> getInvertedGraph();
    Queue<StateNode> getClosedNodes();

}
