package com.logic.checkers;

import com.logic.api.IExp;
import com.logic.asts.IVisitor;
import com.logic.asts.binary.*;
import com.logic.asts.others.*;
import com.logic.asts.unary.ASTNot;
import com.logic.asts.unary.ASTParenthesis;

public class FOLWFFChecker implements IVisitor<Void, Void> {

    FOLWFFChecker() {}

    public static void check(IExp exp) {

    }

    @Override
    public Void visit(ASTSigFun e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTSigPred e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTTop e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTBottom e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTLiteral e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTConstant e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTVariable e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTNot e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTAnd e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTOr e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTConditional e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTBiconditional e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTFun e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTPred e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTExistential e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTUniversal e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTParenthesis e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTSequence e, Void env) {
        return null;
    }

}
