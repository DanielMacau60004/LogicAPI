package com.logic.checkers;

import com.logic.api.IPLExp;
import com.logic.asts.IASTExp;
import com.logic.asts.IVisitor;
import com.logic.asts.PLExp;
import com.logic.asts.binary.*;
import com.logic.asts.others.*;
import com.logic.asts.unary.ASTNot;
import com.logic.asts.unary.ASTParenthesis;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class PLWFFChecker implements IVisitor<Void, Void> {

    public static final String ERROR_MESSAGE = "PL expressions only!";
    final Set<String> literals;

    PLWFFChecker() {
        literals = new TreeSet<>();
    }

    public static IPLExp check(IASTExp exp) {
        PLWFFChecker checker = new PLWFFChecker();
        exp.accept(checker, null);

        return new PLExp(exp, checker.literals);
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
    public Void visit(ASTConstant e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Void visit(ASTLiteral e, Void env) {
        literals.add(e.toString());
        return null;
    }

    @Override
    public Void visit(ASTArbitrary e, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTVariable e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Void visit(ASTNot e, Void env) {
        e.getExp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTAnd e, Void env) {
        e.getLeft().accept(this, env);
        e.getRight().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTOr e, Void env) {
        e.getLeft().accept(this, env);
        e.getRight().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTConditional e, Void env) {
        e.getLeft().accept(this, env);
        e.getRight().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTBiconditional e, Void env) {
        e.getLeft().accept(this, env);
        e.getRight().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTFun e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Void visit(ASTPred e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Void visit(ASTExistential e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Void visit(ASTUniversal e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Void visit(ASTParenthesis e, Void env) {
        e.getExp().accept(this, env);
        return null;
    }
}
