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
    private final Map<StateNode, Set<StateNode>> inverted;

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
        nodes.put(initialNode, initialNode);
        inverted.put(initialNode, new HashSet<>());

        long start = System.currentTimeMillis() + settings.getTimeout();

        while (!explore.isEmpty()) {
            StateNode state = explore.poll();

            if ((start - System.currentTimeMillis()) < 0 || closed.size() == settings.getTotalClosedNodesLimit())
                break;

            Set<StateEdge> edges = new HashSet<>();
            graph.put(state, edges);
            explored++;

            if (state.isClosed()) {
                closed.add(state);
                continue;
            }

            if (explored >= settings.getTotalNodesLimit())
                break;

            if (state.getHeight() + 1 > settings.getHeightLimit())
                continue;

            for (TransitionEdge edge : transitionGraph.getEdges(state.getExp().getAST())) {
                StateEdge e = new StateEdge(edge.getRule());

                if (edge.hasProduces() && state.numberOfHypotheses() + 1 > settings.getHypothesesPerStateLimit())
                    continue;

                for (TransitionNode transition : edge.getTransitions()) {
                    StateNode newState = state.transit(transition.getTo(), transition.getProduces(),
                            transition.getFree());

                    boolean contains = nodes.containsKey(newState);

                    if (contains) {
                        newState = nodes.get(newState);
                        inverted.get(newState).add(state);
                    } else {
                        nodes.put(newState, newState);
                        Set<StateNode> nodes = new HashSet<>();
                        nodes.add(state);
                        inverted.put(newState, nodes);

                        //graph.put(newState, null);
                        explore.add(newState);
                    }

                    e.addTransition(newState, transition.getProduces());

                }

                edges.add(e);
            }
        }

        System.out.println("Nodes: " + graph.size());
        System.out.println(closed.size());

    }

    @Override
    public Map<StateNode, Set<StateEdge>> getGraph() {
        return graph;
    }

    @Override
    public Map<StateNode, Set<StateNode>> getInvertedGraph() {
        return inverted;
    }

    @Override
    public Queue<StateNode> getClosedNodes() {
        return closed;
    }
}
