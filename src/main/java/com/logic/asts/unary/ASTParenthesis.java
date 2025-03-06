package com.logic.asts.unary;

import com.logic.asts.IASTExp;
import com.logic.interpreters.IInterpreter;

public class ASTParenthesis extends AASTUnaryExp {

    public IASTExp e;

    public ASTParenthesis(IASTExp e) {
        super(e);
        this.e = e;
    }

    @Override
    public <T, E> T interpret(IInterpreter<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return getToken(ExpressionsParser.LPAR) + e.toString() + getToken(ExpressionsParser.RPAR);
    }
}
