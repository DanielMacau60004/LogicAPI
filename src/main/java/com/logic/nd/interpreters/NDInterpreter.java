package com.logic.nd.interpreters;

import com.logic.api.IFormula;
import com.logic.api.INDProof;
import com.logic.exps.asts.IASTExp;
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
import com.logic.nd.checkers.NDMarksChecker;
import com.logic.nd.checkers.NDWWFChecker;
import com.logic.nd.checkers.NDWWFExpsChecker;
import com.logic.others.Env;

import java.util.Map;

public class NDInterpreter implements INDVisitor<Integer, Env<Integer, IASTExp>> {

    private int size;

    NDInterpreter() {
        this.size = 0;
    }

    public static INDProof interpret(IASTND nd, Map<IASTExp, IFormula> formulas, Map<IASTExp, Integer> premises) {
        NDInterpreter interpret = new NDInterpreter();
        int height = nd.accept(interpret, new Env<>());
        return new NDProof(formulas.get(nd.getConclusion()), premises, nd, height, interpret.size);
    }

    @Override
    public Integer visit(ASTHypothesis h, Env<Integer, IASTExp> env) {
        size++;
        return 1;
    }

    @Override
    public Integer visit(ASTIImp r, Env<Integer, IASTExp> env) {
        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTINeg r, Env<Integer, IASTExp> env) {
        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTERConj r, Env<Integer, IASTExp> env) {
        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTELConj r, Env<Integer, IASTExp> env) {
        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTIRDis r, Env<Integer, IASTExp> env) {
        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTILDis r, Env<Integer, IASTExp> env) {
        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTAbsurdity r, Env<Integer, IASTExp> env) {
        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTIConj r, Env<Integer, IASTExp> env) {
        size++;
        return Math.max(r.getHyp1().accept(this, env), r.getHyp2().accept(this, env)) + 1;
    }

    @Override
    public Integer visit(ASTEDisj r, Env<Integer, IASTExp> env) {
        size++;
        return Math.max(r.getHyp1().accept(this, env),
                Math.max(r.getHyp2().accept(this, env),  r.getHyp3().accept(this, env))) + 1;
    }

    @Override
    public Integer visit(ASTEImp r, Env<Integer, IASTExp> env) {
        size++;
        return Math.max(r.getHyp1().accept(this, env), r.getHyp2().accept(this, env)) + 1;
    }

    @Override
    public Integer visit(ASTENeg r, Env<Integer, IASTExp> env) {
        size++;
        return Math.max(r.getHyp1().accept(this, env), r.getHyp2().accept(this, env)) + 1;
    }

    @Override
    public Integer visit(ASTEUni r, Env<Integer, IASTExp> env) {
        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTIExist r, Env<Integer, IASTExp> env) {
        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTIUni r, Env<Integer, IASTExp> env) {
        size++;
        return r.getHyp().accept(this, env) + 1;
    }

    @Override
    public Integer visit(ASTEExist r, Env<Integer, IASTExp> env) {
        size++;
        return Math.max(r.getHyp1().accept(this, env), r.getHyp2().accept(this, env)) + 1;
    }

}
