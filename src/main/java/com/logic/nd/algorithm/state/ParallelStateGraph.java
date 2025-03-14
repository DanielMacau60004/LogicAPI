package com.logic.nd.algorithm.state;

import com.logic.nd.algorithm.transition.TransitionEdge;
import com.logic.nd.algorithm.transition.TransitionGraph;
import com.logic.nd.algorithm.transition.TransitionNode;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;

public class ParallelStateGraph extends StateGraph {

    private static final int MAX_BATCH = 5000;

    public ParallelStateGraph(TransitionGraph transitionGraph, int heightLimit, int nodesLimit , int hypothesisLimit) {
        super(transitionGraph, heightLimit, nodesLimit, hypothesisLimit);
    }

    @Override
    void build(TransitionGraph transitionGraph, int heightLimit, int nodesLimit, int hypothesisLimit) {
        nodes = new ConcurrentHashMap<>();

        ConcurrentMap<StateNode, Set<StateEdge>> graph = new ConcurrentHashMap<>();
        Queue<StateNode> closed = new ConcurrentLinkedQueue<>();
        Queue<StateNode> explore = new ConcurrentLinkedQueue<>();
        ConcurrentMap<StateNode, Set<StateEdge>> inverted = new ConcurrentHashMap<>();

        explore.add(getInitState());

        ForkJoinPool pool = new ForkJoinPool(); // Uses all available CPU cores

        while (!explore.isEmpty()) {
            List<StateNode> batch = new ArrayList<>();

            for (int i = 0; i < MAX_BATCH; i++) {
                StateNode node = explore.poll();
                if (node == null) break;
                batch.add(node);
            }

            pool.submit(() ->
                    batch.parallelStream().forEach(state ->
                            processNode(state, transitionGraph, heightLimit, nodesLimit, hypothesisLimit, closed, explore, inverted, graph)
                    )
            ).join();
        }

        trim(closed, inverted, graph);
    }

    private void processNode(StateNode state, TransitionGraph transitionGraph, int heightLimit, int nodesLimit , int hypothesisLimit,
                             Queue<StateNode> closed, Queue<StateNode> explore,
                             ConcurrentMap<StateNode, Set<StateEdge>> inverted, ConcurrentMap<StateNode, Set<StateEdge>> graph) {

        if (graph.containsKey(state) || state.getHeight() > heightLimit || closed.size() >= nodesLimit
            || state.getHypotheses().size() > hypothesisLimit) return;

        Set<StateEdge> edges = ConcurrentHashMap.newKeySet();
        Set<StateEdge> existingEdges = graph.putIfAbsent(state, edges);

        if (existingEdges != null)
            return;

        if (state.isClosed()) {
            closed.add(state);
            return;
        }

        for (TransitionEdge edge : transitionGraph.getEdges(state.getExp())) {
            StateEdge e = new StateEdge(edge.getRule());

            for (TransitionNode transition : edge.getTransitions()) {
                StateNode newState = state.transit(transition.getTo(), transition.getProduces());

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
}
