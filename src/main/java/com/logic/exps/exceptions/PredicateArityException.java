package com.logic.exps.exceptions;

import com.logic.exps.asts.IASTExp;

public class PredicateArityException extends ExpException {

    private final String name;
    private final int expectedArity;
    private final int foundArity;

    public PredicateArityException(IASTExp exp, String name, int expectedArity, int foundArity) {
        super(exp);

        this.name = name;
        this.expectedArity = expectedArity;
        this.foundArity = foundArity;
    }

    public String getPredicateName() {
        return name;
    }

    public int getExpectedArity() {
        return expectedArity;
    }

    public int getFoundArity() {
        return foundArity;
    }

    @Override
    public String getMessage() {
        return "Different arities for predicate \"" + name + "!";
    }
}