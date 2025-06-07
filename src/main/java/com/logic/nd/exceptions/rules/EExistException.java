package com.logic.nd.exceptions.rules;

import com.logic.exps.asts.binary.ASTExistential;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.binary.ASTEExist;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.exceptions.NDRuleException;

import java.util.List;

public class EExistException extends NDRuleException {

    private final ASTEExist rule;
    private ASTExistential exist;

    public EExistException(ASTEExist rule) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.rule = rule;
    }

    public EExistException(ASTEExist rule, ASTExistential exist) {
        super(FeedbackType.SEMANTIC_ERROR);
        this.rule = rule;
        this.exist = exist;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> "Invalid hypothesis!";
            case HIGH, SOLUTION -> exist != null ? "The second hypothesis and the conclusion should be the same"
                    : "The first hypothesis should be an existential!";
        };
    }

    @Override
    public List<IASTND> getPreviews(FeedbackLevel level) {
        if (exist != null && level.equals(FeedbackLevel.SOLUTION)) {
            return List.of(
                    new ASTEExist(new ASTHypothesis(rule.getHyp1().getConclusion(), null),
                            new ASTHypothesis(rule.getConclusion(), null),
                            rule.getConclusion(), rule.getM()));
        }
        return null;
    }
}
