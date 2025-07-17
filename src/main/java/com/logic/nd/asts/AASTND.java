package com.logic.nd.asts;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.ERule;
import com.logic.nd.interpreters.NDInterpretString;
import com.logic.others.Env;
import com.logic.others.Utils;
import com.logic.parser.Parser;

public abstract class AASTND implements IASTND {

    protected ERule rule;
    protected IASTND parent;
    protected Env<String, IASTExp> env;

    protected String getToken(int kind) {
        return Parser.tokenImage[kind].replace("\"", "");
    }

    public AASTND(ERule rule) {
        this.rule = rule;
    }

    @Override
    public void setParent(IASTND parent) {
        this.parent = parent;
    }

    @Override
    public void setEnv(Env<String, IASTExp> env) {
        this.env = env;
    }

    @Override
    public IASTND getParent() {
        return parent;
    }

    @Override
    public Env<String, IASTExp> getEnv() {
        return env;
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
