package com.logic.nd.exceptions;

import com.logic.exps.asts.IASTExp;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CloseMarkException extends NDRuleException {

    private final String mark;
    private final IASTExp exp;

    public CloseMarkException(String mark, IASTExp exp) {
        super(FeedbackType.SEMANTIC_ERROR);

        this.mark = mark;
        this.exp = exp;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid closed mark!";
            case MEDIUM -> "Mark " +mark+" cannot be closed!";
            case HIGH, SOLUTION -> "Mark " + mark + " cannot be closed! Only marks associated with " + exp + " can be closed!";

        };
    }
}
