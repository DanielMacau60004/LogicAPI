package com.logic.nd.asts.binary;

import com.logic.nd.asts.AASTND;
import com.logic.nd.asts.IASTND;

public abstract class AASTBinaryND extends AASTND implements IASTND {

    protected final IASTND hyp1;
    protected final IASTND hyp2;
    protected final IASTND conclusion;

    public AASTBinaryND(IASTND hyp1, IASTND hyp2, IASTND conclusion) {
        this.hyp1 = hyp1;
        this.hyp2 = hyp2;
        this.conclusion = conclusion;
    }

    public IASTND getHyp1() {
        return hyp1;
    }
    public IASTND getHyp2() {
        return hyp2;
    }

    public IASTND getConclusion() {
        return conclusion;
    }

}
