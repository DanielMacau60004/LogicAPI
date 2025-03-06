package com.logic.asts.others;


import com.logic.asts.AASTExp;
import com.logic.interpreters.IInterpreter;

public class ASTTop extends AASTExp {

    @Override
    public <T, E> T interpret(IInterpreter<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {return ExpressionsParser.TOP;}
}
