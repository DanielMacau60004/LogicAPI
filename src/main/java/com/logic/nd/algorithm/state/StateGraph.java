package com.logic.nd.algorithm.state;

import com.logic.api.IFormula;
import com.logic.nd.algorithm.state.strategies.*;
import com.logic.nd.algorithm.transition.ITransitionGraph;

import java.util.*;

public class StateGraph implements IStateGraph {

    //Necessary to keep the refs of the nodes
    protected Map<StateNode, StateEdge> tree;

    protected final ITransitionGraph transitionGraph;
    protected final StateGraphSettings settings;

    public StateGraph(ITransitionGraph transitionGraph, StateGraphSettings settings) {
        this.transitionGraph = transitionGraph;
        this.settings = settings;
        this.tree = new HashMap<>();
    }

    @Override
    public IFormula getInitialState() {
        return settings.getState().getExp();
    }

    @Override
    public Set<IFormula> getPremises() {
        return settings.getState().getPremisses();
    }

    @Override
    public StateEdge getEdge(StateNode node) {
        return tree.get(node);
    }

    @Override
    public void build() {
        settings.getBuildStrategy().build(transitionGraph, settings);
        tree = settings.getTrimStrategy().trim(settings.getBuildStrategy());
    }

    @Override
    public boolean isSolvable() {
        return tree.containsKey(settings.getState());
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
