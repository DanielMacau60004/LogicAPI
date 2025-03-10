package com.logic.nd.interpreters;

import com.logic.exps.ExpsLogic;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.ASTAnd;
import com.logic.exps.asts.binary.ASTConditional;
import com.logic.exps.asts.binary.ASTOr;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;
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

public class NDInterpreter implements INDVisitor<Void, Env<Integer, IASTExp>> {

    private final Map<Integer, IASTExp> premises;

    NDInterpreter() {
        this.premises = new HashMap<>();
    }

    public static void interpret(IASTND nd) {
        NDInterpreter interpret = new NDInterpreter();
        nd.accept(interpret, new Env<>());

        //TODO temporary
        System.out.println(interpret.premises + " |= " + nd.getConclusion());
    }

    @Override
    public Void visit(ASTPremiseND p, Env<Integer, IASTExp> env) {
        IASTExp mark = premises.get(p.getM());
        if (mark != null && mark != p.getConclusion())
            throw new RuntimeException("Invalid hypothesis!");

        premises.put(p.getM(), p.getConclusion());
        return null;
    }

    @Override
    public Void visit(ASTHypothesisND h, Env<Integer, IASTExp> env) {
        IASTExp existingMark = env.find(h.getM());

        if (existingMark == null)
            throw new RuntimeException("Mark not found");

        if (!existingMark.equals(h.getConclusion()))
            throw new RuntimeException("Invalid mark " + h.getM());
        return null;
    }

