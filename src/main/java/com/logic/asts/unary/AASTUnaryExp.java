package com.logic.asts.unary;

import com.logic.asts.AASTExp;
import com.logic.asts.IASTExp;

public abstract class AASTUnaryExp extends AASTExp implements IASTExp {

    protected final IASTExp exp;

    public AASTUnaryExp(IASTExp exp) {
        this.exp = exp;
    }

    public IASTExp getExp() {
        return exp;
    }

}
