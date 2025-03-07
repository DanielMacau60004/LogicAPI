package com.logic.asts.unary;

import com.logic.asts.IASTExp;
import com.logic.interpreters.IInterpreter;
import com.logic.parser.ExpressionsParser;

public class ASTNot extends AASTUnaryExp {

    public ASTNot(IASTExp e) {
        super(e);
    }

    @Override
    public <T, E> T accept(IInterpreter<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return getToken(ExpressionsParser.NOT) + exp.toString();
    }
}
