package com.logic.api;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.others.ASTArbitrary;

import java.util.Iterator;

//TODO add documentation
public interface IFormula {

    IASTExp getFormula();

    Iterator<ASTArbitrary> iterateGenerics();

    boolean hasGenerics();

}
