package com.logic.nd.algorithm;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.nd.algorithm.state.StateNode;

import java.util.HashSet;
import java.util.Set;

public class AlgoProofStateBuilder {

    private final IFormula state;
    private final Set<IFormula> hypotheses;
    private final Set<ASTVariable> notFreeVars;
    private int height;

    public AlgoProofStateBuilder(IFormula state) {
        this.state = state;
        this.height = 0;
        this.hypotheses = new HashSet<>();
        this.notFreeVars = new HashSet<>();
    }

    public AlgoProofStateBuilder addHypothesis(IFormula hypothesis) {
        this.hypotheses.add(hypothesis);
        return this;
    }

    public AlgoProofStateBuilder addHypotheses(Set<IFormula> hypotheses) {
        this.hypotheses.addAll(hypotheses);
        return this;
    }

    public AlgoProofStateBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public AlgoProofStateBuilder setNoFreeVariables(Set<ASTVariable> notFreeVars) {
        this.notFreeVars.addAll(notFreeVars);
        return this;
    }

    public AlgoProofStateBuilder addNoFreeVariable(ASTVariable notFreeVar) {
        this.notFreeVars.add(notFreeVar);
        return this;
    }

    public StateNode build(Set<IFormula> premises) {
        Set<IFormula> notFree = new HashSet<>();
        Set<IFormula> formulas = new HashSet<>(premises);
        formulas.addAll(hypotheses);

        for (IFormula formula : formulas) {
            if (!(formula instanceof IFOLFormula fol)) continue;

            for (ASTVariable var : notFreeVars)
                if (fol.isAFreeVariable(var)) notFree.add(formula);
        }

        return new StateNode(state, premises, hypotheses, height, notFree);
    }

}
