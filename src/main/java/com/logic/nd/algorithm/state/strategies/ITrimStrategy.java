package com.logic.nd.algorithm.state.strategies;

import com.logic.nd.algorithm.state.StateEdge;
import com.logic.nd.algorithm.state.StateNode;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

public interface ITrimStrategy {

    Map<StateNode, StateEdge> trim(IBuildStrategy buildStrategy);
}
