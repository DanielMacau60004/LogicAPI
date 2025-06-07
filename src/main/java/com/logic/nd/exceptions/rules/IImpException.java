package com.logic.nd.exceptions.rules;

import com.logic.exps.asts.binary.ASTConditional;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.ASTIImp;
import com.logic.nd.exceptions.NDRuleException;

import java.util.List;

public class IImpException extends NDRuleException {

    private final ASTIImp rule;
    private ASTConditional imp;

    public IImpException(ASTIImp rule) {
        super(FeedbackType.SEMANTIC_ERROR);

        this.rule = rule;

    }

    public IImpException(ASTIImp rule, ASTConditional imp) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.imp = imp;
        this.rule = rule;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid conclusion!";
            case HIGH -> imp != null ? "The right-hand side of the implication is not the same as the hypothesis!"
                    : "The conclusion should be an implication!";
            case SOLUTION -> imp != null ? "The right-hand side of the implication is not the same as the hypothesis!" +
                    "Consider one of these changes: \n"
                    : "The conclusion should be an implication!";

        };
    }

    @Override
    public List<IASTND> getPreviews(FeedbackLevel level) {
        if (imp != null && level.equals(FeedbackLevel.SOLUTION)) {
            return List.of(
                    new ASTIImp(new ASTHypothesis(rule.getHyp().getConclusion(),null),
                            new ASTConditional(imp.getLeft(), rule.getHyp().getConclusion()), rule.getM()),
                    new ASTIImp(new ASTHypothesis(imp.getRight(),null),
                            imp, rule.getM()));
        }
        return null;
    }
}
