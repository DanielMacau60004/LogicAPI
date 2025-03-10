package com.logic.exps.checkers;

import com.logic.api.IFOLExp;
import com.logic.exps.asts.FOLExp;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.IExpsVisitor;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.others.*;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.asts.unary.ASTParenthesis;
import com.logic.others.Env;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FOLWFFChecker implements IExpsVisitor<Void, Env<String, IASTExp>> {

    public static final String ERROR_MESSAGE_FUNCTION = "Function %s was declared with different arities: %d and %d!";
    public static final String ERROR_MESSAGE_PREDICATE = "Predicate %s was declared with different arities: %d and %d!";

    final Map<String, Integer> functions;
    final Map<String, Integer> predicates;

    final Set<String> boundedVariables;
    final Set<String> unboundedVariables;

    FOLWFFChecker() {
        functions = new HashMap<>();
        predicates = new HashMap<>();

        boundedVariables = new HashSet<>();
        unboundedVariables = new HashSet<>();
    }

    public static IFOLExp check(IASTExp exp) {
        FOLWFFChecker checker = new FOLWFFChecker();
        Env<String, IASTExp> env = new Env<>();
        exp.accept(checker, env);

        return new FOLExp(exp,
                checker.functions.keySet(), checker.predicates.keySet(),
                checker.boundedVariables, checker.unboundedVariables);
    }

    @Override
    public Void visit(ASTTop e, Env<String, IASTExp> env) {
        return null;
    }

    @Override
    public Void visit(ASTBottom e, Env<String, IASTExp> env) {
        return null;
    }

    @Override
    public Void visit(ASTConstant e, Env<String, IASTExp> env) {
        Integer size = functions.get(e.getName());

        if(size == null) {
            functions.put(e.getName(), 0);
        } else if(size != 0)
            throw new RuntimeException(String.format(ERROR_MESSAGE_FUNCTION, e.getName(), size, 0));

        return null;
    }

    @Override
    public Void visit(ASTLiteral e, Env<String, IASTExp> env) {
        Integer size = predicates.get(e.getName());

        if(size == null) {
            predicates.put(e.getName(), 0);
        } else if(size != 0)
            throw new RuntimeException(String.format(ERROR_MESSAGE_PREDICATE, e.getName(), size, 0));

        return null;
    }

    @Override
    public Void visit(ASTArbitrary e, Env<String, IASTExp> env) {
        return null;
    }

    @Override
    public Void visit(ASTVariable e, Env<String, IASTExp> env) {
        if(env.find(e.getName()) != null)
            boundedVariables.add(e.getName());
        else unboundedVariables.add(e.getName());

        return null;
    }

    @Override
    public Void visit(ASTNot e, Env<String, IASTExp> env) {
        e.getExp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTAnd e, Env<String, IASTExp> env) {
        e.getLeft().accept(this, env);
        e.getRight().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTOr e, Env<String, IASTExp> env) {
        e.getLeft().accept(this, env);
        e.getRight().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTConditional e, Env<String, IASTExp> env) {
        e.getLeft().accept(this, env);
        e.getRight().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTBiconditional e, Env<String, IASTExp> env) {
        e.getLeft().accept(this, env);
        e.getRight().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTFun e, Env<String, IASTExp> env) {
        Integer size = functions.get(e.getName());
        int arity = e.getTerms().size();

        if(size == null) {
            functions.put(e.getName(), arity);
        } else if(size != arity)
            throw new RuntimeException(String.format(ERROR_MESSAGE_FUNCTION, e.getName(), size, arity));

        e.getTerms().forEach(t->t.accept(this, env));
        return null;
    }

    @Override
    public Void visit(ASTPred e, Env<String, IASTExp> env) {
        Integer size = predicates.get(e.getName());
        int arity = e.getTerms().size();

        if(size == null) {
            predicates.put(e.getName(), arity);
        } else if(size != arity)
            throw new RuntimeException(String.format(ERROR_MESSAGE_PREDICATE, e.getName(), size, arity));

        e.getTerms().forEach(t->t.accept(this, env));
        return null;
    }

    @Override
    public Void visit(ASTExistential e, Env<String, IASTExp> env) {
        env = env.beginScope();

        ASTVariable variable = ((ASTVariable)e.getLeft());
        env.bind(variable.getName(), variable);
        e.getRight().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTUniversal e, Env<String, IASTExp> env) {
        env = env.beginScope();

        ASTVariable variable = ((ASTVariable)e.getLeft());
        env.bind(variable.getName(), variable);
        e.getRight().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTParenthesis e, Env<String, IASTExp> env) {
        e.getExp().accept(this, env);
        return null;
    }

}
