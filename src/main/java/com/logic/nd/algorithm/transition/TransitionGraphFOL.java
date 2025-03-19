package com.logic.nd.algorithm.transition;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.checkers.FOLWFFChecker;
import com.logic.exps.interpreters.FOLReplaceExps;
import com.logic.nd.ERule;
import com.logic.others.Utils;

import java.util.*;

public class TransitionGraphFOL extends TransitionGraphPL {

    private final Set<ASTExistential> existentials;
    private final Set<AASTTerm> terms;

    //TODO ALLOW ADDING MORE TERMS TO THE SYSTEM
    //TODO ADD A CAP TO FIND A SOLUTION
    //TODO SPECIFY GENERICS FREE VARIABLES
    public TransitionGraphFOL(IASTExp conclusion, Set<IASTExp> premisses) {
        super(conclusion, premisses);

        existentials = new HashSet<>();
        terms = new HashSet<>();

        FOLWFFChecker.check(conclusion).iterateTerms().forEachRemaining(terms::add);
        premisses.forEach(p -> FOLWFFChecker.check(p).iterateTerms().forEachRemaining(terms::add));
    }

    @Override
    public void build() {
        super.build();

        transitions.forEach((e, ts) -> existentials.forEach(exi -> ts.addAll(existentialERule(e, exi))));
    }

    protected void addNode(IASTExp node, boolean canGen) {
        super.addNode(node, canGen);

        if (!node.equals(conclusion) && node instanceof ASTExistential exi)
            existentials.add(exi);
    }

    private void existentialIRule(ASTExistential exi) {
        IASTExp psi = ExpUtils.removeParenthesis(exi.getRight());
        ASTVariable xVar = (ASTVariable) exi.getLeft();

        terms.forEach(t -> {
            IASTExp psiXT = FOLReplaceExps.replace(psi, xVar, t);

            if (t instanceof ASTVariable x && FOLWFFChecker.check(psi).isABoundedVariable(x))
                return;

            addEdge(exi, new TransitionEdge(ERule.INTRO_EXISTENTIAL)
                    .addTransition(psiXT), true);
        });
    }

    private void universalERule(ASTUniversal uni) {
        IASTExp psi = ExpUtils.removeParenthesis(uni.getRight());
        ASTVariable xVar = (ASTVariable) uni.getLeft();

        terms.forEach(t -> {
            IASTExp psiXT = FOLReplaceExps.replace(psi, xVar, t);

            if (t instanceof ASTVariable x && FOLWFFChecker.check(psi).isABoundedVariable(x))
                return;

            addEdge(psiXT, new TransitionEdge(ERule.ELIM_UNIVERSAL)
                    .addTransition(uni), true);
        });
    }

    private void universalIRule(ASTUniversal uni) {
        IASTExp psi = ExpUtils.removeParenthesis(uni.getRight());
        ASTVariable xVar = (ASTVariable) uni.getLeft();

        terms.forEach(t -> {
            if (!(t instanceof ASTVariable yVar))
                return;
            IASTExp psiXY = FOLReplaceExps.replace(psi, xVar, yVar);

            if (!uni.getLeft().equals(yVar) &&
                    FOLWFFChecker.check(psi).isAnUnboundedVariable(yVar))
                return;

            addEdge(uni, new TransitionEdge(ERule.INTRO_UNIVERSAL)
                    .addTransition(psiXY, null, yVar), true);
        });
    }

    private List<TransitionEdge> existentialERule(IASTExp exp, ASTExistential exi) {
        IASTExp psi = ExpUtils.removeParenthesis(exi.getRight());
        ASTVariable xVar = (ASTVariable) exi.getLeft();


        List<TransitionEdge> edges = new ArrayList<>();
        for (AASTTerm t : terms) {
            if (!(t instanceof ASTVariable yVar))
                continue;

            IASTExp psiXY = FOLReplaceExps.replace(psi, xVar, yVar);

            //TODO Do I need to add !r.getConclusion().equals(ExpUtils.BOT)?
            //TODO check with exercise 11 if it generates rules with bottom
            if (!FOLWFFChecker.check(exp).isABoundedVariable(yVar) || (!exi.getLeft().equals(yVar)
                    && FOLWFFChecker.check(psi).isAnUnboundedVariable(yVar)))
                continue;

            edges.add(new TransitionEdge(ERule.ELIM_EXISTENTIAL)
                    .addTransition(exi)
                    .addTransition(exp, psiXY, yVar));
        }

        return edges;
    }

    protected void genTopDown(IASTExp exp) {
        super.genTopDown(exp);
        exp = ExpUtils.removeParenthesis(exp);

        if (exp instanceof ASTUniversal uni) {
            universalERule(uni);
            universalIRule(uni);
        } else if (exp instanceof ASTExistential exi)
            existentialIRule(exi);

    }

    @Override
    public String toString() {
        String str = "";
        str += "Total nodes: " + transitions.size() + "\n";
        str += "Total edges: " + transitions.values().stream().mapToInt(Set::size).sum() + "\n";
        str += "Disjunctions: " + disjunctions + "\n";
        str += "Existentials: " + existentials + "\n";
        for (Map.Entry<IASTExp, Set<TransitionEdge>> entry : transitions.entrySet()) {
            str += entry.getKey() + ":  \n";
            for (TransitionEdge transition : entry.getValue())
                str += "\t" + transition + "\n";
        }
        return str;
    }
}
