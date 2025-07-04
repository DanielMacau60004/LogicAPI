package com.logic.nd.exceptions;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.asts.IASTND;
import com.logic.nd.interpreters.NDInterpretString;
import com.logic.others.Env;

public class MarkAssignException extends NDRuleException {

    private final String mark;
    private final IASTExp assigned;
    private final IASTExp expected;
    private final Env<String, IASTExp> env;

    public MarkAssignException(IASTND rule, String mark, IASTExp assigned, IASTExp expected, Env<String, IASTExp> env) {
        super(rule);

        this.mark = mark;
        this.assigned = assigned;
        this.expected = expected;
        this.env = env;
    }

    public String getMark() {
        return mark;
    }

    public IASTExp getAssigned() {
        return assigned;
    }

    public IASTExp getExpected() {
        return expected;
    }

    public Env<String, IASTExp> getEnv() {
        return env;
    }


    @Override
    public String toString() {
        return "Mark " + mark + " already assigned: \n\t" +
                NDInterpretString.interpret(rule);
    }
}

