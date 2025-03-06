package com.logic.asts.binary;

import com.logic.asts.AASTExp;
import com.logic.asts.IASTExp;

public abstract class AASTBinaryExp extends AASTExp implements IASTExp {

    public IASTExp left;
    public IASTExp right;

    public AASTBinaryExp(IASTExp left, IASTExp right) {
        this.left = left;
        this.right = right;
    }

}

