package com.logic.asts.others;

import com.logic.interpreters.IInterpreter;

public class ASTVariable extends AASTTerm {

    public ASTVariable(String name) {
        super(name);
    }

    public void setValue(String value) {
        this.name = value;
    }

    @Override
    public <T, E> T interpret(IInterpreter<T, E> v, E env) {
        return v.visit(this, env);
    }

}
