package com.logic.nd.checkers;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.api.LogicAPI;
import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.ASTConditional;
import com.logic.exps.asts.binary.ASTExistential;
import com.logic.exps.asts.binary.ASTOr;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.interpreters.FOLReplaceExps;
import com.logic.nd.MarkEnv;
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

public class NDMarksChecker implements INDVisitor<Void, Env<String, MarkEnv>> {

    private final Map<IASTExp, IFormula> formulas;
    private final Map<String, IASTExp> premises;
    private final Map<String, IASTExp> hypotheses;

    NDMarksChecker(Map<IASTExp, IFormula> formulas, Map<String, IASTExp> premises,
                   Map<String, IASTExp> hypotheses) {
        this.formulas = formulas;
        this.premises = premises;
        this.hypotheses = hypotheses;
    }

    public static void check(IASTND nd, Map<IASTExp, IFormula> formulas, Map<String, IASTExp> premises,
                             Map<String, IASTExp> hypotheses) {
        NDMarksChecker checker = new NDMarksChecker(formulas, premises, hypotheses);

        nd.accept(checker, new Env<>());
    }

    @Override
    public Void visit(ASTHypothesis h, Env<String, MarkEnv> env) {
        if (h.hasErrors()) return null;

        IASTExp hyp = h.getConclusion();
        MarkEnv pair = env.findParent(h.getM());

        if (pair != null) { // Hypothesis is assigned to a rule
            IASTExp mark = pair.getExp();
            IASTND rule = pair.getRule();

            if (rule instanceof ASTEExist r && r.getCloseM() == null) {
                ASTExistential exi = (ASTExistential) r.getHyp1().getConclusion();
                ASTVariable x = (ASTVariable) exi.getLeft();

                IASTExp psi = ExpUtils.removeParenthesis(exi.getRight());
                IFOLFormula psiXY = (IFOLFormula) formulas.get(hyp);

                List<ASTVariable> variables = new ArrayList<>();
                variables.add(x); //It can be itself
                psiXY.iterateVariables().forEachRemaining(variables::add);

                IASTExp mappingEx = null;
                for (ASTVariable var : variables) {
                    mappingEx = FOLReplaceExps.replace(psi, x, var);
                    if (mappingEx.equals(hyp)) {
                        r.setMapping(var);
                        break;
                    }
                }

                if (mappingEx == null || hypotheses.containsKey(r.getM()) && !hypotheses.get(r.getM()).equals(mark)) {
                    r.appendErrors(new CloseMarkException(r.getM(), hyp));
                    return null;
                }

                mark = hyp;
                r.setCloseM(mark);

                if (r.getM() != null)
                    hypotheses.put(r.getM(), mappingEx);
            } else if (!mark.equals(hyp)) {

                if (rule instanceof ASTEExist r) {
                    ASTExistential exi = (ASTExistential) r.getHyp1().getConclusion();
                    ASTVariable x = (ASTVariable) exi.getLeft();

                    if (!mark.equals(FOLReplaceExps.replace(hyp, r.getMapping(), x))) {
                        //Mark is assigned to a different expression
                        h.appendErrors(new MarkAssignException(h.getM(), mark));
                    }

                    return null;
                } else if (!mark.equals(hyp)) {
                    //Mark is assigned to a different expression
                    h.appendErrors(new MarkAssignException(h.getM(), mark));
                    return null;
                }
                rule.appendErrors(new CloseMarkException(h.getM(), hyp));
                return null;
            }

            pair.setClosed(mark);
        } else if (h.getM() != null) { //Premise with mark
            IASTExp premise = premises.get(h.getM());
            if (premise != null && !premise.equals(hyp)) {
                //Premise is assigned to a different expression
                h.appendErrors(new MarkAssignException(h.getM(), premise));
                return null;
            }

            premises.put(h.getM(), hyp);
        } else if (!premises.containsValue(hyp)) { //Premise without mark
            premises.put(hyp.toString(), hyp);
        }

        return null;
    }

