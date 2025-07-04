package com.logic.nd.checkers;

import com.logic.api.IFormula;
import com.logic.api.INDProof;
import com.logic.exps.asts.IASTExp;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;
import com.logic.nd.asts.binary.ASTEExist;
import com.logic.nd.asts.binary.ASTEImp;
import com.logic.nd.asts.binary.ASTENeg;
import com.logic.nd.asts.binary.ASTIConj;
import com.logic.nd.asts.others.ASTEDis;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.*;
import com.logic.nd.exceptions.ConclusionException;
import com.logic.others.Env;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class NDProblemChecker implements INDVisitor<Void, Env<String, IASTExp>> {

    private final Set<IASTExp> premises;
    private final Map<ASTHypothesis, Env<String, IASTExp>> rules;

    NDProblemChecker(Set<IASTExp> premises) {
        this.premises = premises;
        this.rules = new HashMap<>();
    }

    public static INDProof solve(INDProof proof, Set<IFormula> premises, IFormula conclusion) {
        IASTND proofAST = proof.getAST();

        NDProblemChecker interpreter = new NDProblemChecker(premises.stream()
                .map(IFormula::getAST).collect(Collectors.toSet()));
        proofAST.accept(interpreter, new Env<>());

        if (!proofAST.getConclusion().equals(conclusion.getAST()) || !interpreter.rules.isEmpty()) {
            Set<IFormula> premisesSet = new HashSet<>();
            proof.getPremises().forEachRemaining(premisesSet::add);

            throw new ConclusionException(proof.getAST(), premises, conclusion, premisesSet, proof.getConclusion(),
                    interpreter.rules);
        }

        return proof;
    }


    @Override
    public Void visit(ASTHypothesis h, Env<String, IASTExp> env) {
        if (h.getM() != null && env.findParent(h.getM()) != null)
            return null;

        if (!premises.contains(h.getConclusion()))
            rules.put(h, env);

        return null;
    }

    @Override
    public Void visit(ASTIImp r, Env<String, IASTExp> env) {
        env = env.beginScope();
        env.bind(r.getM(), r.getCloseM());
        r.getHyp().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTINeg r, Env<String, IASTExp> env) {
        env = env.beginScope();
        env.bind(r.getM(), r.getCloseM());
        r.getHyp().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTERConj r, Env<String, IASTExp> env) {
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTELConj r, Env<String, IASTExp> env) {
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTIRDis r, Env<String, IASTExp> env) {
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTILDis r, Env<String, IASTExp> env) {
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTAbsurdity r, Env<String, IASTExp> env) {
        env = env.beginScope();
        env.bind(r.getM(), r.getCloseM());
        r.getHyp().accept(this, env);
        env.endScope();
        return null;
    }

    @Override
    public Void visit(ASTIConj r, Env<String, IASTExp> env) {
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEDis r, Env<String, IASTExp> env) {
        r.getHyp1().accept(this, env);

        env = env.beginScope();
        env.bind(r.getM(), r.getCloseM());
        r.getHyp2().accept(this, env);
        env = env.endScope();

        env = env.beginScope();
        env.bind(r.getN(), r.getCloseN());
        r.getHyp3().accept(this, env);
        env.endScope();

        return null;
    }

    @Override
    public Void visit(ASTEImp r, Env<String, IASTExp> env) {
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTENeg r, Env<String, IASTExp> env) {
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEUni r, Env<String, IASTExp> env) {
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTIExist r, Env<String, IASTExp> env) {
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTIUni r, Env<String, IASTExp> env) {
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEExist r, Env<String, IASTExp> env) {
        env = env.beginScope();
        env.bind(r.getM(), r.getCloseM());
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        env.endScope();
        return null;
    }
}
