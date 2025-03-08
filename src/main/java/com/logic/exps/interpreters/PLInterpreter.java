package com.logic.exps.interpreters;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.IVisitor;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.others.*;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.asts.unary.ASTParenthesis;

import java.util.Map;

public class PLInterpreter implements IVisitor<Boolean, Void> {

    public static final String ERROR_MESSAGE = "PL expressions only!";
    public static final String ERROR_MESSAGE_ARBITRARY = "Cannot evaluate an expression with arbitrary expressions!";
    public static final String ERROR_INTERPRETATION = "Missing predicate %s in the interpretation!";

    private final Map<String, Boolean> interpretation;

    PLInterpreter(Map<String, Boolean> interpretation){
        this.interpretation = interpretation;
    }

    public static boolean interpret(IASTExp exp, Map<String, Boolean> interpretation) {
        PLInterpreter interpreter = new PLInterpreter(interpretation);
        return exp.accept(interpreter, null);
    }

    @Override
    public Boolean visit(ASTTop e, Void env) {
        return true;
    }

    @Override
    public Boolean visit(ASTBottom e, Void env) {
        return false;
    }

    @Override
    public Boolean visit(ASTConstant e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Boolean visit(ASTLiteral e, Void env) {
        Boolean bool = interpretation.get(e.getName());
        if(bool == null)
            throw new RuntimeException(String.format(ERROR_INTERPRETATION, e.getName()));

        return bool;
    }

    @Override
    public Boolean visit(ASTArbitrary e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE_ARBITRARY);
    }

    @Override
    public Boolean visit(ASTVariable e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Boolean visit(ASTNot e, Void env) {
        return !e.getExp().accept(this, env);
    }

    @Override
    public Boolean visit(ASTAnd e, Void env) {
        return e.getLeft().accept(this, env) && e.getRight().accept(this, env);
    }

    @Override
    public Boolean visit(ASTOr e, Void env) {
        return e.getLeft().accept(this, env) || e.getRight().accept(this, env);
    }

    @Override
    public Boolean visit(ASTConditional e, Void env) {
        return !e.getLeft().accept(this, env) || e.getRight().accept(this, env);
    }

    @Override
    public Boolean visit(ASTBiconditional e, Void env) {
        return e.getLeft().accept(this, env) == e.getRight().accept(this, env);
    }

    @Override
    public Boolean visit(ASTFun e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Boolean visit(ASTPred e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Boolean visit(ASTExistential e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Boolean visit(ASTUniversal e, Void env) {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Boolean visit(ASTParenthesis e, Void env) {
        return e.getExp().accept(this, env);
    }

}
