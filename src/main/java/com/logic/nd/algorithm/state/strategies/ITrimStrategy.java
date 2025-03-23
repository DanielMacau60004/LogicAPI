package com.logic.nd.algorithm.state.strategies;

import com.logic.nd.algorithm.state.StateEdge;
import com.logic.nd.algorithm.state.StateNode;

import java.util.Map;

public interface ITrimStrategy {

    Map<StateNode, StateEdge> trim(IBuildStrategy buildStrategy);
}
