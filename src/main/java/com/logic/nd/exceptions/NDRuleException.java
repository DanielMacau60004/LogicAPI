package com.logic.nd.exceptions;

import com.logic.feedback.FeedbackException;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.IASTND;

import java.util.List;

public abstract class NDRuleException extends FeedbackException {

    public NDRuleException(FeedbackType type) {
        super(type);
    }

    public List<IASTND> getPreviews(FeedbackLevel level) {
        return null;
    }

}
