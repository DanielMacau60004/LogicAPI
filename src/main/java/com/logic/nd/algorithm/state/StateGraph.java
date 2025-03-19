package com.logic.nd.algorithm.state;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.algorithm.transition.TransitionEdge;
import com.logic.nd.algorithm.transition.TransitionGraph;
import com.logic.nd.algorithm.transition.TransitionNode;

import java.util.*;

public class StateGraph {

    //Necessary to keep the refs of the nodes
    protected Map<StateNode, StateNode> nodes;

    protected Map<StateNode, StateEdge> tree;

    protected final IASTExp conclusion;
    protected final Set<IASTExp> premisses;

    public StateGraph(TransitionGraph transitionGraph, int heightLimit, int nodesLimit, int hypothesisLimit) {
        this.nodes = new HashMap<>();
        this.tree = new HashMap<>();

        this.conclusion = transitionGraph.getConclusion();
        this.premisses = transitionGraph.getPremisses();

        build(transitionGraph, heightLimit, nodesLimit, hypothesisLimit);
    }

    StateNode getInitState() {
        return new StateNode(conclusion, premisses);
    }

    void build(TransitionGraph transitionGraph, int heightLimit, int nodesLimit, int hypothesisLimit) {
        Map<StateNode, Set<StateEdge>> graph = new HashMap<>();
        Queue<StateNode> closed = new LinkedList<>();
        Queue<StateNode> explore = new LinkedList<>();
        Map<StateNode, Set<StateEdge>> inverted = new HashMap<>();

        explore.add(getInitState());

        while (!explore.isEmpty()) {
            StateNode state = explore.poll();

            if (graph.containsKey(state))
                continue;

            if (state.getHeight() > heightLimit || closed.size() == nodesLimit
                    || state.getHypotheses().size()> hypothesisLimit)
                break;

            Set<StateEdge> edges = new HashSet<>();
            graph.put(state, edges);

            if (state.isClosed()) {
                closed.add(state);
                continue;
            }

            for (TransitionEdge edge : transitionGraph.getEdges(state.getExp())) {
                StateEdge e = new StateEdge(edge.getRule());
                for (TransitionNode transition : edge.getTransitions()) {
                    StateNode newState = state.transit(transition.getTo(), transition.getProduces());

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

        trim(closed, inverted, graph);
    }

    void trim(Queue<StateNode> explore, Map<StateNode, Set<StateEdge>> inverted, Map<StateNode, Set<StateEdge>> graph) {
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

                            Optional<StateEdge> e = edges.stream().filter(StateEdge::isClosed).findFirst();
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

    }

    public boolean isSolvable() {
        return tree.containsKey(getInitState());
    }


    @Override
    public String toString() {
        String str = "";
        str += "Total nodes: " + tree.size() + "\n";
        str += "Total edges: " + tree.values().stream().filter(Objects::nonNull).count() + "\n";
        //for (Map.Entry<StateNode, Set<StateEdge>> entry : graph.entrySet())
        //    str += entry.getKey() + " edges:"+ entry.getValue() + ": \n";
        return str;
    }

}
