package com.logic.asts.others;


import com.logic.asts.AASTExp;
import com.logic.asts.IASTExp;
import com.logic.interpreters.IInterpreter;

public class ASTLiteral extends AASTExp implements IASTExp {

    private final String id;

    public ASTLiteral(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public <T, E> T accept(IInterpreter<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return id;
    }

}
