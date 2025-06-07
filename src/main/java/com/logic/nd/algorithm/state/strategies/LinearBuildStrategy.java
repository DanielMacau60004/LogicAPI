package com.logic.nd.algorithm.state.strategies;

import com.logic.nd.algorithm.state.*;
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

    private int explored = 0;

    public LinearBuildStrategy() {
        nodes = new HashMap<>();

        graph = new HashMap<>();
        closed = new LinkedList<>();
        explore = new LinkedList<>();
        inverted = new HashMap<>();
    }

    @Override
    public void build(StateNode initialNode, ITransitionGraph transitionGraph, StateGraphSettings settings) {
        explore.add(initialNode);

        long start = System.currentTimeMillis() + settings.getTimeout();

        while (!explore.isEmpty()) {
            StateNode state = explore.poll();

            if ((start - System.currentTimeMillis()) < 0 || closed.size() == settings.getTotalClosedNodesLimit())
                break;

            if (state.getHeight() > settings.getHeightLimit() ||
                    state.numberOfHypotheses() > settings.getHypothesesPerStateLimit())
                continue;

            if (graph.containsKey(state))
                continue;

            Set<StateEdge> edges = new HashSet<>();
            graph.put(state, edges);
            explored++;

            if (state.isClosed()) {
                closed.add(state);
                continue;
            }

            if(explored >= settings.getTotalNodesLimit())
                break;

            for (TransitionEdge edge : transitionGraph.getEdges(state.getExp().getAST())) {
                StateEdge e = new StateEdge(edge.getRule());
                for (TransitionNode transition : edge.getTransitions()) {
                    StateNode newState = state.transit(transition.getTo(), transition.getProduces(),
                            transition.getFree());

                    StateNode finalNewState = newState;
                    newState = nodes.computeIfAbsent(newState, k -> finalNewState);

                    e.addTransition(newState, transition.getProduces());

                    inverted.computeIfAbsent(newState, k -> new HashSet<>())
                            .add(new StateEdge(edge.getRule(), state, null));

                    if (!graph.containsKey(newState))
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
