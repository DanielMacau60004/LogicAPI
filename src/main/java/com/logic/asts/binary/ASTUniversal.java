package com.logic.asts.binary;

import com.logic.asts.IASTExp;
import com.logic.interpreters.IInterpreter;
import com.logic.parser.ExpressionsParser;

public class ASTUniversal extends AASTBinaryExp {

    public ASTUniversal(IASTExp variable, IASTExp exp) {
        super(variable, exp);
    }

    @Override
    public <T, E> T accept(IInterpreter<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return getToken(ExpressionsParser.UNIVERSAL) + left.toString() + " " + right.toString();
    }

}
