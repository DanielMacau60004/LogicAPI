package com.logic.nd.exceptions.rules;

import com.logic.exps.asts.binary.ASTAnd;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.ASTERConj;
import com.logic.nd.exceptions.NDRuleException;

import java.util.List;

public class ERConjException extends NDRuleException {

    private final ASTERConj rule;
    private ASTAnd and;

    public ERConjException(ASTERConj rule) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.rule = rule;
    }

    public ERConjException(ASTERConj rule, ASTAnd and) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.and = and;
        this.rule = rule;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid hypothesis!";
            case HIGH -> and != null ? "The left-hand side of the conjunction is not the same as the conclusion!"
                    : "The hypothesis should be a conjunction!";
            case SOLUTION -> and != null ? "The left-hand side of the conjunction is not the same as the conclusion! " +
                    "Consider changing these changes: "
                    : "The hypothesis should be a conjunction!";

        };
    }

    @Override
    public List<IASTND> getPreviews(FeedbackLevel level) {
        if (and != null && level.equals(FeedbackLevel.SOLUTION)) {
            return List.of(
                    new ASTERConj(new ASTHypothesis(new ASTAnd(rule.getConclusion(), and.getRight()),null),
                            rule.getConclusion()),
                    new ASTERConj(new ASTHypothesis(rule.getHyp().getConclusion(),null),
                            and.getLeft()));
        }
        return null;
    }
}
