package com.logic.nd.exceptions.rules;

import com.logic.exps.asts.binary.ASTOr;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.ASTIRDis;
import com.logic.nd.exceptions.NDRuleException;

import java.util.List;

public class IRDisException extends NDRuleException {

    private final ASTIRDis rule;
    private ASTOr or;

    public IRDisException(ASTIRDis rule) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.rule = rule;
    }

    public IRDisException(ASTIRDis rule, ASTOr or) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.or = or;
        this.rule = rule;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid conclusion!";
            case HIGH -> or != null ? "The left-hand side of the disjunction is not the same as the hypothesis!"
                    : "The conclusion should be a disjunction!";
            case SOLUTION -> or != null ? "The left-hand side of the disjunction is not the same as the hypothesis!" +
                    "Consider changing these changes: "
                    : "The conclusion should be a disjunction!";
        };
    }

    @Override
    public List<IASTND> getPreviews(FeedbackLevel level) {
        if (or != null && level.equals(FeedbackLevel.SOLUTION)) {
            return List.of(
                    new ASTIRDis(new ASTHypothesis(or.getRight(), null),
                            rule.getConclusion()),
                    new ASTIRDis(new ASTHypothesis(rule.getHyp().getConclusion(), null),
                            new ASTOr(or.getLeft(), rule.getHyp().getConclusion())));
        }
        return null;
    }
}
