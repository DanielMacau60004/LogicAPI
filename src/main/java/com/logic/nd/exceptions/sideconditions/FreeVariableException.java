package com.logic.nd.exceptions.sideconditions;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.feedback.FeedbackLevel;
import com.logic.feedback.FeedbackType;
import com.logic.nd.exceptions.NDRuleException;

public class FreeVariableException extends NDRuleException {

    private final ASTVariable variable;
    private final IASTExp exp;
    private final boolean isConclusion;

    public FreeVariableException(ASTVariable variable, IASTExp exp, boolean isConclusion) {
        super(FeedbackType.SEMANTIC_ERROR);

        this.variable = variable;
        this.exp = exp;
        this.isConclusion = isConclusion;
    }

    protected String produceFeedback(FeedbackLevel level) {
        return switch (level) {
            case NONE -> "";
            case LOW -> "Invalid rule!";
            case MEDIUM -> isConclusion
                    ? "Variable appears free in the conclusion!"
                    : "Variable appears free in the open hypotheses!";
            case HIGH, SOLUTION -> isConclusion
                    ? "Variable " + variable + " appears free in " + exp + "!"
                    : "Variable " + variable + " appears free in the open hypothesis " + exp + "!";

        };
    }
}
