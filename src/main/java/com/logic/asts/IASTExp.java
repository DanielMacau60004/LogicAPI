package com.logic.asts;

import com.logic.api.Exp;
import com.logic.interpreters.IInterpreter;

public interface IASTExp extends Exp {

    <T,E> T interpret(IInterpreter<T,E> v, E env);

}
