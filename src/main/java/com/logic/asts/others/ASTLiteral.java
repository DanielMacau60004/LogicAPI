package com.logic.asts.others;


import com.logic.asts.AASTExp;
import com.logic.asts.IASTExp;
import com.logic.asts.IVisitor;

public class ASTLiteral extends AASTExp implements IASTExp {

    private final String name;

    public ASTLiteral(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public <T, E> T accept(IVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return name;
    }

}
