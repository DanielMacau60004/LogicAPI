package com.logic.exps.exceptions;

import com.logic.exps.asts.IASTExp;


public class MissingParenthesisException extends ExpException {

    private final int column;


    public MissingParenthesisException(IASTExp before, int column) {
        super(before);

        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String getMessage() {
        return "You forgot to close the parentheses!";
    }

}
