package com.logic.nd;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.algorithm.state.StateNode;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.binary.ASTEImpND;
import com.logic.nd.asts.binary.ASTIConjND;
import com.logic.nd.asts.others.ASTEDisjND;
import com.logic.nd.asts.unary.*;
import com.logic.others.Utils;
import com.logic.parser.Parser;
import com.logic.parser.ParserConstants;

import java.util.Set;

public enum ERule {

    INTRO_CONJUNCTION(ParserConstants.IAND),
    ELIM_CONJUNCTION_LEFT(ParserConstants.ELAND),
    ELIM_CONJUNCTION_RIGHT(ParserConstants.ERAND),
    ELIM_IMPLICATION(ParserConstants.ECOND),
    INTRO_DISJUNCTION_LEFT(ParserConstants.ILOR),
    INTRO_DISJUNCTION_RIGHT(ParserConstants.IROR),
    INTRO_NEGATION(ParserConstants.INEG),
    ELIM_NEGATION(ParserConstants.ENEG),
    ELIM_DISJUNCTION(ParserConstants.EOR),
    INTRO_IMPLICATION(ParserConstants.ICOND),
    ABSURDITY(ParserConstants.BOTTOM);

    private final int kind;

    ERule(int kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return Utils.getToken(Parser.tokenImage[kind].replace("\"",""));
    }

}
