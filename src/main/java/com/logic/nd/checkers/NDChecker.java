package com.logic.nd.checkers;

import com.logic.exps.checkers.FOLWFFChecker;
import com.logic.exps.checkers.PLWFFChecker;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;
import com.logic.nd.asts.binary.ASTEExistND;
import com.logic.nd.asts.binary.ASTEImpND;
import com.logic.nd.asts.binary.ASTENegND;
import com.logic.nd.asts.binary.ASTIConjND;
import com.logic.nd.asts.others.ASTEDisjND;
import com.logic.nd.asts.others.ASTHypothesisND;
import com.logic.nd.asts.others.ASTPremiseND;
import com.logic.nd.asts.unary.*;

public class NDChecker implements INDVisitor<Void, Void> {

    private final boolean fol;

    NDChecker(boolean fol) {
        this.fol = fol;
    }

    public static void checkPL(IASTND nd) {
        NDChecker checker = new NDChecker(false);
        nd.accept(checker, null);
    }

    public static void checkFOL(IASTND nd) {
        NDChecker checker = new NDChecker(true);
        nd.accept(checker, null);
    }

    @Override
    public Void visit(ASTPremiseND p, Void env) {
        if (fol) FOLWFFChecker.check(p.getConclusion());
        else PLWFFChecker.check(p.getConclusion());

        return null;
    }

    @Override
    public Void visit(ASTHypothesisND h, Void env) {
        if (fol) FOLWFFChecker.check(h.getConclusion());
        else PLWFFChecker.check(h.getConclusion());

        return null;
    }

    @Override
    public Void visit(ASTIImpND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTINegND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTERConjND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTELConjND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTIRDisND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTILDisND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTAbsurdityND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTIConjND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEDisjND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        r.getHyp3().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEImpND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTENegND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEUniND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTIExistND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTIUniND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEExistND r, Void env) {
        if (fol) FOLWFFChecker.check(r.getConclusion());
        else PLWFFChecker.check(r.getConclusion());

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

}
