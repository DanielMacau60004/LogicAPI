package com.logic.nd.asts;

import com.logic.nd.ERule;
import com.logic.nd.exceptions.NDRuleException;
import com.logic.nd.interpreters.NDInterpretString;
import com.logic.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public abstract class AASTND implements IASTND {

    protected ERule rule;

    protected String getToken(int kind) {
        return Parser.tokenImage[kind].replace("\"", "");
    }

    public AASTND(ERule rule) {
        this.rule = rule;
    }

    @Override
    public ERule getRule() {
        return rule;
    }

    @Override
    public String toString() {
        return NDInterpretString.interpret(this);
    }
}
