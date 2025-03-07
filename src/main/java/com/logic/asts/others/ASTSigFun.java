package com.logic.asts.others;

import com.logic.asts.AASTExp;
import com.logic.asts.IVisitor;

import java.util.HashMap;
import java.util.Map;

public class ASTSigFun extends AASTExp {

    private final Map<String, Integer> functions;

    public ASTSigFun() {
        functions = new HashMap<>();
    }

    public void addFunction(String func, int arity) {
        functions.put(func, arity);
    }

    public Map<String, Integer> getFunctions() {
        return functions;
    }

    @Override
    public <T, E> T accept(IVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        String funStr = functions.entrySet()
                .stream()
                .map(e -> e.getKey() + "/" + e.getValue())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        return "SF = {" + funStr + "}";
    }

}

