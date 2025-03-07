package com.logic.asts;

import com.logic.api.IExp;
import com.logic.api.IFOLExp;

import java.util.HashSet;
import java.util.Set;

public class FOLExp implements IFOLExp {

    //TODO implement the rest
    private final IExp exp;
    private final Set<IExp> freeVariables;
    private final Set<IExp> boundedVariables;

    private final Set<IExp> functions;
    private final Set<IExp> predicates;

    public FOLExp(IExp exp) {
        this.exp = exp;

        this.freeVariables = new HashSet<>();
        this.boundedVariables = new HashSet<>();
        this.functions = new HashSet<>();
        this.predicates = new HashSet<>();
    }

    //A sentence of first-order logic is a formula having no free variables
    @Override
    public boolean isASentence() {
        return freeVariables.isEmpty();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
