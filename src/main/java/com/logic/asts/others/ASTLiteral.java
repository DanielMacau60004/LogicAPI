package com.logic.asts.others;


import com.logic.asts.AASTExp;
import com.logic.asts.IASTExp;
import com.logic.interpreters.IInterpreter;

public class ASTLiteral extends AASTExp implements IASTExp {

    public String id;

    public ASTLiteral(String id) {
        this.id = id;
    }

    @Override
    public <T, E> T interpret(IInterpreter<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return id;
    }

}
