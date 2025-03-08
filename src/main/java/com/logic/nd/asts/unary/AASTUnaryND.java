package com.logic.nd.asts.unary;

import com.logic.nd.asts.AASTND;
import com.logic.nd.asts.IASTND;

public abstract class AASTUnaryND extends AASTND implements IASTND {

    protected final IASTND hyp;
    protected final IASTND conclusion;

    public AASTUnaryND(IASTND hyp, IASTND conclusion) {
        this.hyp = hyp;
        this.conclusion = conclusion;
    }

    public IASTND getHyp() {
        return hyp;
    }

    public IASTND getConclusion() {
        return conclusion;
    }

}
