package com.logic.nd.exceptions.rules;

import com.logic.exps.asts.binary.ASTOr;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.others.ASTEDis;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.exceptions.NDRuleException;

import java.util.List;

public class EDisException extends NDRuleException {

    private final ASTEDis rule;
    private ASTOr or;

    public EDisException(ASTEDis rule) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.rule = rule;
    }

    public EDisException(ASTEDis rule, ASTOr or) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.or = or;
        this.rule = rule;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid hypothesis!";
            case HIGH -> or != null ? "The hypothesis is different from the conclusion!"
                    : "The first hypothesis should be a disjunction!";
            case SOLUTION -> or != null ? "The hypothesis is different from the conclusion! " +
                    "Consider changing to: "
                    : "The first hypothesis should be a disjunction!";
        };
    }

    @Override
    public List<IASTND> getPreviews(FeedbackLevel level) {
        if (or != null && level.equals(FeedbackLevel.SOLUTION)) {
            return List.of(
                    new ASTEDis(new ASTHypothesis(rule.getHyp1().getConclusion(), null),
                            new ASTHypothesis(rule.getConclusion(), null),
                            new ASTHypothesis(rule.getConclusion(), null),
                            rule.getConclusion(), rule.getM(), rule.getN()));
        }
        return null;
    }
}
