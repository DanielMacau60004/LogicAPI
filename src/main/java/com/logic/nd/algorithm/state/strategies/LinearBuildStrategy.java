package com.logic.nd.algorithm.state.strategies;

import com.logic.nd.algorithm.state.StateEdge;
import com.logic.nd.algorithm.state.StateGraphSettings;
import com.logic.nd.algorithm.state.StateNode;
import com.logic.nd.algorithm.transition.ITransitionGraph;
import com.logic.nd.algorithm.transition.TransitionEdge;
import com.logic.nd.algorithm.transition.TransitionNode;

import java.util.*;

public class LinearBuildStrategy implements IBuildStrategy {

    private final Map<StateNode, StateNode> nodes;
    private final Map<StateNode, Set<StateEdge>> graph;
    private final Queue<StateNode> closed;
    private final Queue<StateNode> explore;
    private final Map<StateNode, Set<StateEdge>> inverted;

    public LinearBuildStrategy() {
        nodes = new HashMap<>();
        graph = new HashMap<>();
        closed = new LinkedList<>();
        explore = new LinkedList<>();
        inverted = new HashMap<>();
    }

    @Override
    public void build(ITransitionGraph transitionGraph, StateGraphSettings settings) {

        explore.add(settings.getState());

        long start = System.currentTimeMillis() + settings.getTimeout();

        while (!explore.isEmpty()) {
            StateNode state = explore.poll();

            if ((start - System.currentTimeMillis()) < 0 || state.getHeight() > settings.getHeightLimit() ||
                    closed.size() == settings.getTotalClosedNodesLimit()
                    || state.getHypotheses().size() > settings.getHypothesesPerStateLimit())
                break;

            if (graph.containsKey(state))
                continue;

            Set<StateEdge> edges = new HashSet<>();
            graph.put(state, edges);

            if (state.isClosed()) {
                closed.add(state);
                continue;
            }

            for (TransitionEdge edge : transitionGraph.getEdges(state.getExp().getFormula())) {
                StateEdge e = new StateEdge(edge.getRule());
                for (TransitionNode transition : edge.getTransitions()) {
                    StateNode newState = state.transit(transition.getTo(), transition.getProduces(),
                            transition.getFree());

                    StateNode finalNewState = newState;
                    newState = nodes.computeIfAbsent(newState, k -> finalNewState);

                    e.addTransition(newState, transition.getProduces());

                    inverted.computeIfAbsent(newState, k -> new HashSet<>())
                            .add(new StateEdge(edge.getRule(), state, null));

                    explore.add(newState);
                }

                edges.add(e);
            }
        }
    }

    @Override
    public Map<StateNode, Set<StateEdge>> getGraph() {
        return graph;
    }

    @Override
    public Map<StateNode, Set<StateEdge>> getInvertedGraph() {
        return inverted;
    }

    @Override
    public Queue<StateNode> getClosedNodes() {
        return closed;
    }
}
