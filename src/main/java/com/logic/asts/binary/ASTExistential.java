package com.logic.asts.binary;


import com.logic.asts.IASTExp;
import com.logic.interpreters.IInterpreter;
import com.logic.parser.ExpressionsParser;

public class ASTExistential extends AASTBinaryExp {

    public ASTExistential(IASTExp variable, IASTExp exp) {
        super(variable, exp);
    }

    @Override
    public <T, E> T interpret(IInterpreter<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return getToken(ExpressionsParser.EXISTENTIAL) + left.toString() + " " + right.toString();
    }
}
