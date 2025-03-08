package com.logic.api;

import java.util.Set;

public interface IFOLExp {

    Set<String> getBoundedVariables();

    boolean isABoundedVariable(String variable);

    Set<String> getUnboundedVariables();

    //A sentence of first-order logic is a formula having no free variables
    boolean isASentence();
}
