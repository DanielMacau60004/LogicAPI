package com.logic.exps.asts.others;


import com.logic.exps.asts.AASTExp;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.IExpsVisitor;

public class ASTLiteral extends AASTExp implements IASTExp {

    private final String name;

    public ASTLiteral(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public <T, E> T accept(IExpsVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return name;
    }

}