    @Override
    public Void visit(ASTIImpND r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTConditional cond))
            throw new RuntimeException("The introduction of the implication rule is incorrectly typed!");

        IASTExp left = ExpsLogic.removeParenthesis(cond.getLeft());
        IASTExp right = ExpsLogic.removeParenthesis(cond.getRight());

        if (!right.equals(r.getHyp().getConclusion()))
            throw new RuntimeException("The introduction of the implication rule is incorrectly typed! 2 ");

        env = env.beginScope();
        env.bind(r.getM(), left);
        r.getHyp().accept(this, env);
        env.endScope();

        return null;
    }

    @Override
    public Void visit(ASTINegND r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTNot neg))
            throw new RuntimeException("The introduction of the negation rule is incorrectly typed!");

        if (!r.getHyp().getConclusion().equals(ExpsLogic.BOT))
            throw new RuntimeException("The introduction of the negation rule is incorrectly typed!");

        env = env.beginScope();
        env.bind(r.getM(), ExpsLogic.removeParenthesis(neg.getExp()));
        r.getHyp().accept(this, env);
        env.endScope();

        return null;
    }

    @Override
    public Void visit(ASTERConjND r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(r.getHyp().getConclusion() instanceof ASTAnd and))
            throw new RuntimeException("The elimination of the conjunction right rule is incorrectly typed!");

        IASTExp left = ExpsLogic.removeParenthesis(and.getLeft());
        if (!left.equals(exp))
            throw new RuntimeException("The elimination of the conjunction right rule is incorrectly typed!");

        r.getHyp().accept(this, env);

        return null;
    }

    @Override
    public Void visit(ASTELConjND r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(r.getHyp().getConclusion() instanceof ASTAnd and))
            throw new RuntimeException("The elimination of the conjunction left rule is incorrectly typed!");

        IASTExp right = ExpsLogic.removeParenthesis(and.getRight());
        if (!right.equals(exp))
            throw new RuntimeException("The elimination of the conjunction left rule is incorrectly typed!");

        r.getHyp().accept(this, env);

        return null;
    }

    @Override
    public Void visit(ASTIRDisND r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTOr or))
            throw new RuntimeException("The introduction of the disjunction right rule is incorrectly typed!");

        IASTExp left = ExpsLogic.removeParenthesis(or.getLeft());
        if (!left.equals(r.getHyp().getConclusion()))
            throw new RuntimeException("The introduction of the disjunction right rule is incorrectly typed!");

        r.getHyp().accept(this, env);

        return null;
    }

    @Override
    public Void visit(ASTILDisND r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTOr or))
            throw new RuntimeException("The introduction of the disjunction left rule is incorrectly typed!");

        IASTExp right = ExpsLogic.removeParenthesis(or.getRight());
        if (!right.equals(r.getHyp().getConclusion()))
            throw new RuntimeException("The introduction of the disjunction left rule is incorrectly typed!");

        r.getHyp().accept(this, env);

        return null;
    }

    @Override
    public Void visit(ASTAbsurdityND r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!r.getHyp().getConclusion().equals(ExpsLogic.BOT))
            throw new RuntimeException("The absurdity rule is incorrectly typed!");

        env = env.beginScope();
        env.bind(r.getM(), ExpsLogic.negate(exp));
        r.getHyp().accept(this, env);
        env.endScope();

        return null;
    }

    @Override
    public Void visit(ASTIConjND r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTAnd and))
            throw new RuntimeException("The introduction of the conjunction rule is incorrectly typed!");

        IASTExp left = ExpsLogic.removeParenthesis(and.getLeft());
        IASTExp right = ExpsLogic.removeParenthesis(and.getRight());

        if (!left.equals(r.getHyp1().getConclusion()) || !right.equals(r.getHyp2().getConclusion()))
            throw new RuntimeException("The introduction of the conjunction rule is incorrectly typed!");

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);

        return null;
    }

    @Override
    public Void visit(ASTEDisjND r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        ASTOr or = null;
        IASTND orND = null, left = null, right = null;

        if (r.getHyp1().getConclusion() instanceof ASTOr orH) {
            or = orH;
            orND = r.getHyp1();
            left = r.getHyp2();
            right = r.getHyp3();
        } else if (r.getHyp2().getConclusion() instanceof ASTOr orH) {
            or = orH;
            orND = r.getHyp2();
            left = r.getHyp1();
            right = r.getHyp3();
        } else if (r.getHyp3().getConclusion() instanceof ASTOr orH) {
            or = orH;
            orND = r.getHyp3();
            left = r.getHyp1();
            right = r.getHyp2();
        }

        if (or == null)
            throw new RuntimeException("The elimination of the conjunction rule is incorrectly typed!");

        orND.accept(this, env);

        env = env.beginScope();
        env.bind(r.getM(), ExpsLogic.removeParenthesis(or.getLeft()));
        left.accept(this, env);
        env.endScope();

        env = env.beginScope();
        env.bind(r.getN(), ExpsLogic.removeParenthesis(or.getRight()));
        right.accept(this, env);
        env.endScope();

        return null;
    }

    @Override
    public Void visit(ASTEImpND r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        ASTConditional imp = null;
        IASTExp other = null;

        if (r.getHyp1().getConclusion() instanceof ASTConditional cond) {
            imp = cond;
            other = r.getHyp2().getConclusion();
        } else if (r.getHyp2().getConclusion() instanceof ASTConditional cond) {
            imp = cond;
            other = r.getHyp1().getConclusion();
        }

        if (imp == null)
            throw new RuntimeException("The elimination of the implication rule is incorrectly typed!");

        IASTExp left = ExpsLogic.removeParenthesis(imp.getLeft());
        IASTExp right = ExpsLogic.removeParenthesis(imp.getRight());

        if (!left.equals(other) || !right.equals(exp))
            throw new RuntimeException("The elimination of the implication rule is incorrectly typed!");

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);

        return null;
    }

    @Override
    public Void visit(ASTENegND r, Env<Integer, IASTExp> env) {
        IASTExp exp = r.getConclusion();

        if (!exp.equals(ExpsLogic.BOT))
            throw new RuntimeException("The elimination of the negation rule is incorrectly typed!");

        if (!r.getHyp1().getConclusion().equals(ExpsLogic.negate(r.getHyp2().getConclusion())) &&
                !r.getHyp2().getConclusion().equals(ExpsLogic.negate(r.getHyp1().getConclusion())))
            throw new RuntimeException("The elimination of the negation rule is incorrectly typed!");

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);

        return null;
    }

}
