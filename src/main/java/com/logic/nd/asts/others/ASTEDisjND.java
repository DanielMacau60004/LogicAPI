package com.logic.nd.asts.others;

import com.logic.nd.asts.AASTND;
import com.logic.nd.asts.IASTND;

public abstract class ASTEDisjND extends AASTND implements IASTND {

    protected final IASTND disjunction;
    protected final IASTND hyp1;
    protected final IASTND hyp2;
    protected final IASTND conclusion;

    public ASTEDisjND(IASTND disjunction, IASTND hyp1, IASTND hyp2, IASTND conclusion) {
        this.disjunction = disjunction;
        this.hyp1 = hyp1;
        this.hyp2 = hyp2;
        this.conclusion = conclusion;
    }

    public IASTND getDisjunction() {return disjunction;}
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
