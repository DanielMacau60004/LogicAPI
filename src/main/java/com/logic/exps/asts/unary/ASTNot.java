package com.logic.exps.asts.unary;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.IExpsVisitor;
import com.logic.exps.parser.ExpressionsParser;

public class ASTNot extends AASTUnaryExp {

    public ASTNot(IASTExp e) {
        super(e);
    }

    @Override
    public <T, E> T accept(IExpsVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return getToken(ExpressionsParser.NOT) + exp.toString();
    }
}
