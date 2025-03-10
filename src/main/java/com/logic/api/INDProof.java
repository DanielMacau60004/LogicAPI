package com.logic.api;

import com.logic.exps.asts.IASTExp;

import java.util.Iterator;

//TODO add documentation
public interface INDProof {

    Iterator<IASTExp> getPremises();

    int height();

    int size();
}
