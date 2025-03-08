package com.logic.exps.asts.others;


import com.logic.exps.asts.IVisitor;

public class ASTConstant extends AASTTerm {

    public ASTConstant(String value) {
        super(value);
    }

    @Override
    public <T, E> T accept(IVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

}