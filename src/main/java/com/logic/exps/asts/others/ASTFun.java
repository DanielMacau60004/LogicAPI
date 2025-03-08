package com.logic.exps.asts.others;

import com.logic.exps.asts.AASTExp;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.IExpsVisitor;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ASTFun extends AASTExp {

    private final String fun;
    private final List<IASTExp> terms;

    public ASTFun(String fun) {
       this(fun, new LinkedList<>());
    }

    public ASTFun(String fun, List<IASTExp> terms) {
        this.fun = fun;
        this.terms = terms;
    }

    public String getName() {
        return fun;
    }

    public List<IASTExp> getTerms() {
        return terms;
    }

    public void addTerm(IASTExp term) {
        terms.add(term);
    }

    @Override
    public <T, E> T accept(IExpsVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return fun + "(" + terms.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
    }
}

