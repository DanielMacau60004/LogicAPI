package com.logic.nd.algorithm;

import com.logic.api.IFOLFormula;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.exps.asts.others.ASTVariable;

import java.util.HashSet;
import java.util.Set;

public class AlgoProofFOLProblemBuilder extends AlgoProofFOLStateBuilder {

    public final Set<IFOLFormula> premises;

    public AlgoProofFOLProblemBuilder(IFOLFormula state) {
        super(state);

        state.iterateTerms().forEachRemaining(terms::add);
        premises = new HashSet<>();
    }

    public AlgoProofFOLProblemBuilder addPremise(IFOLFormula premise) {
        premise.iterateTerms().forEachRemaining(terms::add);
        this.premises.add(premise);
        return this;
    }

    public AlgoProofFOLProblemBuilder addPremises(Set<IFOLFormula> premises) {
        premises.forEach(premise -> premise.iterateTerms().forEachRemaining(terms::add));
        this.premises.addAll(premises);
        return this;
    }

    public AlgoProofFOLProblemBuilder addHypothesis(IFOLFormula hypothesis) {
        hypothesis.iterateTerms().forEachRemaining(terms::add);
        this.hypotheses.add(hypothesis);
        return this;
    }

    public AlgoProofFOLProblemBuilder addHypotheses(Set<IFOLFormula> hypotheses) {
        hypotheses.forEach(hypothesis -> hypothesis.iterateTerms().forEachRemaining(terms::add));
        this.hypotheses.addAll(hypotheses);
        return this;
    }

    public AlgoProofFOLProblemBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public AlgoProofFOLProblemBuilder setNoFreeVariables(Set<ASTVariable> notFreeVars) {
        this.notFreeVars.addAll(notFreeVars);
        return this;
    }

    public AlgoProofFOLProblemBuilder addNoFreeVariable(ASTVariable notFreeVar) {
        this.notFreeVars.add(notFreeVar);
        return this;
    }

    public AlgoProofFOLProblemBuilder addTerm(AASTTerm term) {
        this.terms.add(term);
        return this;
    }

    public AlgoProofFOLProblemBuilder addTerms(Set<AASTTerm> terms) {
        this.terms.addAll(terms);
        return this;
    }

}
