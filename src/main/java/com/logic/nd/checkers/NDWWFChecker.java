package com.logic.nd.checkers;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.interpreters.FOLReplaceExps;
import com.logic.exps.interpreters.FOLWFFInterpreter;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.INDVisitor;
import com.logic.nd.asts.binary.ASTEExist;
import com.logic.nd.asts.binary.ASTEImp;
import com.logic.nd.asts.binary.ASTENeg;
import com.logic.nd.asts.binary.ASTIConj;
import com.logic.nd.asts.others.ASTEDis;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.*;
import com.logic.nd.exceptions.NDRuleException;
import com.logic.nd.exceptions.InvalidMappingException;

import java.util.*;

public class NDWWFChecker implements INDVisitor<Void, Void> {

    private final Map<IASTExp, IFormula> formulas;

    NDWWFChecker(Map<IASTExp, IFormula> formulas) {
        this.formulas = formulas;
    }

    public static void check(IASTND nd, Map<IASTExp, IFormula> formulas) {
        NDWWFChecker checker = new NDWWFChecker(formulas);
        nd.accept(checker, null);
    }

    @Override
    public Void visit(ASTHypothesis h, Void env) {
        return null;
    }

    @Override
    public Void visit(ASTIImp r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTConditional cond))
            throw new NDRuleException(r);

        IASTExp right = cond.getRight();

        if (!right.equals(r.getHyp().getConclusion()))
            throw new NDRuleException(r);

        r.getHyp().setParent(r);
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTINeg r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTNot) || !r.getHyp().getConclusion().equals(ExpUtils.BOT))
            throw new NDRuleException(r);

        r.getHyp().setParent(r);
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTERConj r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(r.getHyp().getConclusion() instanceof ASTAnd and))
            throw new NDRuleException(r);

        IASTExp left = and.getLeft();
        if (!left.equals(exp))
            throw new NDRuleException(r);

        r.getHyp().setParent(r);
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTELConj r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(r.getHyp().getConclusion() instanceof ASTAnd and))
            throw new NDRuleException(r);

        IASTExp right = and.getRight();
        if (!right.equals(exp))
            throw new NDRuleException(r);

        r.getHyp().setParent(r);
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIRDis r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTOr or))
            throw new NDRuleException(r);

        IASTExp left = or.getLeft();
        if (!left.equals(r.getHyp().getConclusion()))
            throw new NDRuleException(r);

        r.getHyp().setParent(r);
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTILDis r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTOr or))
            throw new NDRuleException(r);

        IASTExp right = or.getRight();
        if (!right.equals(r.getHyp().getConclusion()))
            throw new NDRuleException(r);

        r.getHyp().setParent(r);
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTAbsurdity r, Void env) {
        if (!r.getHyp().getConclusion().equals(ExpUtils.BOT))
            throw new NDRuleException(r);

        r.getHyp().setParent(r);
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIConj r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(exp instanceof ASTAnd and))
            throw new NDRuleException(r);

        IASTExp left = and.getLeft();
        IASTExp right = and.getRight();

        if (!left.equals(r.getHyp1().getConclusion()) || !right.equals(r.getHyp2().getConclusion()))
            throw new NDRuleException(r);

        r.getHyp1().setParent(r);
        r.getHyp2().setParent(r);

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        return null;
    }

    @Override
    public Void visit(ASTEDis r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!(r.getHyp1().getConclusion() instanceof ASTOr or))
            throw new NDRuleException(r);

        IASTExp left = r.getHyp2().getConclusion();
        IASTExp right = r.getHyp3().getConclusion();

        if (!exp.equals(left) || !exp.equals(right))
            throw new NDRuleException(r);

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);
        r.getHyp3().accept(this, env);

        r.getHyp1().setParent(r);
        r.getHyp2().setParent(r);
        r.getHyp3().setParent(r);
        return null;
    }

    @Override
    public Void visit(ASTEImp r, Void env) {
        IASTExp exp = r.getConclusion();

        IASTExp other = r.getHyp1().getConclusion();
        if (!(r.getHyp2().getConclusion() instanceof ASTConditional imp))
            throw new NDRuleException(r);

        IASTExp left = imp.getLeft();
        IASTExp right = imp.getRight();
        if (!left.equals(other) || !right.equals(exp))
            throw new NDRuleException(r);

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);

        r.getHyp1().setParent(r);
        r.getHyp2().setParent(r);
        return null;
    }

    @Override
    public Void visit(ASTENeg r, Void env) {
        IASTExp exp = r.getConclusion();

        if (!exp.equals(ExpUtils.BOT))
            throw new NDRuleException(r);

        IASTExp leftNot = ExpUtils.invert(r.getHyp2().getConclusion());
        IASTExp rightNot = ExpUtils.invert(r.getHyp1().getConclusion());
        if (!r.getHyp1().getConclusion().equals(leftNot) &&
                !r.getHyp2().getConclusion().equals(rightNot))
            throw new NDRuleException(r);

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);

        r.getHyp1().setParent(r);
        r.getHyp2().setParent(r);
        return null;
    }

    @Override
    public Void visit(ASTEUni r, Void env) {
        if (!(r.getHyp().getConclusion() instanceof ASTUniversal uni))
            throw new NDRuleException(r);

        //Find mapping
        ASTVariable x = (ASTVariable) uni.getLeft();
        IASTExp psi = uni.getRight();
        IFOLFormula psiXT = (IFOLFormula) formulas.get(r.getConclusion());

        List<AASTTerm> terms = new ArrayList<>();
        terms.add(x);
        psiXT.iterateTerms().forEachRemaining(terms::add);

        Set<IASTExp> outcomes = new HashSet<>();
        if (r.getMapping() == null)
            for (AASTTerm term : terms) {

                IASTExp cExp = FOLReplaceExps.replace(psi, x, term);
                outcomes.add(cExp);
                if (cExp.equals(psiXT.getAST())) {
                    r.setMapping(term);
                    break;
                }
            }

        if (r.getMapping() == null)
            throw new InvalidMappingException(r, x, psi, psiXT.getAST(), outcomes);

        formulas.put(psi, FOLWFFInterpreter.check(psi));

        r.getHyp().setParent(r);
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIExist r, Void env) {
        if (!(r.getConclusion() instanceof ASTExistential exi))
            throw new NDRuleException(r);

        //Find mapping
        ASTVariable x = (ASTVariable) exi.getLeft();
        IASTExp psi = exi.getRight();
        IFOLFormula psiXT = (IFOLFormula) formulas.get(r.getHyp().getConclusion());

        List<AASTTerm> terms = new ArrayList<>();
        terms.add(x);
        psiXT.iterateTerms().forEachRemaining(terms::add);

        Set<IASTExp> outcomes = new HashSet<>();
        if (r.getMapping() == null)
            for (AASTTerm term : terms) {

                IASTExp cExp = FOLReplaceExps.replace(psi, x, term);
                outcomes.add(cExp);
                if (cExp.equals(psiXT.getAST())) {
                    r.setMapping(term);
                    break;
                }
            }

        if (r.getMapping() == null)
            throw new InvalidMappingException(r, x, psi, psiXT.getAST(), outcomes);

        formulas.put(psi, FOLWFFInterpreter.check(psi));

        r.getHyp().setParent(r);
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTIUni r, Void env) {
        if (!(r.getConclusion() instanceof ASTUniversal uni))
            throw new NDRuleException(r);

        //Find mapping
        ASTVariable x = (ASTVariable) uni.getLeft();
        IASTExp psi = uni.getRight();
        IFOLFormula psiXY = (IFOLFormula) formulas.get(r.getHyp().getConclusion());

        List<ASTVariable> variables = new ArrayList<>();
        variables.add(x); //It can be itself
        psiXY.iterateVariables().forEachRemaining(variables::add);

        Set<IASTExp> outcomes = new HashSet<>();
        if (r.getMapping() == null)
            for (ASTVariable var : variables) {

                IASTExp cExp = FOLReplaceExps.replace(psi, x, var);
                outcomes.add(cExp);
                if (cExp.equals(psiXY.getAST())) {
                    r.setMapping(var);
                    break;
                }
            }

        if (r.getMapping() == null)
            throw new InvalidMappingException(r, x, psi, psiXY.getAST(), outcomes);

        formulas.put(psi, FOLWFFInterpreter.check(psi));

        r.getHyp().setParent(r);
        return r.getHyp().accept(this, env);
    }

    @Override
    public Void visit(ASTEExist r, Void env) {
        if (!(r.getHyp1().getConclusion() instanceof ASTExistential exist) ||
                !r.getConclusion().equals(r.getHyp2().getConclusion()))
            throw new NDRuleException(r);

        IASTExp exp = exist.getRight();
        formulas.put(exp, FOLWFFInterpreter.check(exp));

        r.getHyp1().accept(this, env);
        r.getHyp2().accept(this, env);

        r.getHyp1().setParent(r);
        r.getHyp2().setParent(r);
        return null;
    }

}