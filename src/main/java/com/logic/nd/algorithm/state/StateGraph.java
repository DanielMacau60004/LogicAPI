package com.logic.nd.algorithm.state;

import com.logic.exps.asts.IASTExp;
import com.logic.api.INDProof;
import com.logic.nd.algorithm.transition.TransitionEdge;
import com.logic.nd.algorithm.transition.TransitionGraph;
import com.logic.nd.algorithm.transition.TransitionNode;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.binary.ASTEExistND;
import com.logic.nd.asts.binary.ASTEImpND;
import com.logic.nd.asts.binary.ASTENegND;
import com.logic.nd.asts.binary.ASTIConjND;
import com.logic.nd.asts.others.ASTEDisjND;
import com.logic.nd.asts.others.ASTHypothesisND;
import com.logic.nd.asts.others.ASTPremiseND;
import com.logic.nd.asts.unary.*;
import com.logic.nd.interpreters.NDInterpreter;

import java.util.*;

public class StateGraph {

    //Necessary to keep the refs of the nodes
    protected Map<StateNode, StateNode> nodes;

    protected Map<StateNode, StateEdge> tree;

    private final IASTExp conclusion;
    private final Set<IASTExp> premisses;

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

    public INDProof findSolution() {
        return findSolution(getInitState().getExp(), new HashMap<>());
    }

    //Specify exp and mark in every hypothesis
    public INDProof findSolution(IASTExp exp, Map<IASTExp, Integer> hypotheses) {
        if (!isSolvable())
            return null;

        //TODO will cause conflict with premises marks, they might not start with 1
        int mark = 1;
        Map<IASTExp, Integer> marks = new HashMap<>(hypotheses);
        for(IASTExp e : premisses) marks.put(e, mark++);

        IASTND proof = rule(new StateNode(exp, premisses, hypotheses.keySet()), marks, mark);
        return NDInterpreter.interpret(proof);
    }

    //TODO Store rule objects instead of states in the graph to avoid this crap
    private IASTND rule(StateNode initState, Map<IASTExp, Integer> marks, int lastIndex) {
        StateEdge edge = tree.get(initState);
        IASTExp exp = initState.getExp();

        marks = new HashMap<>(marks);

        if(edge == null)
            return initState.hasHypothesis(exp)
                    ? new ASTHypothesisND(exp, marks.get(exp))
                    : new ASTPremiseND(exp, marks.get(exp));

        List<StateTransitionEdge> transitions = edge.getTransitions();
        
        for(StateTransitionEdge e : transitions) 
            if(e.getProduces() != null) marks.put(e.getProduces(), lastIndex++);

        IASTND first = !transitions.isEmpty() ? rule(transitions.get(0).getTransition(), marks, lastIndex) : null;
        IASTND second = transitions.size() > 1 ? rule(transitions.get(1).getTransition(), marks, lastIndex) : null;
        IASTND third = transitions.size() > 2 ? rule(transitions.get(2).getTransition(), marks, lastIndex) : null;

        return switch (edge.getRule()) {
            case INTRO_CONJUNCTION  -> new ASTIConjND(first, second, exp);
            case ELIM_CONJUNCTION_LEFT  -> new ASTELConjND(first, exp);
            case ELIM_CONJUNCTION_RIGHT -> new ASTERConjND(first, exp);
            case INTRO_DISJUNCTION_LEFT -> new ASTILDisND(first, exp);
            case INTRO_DISJUNCTION_RIGHT -> new ASTIRDisND(first, exp);
            case ELIM_DISJUNCTION -> new ASTEDisjND(first, second, third, exp,
                    marks.get(transitions.get(1).getProduces()), marks.get(transitions.get(2).getProduces()));
            case INTRO_IMPLICATION -> new ASTIImpND(first, exp, marks.get(transitions.get(0).getProduces()));
            case INTRO_NEGATION -> new ASTINegND(first, exp, marks.get(transitions.get(0).getProduces()));
            case ELIM_IMPLICATION -> new ASTEImpND(first, second, exp);
            case ABSURDITY -> new ASTAbsurdityND(first, exp, marks.get(transitions.get(0).getProduces()));
            case ELIM_NEGATION -> new ASTENegND(first, second, exp);
            case ELIM_UNIVERSAL -> new ASTEUniND(first, exp);
            case INTRO_EXISTENTIAL -> new ASTIExistND(first, exp);
            case INTRO_UNIVERSAL -> new ASTIUniND(first, exp);
            case ELIM_EXISTENTIAL -> new ASTEExistND(first, second, exp, marks.get(transitions.get(1).getProduces()));
        };

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
