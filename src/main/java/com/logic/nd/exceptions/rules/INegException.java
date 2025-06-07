package com.logic.nd.exceptions.rules;

import com.logic.exps.ExpUtils;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.unary.ASTINeg;
import com.logic.nd.exceptions.NDRuleException;

public class INegException extends NDRuleException {

    private ASTINeg rule;
    private ASTNot neg;

    public INegException(ASTINeg rule) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.rule = rule;
    }

    public INegException(ASTINeg rule, ASTNot neg) {
        super(FeedbackType.SEMANTIC_ERROR);

        this.rule = rule;
        this.neg = neg;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid conclusion!";
            case HIGH, SOLUTION -> neg != null ? "The hypothesis should be " + ExpUtils.BOT + "!"
                    : "The conclusion should be a negation!";
        };
    }
}
