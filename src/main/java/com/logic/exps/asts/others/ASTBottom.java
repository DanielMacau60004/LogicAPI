package com.logic.exps.asts.others;


import com.logic.exps.asts.AASTExp;
import com.logic.exps.asts.IVisitor;
import com.logic.exps.parser.ExpressionsParser;

public class ASTBottom extends AASTExp {

    @Override
    public <T, E> T accept(IVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {return getToken(ExpressionsParser.BOTTOM);}
}
