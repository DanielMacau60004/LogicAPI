package com.logic.exps.asts.binary;


import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.IExpsVisitor;
import com.logic.exps.parser.ExpressionsParser;

public class ASTExistential extends AASTBinaryExp {

    public ASTExistential(IASTExp variable, IASTExp exp) {
        super(variable, exp);
    }

    @Override
    public <T, E> T accept(IExpsVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return getToken(ExpressionsParser.EXISTENTIAL) + left.toString() + " " + right.toString();
    }
}
