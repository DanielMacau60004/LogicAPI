package com.logic.nd.exceptions.rules;

import com.logic.exps.asts.binary.ASTConditional;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.binary.ASTEImp;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.exceptions.NDRuleException;

import java.util.List;

public class EImpException extends NDRuleException {

    private final ASTEImp rule;
    private ASTConditional imp;

    public EImpException(ASTEImp rule) {
        super(FeedbackType.SEMANTIC_ERROR);

        this.rule = rule;

    }

    public EImpException(ASTEImp rule, ASTConditional imp) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.imp = imp;
        this.rule = rule;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid hypothesis!";
            case HIGH ->
                    imp != null ? "The first hypothesis and the conclusion should form the implication in the second hypothesis!"
                            : "The second hypothesis should be an implication!";
            case SOLUTION ->
                    imp != null ? "The first hypothesis and the conclusion should form the implication in the second hypothesis!" +
                            "Consider one of these changes: \n"
                            : "The second hypothesis should be an implication!";

        };
    }

    @Override
    public List<IASTND> getPreviews(FeedbackLevel level) {
        if (imp != null && level.equals(FeedbackLevel.SOLUTION)) {
            return List.of(
                    new ASTEImp(new ASTHypothesis(rule.getHyp1().getConclusion(), null),
                            new ASTHypothesis(new ASTConditional(rule.getHyp1().getConclusion(), rule.getConclusion()), null),
                            rule.getConclusion()),
                    new ASTEImp(new ASTHypothesis(imp.getLeft(), null),
                            new ASTHypothesis(rule.getHyp2().getConclusion(), null),
                            imp.getRight()));
        }
        return null;
    }
}
