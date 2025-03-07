package com.logic.asts;

import com.logic.api.IExp;

public interface IASTExp extends IExp {

    <T,E> T accept(IVisitor<T,E> v, E env);

}
