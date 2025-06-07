package com.logic.nd.algorithm.state;

import com.logic.nd.algorithm.AlgoProofPLProblemBuilder;
import com.logic.nd.algorithm.AlgoProofPLStateBuilder;
import com.logic.nd.algorithm.transition.ITransitionGraph;

import java.util.*;

public class StateGraphPL extends StateGraph {

    public StateGraphPL(AlgoProofPLProblemBuilder problem, AlgoProofPLStateBuilder state,
                        ITransitionGraph transitionGraph, StateGraphSettings settings) {
        super(new HashSet<>(problem.premises), transitionGraph, settings);

        rootState = problem.build(handler);
        initialState = state.build(handler);
    }

}
