package com.logic.nd.algorithm;

import com.logic.api.IFormula;
import com.logic.api.INDProof;
import com.logic.nd.ERule;
import com.logic.nd.algorithm.state.IStateGraph;
import com.logic.nd.algorithm.state.StateGraphPL;
import com.logic.nd.algorithm.state.StateGraphSettings;
import com.logic.nd.algorithm.state.StateSolution;
import com.logic.nd.algorithm.transition.ITransitionGraph;
import com.logic.nd.algorithm.transition.TransitionGraphPL;
import com.logic.others.Utils;

import java.util.HashSet;
import java.util.Set;

public class AlgoProofPLBuilder {

    private final AlgoProofPLProblemBuilder problem;
    private AlgoProofPLStateBuilder initialState;

    private final Set<ERule> forbiddenRules;
    private AlgoSettingsBuilder algoSettingsBuilder = new AlgoSettingsBuilder();

    public AlgoProofPLBuilder(AlgoProofPLProblemBuilder problem) {
        this.problem = problem;
        this.forbiddenRules = new HashSet<>();
    }

    public AlgoProofPLBuilder addForbiddenRule(ERule forbiddenRule) {
        this.forbiddenRules.add(forbiddenRule);
        return this;
    }

    public AlgoProofPLBuilder addForbiddenRules(Set<ERule> forbiddenRules) {
        this.forbiddenRules.addAll(forbiddenRules);
        return this;
    }

    public AlgoProofPLBuilder setInitialState(AlgoProofPLStateBuilder initialState) {
        this.initialState = initialState;
        return this;
    }

    public AlgoProofPLBuilder setAlgoSettingsBuilder(AlgoSettingsBuilder algoSettingsBuilder) {
        this.algoSettingsBuilder = algoSettingsBuilder;
        return this;
    }


    public INDProof build() {
        if (initialState == null)
            initialState = problem;

        StateGraphSettings s = algoSettingsBuilder.build();
        Set<IFormula> expressions = new HashSet<>(problem.premises);
        expressions.add(problem.state);
        expressions.addAll(problem.hypotheses);
        expressions.addAll(initialState.hypotheses);
        expressions.add(initialState.state);

        ITransitionGraph tg = new TransitionGraphPL(expressions, forbiddenRules);
        tg.build();

        //System.out.println(Utils.getToken(tg.toString()));

        IStateGraph sg = new StateGraphPL(problem, initialState, tg, s);
        sg.build();

        //System.out.println(Utils.getToken(sg.toString()));

        return new StateSolution(sg, false).findSolution();
    }
}
