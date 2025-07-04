package com.logic.nd.exceptions;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.unary.ASTEUni;
import com.logic.nd.interpreters.NDInterpretString;

import java.util.Set;

public class InvalidMappingException extends NDRuleException {

    private final ASTVariable variable;
    private final IASTExp from;
    private final IASTExp to;
    private final Set<IASTExp> outcomes;

    public InvalidMappingException(IASTND rule, ASTVariable variable, IASTExp from, IASTExp to, Set<IASTExp> outcomes) {
        super(rule);

        this.variable = variable;
        this.from = from;
        this.to = to;
        this.outcomes = outcomes;

    }

    public ASTVariable getVariable() {
        return variable;
    }

    public IASTExp getFrom() {
        return from;
    }

    public IASTExp getTo() {
        return to;
    }

    public Set<IASTExp> getOutcomes() {
        return outcomes;
    }

    @Override
    public String getMessage() {
        return "Invalid mapping! No mapping of " + variable + " in " + from + " that can produce " + to + ": \n\t" +
                NDInterpretString.interpret(rule);
    }

}
