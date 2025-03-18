package com.logic.nd;

import com.logic.api.INDProof;
import com.logic.exps.asts.IASTExp;
import com.logic.nd.asts.IASTND;
import com.logic.others.Utils;

import java.util.Iterator;
import java.util.Map;

public class NDProof implements INDProof {

    private final Map<IASTExp, Integer> premises;
    private final IASTND proof;
    private final int height;
    private final int size;

    public NDProof(Map<IASTExp, Integer> premises, IASTND proof, int height, int size) {
        this.premises = premises;
        this.proof = proof;
        this.height = height;
        this.size = size;
    }

    @Override
    public Iterator<IASTExp> getPremises() {
        return premises.keySet().iterator();
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString()  {
        return Utils.getToken(proof.toString());
    }
}
