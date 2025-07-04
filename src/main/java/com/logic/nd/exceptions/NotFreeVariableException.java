package com.logic.nd.exceptions;

import com.logic.api.IFOLFormula;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.nd.interpreters.NDInterpretString;
import com.logic.nd.asts.IASTND;

public class NotFreeVariableException extends NDRuleException {

    private final AASTTerm term;
    private final IASTExp from;
    private final IFOLFormula to;

    public NotFreeVariableException(IASTND rule, AASTTerm term, IASTExp from, IFOLFormula to) {
        super(rule);

        this.term = term;
        this.from = from;
        this.to = to;
    }

    public AASTTerm getTerm() {
        return term;
    }

    public IASTExp getFrom() {
        return from;
    }

    public IFOLFormula getTo() {
        return to;
    }

    @Override
    public String getMessage() {
        return "Term " + term + " is not free to " + from + " in " + to + ": \n\t" +
                NDInterpretString.interpret(rule);
    }
}

