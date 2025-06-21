package com.logic.nd.algorithm;

import com.logic.api.IFormula;
import com.logic.api.INDProof;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.nd.ERule;
import com.logic.nd.algorithm.state.StateGraphFOL;
import com.logic.nd.algorithm.state.StateGraphSettings;
import com.logic.nd.algorithm.state.StateSolution;
import com.logic.nd.algorithm.state.IStateGraph;
import com.logic.nd.algorithm.transition.ITransitionGraph;
import com.logic.nd.algorithm.transition.TransitionGraphFOL;
import com.logic.others.Utils;

import java.util.HashSet;
import java.util.Set;

public class AlgoProofFOLBuilder {

    private final AlgoProofFOLProblemBuilder problem;
    private AlgoProofFOLStateBuilder initialState;

    private final Set<ERule> forbiddenRules;
    private AlgoSettingsBuilder algoSettingsBuilder = new AlgoSettingsBuilder();

    public AlgoProofFOLBuilder(AlgoProofFOLProblemBuilder problem) {
        this.problem = problem;
        this.forbiddenRules = new HashSet<>();
    }

    public AlgoProofFOLBuilder addForbiddenRule(ERule forbiddenRule) {
        this.forbiddenRules.add(forbiddenRule);
        return this;
    }

    public AlgoProofFOLBuilder addForbiddenRules(Set<ERule> forbiddenRules) {
        this.forbiddenRules.addAll(forbiddenRules);
        return this;
    }

    public AlgoProofFOLBuilder setInitialState(AlgoProofFOLStateBuilder initialState) {
        this.initialState = initialState;
        return this;
    }

    public AlgoProofFOLBuilder setAlgoSettingsBuilder(AlgoSettingsBuilder algoSettingsBuilder) {
        this.algoSettingsBuilder = algoSettingsBuilder;
        return this;
    }

    public INDProof build() {
        if(initialState == null)
            initialState = problem;

        Set<AASTTerm> terms = new HashSet<>(problem.terms);
        terms.addAll(initialState.terms);

        StateGraphSettings s = algoSettingsBuilder.build();
        Set<IFormula> expressions = new HashSet<>(problem.premises);
        expressions.add(problem.state);
        expressions.addAll(problem.hypotheses);
        expressions.addAll(initialState.hypotheses);
        expressions.add(initialState.state);

        ITransitionGraph tg = new TransitionGraphFOL(expressions, forbiddenRules, terms);
        tg.build();

        IStateGraph sg = new StateGraphFOL(problem, initialState, tg, s);
        sg.build();

        return new StateSolution(sg, true).findSolution();
    }

}
