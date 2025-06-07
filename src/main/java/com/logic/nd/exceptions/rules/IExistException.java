package com.logic.nd.exceptions.rules;

import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.unary.ASTIExist;
import com.logic.nd.exceptions.NDRuleException;

public class IExistException extends NDRuleException {

    private ASTIExist rule;

    public IExistException(ASTIExist rule) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.rule = rule;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid conclusion!";
            case HIGH, SOLUTION -> "The conclusion should be an existential!";
        };
    }
}
