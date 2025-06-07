package com.logic.nd.exceptions.rules;

import com.logic.exps.ExpUtils;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.exceptions.NDRuleException;

public class AbsurdityException extends NDRuleException {

    public AbsurdityException() {
        super(FeedbackType.SEMANTIC_ERROR);
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid conclusion!";
            case HIGH, SOLUTION -> "The hypothesis should be " + ExpUtils.BOT + "!";
        };
    }
}
