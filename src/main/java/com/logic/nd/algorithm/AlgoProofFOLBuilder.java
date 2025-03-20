package com.logic.nd.algorithm;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.api.INDProof;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.nd.ERule;
import com.logic.nd.algorithm.state.ParallelStateGraph;
import com.logic.nd.algorithm.state.StateGraph;
import com.logic.nd.algorithm.state.StateSolution;
import com.logic.nd.algorithm.transition.ITransitionGraph;
import com.logic.nd.algorithm.transition.TransitionGraphFOL;
import com.logic.nd.algorithm.transition.TransitionGraphPL;
import com.logic.others.Utils;

import java.util.HashSet;
import java.util.Set;

public class AlgoProofFOLBuilder extends AAlgoProofBuilder<AlgoProofFOLBuilder> {

    private final IFOLFormula conclusion;
    private final Set<IFormula> premises;
    private final Set<ERule> forbiddenRules;
    private final Set<AASTTerm> terms;

    public AlgoProofFOLBuilder(IFOLFormula conclusion) {
        super(new AlgoProofStateBuilder(conclusion));
        this.conclusion = conclusion;
        this.premises = new HashSet<>();
        this.forbiddenRules = new HashSet<>();
        this.terms = new HashSet<>();

        conclusion.iterateVariables().forEachRemaining(terms::add);
    }

    public AlgoProofFOLBuilder addPremise(IFOLFormula premise) {
        this.premises.add(premise);
        premise.iterateVariables().forEachRemaining(terms::add);
        return this;
    }

    public AlgoProofFOLBuilder addPremises(Set<IFOLFormula> premises) {
        this.premises.addAll(premises);
        premises.forEach(premise -> premise.iterateVariables().forEachRemaining(terms::add));
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

    public INDProof build() {
        ITransitionGraph tg = new TransitionGraphFOL(conclusion, premises, forbiddenRules, terms);
        tg.build();

        StateGraph sg = useParallel ?
                new ParallelStateGraph(tg, state.build(premises), heightLimit, totalClosedNodesLimit, hypothesesPerStateLimit) :
                new StateGraph(tg, state.build(premises), heightLimit, totalClosedNodesLimit, hypothesesPerStateLimit);

        return new StateSolution(sg, true).findSolution();
    }

    @Override
    protected AlgoProofFOLBuilder self() {
        return this;
    }
}
