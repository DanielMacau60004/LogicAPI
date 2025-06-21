package com.logic.nd.checkers;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.exps.ExpUtils;
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
import com.logic.nd.asts.others.ASTEDis;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.*;
import com.logic.nd.exceptions.sideconditions.FreeVariableException;
import com.logic.nd.exceptions.sideconditions.NotFreeVariableException;
import com.logic.others.Env;
import com.logic.others.Utils;

import java.util.Map;

public class NDSideCondChecker implements INDVisitor<Void, Env<String, IASTExp>> {

    private final Map<IASTExp, IFormula> formulas;

    NDSideCondChecker(Map<IASTExp, IFormula> formulas) {
        this.formulas = formulas;
    }

    public static Map<IASTExp, IFormula> check(IASTND nd, Map<IASTExp, IFormula> formulas, Map<String, IASTExp> premises) {
        NDSideCondChecker checker = new NDSideCondChecker(formulas);
        Env<String, IASTExp> env = new Env<>();
        premises.forEach(env::bind);
        nd.accept(checker, env);
        return checker.formulas;
    }

    @Override
    public Void visit(ASTHypothesis h, Env<String, IASTExp> env) {
        return null;
    }

    @Override
    public Void visit(ASTIImp r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        env = env.beginScope();
        if (r.getCloseM() != null)
            env.bind(r.getM(), r.getCloseM());
        r.getHyp().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTINeg r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        env = env.beginScope();
        if (r.getCloseM() != null)
            env.bind(r.getM(), r.getCloseM());
        r.getHyp().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTERConj r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTELConj r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIRDis r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTILDis r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTAbsurdity r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        env = env.beginScope();
        if (r.getCloseM() != null)
            env.bind(r.getM(), r.getCloseM());
        r.getHyp().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTIConj r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEDis r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        r.getHyp1().accept(this, env);

        env = env.beginScope();
        if (r.getCloseM() != null)
            env.bind(r.getM(), r.getCloseM());
        r.getHyp2().accept(this, env);
        env.endScope();

        env = env.beginScope();
        if (r.getCloseN() != null)
            env.bind(r.getN(), r.getCloseN());
        r.getHyp3().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTEImp r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTENeg r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEUni r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        ASTUniversal uni = (ASTUniversal) r.getHyp().getConclusion();
        IFOLFormula psi = (IFOLFormula) formulas.get(ExpUtils.removeParenthesis(uni.getRight()));

        if (r.getMapping() instanceof ASTVariable x && psi.isABoundedVariable(x)) {
            r.appendErrors(new NotFreeVariableException(x, uni.getLeft(), psi.getAST()));
            return null;
        }

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIExist r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        ASTExistential exi = (ASTExistential) r.getConclusion();
        IFOLFormula psi = (IFOLFormula) formulas.get(ExpUtils.removeParenthesis(exi.getRight()));

        if (r.getMapping() instanceof ASTVariable x && psi.isABoundedVariable(x)) {
            r.appendErrors(new NotFreeVariableException(x, exi.getLeft(), psi.getAST()));
            return null;
        }

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIUni r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        ASTUniversal uni = (ASTUniversal) r.getConclusion();
        IFOLFormula psi = (IFOLFormula) formulas.get(ExpUtils.removeParenthesis(uni.getRight()));

        if (!uni.getLeft().equals(r.getMapping()) && psi.appearsFreeVariable(r.getMapping())) {
            r.appendErrors(new FreeVariableException(r.getMapping(), psi.getAST(), true));
            return null;
        }

        for (Map.Entry<String, IASTExp> e : env.mapParent().entrySet()) {
            IFOLFormula formula = (IFOLFormula) formulas.get(e.getValue());

            if (formula.appearsFreeVariable(r.getMapping())) {
                r.appendErrors(new FreeVariableException(r.getMapping(), e.getValue(), false));
                return null;
            }
        }

        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTEExist r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        ASTExistential exi = (ASTExistential) r.getHyp1().getConclusion();
        IFOLFormula psi = (IFOLFormula) formulas.get(ExpUtils.removeParenthesis(exi.getRight()));
        IFOLFormula exp = (IFOLFormula) formulas.get(r.getConclusion());

        if (!exi.getLeft().equals(r.getMapping()) && psi.appearsFreeVariable(r.getMapping())) {
            r.appendErrors(new FreeVariableException(r.getMapping(), psi.getAST(), true));
            return null;
        }

        if (exp.appearsFreeVariable(r.getMapping())) {
            r.appendErrors(new FreeVariableException(r.getMapping(), exp.getAST(), true));
            return null;
        }

        for (Map.Entry<String, IASTExp> e : env.mapParent().entrySet()) {
            IFOLFormula formula = (IFOLFormula) formulas.get(e.getValue());

            if (!e.getValue().equals(r.getCloseM()) && formula != null && formula.appearsFreeVariable(r.getMapping())) {
                r.appendErrors(new FreeVariableException(r.getMapping(), e.getValue(), false));
                return null;
            }
        }

        r.getHyp1().accept(this, env);

        env = env.beginScope();
        if (r.getCloseM() != null)
            env.bind(r.getM(), r.getCloseM());
        r.getHyp2().accept(this, env);
        env.endScope();

        return null;
    }

}