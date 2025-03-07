package com.logic.asts.others;


import com.logic.asts.AASTExp;
import com.logic.asts.IVisitor;
import com.logic.parser.ExpressionsParser;

public class ASTTop extends AASTExp {

    @Override
    public <T, E> T accept(IVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {return getToken(ExpressionsParser.TOP);}
}
