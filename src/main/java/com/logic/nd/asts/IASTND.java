package com.logic.nd.asts;

public interface IASTND {

    <T,E> T accept(INDVisitor<T,E> v, E env);
}
