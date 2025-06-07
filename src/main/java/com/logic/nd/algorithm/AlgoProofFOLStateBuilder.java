package com.logic.nd.algorithm;

import com.logic.api.IFOLFormula;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.nd.algorithm.state.BitGraphHandler;
import com.logic.nd.algorithm.state.StateNode;

import java.util.HashSet;
import java.util.Set;

public class AlgoProofFOLStateBuilder {

    protected final IFOLFormula state;
    protected final Set<IFOLFormula> hypotheses;
    protected final Set<ASTVariable> notFreeVars;
    protected final Set<AASTTerm> terms;
    protected int height;

    public AlgoProofFOLStateBuilder(IFOLFormula state) {
        this.state = state;
        this.height = 0;
        this.hypotheses = new HashSet<>();
        this.notFreeVars = new HashSet<>();
        this.terms = new HashSet<>();

        state.iterateTerms().forEachRemaining(terms::add);
    }

    public AlgoProofFOLStateBuilder addHypothesis(IFOLFormula hypothesis) {
        hypothesis.iterateTerms().forEachRemaining(terms::add);
        this.hypotheses.add(hypothesis);
        return this;
    }

    public AlgoProofFOLStateBuilder addHypotheses(Set<IFOLFormula> hypotheses) {
        hypotheses.forEach(hypothesis -> hypothesis.iterateTerms().forEachRemaining(terms::add));
        this.hypotheses.addAll(hypotheses);
        return this;
    }

    public AlgoProofFOLStateBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public AlgoProofFOLStateBuilder setNoFreeVariables(Set<ASTVariable> notFreeVars) {
        this.notFreeVars.addAll(notFreeVars);
        return this;
    }

    public AlgoProofFOLStateBuilder addNoFreeVariable(ASTVariable notFreeVar) {
        this.notFreeVars.add(notFreeVar);
        return this;
    }

    public AlgoProofFOLStateBuilder addTerm(AASTTerm term) {
        this.terms.add(term);
        return this;
    }

    public AlgoProofFOLStateBuilder addTerms(Set<AASTTerm> terms) {
        this.terms.addAll(terms);
        return this;
    }

    public StateNode build(Set<IFOLFormula> premises, BitGraphHandler handler) {
        Set<IFOLFormula> notFree = new HashSet<>();
        Set<IFOLFormula> formulas = new HashSet<>(premises);
        formulas.addAll(hypotheses);

        for (IFOLFormula formula : formulas) {
            for (ASTVariable var : notFreeVars)
                if (formula.appearsFreeVariable(var)) notFree.add(formula);
        }

        return new StateNode(handler.getIndex(state), handler.toBitSet(new HashSet<>(hypotheses)),
                height, handler.toBitSet(new HashSet<>(notFree)), handler);
    }

}
