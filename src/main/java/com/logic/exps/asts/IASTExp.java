package com.logic.exps.asts;

public interface IASTExp{

    <T,E> T accept(IExpsVisitor<T,E> v, E env);

}
