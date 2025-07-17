package com.logic.nd.exceptions;

import com.logic.api.IFormula;
import com.logic.exps.asts.IASTExp;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.others.Env;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ConclusionException extends NDRuleException {

    private final Set<IFormula> premises;
    private final IFormula conclusion;

    private final Set<IFormula> provedPremises;
    private final IFormula provedConclusion;

    private final Set<ASTHypothesis> unclosed;

    public ConclusionException(IASTND rule, Set<IFormula> premises, IFormula conclusion, Set<IFormula> provedPremises,
                               IFormula provedConclusion, Set<ASTHypothesis> unclosed) {
        super(rule);
        this.premises = premises;
        this.conclusion = conclusion;
        this.provedPremises = provedPremises;
        this.provedConclusion = provedConclusion;
        this.unclosed = unclosed;
    }

    public Set<IFormula> getPremises() {
        return premises;
    }

    public IFormula getConclusion() {
        return conclusion;
    }

    public IFormula getProvedConclusion() {
        return provedConclusion;
    }

    public Set<IFormula> getProvedPremises() {
        return provedPremises;
    }

    public Set<ASTHypothesis> getUnclosed() {
        return unclosed;
    }

    @Override
    public String getMessage() {
        return "This tree doesn't solve the problem!\n" +
                "You proved:\n" +
                (provedPremises != null && !provedPremises.isEmpty()
                        ? "{"+provedPremises.stream().map(Object::toString).collect(Collectors.joining(", "))+"} "
                        : "") +
                "⊢ " + provedConclusion.toString();
    }
}
