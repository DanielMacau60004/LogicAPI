package com.logic.exps.checkers;

import com.logic.api.IFOLFormula;
import com.logic.exps.asts.FOLExp;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.IExpsVisitor;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.others.*;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.asts.unary.ASTParenthesis;
import com.logic.others.Env;

import java.util.*;

public class FOLWFFChecker implements IExpsVisitor<Void, Env<String, ASTVariable>> {

    public static final String ERROR_MESSAGE_FUNCTION = "Function %s was declared with different arities: %d and %d!";
    public static final String ERROR_MESSAGE_PREDICATE = "Predicate %s was declared with different arities: %d and %d!";

    final Map<String, Integer> functionsMap;
    final Map<String, Integer> predicatesMap;

    final Set<ASTFun> functions;
    final Set<ASTPred> predicates;

    final Set<ASTVariable> boundedVariables;
    final Set<ASTVariable> unboundedVariables;

    final Map<ASTArbitrary, Set<ASTVariable>> generics;

    FOLWFFChecker() {
        functionsMap = new HashMap<>();
        predicatesMap = new HashMap<>();

        functions = new HashSet<>();
        predicates = new HashSet<>();
        boundedVariables = new HashSet<>();
        unboundedVariables = new HashSet<>();

        generics = new HashMap<>();
    }

    public static IFOLFormula check(IASTExp exp) {
        FOLWFFChecker checker = new FOLWFFChecker();
        Env<String, ASTVariable> env = new Env<>();
        exp.accept(checker, env);

        for(Set<ASTVariable> s : checker.generics.values()) {
            for(ASTVariable v : checker.boundedVariables) {
                if(!s.contains(v))
                    checker.unboundedVariables.add(v);
            }
        }

        return new FOLExp(exp,
                checker.functions, checker.predicates,
                checker.boundedVariables, checker.unboundedVariables,
                checker.generics.keySet());
    }

    @Override
    public Void visit(ASTTop e, Env<String, ASTVariable> env) {
        return null;
    }

    @Override
    public Void visit(ASTBottom e, Env<String, ASTVariable> env) {
        return null;
    }

    @Override
    public Void visit(ASTConstant e, Env<String, ASTVariable> env) {
        Integer size = functionsMap.get(e.getName());

        if(size == null) {
            functionsMap.put(e.getName(), 0);
            functions.add(e);
        } else if(size != 0)
            throw new RuntimeException(String.format(ERROR_MESSAGE_FUNCTION, e.getName(), size, 0));

        return null;
    }

    @Override
    public Void visit(ASTLiteral e, Env<String, ASTVariable> env) {
        Integer size = predicatesMap.get(e.getName());

        if(size == null) {
            predicatesMap.put(e.getName(), 0);
            predicates.add(e);
        } else if(size != 0)
            throw new RuntimeException(String.format(ERROR_MESSAGE_PREDICATE, e.getName(), size, 0));

        return null;
    }

    @Override
    public Void visit(ASTArbitrary e, Env<String, ASTVariable> env) {
        Set<ASTVariable> bounded = generics.get(e);
        if(bounded != null) bounded.addAll(env.map().values());
        else bounded = new HashSet<>(env.map().values());

        generics.put(e, bounded);
        return null;
    }

    @Override
    public Void visit(ASTVariable e, Env<String, ASTVariable> env) {
        if(env.find(e.getName()) != null)
            boundedVariables.add(e);
        else unboundedVariables.add(e);

        return null;
    }

    @Override
    public Void visit(ASTNot e, Env<String, ASTVariable> env) {
        e.getExp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTAnd e, Env<String, ASTVariable> env) {
        e.getLeft().accept(this, env);
        e.getRight().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTOr e, Env<String, ASTVariable> env) {
        e.getLeft().accept(this, env);
        e.getRight().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTConditional e, Env<String, ASTVariable> env) {
        e.getLeft().accept(this, env);
        e.getRight().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTBiconditional e, Env<String, ASTVariable> env) {
        e.getLeft().accept(this, env);
        e.getRight().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTFun e, Env<String, ASTVariable> env) {
        Integer size = functionsMap.get(e.getName());
        int arity = e.getTerms().size();

        if(size == null) {
            functionsMap.put(e.getName(), arity);
            functions.add(e);
        } else if(size != arity)
            throw new RuntimeException(String.format(ERROR_MESSAGE_FUNCTION, e.getName(), size, arity));

        e.getTerms().forEach(t->t.accept(this, env));
        return null;
    }

    @Override
    public Void visit(ASTPred e, Env<String, ASTVariable> env) {
        Integer size = predicatesMap.get(e.getName());
        int arity = e.getTerms().size();

        if(size == null) {
            predicatesMap.put(e.getName(), arity);
            predicates.add(e);
        } else if(size != arity)
            throw new RuntimeException(String.format(ERROR_MESSAGE_PREDICATE, e.getName(), size, arity));

        e.getTerms().forEach(t->t.accept(this, env));
        return null;
    }

    @Override
    public Void visit(ASTExistential e, Env<String, ASTVariable> env) {
        env = env.beginScope();

        ASTVariable variable = ((ASTVariable)e.getLeft());
        env.bind(variable.getName(), variable);
        boundedVariables.add(variable);
        e.getRight().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTUniversal e, Env<String, ASTVariable> env) {
        env = env.beginScope();

        ASTVariable variable = ((ASTVariable)e.getLeft());
        env.bind(variable.getName(), variable);
        boundedVariables.add(variable);
        e.getRight().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTParenthesis e, Env<String, ASTVariable> env) {
        e.getExp().accept(this, env);
        return null;
    }

}
