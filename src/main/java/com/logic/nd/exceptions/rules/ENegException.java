package com.logic.nd.exceptions.rules;

import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.binary.ASTENeg;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.exceptions.NDRuleException;

import java.util.List;

public class ENegException extends NDRuleException {

    private final ASTENeg rule;
    private IASTExp leftNot;
    private IASTExp rightNot;

    public ENegException(ASTENeg rule) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.rule = rule;
    }

    public ENegException(ASTENeg rule, IASTExp leftNot, IASTExp rightNot) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.rule = rule;
        this.leftNot = leftNot;
        this.rightNot = rightNot;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid conclusion!";
            case HIGH -> leftNot != null ? "The conclusion should be " + ExpUtils.BOT + "!"
                    : "The hypotheses should be negations of each other!";
            case SOLUTION -> leftNot != null ? "The conclusion should be " + ExpUtils.BOT + "!" +
                    "Consider changing these changes: "
                    : "The hypotheses should be negations of each other!";
        };
    }

    @Override
    public List<IASTND> getPreviews(FeedbackLevel level) {
        if (leftNot != null && level.equals(FeedbackLevel.SOLUTION)) {
            return List.of(
                    new ASTENeg(
                            new ASTHypothesis(rule.getHyp1().getConclusion(), null),
                            new ASTHypothesis(leftNot, null),
                            rule.getConclusion()),
                    new ASTENeg(
                            new ASTHypothesis(rightNot, null),
                            new ASTHypothesis(rule.getHyp2().getConclusion(), null),
                            rule.getConclusion()));
        }
        return null;
    }

}
