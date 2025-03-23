package com.logic.nd.algorithm;

import com.logic.api.IFormula;
import com.logic.api.INDProof;
import com.logic.api.IPLFormula;
import com.logic.nd.ERule;
import com.logic.nd.algorithm.state.StateGraph;
import com.logic.nd.algorithm.state.StateGraphSettings;
import com.logic.nd.algorithm.state.StateSolution;
import com.logic.nd.algorithm.state.strategies.IStateGraph;
import com.logic.nd.algorithm.transition.ITransitionGraph;
import com.logic.nd.algorithm.transition.TransitionGraphPL;

import java.util.HashSet;
import java.util.Set;

public class AlgoProofPLBuilder {

    private final IPLFormula conclusion;
    private final Set<IFormula> premises;
    private final Set<ERule> forbiddenRules;
    private AlgoSettingsBuilder algoSettingsBuilder = new AlgoSettingsBuilder();

    public AlgoProofPLBuilder(IPLFormula conclusion) {
        this.conclusion = conclusion;
        this.premises = new HashSet<>();
        this.forbiddenRules = new HashSet<>();

    }

    public AlgoProofPLBuilder addPremise(IPLFormula premise) {
        this.premises.add(premise);
        return this;
    }

    public AlgoProofPLBuilder addPremises(Set<IPLFormula> premises) {
        this.premises.addAll(premises);
        return this;
    }

    public AlgoProofPLBuilder addForbiddenRule(ERule forbiddenRule) {
        this.forbiddenRules.add(forbiddenRule);
        return this;
    }

    public AlgoProofPLBuilder addForbiddenRules(Set<ERule> forbiddenRules) {
        this.forbiddenRules.addAll(forbiddenRules);
        return this;
    }

    public AlgoProofPLBuilder setAlgoSettingsBuilder(AlgoSettingsBuilder algoSettingsBuilder) {
        this.algoSettingsBuilder = algoSettingsBuilder;
        return this;
    }


    public INDProof build() {
        StateGraphSettings s = algoSettingsBuilder.build(conclusion, premises);
        ITransitionGraph tg = new TransitionGraphPL(conclusion, premises, forbiddenRules);
        tg.build();

        IStateGraph sg = new StateGraph(tg, s);
        sg.build();

        return new StateSolution(sg, false).findSolution();
    }
}
