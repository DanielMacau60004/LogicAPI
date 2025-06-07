package com.logic.nd.exceptions.rules;

import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.unary.ASTIUni;
import com.logic.nd.exceptions.NDRuleException;

public class IUniException extends NDRuleException {

    private ASTIUni rule;

    public IUniException(ASTIUni rule) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.rule = rule;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid conclusion!";
            case HIGH, SOLUTION -> "The conclusion should be an universal!";
        };
    }
}
