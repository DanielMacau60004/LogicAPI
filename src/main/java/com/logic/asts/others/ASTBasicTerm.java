package com.logic.asts.others;

import com.logic.asts.AASTExp;
import com.logic.interpreters.IInterpreter;

public class ASTBasicTerm extends AASTExp {

    private final String name;
    private boolean variable;

    public ASTBasicTerm(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }

    public void setVariable(boolean variable) {
        this.variable = variable;
    }

    public boolean isVariable() {
        return variable;
    }

    @Override
    public <T, E> T interpret(IInterpreter<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return name;
    }

}
