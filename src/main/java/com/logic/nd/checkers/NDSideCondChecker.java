package com.logic.nd.checkers;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.ASTExistential;
import com.logic.exps.asts.binary.ASTUniversal;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.exps.checkers.FOLWFFChecker;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;
import com.logic.nd.asts.binary.ASTEExist;
import com.logic.nd.asts.binary.ASTEImp;
import com.logic.nd.asts.binary.ASTENeg;
import com.logic.nd.asts.binary.ASTIConj;
import com.logic.nd.asts.others.ASTEDisj;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.*;
import com.logic.others.Env;
import com.logic.others.Utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NDSideCondChecker implements INDVisitor<Void, Env<Integer, IASTExp>> {

    private final Map<IASTExp, IFormula> formulas;

    NDSideCondChecker(Map<IASTExp, IFormula> formulas) {
        this.formulas = formulas;
    }

    public static Map<IASTExp, IFormula> check(IASTND nd, Map<IASTExp, IFormula> formulas, Map<IASTExp, Integer> premises) {
        NDSideCondChecker checker = new NDSideCondChecker(formulas);
        Env<Integer, IASTExp> env = new Env<>();
        premises.forEach((p,m) -> env.bind(m, p));
        nd.accept(checker, env);
        return checker.formulas;
    }

    @Override
    public Void visit(ASTHypothesis h, Env<Integer, IASTExp> env) {
        return null;
    }

    @Override
    public Void visit(ASTIImp r, Env<Integer, IASTExp> env) {
        env = env.beginScope();
        env.bind(r.getM(), r.getGeneratedHypothesis());
        r.getHyp().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTINeg r, Env<Integer, IASTExp> env) {
        env = env.beginScope();
        env.bind(r.getM(), r.getGeneratedHypothesis());
        r.getHyp().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTERConj r, Env<Integer, IASTExp> env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTELConj r, Env<Integer, IASTExp> env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIRDis r, Env<Integer, IASTExp> env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTILDis r, Env<Integer, IASTExp> env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTAbsurdity r, Env<Integer, IASTExp> env) {
        env = env.beginScope();
        env.bind(r.getM(), r.getGeneratedHypothesis());
        r.getHyp().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTIConj r, Env<Integer, IASTExp> env) {
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEDisj r, Env<Integer, IASTExp> env) {
        r.getHyp1().accept(this, env);

        env = env.beginScope();
        env.bind(r.getM(), r.getGeneratedHypothesisM());
        r.getHyp2().accept(this, env);
        env.endScope();

        env = env.beginScope();
        env.bind(r.getN(), r.getGeneratedHypothesisN());
        r.getHyp3().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTEImp r, Env<Integer, IASTExp> env) {
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTENeg r, Env<Integer, IASTExp> env) {
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEUni r, Env<Integer, IASTExp> env) {
        ASTUniversal uni = (ASTUniversal) r.getHyp().getConclusion();
        IFOLFormula psi = (IFOLFormula) formulas.get(ExpUtils.removeParenthesis(uni.getRight()));

        if (r.getMapping() instanceof ASTVariable x && psi.isABoundedVariable(x))
            throw new RuntimeException("The elimination of the universal rule is incorrectly typed!\n" +
                    "Variable " + x + " in not free to " + uni.getLeft() + " to " + psi + "!");

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIExist r, Env<Integer, IASTExp> env) {
        ASTExistential exi = (ASTExistential) r.getConclusion();
        IFOLFormula psi = (IFOLFormula) formulas.get(ExpUtils.removeParenthesis(exi.getRight()));

        if (r.getMapping() instanceof ASTVariable x && psi.isABoundedVariable(x))
            throw new RuntimeException("The introduction of the existential rule is incorrectly typed!\n" +
                    "Variable " + x + " in not free to " + exi.getLeft() + " to " + psi + "!");

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIUni r, Env<Integer, IASTExp> env) {
        ASTUniversal uni = (ASTUniversal) r.getConclusion();
        IFOLFormula psi = (IFOLFormula) formulas.get(ExpUtils.removeParenthesis(uni.getRight()));

        if (!uni.getLeft().equals(r.getMapping()) && psi.isAnUnboundedVariable(r.getMapping()))
            throw new RuntimeException("The introduction of the universal rule is incorrectly typed!\n" +
                    "Variable " + r.getMapping() + " appears free in " + psi + "!");

        for (Map.Entry<Integer, IASTExp> e : env.map().entrySet()) {
            IFOLFormula formula = (IFOLFormula) formulas.get(e.getValue());
            if (formula.isAnUnboundedVariable(r.getMapping()))
                throw new RuntimeException("The introduction of the universal rule is incorrectly typed!\n" +
                        "Variable " + r.getMapping() + " appears free in " + e.getKey() + "!");
        }

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTEExist r, Env<Integer, IASTExp> env) {
        ASTExistential exi = (ASTExistential) r.getHyp1().getConclusion();
        IFOLFormula psi = (IFOLFormula) formulas.get(ExpUtils.removeParenthesis(exi.getRight()));
        IFOLFormula exp = (IFOLFormula) formulas.get(r.getConclusion());

        if (!exi.getLeft().equals(r.getMapping()) && psi.isAnUnboundedVariable(r.getMapping()))
            throw new RuntimeException("The elimination of the existential rule is incorrectly typed!\n" +
                    "Variable " + r.getMapping() + " appears free in " + psi + "!");

        if (!r.getConclusion().equals(ExpUtils.BOT) && !exp.isABoundedVariable(r.getMapping()))
            throw new RuntimeException("The elimination of the existential rule is incorrectly typed!\n" +
                    "Variable " + r.getMapping() + " appears free in " + exp + "!");

        for (Map.Entry<Integer, IASTExp> e : env.map().entrySet()) {
            IFOLFormula formula = (IFOLFormula) formulas.get(e.getValue());
            if (!e.equals(r.getGeneratedHypothesis()) && formula.isAnUnboundedVariable(r.getMapping()))
                throw new RuntimeException("The elimination of the existential rule is incorrectly typed!\n" +
                        "Variable " + r.getMapping() + " appears free in mark " + e.getKey() + "!");
        }

        r.getHyp1().accept(this, env);

        env = env.beginScope();
        env.bind(r.getM(), r.getGeneratedHypothesis());
        r.getHyp2().accept(this, env);
        env.endScope();

        return null;
    }

}