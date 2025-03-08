package com.logic.asts.unary;

import com.logic.asts.IASTExp;
import com.logic.asts.IVisitor;
import com.logic.parser.ExpressionsParser;

public class ASTParenthesis extends AASTUnaryExp {

    private final IASTExp e;

    public ASTParenthesis(IASTExp e) {
        super(e);
        this.e = e;
    }

    @Override
    public <T, E> T accept(IVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return getToken(ExpressionsParser.LPAR) + e.toString() + getToken(ExpressionsParser.RPAR);
    }
}
