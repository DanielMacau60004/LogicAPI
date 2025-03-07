package com.logic.asts.others;

import com.logic.asts.AASTExp;
import com.logic.asts.IVisitor;

import java.util.HashMap;
import java.util.Map;

public class ASTSigPred extends AASTExp {

    private final Map<String, Integer> predicates;

    public ASTSigPred() {
        predicates = new HashMap<>();
    }

    public void addPredicate(String func, int arity) {
        predicates.put(func, arity);
    }

    public Map<String, Integer> getPredicates() {
        return predicates;
    }

    @Override
    public <T, E> T accept(IVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        String predStr = predicates.entrySet()
                .stream()
                .map(e -> e.getKey() + "/" + e.getValue())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        return "SF = {" + predStr + "}";
    }

}

