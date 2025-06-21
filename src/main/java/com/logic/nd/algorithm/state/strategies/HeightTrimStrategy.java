package com.logic.nd.algorithm.state.strategies;

import com.logic.nd.algorithm.state.StateEdge;
import com.logic.nd.algorithm.state.StateNode;

import java.util.*;

public class HeightTrimStrategy implements ITrimStrategy {

    //The final answer may differ, it always returns solutions with the lowest height possible
    //It is faster time complexity O(E+N)
    @Override
    public Map<StateNode, StateEdge> trim(IBuildStrategy buildStrategy) {
        Queue<StateNode> explore = buildStrategy.getClosedNodes();
        Map<StateNode, Set<StateEdge>> graph = buildStrategy.getGraph();
        Map<StateNode, Set<StateNode>> inverted = buildStrategy.getInvertedGraph();

        Map<StateNode, StateEdge> tree = new HashMap<>();
        Set<StateNode> explored = new HashSet<>();

        while (!explore.isEmpty()) {
            StateNode state = explore.poll();
            tree.putIfAbsent(state, null);
            state.setClosed();

            if (explored.contains(state)) continue;
            explored.add(state);

            if (inverted.get(state) != null) {
                for (StateNode to : inverted.get(state)) {
                    Set<StateEdge> edges = graph.get(to);
                    if (edges != null) {

                        Optional<StateEdge> e = edges.stream().filter(StateEdge::isClosed).findFirst();
                        if (e.isPresent()) {
                            explore.add(to);

                            if (!tree.containsKey(to))
                                tree.put(to, e.get());
                        }
                    }
                }
            }
        }

        return tree;
    }
}
