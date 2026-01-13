package com.logic.nd.checkers;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.ASTConditional;
import com.logic.exps.asts.binary.ASTExistential;
import com.logic.exps.asts.binary.ASTOr;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.interpreters.FOLReplaceExps;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;
import com.logic.nd.asts.binary.ASTEExist;
import com.logic.nd.asts.binary.ASTEImp;
import com.logic.nd.asts.binary.ASTENeg;
import com.logic.nd.asts.binary.ASTIConj;
import com.logic.nd.asts.others.ASTEDis;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.*;
import com.logic.nd.exceptions.CloseMarkException;
import com.logic.nd.exceptions.MarkAssignException;
import com.logic.others.Env;
import com.logic.others.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NDMarksChecker implements INDVisitor<Void, Env<String, IASTExp>> {

    private final Map<IASTExp, IFormula> formulas;
    private final Map<String, IASTExp> hypotheses;
    private Env<String, IASTExp> marksEnv;

    NDMarksChecker(Map<IASTExp, IFormula> formulas, Map<String, IASTExp> hypotheses) {
        this.formulas = formulas;
        this.hypotheses = hypotheses;
        this.marksEnv = new Env<>();
    }

    public static void check(IASTND nd, Map<IASTExp, IFormula> formulas, Map<String, IASTExp> premises,
                             Map<String, IASTExp> hypotheses) {
        NDMarksChecker checker = new NDMarksChecker(formulas, hypotheses);
        Env<String, IASTExp> env = new Env<>();
        nd.accept(checker, env);

        premises.putAll(env.mapChild());
    }

    @Override
    public Void visit(ASTHypothesis h, Env<String, IASTExp> env) {
        IASTExp hyp = h.getConclusion();
        h.setEnv(marksEnv);

        if (h.getM() != null && hypotheses.containsKey(h.getM()) && !hypotheses.get(h.getM()).equals(hyp))
            throw new MarkAssignException(h, h.getM(), hyp, hypotheses.get(h.getM()), env);

        hypotheses.put(h.getM(), hyp);
        env.bind(h.getM(), hyp);
        return null;
    }

    @Override
    public Void visit(ASTIImp r, Env<String, IASTExp> env) {
        ASTConditional cond = (ASTConditional) r.getConclusion();
        IASTExp mark = cond.getLeft();

        r.setEnv(marksEnv);
        marksEnv = marksEnv.beginScope();
        env = env.beginScope();
        marksEnv.bind(r.getM() != null ? r.getM() : mark.toString(), mark);

        r.getHyp().accept(this, env);

        if (r.getM() != null) {
            IASTExp refMark = env.findChild(r.getM());

            if ((hypotheses.containsKey(r.getM()) && !mark.equals(hypotheses.get(r.getM()))) ||
                    (refMark != null && !mark.equals(refMark)))
                throw new CloseMarkException(r, r.getM(), hypotheses.get(r.getM()), mark, env);

            r.setCloseM(mark);
            env.removeAllChildren(r.getM());

        }

        env.endScope();
        marksEnv = marksEnv.endScope();
        return null;
    }

    @Override
    public Void visit(ASTINeg r, Env<String, IASTExp> env) {
        ASTNot neg = (ASTNot) r.getConclusion();
        IASTExp mark = neg.getExp();

        r.setEnv(marksEnv);
        marksEnv = marksEnv.beginScope();
        env = env.beginScope();
        marksEnv.bind(r.getM() != null ? r.getM() : mark.toString(), mark);
        r.getHyp().accept(this, env);

        if (r.getM() != null) {
            IASTExp refMark = env.findChild(r.getM());

            if ((hypotheses.containsKey(r.getM()) && !mark.equals(hypotheses.get(r.getM()))) ||
                    (refMark != null && !mark.equals(refMark)))
                throw new CloseMarkException(r, r.getM(), hypotheses.get(r.getM()), mark, env);

            r.setCloseM(mark);
            env.removeAllChildren(r.getM());

        }

        env.endScope();
        marksEnv = marksEnv.endScope();
        return null;
    }

    @Override
    public Void visit(ASTERConj r, Env<String, IASTExp> env) {
        r.setEnv(marksEnv);
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTELConj r, Env<String, IASTExp> env) {
        r.setEnv(marksEnv);
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTIRDis r, Env<String, IASTExp> env) {
        r.setEnv(marksEnv);
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTILDis r, Env<String, IASTExp> env) {
        r.setEnv(marksEnv);
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTAbsurdity r, Env<String, IASTExp> env) {
        IASTExp mark = ExpUtils.negate(r.getConclusion());

        r.setEnv(marksEnv);
        marksEnv = marksEnv.beginScope();
        env = env.beginScope();
        marksEnv.bind(r.getM() != null ? r.getM() : mark.toString(), mark);
        r.getHyp().accept(this, env);

        if (r.getM() != null) {
            IASTExp refMark = env.findChild(r.getM());

            if ((hypotheses.containsKey(r.getM()) && !mark.equals(hypotheses.get(r.getM()))) ||
                    (refMark != null && !mark.equals(refMark)))
                throw new CloseMarkException(r, r.getM(), hypotheses.get(r.getM()), mark, env);
            r.setCloseM(mark);
            env.removeAllChildren(r.getM());

        }

        env.endScope();
        marksEnv = marksEnv.endScope();
        return null;
    }

    @Override
    public Void visit(ASTIConj r, Env<String, IASTExp> env) {
        r.setEnv(marksEnv);
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEDis r, Env<String, IASTExp> env) {
        ASTOr or = (ASTOr) r.getHyp1().getConclusion();
        IASTExp left = or.getLeft();
        IASTExp right = or.getRight();

        r.setEnv(marksEnv);
        r.getHyp1().accept(this, env);

        marksEnv = marksEnv.beginScope();
        env = env.beginScope();
        marksEnv.bind(r.getM() != null ? r.getM() : left.toString(), left);
        r.getHyp2().accept(this, env);

        if (r.getM() != null) {
            IASTExp refMark = env.findChild(r.getM());

            if ((hypotheses.containsKey(r.getM()) && !left.equals(hypotheses.get(r.getM()))) ||
                    (refMark != null && !left.equals(refMark)))
                throw new CloseMarkException(r, r.getM(), hypotheses.get(r.getM()), left, env);

            r.setCloseM(left);
            env.removeAllChildren(r.getM());

        }

        env = env.endScope();
        marksEnv = marksEnv.endScope();

        marksEnv = marksEnv.beginScope();
        env = env.beginScope();
        marksEnv.bind(r.getN() != null ? r.getN() : right.toString(), right);
        r.getHyp3().accept(this, env);

        if (r.getN() != null) {
            IASTExp refMark = env.findChild(r.getN());

            if ((hypotheses.containsKey(r.getN()) && !right.equals(hypotheses.get(r.getN()))) ||
                    (refMark != null && !right.equals(refMark)))
                throw new CloseMarkException(r, r.getN(), hypotheses.get(r.getN()), right, env);

            r.setCloseN(right);
            env.removeAllChildren(r.getN());

        }

        env.endScope();
        marksEnv = marksEnv.endScope();

        return null;
    }

    @Override
    public Void visit(ASTEImp r, Env<String, IASTExp> env) {
        r.setEnv(marksEnv);
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTENeg r, Env<String, IASTExp> env) {
        r.setEnv(marksEnv);
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEUni r, Env<String, IASTExp> env) {
        r.setEnv(marksEnv);
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTIExist r, Env<String, IASTExp> env) {
        r.setEnv(marksEnv);
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTIUni r, Env<String, IASTExp> env) {
        r.setEnv(marksEnv);
        r.getHyp().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEExist r, Env<String, IASTExp> env) {
        ASTExistential exi = (ASTExistential) r.getHyp1().getConclusion();
        r.getHyp1().accept(this, env);

        r.setEnv(marksEnv);

        ASTVariable replaceable = new ASTVariable("?");
        IASTExp mark = FOLReplaceExps.replace(exi.getRight()
                , exi.getLeft(), replaceable);
        r.setCloseM(mark);
        r.setMapping(replaceable);

        marksEnv = marksEnv.beginScope();
        env = env.beginScope();
        marksEnv.bind(r.getM() != null ? r.getM() : mark.toString(), mark);
        r.getHyp2().accept(this, env);

        if (r.getM() != null) {
            IASTExp refMark = env.findChild(r.getM());
            IASTExp mappingEx = null;

            if (refMark != null) {
                ASTVariable x = (ASTVariable) exi.getLeft();
                IASTExp psi = exi.getRight();
                IFOLFormula psiXY = (IFOLFormula) formulas.get(refMark);

                List<ASTVariable> variables = new ArrayList<>();
                variables.add(x);
                psiXY.iterateVariables().forEachRemaining(variables::add);

                for (ASTVariable var : variables) {
                    mappingEx = FOLReplaceExps.replace(psi, x, var);
                    if (mappingEx.equals(refMark)) {
                        r.setMapping(var);
                        break;
                    }
                }

                r.setCloseM(mappingEx);
                marksEnv.unbind(mark.toString());
                marksEnv.bind(r.getM(), mappingEx);
                env.removeAllChildren(r.getM());
            }

            if ((refMark != null && r.getMapping().equals(replaceable)))
                throw new CloseMarkException(r, r.getM(), null, mark, env);
        }

        env.endScope();
        marksEnv = marksEnv.endScope();

        return null;
    }


}
