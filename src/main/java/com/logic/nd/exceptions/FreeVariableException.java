package com.logic.nd.exceptions;

import com.logic.exps.asts.others.ASTVariable;
import com.logic.nd.asts.IASTND;
import com.logic.nd.interpreters.NDInterpretString;

import java.util.List;

public class FreeVariableException extends NDRuleException {

    private final List<IASTND> freeHypotheses;
    private final ASTVariable variable;
    private final ASTVariable from;

    public FreeVariableException(IASTND rule, List<IASTND> freeHypotheses, ASTVariable variable, ASTVariable from) {
        super(rule);

        this.freeHypotheses = freeHypotheses;
        this.variable = variable;
        this.from = from;
    }

    public List<IASTND> getFreeHypotheses() {
        return freeHypotheses;
    }

    public ASTVariable getVariable() {
        return variable;
    }

    public ASTVariable getFrom() {
        return from;
    }

    @Override
    public String getMessage() {
        return "Variable " + variable + " appears free: \n\t" +
                NDInterpretString.interpret(rule);
    }
}
