package com.logic.nd.algorithm.state.strategies;

import com.logic.nd.algorithm.state.StateEdge;
import com.logic.nd.algorithm.state.StateNode;
import com.logic.nd.algorithm.state.StateTransitionEdge;

import java.util.*;

public class DFSTrimStrategy implements ITrimStrategy {

    @Override
    public Map<StateNode, StateEdge> trim(IBuildStrategy buildStrategy) {
        Queue<StateNode> explore = buildStrategy.getClosedNodes();
        Map<StateNode, Set<StateEdge>> graph = buildStrategy.getGraph();
        Map<StateNode, Set<StateEdge>> inverted = buildStrategy.getInvertedGraph();

        Map<StateNode, StateEdge> tree = new HashMap<>();
        Set<StateNode> explored = new HashSet<>();

        while (!explore.isEmpty()) {
            StateNode state = explore.poll();
            tree.putIfAbsent(state, null);
            state.setClosed();

            if (explored.contains(state)) continue;
            explored.add(state);

            if (inverted.get(state) != null) {
                for (StateEdge prev : inverted.get(state)) {
                    for (StateTransitionEdge to : prev.getTransitions()) {
                        Set<StateEdge> edges = graph.get(to.getTransition());
                        if (edges != null) {

                            Optional<StateEdge> e = edges.stream().filter(StateEdge::isClosed)
                                    .min(Comparator.comparingInt(o -> o.getTransitions().size()));
                            if (e.isPresent()) {
                                explore.add(to.getTransition());

                                if(!tree.containsKey(to.getTransition()))
                                    tree.put(to.getTransition(), e.get());
                            }
                        }
                    }
                }
            }
        }

        return tree;
    }
}
