package com.logic.nd.algorithm.state.strategies;

import com.logic.nd.algorithm.state.StateEdge;
import com.logic.nd.algorithm.state.StateGraphSettings;
import com.logic.nd.algorithm.state.StateNode;
import com.logic.nd.algorithm.transition.ITransitionGraph;
import com.logic.nd.algorithm.transition.TransitionEdge;
import com.logic.nd.algorithm.transition.TransitionNode;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;

public class ParallelBuildStrategy implements IBuildStrategy {

    private static final int MAX_BATCH = 5000;

    private final Map<StateNode, StateNode> nodes;
    public final Map<StateNode, Set<StateEdge>> graph;
    public final Queue<StateNode> closed;
    public final Queue<StateNode> explore;
    public final Map<StateNode, Set<StateEdge>> inverted;

    public ParallelBuildStrategy() {
        nodes = new ConcurrentHashMap<>();
        graph = new ConcurrentHashMap<>();
        closed = new ConcurrentLinkedQueue<>();
        explore = new ConcurrentLinkedQueue<>();
        inverted = new ConcurrentHashMap<>();
    }

    @Override
    public void build(ITransitionGraph transitionGraph, StateGraphSettings settings) {

        explore.add(settings.getState());

        ForkJoinPool pool = new ForkJoinPool(); // Uses all available CPU cores

        long start = System.currentTimeMillis() + settings.getTimeout();

        while (!explore.isEmpty()) {
            if ((start - System.currentTimeMillis()) < 0)
                break;

            List<StateNode> batch = new ArrayList<>();

            for (int i = 0; i < MAX_BATCH; i++) {
                StateNode node = explore.poll();
                if (node == null) break;
                batch.add(node);
            }

            pool.submit(() ->
                    batch.parallelStream().forEach(state ->
                            processNode(transitionGraph, state, settings)
                    )
            ).join();
        }

    }

    private void processNode(ITransitionGraph transitionGraph, StateNode  state, StateGraphSettings settings) {
        if (graph.containsKey(state) || state.getHeight() > settings.getHeightLimit()
                || closed.size() >= settings.getTotalClosedNodesLimit()
                || state.getHypotheses().size() > settings.getHypothesesPerStateLimit())
            return;

        Set<StateEdge> edges = ConcurrentHashMap.newKeySet();
        Set<StateEdge> existingEdges = graph.putIfAbsent(state, edges);

        if (existingEdges != null)
            return;

        if (state.isClosed()) {
            closed.add(state);
            return;
        }

        for (TransitionEdge edge : transitionGraph.getEdges(state.getExp().getFormula())) {
            StateEdge e = new StateEdge(edge.getRule());

            for (TransitionNode transition : edge.getTransitions()) {
                StateNode newState = state.transit(transition.getTo(), transition.getProduces(),
                        transition.getFree());

                StateNode existingState = nodes.putIfAbsent(newState, newState);
                if (existingState != null)
                    newState = existingState;

                e.addTransition(newState, transition.getProduces());

                inverted.computeIfAbsent(newState, k -> ConcurrentHashMap.newKeySet())
                        .add(new StateEdge(edge.getRule(), state, null));
                explore.add(newState);
            }

            edges.add(e);
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
