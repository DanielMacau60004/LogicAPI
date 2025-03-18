package com.logic.nd.interpreters;

import com.logic.api.IFOLFormula;
import com.logic.api.INDProof;
import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.checkers.FOLWFFChecker;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NDInterpreter implements INDVisitor<Integer, Env<Integer, IASTExp>> {

    private final Map<Integer, IASTExp> premises;
    private int size;

    NDInterpreter() {
        this.premises = new HashMap<>();
        this.size = 0;
    }

    //Split this into multi interpreters each one responsible to check a part of the proof

    //1. Check WFF [DONE]
    //2. Check if the expressions are correct (relation between hypotheses and conclusion), mappings [DONE]
    //3. Check marks [DONE]
    //4. Check side conditions (FOL)
    public static INDProof interpret(IASTND nd) {
        NDInterpreter interpret = new NDInterpreter();

        int height = nd.accept(interpret, new Env<>());
        return new NDProof(null, nd, height, interpret.size);
    }

    @Override
    public Integer visit(ASTHypothesis h, Env<Integer, IASTExp> env) {
        IASTExp existingMark = env.find(h.getM());
        if (existingMark == null)
            throw new RuntimeException("Mark not found");

        if (!existingMark.equals(h.getConclusion()))
            throw new RuntimeException("Invalid mark " + h.getM());

        size++;
        return 1;
    }

    @Override
    public Integer visit(ASTIImp r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTConditional cond))
            throw new RuntimeException("The introduction of the implication rule is incorrectly typed!");

        IASTExp left = ExpUtils.removeParenthesis(cond.getLeft());
        IASTExp right = ExpUtils.removeParenthesis(cond.getRight());

        if (!right.equals(r.getHyp().getConclusion()))
            throw new RuntimeException("The introduction of the implication rule is incorrectly typed! 2 ");

        env = env.beginScope();
        env.bind(r.getM(), left);
        int depth = r.getHyp().accept(this, env);
        env.endScope();

        size++;
        return depth + 1;
    }

    @Override
    public Integer visit(ASTINeg r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTNot neg))
            throw new RuntimeException("The introduction of the negation rule is incorrectly typed!");

        if (!r.getHyp().getConclusion().equals(ExpUtils.BOT))
            throw new RuntimeException("The introduction of the negation rule is incorrectly typed!");

        env = env.beginScope();
        env.bind(r.getM(), ExpUtils.removeParenthesis(neg.getExp()));
        int depth = r.getHyp().accept(this, env);
        env.endScope();

        size++;
        return depth + 1;
    }

    @Override
    public Integer visit(ASTERConj r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(r.getHyp().getConclusion() instanceof ASTAnd and))
            throw new RuntimeException("The elimination of the conjunction right rule is incorrectly typed!");

        IASTExp left = ExpUtils.removeParenthesis(and.getLeft());
        if (!left.equals(exp))
            throw new RuntimeException("The elimination of the conjunction right rule is incorrectly typed!");

        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTELConj r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(r.getHyp().getConclusion() instanceof ASTAnd and))
            throw new RuntimeException("The elimination of the conjunction left rule is incorrectly typed!");

        IASTExp right = ExpUtils.removeParenthesis(and.getRight());
        if (!right.equals(exp))
            throw new RuntimeException("The elimination of the conjunction left rule is incorrectly typed!");

        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTIRDis r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTOr or))
            throw new RuntimeException("The introduction of the disjunction right rule is incorrectly typed!");

        IASTExp left = ExpUtils.removeParenthesis(or.getLeft());
        if (!left.equals(r.getHyp().getConclusion()))
            throw new RuntimeException("The introduction of the disjunction right rule is incorrectly typed!");

        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTILDis r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTOr or))
            throw new RuntimeException("The introduction of the disjunction left rule is incorrectly typed!");

        IASTExp right = ExpUtils.removeParenthesis(or.getRight());
        if (!right.equals(r.getHyp().getConclusion()))
            throw new RuntimeException("The introduction of the disjunction left rule is incorrectly typed!");

        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTAbsurdity r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!r.getHyp().getConclusion().equals(ExpUtils.BOT))
            throw new RuntimeException("The absurdity rule is incorrectly typed!");

        env = env.beginScope();
        env.bind(r.getM(), ExpUtils.negate(exp));
        int depth = r.getHyp().accept(this, env);
        env.endScope();

        size++;
        return depth + 1;
    }

    @Override
    public Integer visit(ASTIConj r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTAnd and))
            throw new RuntimeException("The introduction of the conjunction rule is incorrectly typed!");

        IASTExp left = ExpUtils.removeParenthesis(and.getLeft());
        IASTExp right = ExpUtils.removeParenthesis(and.getRight());

        if (!left.equals(r.getHyp1().getConclusion()) || !right.equals(r.getHyp2().getConclusion()))
            throw new RuntimeException("The introduction of the conjunction rule is incorrectly typed!");

        int depth1 = r.getHyp1().accept(this, env);
        int depth2 = r.getHyp2().accept(this, env);

        size++;
        return Math.max(depth1, depth2) + 1;
    }

    @Override
    public Integer visit(ASTEDisj r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(r.getHyp1().getConclusion() instanceof ASTOr or))
            throw new RuntimeException("The elimination of the conjunction rule is incorrectly typed!");

        if (!exp.equals(r.getHyp2().getConclusion()) || !exp.equals(r.getHyp3().getConclusion()))
            throw new RuntimeException("The elimination of the conjunction rule is incorrectly typed!");

        int depth1 = r.getHyp1().accept(this, env);

        env = env.beginScope();
        env.bind(r.getM(), ExpUtils.removeParenthesis(or.getLeft()));
        int depth2 = r.getHyp2().accept(this, env);
        env.endScope();

        env = env.beginScope();
        env.bind(r.getN(), ExpUtils.removeParenthesis(or.getRight()));
        int depth3 = r.getHyp3().accept(this, env);
        env.endScope();

        size++;
        return Math.max(depth1, Math.max(depth2, depth3)) + 1;
    }

    @Override
    public Integer visit(ASTEImp r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        IASTExp other = r.getHyp1().getConclusion();
        if (!(r.getHyp2().getConclusion() instanceof ASTConditional imp))
            throw new RuntimeException("The elimination of the implication rule is incorrectly typed!");

        IASTExp left = ExpUtils.removeParenthesis(imp.getLeft());
        IASTExp right = ExpUtils.removeParenthesis(imp.getRight());
        if (!left.equals(other) || !right.equals(exp))
            throw new RuntimeException("The elimination of the implication rule is incorrectly typed!");

        int depth1 = r.getHyp1().accept(this, env);
        int depth2 = r.getHyp2().accept(this, env);

        size++;
        return Math.max(depth1, depth2) + 1;
    }

    @Override
    public Integer visit(ASTENeg r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!exp.equals(ExpUtils.BOT))
            throw new RuntimeException("The elimination of the negation rule is incorrectly typed!");

        if (!r.getHyp1().getConclusion().equals(ExpUtils.invert(r.getHyp2().getConclusion())) &&
                !r.getHyp2().getConclusion().equals(ExpUtils.invert(r.getHyp1().getConclusion())))
            throw new RuntimeException("The elimination of the negation rule is incorrectly typed!");

        int depth1 = r.getHyp1().accept(this, env);
        int depth2 = r.getHyp2().accept(this, env);

        size++;
        return Math.max(depth1, depth2) + 1;
    }

    @Override
    public Integer visit(ASTEUni r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTUniversal uni))
            throw new RuntimeException("The elimination of the universal rule is incorrectly typed!");

        IASTExp x = uni.getLeft();
        IFOLFormula psi = FOLWFFChecker.check(uni.getRight());

        IFOLFormula hyp = FOLWFFChecker.check(r.getHyp().getConclusion());
        IASTExp psiXT = hyp.getFormula();

        Iterator<AASTTerm> it = hyp.iterateTerms();
        AASTTerm t = null;
        while (it.hasNext()) {
            t = it.next();
            if (FOLReplaceExps.replace(psi.getFormula(), x, t).equals(psiXT))
                break;
            t = null;
        }

        if (t == null)
            throw new RuntimeException("The elimination of the universal rule is incorrectly typed!");

        if (t instanceof ASTVariable varT) {
            if (psi.isABoundedVariable(varT))
                throw new RuntimeException("The elimination of the universal rule is incorrectly typed!");
        }

        size++;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Integer visit(ASTIExist r, Env<Integer, IASTExp> env) {
        IASTExp exp = FOLWFFChecker.check(r.getHyp().getConclusion()).getFormula();

        if (!(exp instanceof ASTExistential exi))
            throw new RuntimeException("The introduction of the existential rule is incorrectly typed!");

        IASTExp x = exi.getLeft();
        IFOLFormula psi = FOLWFFChecker.check(exi.getRight());

        IFOLFormula conclusion = FOLWFFChecker.check(r.getConclusion());
        IASTExp psiXT = conclusion.getFormula();

        Iterator<AASTTerm> it = conclusion.iterateTerms();
        AASTTerm t = null;
        while (it.hasNext()) {
            t = it.next();
            if (FOLReplaceExps.replace(psi.getFormula(), x, t).equals(psiXT))
                break;
            t = null;
        }

        if (t == null)
            throw new RuntimeException("The introduction of the existential rule is incorrectly typed!");

        if (t instanceof ASTVariable varT) {
            if (psi.isABoundedVariable(varT))
                throw new RuntimeException("The introduction of the existential rule is incorrectly typed!");
        }

        size++;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Integer visit(ASTIUni r, Env<Integer, IASTExp> env) {
        IASTExp exp = FOLWFFChecker.check(r.getHyp().getConclusion()).getFormula();

        if (!(exp instanceof ASTUniversal uni))
            throw new RuntimeException("The introduction of the universal rule is incorrectly typed!");

        IASTExp x = uni.getLeft();
        IFOLFormula psi = FOLWFFChecker.check(uni.getRight());

        IFOLFormula conclusion = FOLWFFChecker.check(r.getConclusion());
        IASTExp psiXY = conclusion.getFormula();

        Iterator<ASTVariable> it = conclusion.iterateVariables();
            ASTVariable y = null;
        while (it.hasNext()) {
            y = it.next();
            if (FOLReplaceExps.replace(psi.getFormula(), x, y).equals(psiXY))
                break;
            y = null;
        }

        if (y == null)
            throw new RuntimeException("The introduction of the universal rule is incorrectly typed!");

        if (x.equals(y) && psi.isABoundedVariable(y))
            throw new RuntimeException("The introduction of the universal rule is incorrectly typed!");

        size++;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Integer visit(ASTEExist r, Env<Integer, IASTExp> env) {
        return 0;
    }

}
