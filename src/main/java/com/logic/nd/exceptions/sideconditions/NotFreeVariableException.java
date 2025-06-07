package com.logic.nd.exceptions.sideconditions;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.exceptions.NDRuleException;

public class NotFreeVariableException extends NDRuleException {

    private final ASTVariable variable;
    private final IASTExp from;
    private final IASTExp to;


    public NotFreeVariableException(ASTVariable variable, IASTExp from, IASTExp to) {
        super(FeedbackType.SEMANTIC_ERROR);

        this.variable = variable;
        this.from = from;
        this.to = to;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Variable is not free!";
            case HIGH, SOLUTION -> "Variable " + variable + " in not free to " + from + " to " + to + "!";

        };
    }
}
