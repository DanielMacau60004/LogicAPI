package com.logic.asts;

import com.logic.api.IFOLExp;

import java.util.Set;

public class FOLExp implements IFOLExp {

    private final IASTExp exp;

    private final Set<String> boundedVariables;
    private final Set<String> unboundedVariables;

    public FOLExp(IASTExp exp,
                  Set<String> boundedVariables, Set<String> unboundedVariables) {
        this.exp = exp;
        this.boundedVariables = boundedVariables;
        this.unboundedVariables = unboundedVariables;
    }

    @Override
    public Set<String> getBoundedVariables() {
        return boundedVariables;
    }

    @Override
    public boolean isABoundedVariable(String variable) {
        return boundedVariables.contains(variable);
    }

    @Override
    public Set<String> getUnboundedVariables() {
        return unboundedVariables;
    }

    @Override
    public boolean isASentence() {
        return unboundedVariables.isEmpty();
    }

    @Override
    public String toString() {
        return exp.toString();
    }
}
