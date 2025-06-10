package com.logic.nd.algorithm.state.strategies;

import com.logic.nd.algorithm.state.StateEdge;
import com.logic.nd.algorithm.state.StateNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class HeightTrimStrategy implements ITrimStrategy {

    //Can be slower than HeightTrimStrategy as is some cases require to pass thought the same node and edge multiple times
    //The worst case is O(n^2)
    @Override
    public Map<StateNode, StateEdge> trim(IBuildStrategy buildStrategy) {
        Queue<StateNode> explore = buildStrategy.getClosedNodes();
        Map<StateNode, Set<StateEdge>> graph = buildStrategy.getGraph();
        Map<StateNode, Set<StateNode>> inverted = buildStrategy.getInvertedGraph();

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
                for (StateNode to : inverted.get(state)) {
                    Set<StateEdge> edges = graph.get(to);
                    if (edges != null) {
                        edges.removeIf(e -> {
                            if (!e.isClosed()) return false;

                            //Ignore node because there is a smaller path to that node
                            if (to.getHeight() > 0 && to.getHeight() <= e.height() + 1) return true;

                            to.setHeight(e.height() + 1);

                            explore.add(to);
                            tree.put(to, e);

                            return true;
                        });
                    }

                }
            }
        }

        return tree;
    }
}
