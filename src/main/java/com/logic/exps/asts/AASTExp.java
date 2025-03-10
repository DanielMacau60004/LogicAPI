package com.logic.exps.asts;

import com.logic.parser.Parser;
import com.logic.others.Utils;

public abstract class AASTExp implements IASTExp {

    protected String getToken(int kind) {
        return Utils.getToken(Parser.tokenImage[kind].replace("\"",""));
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
