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

    NDMarksChecker(Map<IASTExp, IFormula> formulas, Map<String, IASTExp> hypotheses) {
        this.formulas = formulas;
        this.hypotheses = hypotheses;
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
        if (h.hasErrors()) return null;

        IASTExp hyp = h.getConclusion();

        if (hypotheses.containsKey(h.getM()) && !hypotheses.get(h.getM()).equals(hyp)) {
            h.appendErrors(new MarkAssignException(h.getM(), hyp)); //TODO change the params
            return null;
        }

        hypotheses.put(h.getM(), hyp);
        env.bind(h.getM(), hyp);

        return null;
    }

    /*@Override
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
    }*/

    @Override
    public Void visit(ASTIImp r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;

        ASTConditional cond = (ASTConditional) r.getConclusion();
        IASTExp mark = ExpUtils.removeParenthesis(cond.getLeft());

        env = env.beginScope();
        r.getHyp().accept(this, env);

        if (r.getM() != null) {
            IASTExp refMark = env.findChild(r.getM());

            if (refMark != null) {
                if (!refMark.equals(mark)) {
                    r.appendErrors(new CloseMarkException(r.getM(), mark));
                    return null;
                }

                r.setCloseM(mark);
                env.removeAllChildren(r.getM());
            }

        }

        env.endScope();

        return null;
    }

    @Override
    public Void visit(ASTINeg r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;

        ASTNot neg = (ASTNot) r.getConclusion();
        IASTExp mark = ExpUtils.removeParenthesis(neg.getExp());

        env = env.beginScope();
        r.getHyp().accept(this, env);

        if (r.getM() != null) {
            IASTExp refMark = env.findChild(r.getM());

            if (refMark != null) {
                if (!refMark.equals(mark)) {
                    r.appendErrors(new CloseMarkException(r.getM(), mark));
                    return null;
                }
                r.setCloseM(mark);
                env.removeAllChildren(r.getM());
            }

        }

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

        IASTExp mark = ExpUtils.negate(r.getConclusion());

        env = env.beginScope();
        r.getHyp().accept(this, env);

        if (r.getM() != null) {
            IASTExp refMark = env.findChild(r.getM());

            if (refMark != null) {
                if (!refMark.equals(mark)) {
                    r.appendErrors(new CloseMarkException(r.getM(), mark));
                    return null;
                }
                r.setCloseM(mark);
                env.removeAllChildren(r.getM());
            }
        }

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
        ASTOr or = (ASTOr) r.getHyp1().getConclusion();
        IASTExp left = ExpUtils.removeParenthesis(or.getLeft());
        IASTExp right = ExpUtils.removeParenthesis(or.getRight());

        r.getHyp1().accept(this, env);

        env = env.beginScope();
        r.getHyp2().accept(this, env);

        if (r.getM() != null) {
            IASTExp refMark = env.findChild(r.getM());

            if (refMark != null) {
                if (!refMark.equals(left)) {
                    r.appendErrors(new CloseMarkException(r.getM(), left));
                    return null;
                }
                r.setCloseM(left);
                env.removeAllChildren(r.getM());
            }
        }

        env = env.endScope();

        env = env.beginScope();
        r.getHyp3().accept(this, env);

        if (r.getN() != null) {
            IASTExp refMark = env.findChild(r.getN());

            if (refMark != null) {
                if (!refMark.equals(right)) {
                    r.appendErrors(new CloseMarkException(r.getN(), right));
                    return null;
                }

                r.setCloseN(right);
                env.removeAllChildren(r.getN());
            }
        }

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
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIExist r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIUni r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTEExist r, Env<String, IASTExp> env) {
        if (r.hasErrors()) return null;
        ASTExistential exi = (ASTExistential) r.getHyp1().getConclusion();
        IASTExp mark = ExpUtils.removeParenthesis(exi.getRight());
        r.getHyp1().accept(this, env);

        env = env.beginScope();
        r.getHyp2().accept(this, env);

        if (r.getM() != null) {
            IASTExp refMark = env.findChild(r.getM());

            if (refMark != null) {
                ASTVariable x = (ASTVariable) exi.getLeft();
                IASTExp psi = ExpUtils.removeParenthesis(exi.getRight());
                IFOLFormula psiXY = (IFOLFormula) formulas.get(refMark);

                List<ASTVariable> variables = new ArrayList<>();
                variables.add(x);
                psiXY.iterateVariables().forEachRemaining(variables::add);

                IASTExp mappingEx = null;
                for (ASTVariable var : variables) {
                    mappingEx = FOLReplaceExps.replace(psi, x, var);
                    if (mappingEx.equals(refMark)) {
                        r.setMapping(var);
                        break;
                    }
                }

                if (mappingEx == null) {
                    r.appendErrors(new CloseMarkException(r.getM(), mark));
                    return null;
                }

                r.setCloseM(mappingEx);
                env.removeAllChildren(r.getM());
            }

        }

        env.endScope();

        return null;
    }


}
