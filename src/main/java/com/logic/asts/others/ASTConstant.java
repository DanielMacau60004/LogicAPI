package com.logic.asts.others;


import com.logic.interpreters.IInterpreter;

public class ASTConstant extends AASTTerm {

    public ASTConstant(String value) {
        super(value);
    }

    @Override
    public <T, E> T interpret(IInterpreter<T, E> v, E env) {
        return v.visit(this, env);
    }

}
