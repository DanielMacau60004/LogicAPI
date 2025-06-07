package com.logic.exps.asts;

import com.logic.feedback.FeedbackException;
import com.logic.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public abstract class AASTExp implements IASTExp {

    private int iD;
    private String name;

    public int getID() {
        if (name == null) {
            name = toString();
            iD = name.hashCode();
        }
        return iD;
    }

    protected String getToken(int kind) {
        return Parser.tokenImage[kind].replace("\"", "");
    }

    @Override
    public String toString() {
        getID();
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AASTExp s)
            return getID() == s.getID();
        return false;
    }

    @Override
    public int hashCode() {
        return getID();
    }

}
