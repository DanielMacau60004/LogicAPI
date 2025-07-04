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

    private final Map<ASTHypothesis, Env<String, IASTExp>> rules;

    public ConclusionException(IASTND rule, Set<IFormula> premises, IFormula conclusion, Set<IFormula> provedPremises,
                               IFormula provedConclusion, Map<ASTHypothesis, Env<String, IASTExp>> rules) {
        super(rule);
        this.premises = premises;
        this.conclusion = conclusion;
        this.provedPremises = provedPremises;
        this.provedConclusion = provedConclusion;
        this.rules = rules;
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

    public Map<ASTHypothesis, Env<String, IASTExp>> getRules() {
        return rules;
    }

    @Override
    public String getMessage() {
        return "This tree doesn't solve the problem!\n" +
                "You proved:\n" +
                (premises != null && !premises.isEmpty()
                        ? "{"+premises.stream().map(Object::toString).collect(Collectors.joining(", "))+"} "
                        : "") +
                "⊢ " + conclusion.toString();
    }
}
