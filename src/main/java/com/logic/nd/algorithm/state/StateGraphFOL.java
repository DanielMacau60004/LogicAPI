package com.logic.nd.algorithm.state;

import com.logic.nd.algorithm.AlgoProofFOLProblemBuilder;
import com.logic.nd.algorithm.AlgoProofFOLStateBuilder;
import com.logic.nd.algorithm.transition.ITransitionGraph;

import java.util.*;

public class StateGraphFOL extends StateGraph {

    public StateGraphFOL(AlgoProofFOLProblemBuilder problem, AlgoProofFOLStateBuilder state,
                         ITransitionGraph transitionGraph, StateGraphSettings settings) {
        super(new HashSet<>(problem.premises), transitionGraph, settings);

        rootState = problem.build(problem.premises, handler);
        initialState = state.build(problem.premises, handler);
    }

}
