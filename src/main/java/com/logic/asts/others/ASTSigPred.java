package com.logic.asts.others;

import com.logic.asts.AASTExp;
import com.logic.interpreters.IInterpreter;

import java.util.HashMap;
import java.util.Map;

public class ASTSigPred extends AASTExp {

    private Map<String, Integer> predicates;

    public ASTSigPred() {
        predicates = new HashMap<>();
    }

    public void addPredicate(String func, String arity) {
        predicates.put(func, Integer.parseInt(arity));
    }

    public Map<String, Integer> getPredicates() {
        return predicates;
    }

    @Override
    public <T, E> T interpret(IInterpreter<T, E> v, E env) {
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

