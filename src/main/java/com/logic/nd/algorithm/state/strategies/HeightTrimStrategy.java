package com.logic.nd.algorithm.state.strategies;

import com.logic.nd.algorithm.state.StateEdge;
import com.logic.nd.algorithm.state.StateNode;
import com.logic.nd.algorithm.state.StateTransitionEdge;
import com.logic.others.Utils;

import java.util.*;

public class HeightTrimStrategy implements ITrimStrategy {

    //Can be slower than HeightTrimStrategy as is some cases require to pass thought the same node and edge multiple times
    //The worst case is O(n^2)
    @Override
    public Map<StateNode, StateEdge> trim(IBuildStrategy buildStrategy) {
        Queue<StateNode> explore = buildStrategy.getClosedNodes();
        Map<StateNode, Set<StateEdge>> graph = buildStrategy.getGraph();
        Map<StateNode, Set<StateEdge>> inverted = buildStrategy.getInvertedGraph();

        Map<StateNode, StateEdge> tree = new HashMap<>();
        Map<StateNode, Integer> explored = new HashMap<>();

        //Reset heights
        graph.keySet().forEach(node -> node.setHeight(node.isClosed() ? 1 : -1));

        while (!explore.isEmpty()) {
            StateNode state = explore.poll();
            tree.putIfAbsent(state, null);
            state.setClosed();

            if (explored.get(state) != null && explored.get(state) <= state.getHeight()) continue;
            explored.put(state, state.getHeight());

            if (inverted.get(state) != null) {
                for (StateEdge prev : inverted.get(state)) {
                    for (StateTransitionEdge to : prev.getTransitions()) {
                        Set<StateEdge> edges = graph.get(to.getNode());
                        if (edges != null) {
                            edges.removeIf(e -> {
                                if (!e.isClosed()) return false;

                                //Ignore node because there is a smaller path to that node
                                StateNode toNode = to.getNode();
                                if (toNode.getHeight() > 0 && toNode.getHeight() <= e.height()) return true;

                                toNode.setHeight(e.height() + 1);

                                explore.add(toNode);
                                tree.put(toNode, e);

                                return true;
                            });
                        }
                    }
                }
            }
        }

        return tree;
    }
}
