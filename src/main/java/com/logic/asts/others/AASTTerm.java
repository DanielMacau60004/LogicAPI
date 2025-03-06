package com.logic.asts.others;

import com.logic.asts.AASTExp;

public abstract class AASTTerm extends AASTExp {

    protected String name;

    public AASTTerm(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
