package com.logic.nd.checkers;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.ASTExistential;
import com.logic.exps.asts.binary.ASTUniversal;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;
import com.logic.nd.asts.binary.ASTEExist;
import com.logic.nd.asts.binary.ASTEImp;
import com.logic.nd.asts.binary.ASTENeg;
import com.logic.nd.asts.binary.ASTIConj;
import com.logic.nd.asts.others.ASTEDisj;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NDSideCondChecker implements INDVisitor<Set<IASTExp>, Void> {

    private final Map<IASTExp, IFormula> formulas;

    NDSideCondChecker(Map<IASTExp, IFormula> formulas) {
        this.formulas = formulas;
    }

    public static Map<IASTExp, IFormula> check(IASTND nd, Map<IASTExp, IFormula> formulas) {
        NDSideCondChecker checker = new NDSideCondChecker(formulas);
        nd.accept(checker, null);
        return checker.formulas;
    }

    @Override
    public Set<IASTExp> visit(ASTHypothesis h, Void env) {
        HashSet<IASTExp> result = new HashSet<>();
        result.add(h.getConclusion());
        return result;
    }

    @Override
    public Set<IASTExp> visit(ASTIImp r, Void env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Set<IASTExp> visit(ASTINeg r, Void env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Set<IASTExp> visit(ASTERConj r, Void env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Set<IASTExp> visit(ASTELConj r, Void env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Set<IASTExp> visit(ASTIRDis r, Void env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Set<IASTExp> visit(ASTILDis r, Void env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Set<IASTExp> visit(ASTAbsurdity r, Void env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Set<IASTExp> visit(ASTIConj r, Void env) {
        Set<IASTExp> result = r.getHyp1().accept(this, env);
        result.addAll(r.getHyp2().accept(this, env));
        return result;
    }

    @Override
    public Set<IASTExp> visit(ASTEDisj r, Void env) {
        Set<IASTExp> result = r.getHyp1().accept(this, env);
        result.addAll(r.getHyp2().accept(this, env));
        result.addAll(r.getHyp3().accept(this, env));
        return result;
    }

    @Override
    public Set<IASTExp> visit(ASTEImp r, Void env) {
        Set<IASTExp> result = r.getHyp1().accept(this, env);
        result.addAll(r.getHyp2().accept(this, env));
        return result;
    }

    @Override
    public Set<IASTExp> visit(ASTENeg r, Void env) {
        Set<IASTExp> result = r.getHyp1().accept(this, env);
        result.addAll(r.getHyp2().accept(this, env));
        return result;
    }

    @Override
    public Set<IASTExp> visit(ASTEUni r, Void env) {
        ASTUniversal uni = (ASTUniversal) r.getHyp().getConclusion();
        IFOLFormula psi = (IFOLFormula) formulas.get(uni.getRight());

        if (r.getMapping() instanceof ASTVariable x && psi.isABoundedVariable(x))
            throw new RuntimeException("The elimination of the universal rule is incorrectly typed!\n" +
                    "Variable " + x + " in not free to " + uni.getLeft() + " to " + psi + "!");

        return r.getHyp().accept(this, env);
    }

    @Override
    public Set<IASTExp> visit(ASTIExist r, Void env) {
        ASTExistential exi = (ASTExistential) r.getConclusion();
        IFOLFormula psi = (IFOLFormula) formulas.get(exi.getRight());

        if (r.getMapping() instanceof ASTVariable x && psi.isABoundedVariable(x))
            throw new RuntimeException("The introduction of the existential rule is incorrectly typed!\n" +
                    "Variable " + x + " in not free to " + exi.getLeft() + " to " + psi + "!");

        return r.getHyp().accept(this, env);
    }

    @Override
    public Set<IASTExp> visit(ASTIUni r, Void env) {
        ASTUniversal uni = (ASTUniversal) r.getConclusion();
        IFOLFormula psi = (IFOLFormula) formulas.get(uni.getRight());

        if (uni.getLeft().equals(r.getMapping()) && !psi.isABoundedVariable(r.getMapping()))
            throw new RuntimeException("The introduction of the universal rule is incorrectly typed!\n" +
                    "Variable " + r.getMapping() + " appears free in " + psi + "!");

        Set<IASTExp> result = r.getHyp().accept(this, env);
        for (IASTExp e : result) {
            IFOLFormula formula = (IFOLFormula) formulas.get(e);
            if (!formula.isABoundedVariable(r.getMapping()))
                throw new RuntimeException("The introduction of the universal rule is incorrectly typed!\n" +
                        "Variable " + r.getMapping() + " appears free in " + e + "!");
        }

        return result;
    }

    @Override
    public Set<IASTExp> visit(ASTEExist r, Void env) {
        ASTExistential exi = (ASTExistential) r.getHyp1().getConclusion();
        IFOLFormula psi = (IFOLFormula) formulas.get(exi.getRight());

        if (exi.getLeft().equals(r.getMapping()) && !psi.isABoundedVariable(r.getMapping()))
            throw new RuntimeException("The elimination of the existential rule is incorrectly typed!\n" +
                    "Variable " + r.getMapping() + " appears free in " + psi + "!");

        Set<IASTExp> result = r.getHyp2().accept(this, env);
        for (IASTExp e : result) {
            IFOLFormula formula = (IFOLFormula) formulas.get(e);
            if (!e.equals(r.getGeneratedHypothesis()) && !formula.isABoundedVariable(r.getMapping()))
                throw new RuntimeException("The elimination of the existential rule is incorrectly typed!\n" +
                        "Variable " + r.getMapping() + " appears free in " + e + "!");
        }

        result.addAll(r.getHyp1().accept(this, env));
        return result;
    }

}