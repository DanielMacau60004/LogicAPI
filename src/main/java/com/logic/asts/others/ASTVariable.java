package com.logic.asts.others;


import com.logic.asts.IVisitor;

public class ASTVariable extends AASTTerm {

    public ASTVariable(String value) {
        super(value);
    }

    @Override
    public <T, E> T accept(IVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

}