package com.logic.asts.others;

import com.logic.asts.AASTExp;
import com.logic.interpreters.IInterpreter;
import com.logic.parser.ExpressionsParser;

import java.util.HashMap;
import java.util.Map;

public class ASTSigFun extends AASTExp {

    private Map<String, Integer> functions;

    public ASTSigFun() {
        functions = new HashMap<>();
    }

    public void addFunction(String func, String arity) {
        functions.put(func, Integer.parseInt(arity));
    }

    public Map<String, Integer> getFunctions() {
        return functions;
    }

    @Override
    public <T, E> T interpret(IInterpreter<T, E> v, E env) {
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

