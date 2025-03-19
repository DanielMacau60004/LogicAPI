package com.logic.api;

import com.logic.exps.asts.IASTExp;

import java.util.Iterator;

//TODO add documentation
public interface INDProof {

    IFormula getConclusion();

    Iterator<IASTExp> getPremises();

    int height();

    int size();
}
