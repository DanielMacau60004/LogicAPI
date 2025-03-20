package com.logic.nd.algorithm;

import com.logic.api.IFormula;
import com.logic.api.INDProof;
import com.logic.api.IPLFormula;
import com.logic.nd.ERule;
import com.logic.nd.algorithm.state.ParallelStateGraph;
import com.logic.nd.algorithm.state.StateGraph;
import com.logic.nd.algorithm.state.StateNode;
import com.logic.nd.algorithm.state.StateSolution;
import com.logic.nd.algorithm.transition.ITransitionGraph;
import com.logic.nd.algorithm.transition.TransitionGraphFOL;
import com.logic.nd.algorithm.transition.TransitionGraphPL;

import java.util.HashSet;
import java.util.Set;

public class AlgoProofPLBuilder extends AAlgoProofBuilder<AlgoProofPLBuilder> {

    private final IPLFormula conclusion;
    private final Set<IFormula> premises;
    private final Set<ERule> forbiddenRules;
    private AlgoProofStateBuilder state;

    public AlgoProofPLBuilder(IPLFormula conclusion) {
        super(new AlgoProofStateBuilder(conclusion));
        this.conclusion = conclusion;
        this.premises = new HashSet<>();
        this.forbiddenRules = new HashSet<>();

        state = new AlgoProofStateBuilder(conclusion);
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

    @Override
    protected AlgoProofPLBuilder self() {
        return this;
    }

    //TODO the graph should be generated from this node
    public AlgoProofPLBuilder setInitialState(AlgoProofStateBuilder stateBuilder) {
        this.state = stateBuilder;
        return this;
    }

    public INDProof build() {
        ITransitionGraph tg = new TransitionGraphPL(conclusion, premises, forbiddenRules);
        tg.build();

        StateGraph sg = useParallel ?
                new ParallelStateGraph(tg, state.build(premises), heightLimit, totalClosedNodesLimit, hypothesesPerStateLimit) :
                new StateGraph(tg, state.build(premises), heightLimit, totalClosedNodesLimit, hypothesesPerStateLimit);

        return new StateSolution(sg, false).findSolution();
    }
}
