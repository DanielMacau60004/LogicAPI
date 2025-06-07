package com.logic.nd.exceptions;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.others.Levenshtein;

import java.util.Set;
import java.util.stream.Collectors;

public class InvalidMappingException extends NDRuleException {

    private final ASTVariable variable;
    private final IASTExp from;
    private final IASTExp to;
    private final Set<IASTExp> outcomes;

    public InvalidMappingException(ASTVariable variable, IASTExp from, IASTExp to, Set<IASTExp> outcomes) {
        super(FeedbackType.SEMANTIC_ERROR);

        this.variable = variable;
        this.from = from;
        this.to = to;
        this.outcomes = outcomes;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid mapping!";
            case MEDIUM, HIGH -> "There is no mapping of " + variable + " in " + from + " that can produce " + to + "!";
            case SOLUTION -> "There is no mapping of " + variable + " in " + from + " that can produce " + to + "! " +
                    "Did you mean: "+ Levenshtein.findMostSimilar(outcomes.stream().map(Object::toString).collect(Collectors.toSet()), to.toString());
        };
    }
}
