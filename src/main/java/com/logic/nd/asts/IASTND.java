package com.logic.nd.asts;

import com.logic.exps.asts.IASTExp;

public interface IASTND {

    IASTExp getConclusion();

    <T,E> T accept(INDVisitor<T,E> v, E env);
}
