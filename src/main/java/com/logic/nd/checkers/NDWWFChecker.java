package com.logic.nd.checkers;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.checkers.FOLWFFChecker;
import com.logic.exps.interpreters.FOLReplaceExps;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;
import com.logic.nd.asts.binary.ASTEExist;
import com.logic.nd.asts.binary.ASTEImp;
import com.logic.nd.asts.binary.ASTENeg;
import com.logic.nd.asts.binary.ASTIConj;
import com.logic.nd.asts.others.ASTEDisj;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.*;
import com.logic.others.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NDWWFChecker implements INDVisitor<Void, Void> {

    private final Map<IASTExp, IFormula> formulas;

    NDWWFChecker(Map<IASTExp, IFormula> formulas) {
        this.formulas = formulas;
    }

    public static void check(IASTND nd, Map<IASTExp, IFormula> formulas) {
        NDWWFChecker checker = new NDWWFChecker(formulas);
        nd.accept(checker, null);
    }

    @Override
    public Void visit(ASTHypothesis h, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTIImp r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTConditional cond))
            throw new RuntimeException("The introduction of the implication rule is incorrectly typed!\n " +
                    "The conclusion should be an implication, but you provided: " + exp + "!");

        IASTExp right = ExpUtils.removeParenthesis(cond.getRight());

        if (!right.equals(r.getHyp().getConclusion()))
            throw new RuntimeException("The introduction of the implication rule is incorrectly typed!\n " +
                    "The hypothesis is not the same as the right-hand side of the implication!");

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTINeg r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTNot))
            throw new RuntimeException("The introduction of the negation rule is incorrectly typed!\n " +
                    "The conclusion should be a negation, but you provided: " + exp + "!");

        if (!r.getHyp().getConclusion().equals(ExpUtils.BOT))
            throw new RuntimeException("The introduction of the negation rule is incorrectly typed!\n " +
                    "The hypothesis should be " + ExpUtils.BOT + "!");

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTERConj r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(r.getHyp().getConclusion() instanceof ASTAnd and))
            throw new RuntimeException("The elimination of the conjunction right rule is incorrectly typed!\n " +
                    "The hypothesis should be a conjunction, but you provided: " + r.getHyp() + "!");

        IASTExp left = ExpUtils.removeParenthesis(and.getLeft());
        if (!left.equals(exp))
            throw new RuntimeException("The elimination of the conjunction right rule is incorrectly typed!\n " +
                    "The conclusion is not the same as the left-hand side of the conjunction!");

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTELConj r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(r.getHyp().getConclusion() instanceof ASTAnd and))
            throw new RuntimeException("The elimination of the conjunction left rule is incorrectly typed!\n " +
                    "The hypothesis should be a conjunction, but you provided: " + r.getHyp() + "!");

        IASTExp right = ExpUtils.removeParenthesis(and.getRight());
        if (!right.equals(exp))
            throw new RuntimeException("The elimination of the conjunction left rule is incorrectly typed!\n " +
                    "The conclusion is not the same as the right-hand side of the conjunction!");

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIRDis r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTOr or))
            throw new RuntimeException("The introduction of the disjunction right rule is incorrectly typed!\n " +
                    "The conclusion should be a disjunction, but you provided: " + exp + "!");

        IASTExp left = ExpUtils.removeParenthesis(or.getLeft());
        if (!left.equals(r.getHyp().getConclusion()))
            throw new RuntimeException("The introduction of the disjunction right rule is incorrectly typed!\n " +
                    "The hypothesis is not the same as the left-hand side of the disjunction!");

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTILDis r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTOr or))
            throw new RuntimeException("The introduction of the disjunction left rule is incorrectly typed!\n " +
                    "The conclusion should be a disjunction, but you provided: " + exp + "!");

        IASTExp right = ExpUtils.removeParenthesis(or.getRight());
        if (!right.equals(r.getHyp().getConclusion()))
            throw new RuntimeException("The introduction of the disjunction left rule is incorrectly typed!\n " +
                    "The hypothesis is not the same as the right-hand side of the disjunction!");

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTAbsurdity r, Void env) {

        if (!r.getHyp().getConclusion().equals(ExpUtils.BOT))
            throw new RuntimeException("The absurdity rule is incorrectly typed!\n " +
                    "The hypothesis should be a disjunction, but you provided: " + r.getHyp() + "!");

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIConj r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTAnd and))
            throw new RuntimeException("The introduction of the conjunction rule is incorrectly typed!\n " +
                    "The conclusion should be a conjunction, but you provided: " + exp + "!");

        IASTExp left = ExpUtils.removeParenthesis(and.getLeft());
        IASTExp right = ExpUtils.removeParenthesis(and.getRight());

        if (!left.equals(r.getHyp1().getConclusion()) || !right.equals(r.getHyp2().getConclusion()))
            throw new RuntimeException("The introduction of the conjunction rule is incorrectly typed!\n " +
                    "The conjunction of the hypotheses is different from the conclusion!");

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);

        return null;
    }

    @Override
    public Void visit(ASTEDisj r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(r.getHyp1().getConclusion() instanceof ASTOr or))
            throw new RuntimeException("The elimination of the disjunction rule is incorrectly typed!\n " +
                    "The first hypothesis should be a disjunction, but you provided: " + r.getHyp1() + "!");

        IASTExp left = ExpUtils.removeParenthesis(r.getHyp2().getConclusion());
        IASTExp right = ExpUtils.removeParenthesis(r.getHyp3().getConclusion());

        if (!exp.equals(left))
            throw new RuntimeException("The elimination of the disjunction rule is incorrectly typed!\n " +
                    "The second hypothesis is different from the conclusion!");
        if (!exp.equals(right))
            throw new RuntimeException("The elimination of the disjunction rule is incorrectly typed!\n " +
                    "The third hypothesis is different from the conclusion!");

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        r.getHyp3().accept(this, env);

        return null;
    }

    @Override
    public Void visit(ASTEImp r, Void env) {
        IASTExp exp = r.getConclusion();

        IASTExp other = r.getHyp1().getConclusion();
        if (!(r.getHyp2().getConclusion() instanceof ASTConditional imp))
            throw new RuntimeException("The elimination of the implication rule is incorrectly typed!\n" +
                    "The second hypothesis should be an implication, but you provided: " + r.getHyp2() + "!");

        IASTExp left = ExpUtils.removeParenthesis(imp.getLeft());
        IASTExp right = ExpUtils.removeParenthesis(imp.getRight());
        if (!left.equals(other) || !right.equals(exp))
            throw new RuntimeException("The elimination of the implication rule is incorrectly typed!\n" +
                    "The first hypothesis and the conclusion should form the implication in the second hypothesis!");

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);

        return null;
    }

    @Override
    public Void visit(ASTENeg r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!exp.equals(ExpUtils.BOT))
            throw new RuntimeException("The elimination of the negation rule is incorrectly typed!\n" +
                    "The conclusion should be " + ExpUtils.BOT + "!");

        if (!r.getHyp1().getConclusion().equals(ExpUtils.invert(r.getHyp2().getConclusion())) &&
                !r.getHyp2().getConclusion().equals(ExpUtils.invert(r.getHyp1().getConclusion())))
            throw new RuntimeException("The elimination of the negation rule is incorrectly typed!\n" +
                    "The hypotheses should be negations of each other!");

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);

        return null;
    }

    @Override
    public Void visit(ASTEUni r, Void env) {
        if (!(r.getHyp().getConclusion() instanceof ASTUniversal uni))
            throw new RuntimeException("The elimination of the universal rule is incorrectly typed!\n" +
                    "The hypothesis should be an universal, but you provided: " + r.getHyp() + "!");

        //Find mapping
        ASTVariable x = (ASTVariable) uni.getLeft();
        IASTExp psi = ExpUtils.removeParenthesis(uni.getRight());
        IFOLFormula psiXT = (IFOLFormula) formulas.get(r.getConclusion());

        List<AASTTerm> terms = new ArrayList<>();
        terms.add(x);
        psiXT.iterateTerms().forEachRemaining(terms::add);

        if (r.getMapping() == null)
            for (AASTTerm term : terms) {
                if (FOLReplaceExps.replace(psi, x, term).equals(psiXT.getFormula())) {
                    r.setMapping(term);
                    break;
                }
            }

        if (r.getMapping() == null)
            throw new RuntimeException("The elimination of the universal rule is incorrectly typed!\n" +
                    "There is no mapping of " + x + " in " + psi + " that can produce " + psiXT + "!");

        formulas.put(psi, FOLWFFChecker.check(psi));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIExist r, Void env) {
        if (!(r.getConclusion() instanceof ASTExistential exi))
            throw new RuntimeException("The introduction of the existential rule is incorrectly typed!\n" +
                    "The conclusion should be an existential, but you provided: " + r.getConclusion() + "!");

        //Find mapping
        ASTVariable x = (ASTVariable) exi.getLeft();
        IASTExp psi = ExpUtils.removeParenthesis(exi.getRight());
        IFOLFormula psiXT = (IFOLFormula) formulas.get(r.getHyp().getConclusion());

        List<AASTTerm> terms = new ArrayList<>();
        terms.add(x);
        psiXT.iterateTerms().forEachRemaining(terms::add);

        if (r.getMapping() == null)
            for (AASTTerm term : terms) {
                if (FOLReplaceExps.replace(psi, x, term).equals(psiXT.getFormula())) {
                    r.setMapping(term);
                    break;
                }
            }

        if (r.getMapping() == null)
            throw new RuntimeException("The introduction of the existential rule is incorrectly typed!\n" +
                    "There is no mapping of " + x + " in " + psi + " that can produce " + psiXT + "!");

        formulas.put(psi, FOLWFFChecker.check(psi));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIUni r, Void env) {
        if (!(r.getConclusion() instanceof ASTUniversal uni))
            throw new RuntimeException("The introduction of the universal rule is incorrectly typed!\n" +
                    "The conclusion should be an universal, but you provided: " + r.getConclusion() + "!");

        //Find mapping
        ASTVariable x = (ASTVariable) uni.getLeft();
        IASTExp psi = ExpUtils.removeParenthesis(uni.getRight());
        IFOLFormula psiXY = (IFOLFormula) formulas.get(r.getHyp().getConclusion());

        List<ASTVariable> variables = new ArrayList<>();
        variables.add(x); //It can be itself
        psiXY.iterateVariables().forEachRemaining(variables::add);

        if (r.getMapping() == null)
            for (ASTVariable var : variables) {
                if (FOLReplaceExps.replace(psi, x, var).equals(psiXY.getFormula())) {
                    r.setMapping(var);
                    break;
                }
            }

        if (r.getMapping() == null)
            throw new RuntimeException("The introduction of the universal rule is incorrectly typed!\n" +
                    "There is no mapping of " + x + " in " + psi + " that can produce " + psiXY + "!");

        formulas.put(psi, FOLWFFChecker.check(psi));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTEExist r, Void env) {
        if (!(r.getHyp1().getConclusion() instanceof ASTExistential exist))
            throw new RuntimeException("The elimination of the existential rule is incorrectly typed!\n" +
                    "The first hypothesis should be an existential, but you provided: " + r.getConclusion() + "!");

        if (!r.getConclusion().equals(r.getHyp2().getConclusion()))
            throw new RuntimeException("The elimination of the existential rule is incorrectly typed!\n" +
                    "The second hypothesis and the conclusion should be the same!");

        IASTExp exp = ExpUtils.removeParenthesis(exist.getRight());
        formulas.put(exp, FOLWFFChecker.check(exp));

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);

        return null;
    }

}