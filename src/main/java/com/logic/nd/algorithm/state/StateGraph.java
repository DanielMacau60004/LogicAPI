package com.logic.nd.algorithm.state;

import com.logic.api.IFormula;
import com.logic.nd.algorithm.AlgoProofFOLProblemBuilder;
import com.logic.nd.algorithm.AlgoProofFOLStateBuilder;
import com.logic.nd.algorithm.transition.ITransitionGraph;

import java.util.*;

public abstract class StateGraph implements IStateGraph {

    //Necessary to keep the refs of the nodes
    protected Map<StateNode, StateEdge> tree;
    protected final BitGraphHandler handler;

    protected final ITransitionGraph transitionGraph;
    protected final StateGraphSettings settings;

    protected final Set<IFormula> premises;

    protected StateNode rootState;
    protected StateNode initialState;

    public StateGraph(Set<IFormula> premises, ITransitionGraph transitionGraph, StateGraphSettings settings) {
        this.premises = new HashSet<>(premises);
        this.handler = new BitGraphHandler(this.premises, transitionGraph.getFormulas());
        this.transitionGraph = transitionGraph;
        this.settings = settings;
        this.tree = new HashMap<>();
    }

    @Override
    public StateNode getRootState() {
        return rootState;
    }

    @Override
    public StateNode getInitialState() {
        return initialState;
    }

    @Override
    public Set<IFormula> getPremises() {
        return premises;
    }

    @Override
    public StateGraphSettings getSettings() {
        return settings;
    }

    @Override
    public StateEdge getEdge(StateNode node) {
        return tree.get(node);
    }

    @Override
    public void build() {
        settings.getBuildStrategy().build(initialState, transitionGraph, settings);
        tree = settings.getTrimStrategy().trim(settings.getBuildStrategy());
    }

    @Override
    public boolean isSolvable() {
        return tree.containsKey(initialState);
    }

    @Override
    public String toString() {
        String str = "";
        str += "Total nodes: " + tree.size() + "\n";
        str += "Total edges: " + tree.values().stream().filter(Objects::nonNull).mapToLong(t -> t.getTransitions().size()).sum() + "\n";
        //for (Map.Entry<IStateNode, StateEdge> entry : tree.entrySet())
        //    str += "{" + entry.getKey().getExp() + " h:" + entry.getKey().getHypotheses() + "} edges:" + entry.getValue() + ": \n";
        return str;
    }

}
