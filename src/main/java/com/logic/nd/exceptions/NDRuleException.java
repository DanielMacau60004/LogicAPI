package com.logic.nd.exceptions;

import com.logic.nd.asts.IASTND;
import com.logic.nd.interpreters.NDInterpretString;


public class NDRuleException extends RuntimeException {

    protected final IASTND rule;

    public NDRuleException(IASTND rule) {
        this.rule = rule;
    }

    public IASTND getRule() {
        return rule;
    }

    @Override
    public String getMessage() {
        return rule.getRule().name() + " is incorrect: \n\t" +
                NDInterpretString.interpret(rule);
    }
}
