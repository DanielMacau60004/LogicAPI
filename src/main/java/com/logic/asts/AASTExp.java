package com.logic.asts;

import com.logic.interpreters.IInterpreter;

public abstract class AASTExp implements IASTExp {

    @Override
    public <T, E> T interpret(IInterpreter<T, E> v, E env) {
        throw new RuntimeException("This operation is not valid in first-order logic");}

    protected String getToken(int kind) {
        return ExpressionsParser.tokenImage[kind].replace("\"","");
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AASTExp s)
            return this.toString().equals(s.toString());
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
