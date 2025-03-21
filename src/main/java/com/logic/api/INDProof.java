package com.logic.api;

import java.util.Iterator;

//TODO add documentation
public interface INDProof {

    IFormula getConclusion();

    Iterator<IFormula> getPremises();

    int height();

    int size();
}
