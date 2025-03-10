package com.logic.nd.asts;

import com.logic.nd.asts.binary.ASTEImpND;
import com.logic.nd.asts.binary.ASTENegND;
import com.logic.nd.asts.binary.ASTIConjND;
import com.logic.nd.asts.others.ASTEDisjND;
import com.logic.nd.asts.others.ASTHypothesisND;
import com.logic.nd.asts.others.ASTPremiseND;
import com.logic.nd.asts.unary.*;

public interface INDVisitor <T, E> {

    T visit(ASTPremiseND p, E env);

    T visit(ASTHypothesisND h, E env);


    T visit(ASTIImpND r, E env);

    T visit(ASTINegND r, E env);

    T visit(ASTERConjND r, E env);

    T visit(ASTELConjND r, E env);

    T visit(ASTIRDisND r, E env);

    T visit(ASTILDisND r, E env);

    T visit(ASTAbsurdityND r, E env);

    T visit(ASTIConjND r, E env);

    T visit(ASTEDisjND r, E env);

    T visit(ASTEImpND r, E env);

    T visit(ASTENegND r, E env);

}
