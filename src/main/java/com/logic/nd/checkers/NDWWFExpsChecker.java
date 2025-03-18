package com.logic.nd.checkers;

import com.logic.api.IFormula;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.checkers.FOLWFFChecker;
import com.logic.exps.checkers.PLWFFChecker;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;
import com.logic.nd.asts.binary.ASTEExist;
import com.logic.nd.asts.binary.ASTEImp;
import com.logic.nd.asts.binary.ASTENeg;
import com.logic.nd.asts.binary.ASTIConj;
import com.logic.nd.asts.others.ASTEDisj;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.*;

import java.util.HashMap;
import java.util.Map;

public class NDWWFExpsChecker implements INDVisitor<Void, Void> {

    private final boolean fol;

    private final Map<IASTExp, IFormula> formulas;

    NDWWFExpsChecker(boolean fol) {
        this.fol = fol;
        formulas = new HashMap<>();
    }

    public static Map<IASTExp, IFormula> checkPL(IASTND nd) {
        NDWWFExpsChecker checker = new NDWWFExpsChecker(false);
        nd.accept(checker, null);
        return checker.formulas;
    }

    public static void checkFOL(IASTND nd) {
        NDWWFExpsChecker checker = new NDWWFExpsChecker(true);
        nd.accept(checker, null);
    }

    private IFormula verifyAndCreateFormula(IASTExp exp) {
        return fol ?  FOLWFFChecker.check(exp)
             : PLWFFChecker.check(exp);
    }

    @Override
    public Void visit(ASTHypothesis h, Void env) {
        IASTExp exp = h.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));
        return null;
    }

    @Override
    public Void visit(ASTIImp r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTINeg r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTERConj r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTELConj r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIRDis r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTILDis r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTAbsurdity r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIConj r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEDisj r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        r.getHyp3().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEImp r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTENeg r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEUni r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIExist r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIUni r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTEExist r, Void env) {
        IASTExp exp = r.getConclusion();
        formulas.put(exp, verifyAndCreateFormula(exp));

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

}