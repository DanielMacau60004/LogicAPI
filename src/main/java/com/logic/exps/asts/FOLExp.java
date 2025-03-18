package com.logic.exps.asts;

import com.logic.api.IFOLFormula;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.exps.asts.others.ASTFun;
import com.logic.exps.asts.others.ASTPred;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.others.Utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FOLExp implements IFOLFormula {

    private final IASTExp exp;

    private final Set<ASTFun> functions;
    private final Set<ASTPred> predicates;

    private final Set<ASTVariable> boundedVariables;
    private final Set<ASTVariable> unboundedVariables;

    public FOLExp(IASTExp exp,
                  Set<ASTFun> functions, Set<ASTPred> predicates,
                  Set<ASTVariable> boundedVariables, Set<ASTVariable> unboundedVariables) {
        this.exp = exp;
        this.functions = functions;
        this.predicates = predicates;
        this.boundedVariables = boundedVariables;
        this.unboundedVariables = unboundedVariables;

    }

    @Override
    public IASTExp getFormula() {
        return exp;
    }

    @Override
    public Iterator<ASTFun> iterateFunctions() {
        return functions.iterator();
    }

    @Override
    public Iterator<ASTPred> iteratePredicates() {
        return predicates.iterator();
    }

    @Override
    public Iterator<ASTVariable> iterateBoundedVariables() {
        return boundedVariables.iterator();
    }

    @Override
    public Iterator<AASTTerm> iterateTerms() {
        Set<AASTTerm> terms = new HashSet<>();
        terms.addAll(functions);
        terms.addAll(boundedVariables);
        terms.addAll(unboundedVariables);

        return terms.iterator();
    }

    @Override
    public Iterator<ASTVariable> iterateVariables() {
        Set<ASTVariable> variables = new HashSet<>();
        variables.addAll(boundedVariables);
        variables.addAll(unboundedVariables);

        return variables.iterator();
    }

    @Override
    public boolean isABoundedVariable(ASTVariable variable) {
        return boundedVariables.contains(variable);
    }

    @Override
    public boolean isAnUnboundedVariable(ASTVariable variable) {
        return unboundedVariables.contains(variable);
    }

    @Override
    public Iterator<ASTVariable> iterateUnboundedVariables() {
        return unboundedVariables.iterator();
    }

    @Override
    public boolean isASentence() {
        return unboundedVariables.isEmpty();
    }

    @Override
    public String toString() {
        return Utils.getToken(exp.toString());
    }
}
