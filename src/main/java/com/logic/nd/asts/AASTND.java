package com.logic.nd.asts;

import com.logic.parser.Parser;
import com.logic.others.Utils;

public abstract class AASTND implements IASTND {

    protected String getToken(int kind) {
        return Utils.getToken(Parser.tokenImage[kind].replace("\"",""));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AASTND s)
            return this.toString().equals(s.toString());
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
