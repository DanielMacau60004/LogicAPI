package com.logic.nd.exceptions;

import com.logic.exps.asts.IASTExp;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;

import java.util.Set;
import java.util.stream.Collectors;

public class PremiseException extends NDRuleException {

    private final IASTExp premise;
    private final Set<IASTExp> premises;

    public PremiseException(IASTExp premise, Set<IASTExp> premises) {
        super(FeedbackType.SEMANTIC_ERROR);

        this.premise = premise;
        this.premises = premises;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW, MEDIUM -> "Expression " + premise + " is not a premise!";
            case HIGH, SOLUTION -> "Expression " + premise + " is not a premise! " +
                    (premises.isEmpty() ? "" : "These are the available premises: " + premises.stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(", ")) + "!");
        };
    }
}
