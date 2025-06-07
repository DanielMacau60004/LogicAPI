package com.logic.nd.algorithm;

import com.logic.api.IFOLFormula;
import com.logic.api.IPLFormula;

import java.util.HashSet;
import java.util.Set;

public class AlgoProofPLProblemBuilder extends AlgoProofPLStateBuilder {

    public final Set<IPLFormula> premises;

    public AlgoProofPLProblemBuilder(IPLFormula state) {
        super(state);

        this.premises = new HashSet<>();
    }

    public AlgoProofPLProblemBuilder addPremise(IPLFormula premise) {
        this.premises.add(premise);
        return this;
    }

    public AlgoProofPLProblemBuilder addPremises(Set<IPLFormula> premises) {
        this.premises.addAll(premises);
        return this;
    }

    public AlgoProofPLProblemBuilder addHypothesis(IPLFormula hypothesis) {
        this.hypotheses.add(hypothesis);
        return this;
    }

    public AlgoProofPLProblemBuilder addHypotheses(Set<IPLFormula> hypotheses) {
        this.hypotheses.addAll(hypotheses);
        return this;
    }

    public AlgoProofPLProblemBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

}