    @Override
    public Void visit(ASTIImp r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;

        ASTConditional cond = (ASTConditional) r.getConclusion();
        IASTExp mark = ExpUtils.removeParenthesis(cond.getLeft());

        env = env.beginScope();
        MarkEnv markEnv = new MarkEnv(r, mark);

        if (hypotheses.containsKey(r.getM()) && !hypotheses.get(r.getM()).equals(mark)) {
            r.appendErrors(new MarkAssignException(r.getM(), mark));
            return null;
        }

        if (r.getM() != null) {
            env.bind(r.getM(), markEnv);
            hypotheses.put(r.getM(), mark);
        }

        r.getHyp().accept(this, env);
        r.setCloseM(mark);
        env.endScope();

        return null;
    }

    @Override
    public Void visit(ASTINeg r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;

        ASTNot neg = (ASTNot) r.getConclusion();
        IASTExp mark = ExpUtils.removeParenthesis(neg.getExp());

        env = env.beginScope();
        MarkEnv markEnv = new MarkEnv(r, mark);

        if (hypotheses.containsKey(r.getM()) && !hypotheses.get(r.getM()).equals(mark)) {
            r.appendErrors(new MarkAssignException(r.getM(), mark));
            return null;
        }

        if (r.getM() != null) {
            env.bind(r.getM(), markEnv);
            hypotheses.put(r.getM(), mark);
        }

        r.getHyp().accept(this, env);
        r.setCloseM(mark);
        env.endScope();

        return null;
    }

    @Override
    public Void visit(ASTERConj r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTELConj r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIRDis r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTILDis r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTAbsurdity r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;

        IASTExp mark = ExpUtils.negate(r.getConclusion());

        env = env.beginScope();
        MarkEnv markEnv = new MarkEnv(r, mark);

        if (hypotheses.containsKey(r.getM()) && !hypotheses.get(r.getM()).equals(mark)) {
            r.appendErrors(new MarkAssignException(r.getM(), mark));
            return null;
        }

        if (r.getM() != null) {
            env.bind(r.getM(), markEnv);
            hypotheses.put(r.getM(), mark);
        }

        r.getHyp().accept(this, env);
        r.setCloseM(mark);
        env.endScope();

        return null;
    }

    @Override
    public Void visit(ASTIConj r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEDis r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;
        ASTOr or = (ASTOr) r.getHyp1().getConclusion();
        IASTExp left = ExpUtils.removeParenthesis(or.getLeft());
        IASTExp right = ExpUtils.removeParenthesis(or.getRight());

        r.getHyp1().accept(this, env);

        env = env.beginScope();
        MarkEnv markEnv = new MarkEnv(r, left);

        if (hypotheses.containsKey(r.getM()) && !hypotheses.get(r.getM()).equals(left)) {
            r.appendErrors(new MarkAssignException(r.getM(), left));
            return null;
        }

        if (r.getM() != null) {
            env.bind(r.getM(), markEnv);
            hypotheses.put(r.getM(), left);
        }

        r.getHyp2().accept(this, env);
        r.setCloseM(left);
        env = env.endScope();

        env = env.beginScope();
        markEnv = new MarkEnv(r, right);

        if (hypotheses.containsKey(r.getN()) && !hypotheses.get(r.getN()).equals(right)) {
            r.appendErrors(new MarkAssignException(r.getN(), right));
            return null;
        }
        hypotheses.put(r.getN(), right);

        if (r.getN() != null) {
            env.bind(r.getN(), markEnv);
            hypotheses.put(r.getN(), right);
        }

        r.getHyp3().accept(this, env);
        r.setCloseN(right);
        env.endScope();

        return null;
    }

    @Override
    public Void visit(ASTEImp r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);

        return null;
    }

    @Override
    public Void visit(ASTENeg r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;
        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);

        return null;
    }

    @Override
    public Void visit(ASTEUni r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIExist r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIUni r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTEExist r, Env<String, MarkEnv> env) {
        if (r.hasErrors()) return null;
        ASTExistential exi = (ASTExistential) r.getHyp1().getConclusion();
        IASTExp mark = ExpUtils.removeParenthesis(exi.getRight());
        r.getHyp1().accept(this, env);

        env = env.beginScope();
        MarkEnv markEnv = new MarkEnv(r, mark);

        if (r.getM() != null)
            env.bind(r.getM(), markEnv);
        r.getHyp2().accept(this, env);
        env.endScope();

        return null;
    }

}
