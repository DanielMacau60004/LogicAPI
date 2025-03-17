package com.logic.nd.interpreters;

import com.logic.api.INDProof;
import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.ASTAnd;
import com.logic.exps.asts.binary.ASTConditional;
import com.logic.exps.asts.binary.ASTOr;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.nd.NDProof;
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
import com.logic.others.Env;

import java.util.HashMap;
import java.util.Map;

public class NDInterpreter implements INDVisitor<Integer, Env<Integer, IASTExp>> {

    private final Map<Integer, IASTExp> premises;
    private int size;

    NDInterpreter() {
        this.premises = new HashMap<>();
        this.size = 0;
    }

    //TODO Maybe it's not correct to compute the height and size of the solution in this interpreter
    public static INDProof interpret(IASTND nd) {
        NDInterpreter interpret = new NDInterpreter();

        int height = nd.accept(interpret, new Env<>());
        return new NDProof(interpret.premises, nd, height, interpret.size);
    }

    @Override
    public Integer visit(ASTPremiseND p, Env<Integer, IASTExp> env) {
        IASTExp mark = premises.get(p.getM());
        if (mark != null && mark != p.getConclusion())
            throw new RuntimeException("Invalid hypothesis!");

        premises.put(p.getM(), p.getConclusion());

        size++;
        return 1;
    }

    @Override
    public Integer visit(ASTHypothesisND h, Env<Integer, IASTExp> env) {
        IASTExp existingMark = env.find(h.getM());

        if (existingMark == null)
            throw new RuntimeException("Mark not found");

        if (!existingMark.equals(h.getConclusion()))
            throw new RuntimeException("Invalid mark " + h.getM());

        size++;
        return 1;
    }

    @Override
    public Integer visit(ASTIImpND r, Env<Integer, IASTExp> env) {
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
    public Integer visit(ASTINegND r, Env<Integer, IASTExp> env) {
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
    public Integer visit(ASTERConjND r, Env<Integer, IASTExp> env) {
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
    public Integer visit(ASTELConjND r, Env<Integer, IASTExp> env) {
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
    public Integer visit(ASTIRDisND r, Env<Integer, IASTExp> env) {
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
    public Integer visit(ASTILDisND r, Env<Integer, IASTExp> env) {
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
    public Integer visit(ASTAbsurdityND r, Env<Integer, IASTExp> env) {
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
    public Integer visit(ASTIConjND r, Env<Integer, IASTExp> env) {
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
    public Integer visit(ASTEDisjND r, Env<Integer, IASTExp> env) {
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
    public Integer visit(ASTEImpND r, Env<Integer, IASTExp> env) {
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
    public Integer visit(ASTENegND r, Env<Integer, IASTExp> env) {
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
    public Integer visit(ASTEUniND r, Env<Integer, IASTExp> env) {
        return 0;
    }

    @Override
    public Integer visit(ASTIExistND r, Env<Integer, IASTExp> env) {
        return 0;
    }

    @Override
    public Integer visit(ASTIUniND r, Env<Integer, IASTExp> env) {
        return 0;
    }

    @Override
    public Integer visit(ASTEExistND r, Env<Integer, IASTExp> env) {
        return 0;
    }

}
