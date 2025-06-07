package com.logic.nd.exceptions.rules;

import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.unary.ASTEUni;
import com.logic.nd.exceptions.NDRuleException;

public class EUniException extends NDRuleException {

    private ASTEUni rule;

    public EUniException(ASTEUni rule) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.rule = rule;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid hypothesis!";
            case HIGH, SOLUTION -> "The hypothesis should be an universal";
        };
    }
}
