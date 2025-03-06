package com.logic.asts.others;

import com.logic.asts.AASTExp;
import com.logic.interpreters.IInterpreter;

public class ASTSignature extends AASTExp {

    public final String name;
    public final int size;

    public ASTSignature(String name, String size) {
        this.name = name;
        this.size = Integer.parseInt(size);
    }

    @Override
    public <T, E> T interpret(IInterpreter<T, E> v, E env) {
        return v.visit(this, env);
    }
    @Override
    public String toString() {
        return name + "/" + size;
    }

}

