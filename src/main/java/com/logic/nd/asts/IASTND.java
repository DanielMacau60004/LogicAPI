package com.logic.nd.asts;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.ERule;
import com.logic.nd.exceptions.NDRuleException;
import com.logic.others.Env;

import java.util.List;

public interface IASTND {

    IASTExp getConclusion();

    <T, E> T accept(INDVisitor<T, E> v, E env);

    void setParent(IASTND parent);

    void setEnv(Env<String, IASTExp> env);

    IASTND getParent();

    Env<String, IASTExp> getEnv();

    ERule getRule();

}
