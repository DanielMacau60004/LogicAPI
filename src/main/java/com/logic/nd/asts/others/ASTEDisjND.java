package com.logic.nd.asts.others;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.asts.AASTND;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;
import com.logic.parser.ParserConstants;

public class ASTEDisjND extends AASTND implements IASTND {

    private final IASTND hyp1;
    private final IASTND hyp2;
    private final IASTND hyp3;
    private final IASTExp conclusion;

    private final int m, n;

    public ASTEDisjND(IASTND hyp1, IASTND hyp2, IASTND hyp3, IASTExp conclusion, int m, int n) {
        this.hyp1 = hyp1;
        this.hyp2 = hyp2;
        this.hyp3 = hyp3;
        this.conclusion = conclusion;
        this.m = m;
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public IASTND getHyp3() {
        return hyp3;
    }

    public IASTND getHyp1() {
        return hyp1;
    }

    public IASTND getHyp2() {
        return hyp2;
    }

    public IASTExp getConclusion() {
        return conclusion;
    }

    @Override
    public <T, E> T accept(INDVisitor<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        return "[" + getToken(ParserConstants.EOR) + ", " + m + ", " + n + "] " +
                "[" + conclusion + ". " + hyp1 + " " + hyp2 + " " + hyp3 + "]";
    }
}
