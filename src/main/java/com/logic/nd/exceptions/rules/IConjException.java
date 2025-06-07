package com.logic.nd.exceptions.rules;

import com.logic.exps.asts.binary.ASTAnd;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.binary.ASTIConj;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.exceptions.NDRuleException;

import java.util.List;

public class IConjException extends NDRuleException {

    private final ASTIConj rule;
    private ASTAnd and;

    public IConjException(ASTIConj rule) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.rule = rule;
    }

    public IConjException(ASTIConj rule, ASTAnd and) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.and = and;
        this.rule = rule;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid conclusion!";
            case HIGH -> and != null ? "The conjunction of the hypotheses is different from the conclusion!"
                    : "The conclusion should be a conjunction!";
            case SOLUTION -> and != null ? "The conjunction of the hypotheses is different from the conclusion!" +
                    "Consider changing these changes: "
                    : "The conclusion should be a conjunction!";

        };
    }

    @Override
    public List<IASTND> getPreviews(FeedbackLevel level) {
        if (and != null && level.equals(FeedbackLevel.SOLUTION)) {
            return List.of(
                    new ASTIConj(new ASTHypothesis(and.getLeft(), null), new ASTHypothesis(and.getRight(), null),
                            rule.getConclusion()),
                    new ASTIConj(new ASTHypothesis(rule.getHyp1().getConclusion(), null),
                            new ASTHypothesis(rule.getHyp2().getConclusion(), null),
                            new ASTAnd(rule.getHyp1().getConclusion(), rule.getHyp2().getConclusion())));
        }
        return null;
    }
}
