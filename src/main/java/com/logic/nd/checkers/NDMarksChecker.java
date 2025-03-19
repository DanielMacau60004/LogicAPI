package com.logic.nd.checkers;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.api.INDProof;
import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.interpreters.FOLReplaceExps;
import com.logic.nd.NDProof;
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

import java.util.*;

public class NDMarksChecker implements INDVisitor<Void, Env<Integer, IASTExp>> {

    private final Map<IASTExp, IFormula> formulas;
    private final Map<IASTExp, Integer> premises;
    private final Map<Integer, IASTExp> hypotheses;

    NDMarksChecker(Map<IASTExp, IFormula> formulas) {
        this.premises = new HashMap<>();
        this.hypotheses = new HashMap<>();
        this.formulas = formulas;
    }

    public static Map<IASTExp, Integer> check(IASTND nd, Map<IASTExp, IFormula> formulas) {
        NDMarksChecker checker = new NDMarksChecker(formulas);

        nd.accept(checker, new Env<>());
        return checker.premises;
    }

    @Override
    public Void visit(ASTHypothesis h, Env<Integer, IASTExp> env) {
        IASTExp hyp = h.getConclusion();
        IASTExp exp = hypotheses.get(h.getM());

        if (exp != null && !exp.equals(hyp))
            throw new RuntimeException("Mark " + h.getM() + " is assigned to " + exp + "!");

        if (env.find(h.getM()) == null) { //It is a premise
            Integer currentMark = premises.get(hyp);
            if (currentMark != null && currentMark != h.getM())
                throw new RuntimeException("Premise " + h + "has more than one mark!");

            premises.put(hyp, h.getM());
            hypotheses.put(h.getM(), hyp);
            return null;
        }

        hypotheses.put(h.getM(), hyp);
        return null;
    }

    @Override
    public Void visit(ASTIImp r, Env<Integer, IASTExp> env) {
        ASTConditional cond = (ASTConditional) r.getConclusion();
        IASTExp left = ExpUtils.removeParenthesis(cond.getLeft());

        env = env.beginScope();
        env.bind(r.getM(), left);
        r.getHyp().accept(this, env);

        if (hypotheses.containsKey(r.getM()) && !left.equals(hypotheses.get(r.getM())))
            throw new RuntimeException("The introduction of the implication rule cannot close mark " + r.getM() + "!");

        r.setGeneratedHypothesis(left);
        env.endScope();

        return null;
    }

    @Override
    public Void visit(ASTINeg r, Env<Integer, IASTExp> env) {
        ASTNot neg = (ASTNot) r.getConclusion();
        IASTExp notNeg = ExpUtils.removeParenthesis(neg.getExp());

        env = env.beginScope();
        env.bind(r.getM(), notNeg);
        r.getHyp().accept(this, env);

        if (hypotheses.containsKey(r.getM()) && !notNeg.equals(hypotheses.get(r.getM())))
            throw new RuntimeException("The introduction of the negation rule cannot close mark " + r.getM() + "!");

        r.setGeneratedHypothesis(notNeg);
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
        IASTExp neg = ExpUtils.negate(r.getConclusion());

        env = env.beginScope();
        env.bind(r.getM(), neg);
        r.getHyp().accept(this, env);

        if (hypotheses.containsKey(r.getM()) && !neg.equals(hypotheses.get(r.getM())))
            throw new RuntimeException("The absurdity rule cannot close mark " + r.getM() + "!");

        r.setGeneratedHypothesis(neg);
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
        ASTOr or = (ASTOr) r.getHyp1().getConclusion();
        IASTExp left = ExpUtils.removeParenthesis(or.getLeft());
        IASTExp right = ExpUtils.removeParenthesis(or.getRight());

        r.getHyp1().accept(this, env);

        env = env.beginScope();
        env.bind(r.getM(), left);
        r.getHyp2().accept(this, env);

        if (hypotheses.containsKey(r.getM()) && !left.equals(hypotheses.get(r.getM())))
            throw new RuntimeException("The elimination of the disjunction rule cannot close mark " + r.getM() + "!");

        r.setGeneratedHypothesisM(left);
        env.endScope();

        env = env.beginScope();
        env.bind(r.getN(), right);
        r.getHyp3().accept(this, env);

        if (hypotheses.containsKey(r.getN()) && !right.equals(hypotheses.get(r.getN())))
            throw new RuntimeException("The elimination of the disjunction rule cannot close mark " + r.getN() + "!");

        r.setGeneratedHypothesisN(right);
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
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIExist r, Env<Integer, IASTExp> env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIUni r, Env<Integer, IASTExp> env) {
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTEExist r, Env<Integer, IASTExp> env) {
        ASTExistential exi = (ASTExistential) r.getHyp1().getConclusion();
        r.getHyp1().accept(this, env);

        env = env.beginScope();
        env.bind(r.getM(), exi);
        r.getHyp2().accept(this, env);
        env.endScope();

        IASTExp exp = hypotheses.get(r.getM());
        if(exp == null) return null;

        IASTExp x = exi.getLeft();
        IASTExp psi = ExpUtils.removeParenthesis(exi.getRight());
        IFOLFormula psiXT = (IFOLFormula) formulas.get(exp);

        Iterator<ASTVariable> it = psiXT.iterateVariables();
        while (it.hasNext()) {
            ASTVariable t = it.next();
            if (FOLReplaceExps.replace(psi, x, t).equals(psiXT.getFormula())) {
                r.setMapping(t);
                break;
            }
        }

        if (r.getMapping() == null)
            throw new RuntimeException("The elimination of the existential rule is incorrectly typed!\n" +
                    "There is no mapping of " + x + " in " + psi + " that can produce " + psiXT + "!");

        if (hypotheses.containsKey(r.getM()) && !exp.equals(hypotheses.get(r.getM())))
            throw new RuntimeException("The elimination of the existential rule cannot close mark " + r.getM() + "!");

        r.setGeneratedHypothesis(exp);
        return null;
    }

}
