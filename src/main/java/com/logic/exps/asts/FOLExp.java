package com.logic.exps.asts;

import com.logic.api.IFOLExp;

import java.util.Iterator;
import java.util.Set;

public class FOLExp implements IFOLExp {

    private final IASTExp exp;

    private final Set<String> functions;
    private final Set<String> predicates;

    private final Set<String> boundedVariables;
    private final Set<String> unboundedVariables;

    public FOLExp(IASTExp exp,
                  Set<String> functions, Set<String> predicates,
                  Set<String> boundedVariables, Set<String> unboundedVariables) {
        this.exp = exp;
        this.functions = functions;
        this.predicates = predicates;
        this.boundedVariables = boundedVariables;
        this.unboundedVariables = unboundedVariables;
    }

    @Override
    public Iterator<String> iterateFunctions() {
        return functions.iterator();
    }

    @Override
    public Iterator<String> iteratePredicates() {
        return predicates.iterator();
    }

    @Override
    public Iterator<String> iterateBoundedVariables() {
        return boundedVariables.iterator();
    }

    @Override
    public boolean isABoundedVariable(String variable) {
        return boundedVariables.contains(variable);
    }

    @Override
    public Iterator<String> iterateUnboundedVariables() {
        return unboundedVariables.iterator();
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
