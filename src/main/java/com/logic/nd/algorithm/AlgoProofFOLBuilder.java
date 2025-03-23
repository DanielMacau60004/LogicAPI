package com.logic.nd.algorithm;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.api.INDProof;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.nd.ERule;
import com.logic.nd.algorithm.state.StateGraph;
import com.logic.nd.algorithm.state.StateGraphSettings;
import com.logic.nd.algorithm.state.StateSolution;
import com.logic.nd.algorithm.state.strategies.IStateGraph;
import com.logic.nd.algorithm.transition.ITransitionGraph;
import com.logic.nd.algorithm.transition.TransitionGraphFOL;

import java.util.HashSet;
import java.util.Set;

public class AlgoProofFOLBuilder {

    private final IFOLFormula conclusion;
    private final Set<IFormula> premises;
    private final Set<ERule> forbiddenRules;
    private final Set<AASTTerm> terms;
    private AlgoSettingsBuilder algoSettingsBuilder = new AlgoSettingsBuilder();

    public AlgoProofFOLBuilder(IFOLFormula conclusion) {
        this.conclusion = conclusion;
        this.premises = new HashSet<>();
        this.forbiddenRules = new HashSet<>();
        this.terms = new HashSet<>();

        conclusion.iterateVariables().forEachRemaining(terms::add);
    }

    public AlgoProofFOLBuilder addPremise(IFOLFormula premise) {
        this.premises.add(premise);
        premise.iterateTerms().forEachRemaining(terms::add);
        return this;
    }

    public AlgoProofFOLBuilder addPremises(Set<IFOLFormula> premises) {
        this.premises.addAll(premises);
        premises.forEach(premise -> premise.iterateTerms().forEachRemaining(terms::add));
        return this;
    }

    public AlgoProofFOLBuilder addForbiddenRule(ERule forbiddenRule) {
        this.forbiddenRules.add(forbiddenRule);
        return this;
    }

    public AlgoProofFOLBuilder addForbiddenRules(Set<ERule> forbiddenRules) {
        this.forbiddenRules.addAll(forbiddenRules);
        return this;
    }

    public AlgoProofFOLBuilder addTerm(AASTTerm term) {
        this.terms.add(term);
        return this;
    }

    public AlgoProofFOLBuilder addTerms(Set<AASTTerm> terms) {
        this.terms.addAll(terms);
        return this;
    }

    public AlgoProofFOLBuilder setAlgoSettingsBuilder(AlgoSettingsBuilder algoSettingsBuilder) {
        this.algoSettingsBuilder = algoSettingsBuilder;
        return this;
    }

    public INDProof build() {
        StateGraphSettings s = algoSettingsBuilder.build(conclusion, premises);
        ITransitionGraph tg = new TransitionGraphFOL(conclusion, premises, forbiddenRules, terms);
        tg.build();

        IStateGraph sg = new StateGraph(tg, s);
        sg.build();

        return new StateSolution(sg, true).findSolution();
    }

}
