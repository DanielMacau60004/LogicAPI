package com.logic.asts.others;

import com.logic.asts.AASTExp;
import com.logic.interpreters.IInterpreter;

public abstract class AASTTerm extends AASTExp {

    private final String name;
    private boolean variable;

    public AASTTerm(String value) {
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
    public String toString() {
        return name;
    }

}
