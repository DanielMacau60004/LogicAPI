package com.logic.exps.asts;

public interface IASTExp{

    <T,E> T accept(IVisitor<T,E> v, E env);

}
