package com.logic.nd.algorithm.transition;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.others.AASTTerm;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.exps.checkers.FOLWFFChecker;
import com.logic.exps.interpreters.FOLReplaceExps;
import com.logic.nd.ERule;

import java.util.*;

public class TransitionGraphFOL extends TransitionGraphPL implements ITransitionGraph {

    private final Set<ASTExistential> existentials;
    private final Set<AASTTerm> terms;

    //TODO SPECIFY GENERICS FREE VARIABLES
    public TransitionGraphFOL(IFOLFormula conclusion, Set<IFormula> premisses, Set<ERule> forbiddenRules,
                              Set<AASTTerm> terms) {
        super(conclusion, premisses, forbiddenRules);
        this.terms = terms;
        existentials = new HashSet<>();
    }

    @Override
    public void build() {
        super.build();

        if (!forbiddenRules.contains(ERule.ELIM_EXISTENTIAL))
            graph.forEach((e, ts) -> existentials.forEach(exi -> ts.addAll(existentialERule(e, exi))));
    }

    protected void addNode(IASTExp node, boolean canGen) {
        super.addNode(node, canGen);

        if (!node.equals(conclusion) && node instanceof ASTExistential exi)
            existentials.add(exi);
    }

    private void existentialIRule(ASTExistential exi) {
        IASTExp psi = ExpUtils.removeParenthesis(exi.getRight());
        ASTVariable xVar = (ASTVariable) exi.getLeft();

        for (AASTTerm t : terms) {
            IASTExp psiXT = FOLReplaceExps.replace(psi, xVar, t);

            if (t instanceof ASTVariable x && FOLWFFChecker.check(psi).isABoundedVariable(x))
                continue;

            addEdge(exi, new TransitionEdge(ERule.INTRO_EXISTENTIAL)
                    .addTransition(psiXT), true);
        }
    }

    private void universalERule(ASTUniversal uni) {
        IASTExp psi = ExpUtils.removeParenthesis(uni.getRight());
        ASTVariable xVar = (ASTVariable) uni.getLeft();

        for (AASTTerm t : terms) {
            IASTExp psiXT = FOLReplaceExps.replace(psi, xVar, t);

            if ((t instanceof ASTVariable x && FOLWFFChecker.check(psi).isABoundedVariable(x)) ||
                    !FOLReplaceExps.replace(psiXT, t, xVar).equals(psi))
                continue;

            addEdge(psiXT, new TransitionEdge(ERule.ELIM_UNIVERSAL)
                    .addTransition(uni), true);
        }
    }

    private void universalIRule(ASTUniversal uni) {
        IASTExp psi = ExpUtils.removeParenthesis(uni.getRight());
        ASTVariable xVar = (ASTVariable) uni.getLeft();

        for (AASTTerm t : terms) {
            if (!(t instanceof ASTVariable yVar))
                continue;

            IASTExp psiXY = FOLReplaceExps.replace(psi, xVar, yVar);
            if (!uni.getLeft().equals(yVar) &&
                    FOLWFFChecker.check(psi).isAnUnboundedVariable(yVar))
                return;

            addEdge(uni, new TransitionEdge(ERule.INTRO_UNIVERSAL)
                    .addTransition(psiXY, null, yVar), true);
        }
    }

    private List<TransitionEdge> existentialERule(IASTExp exp, ASTExistential exi) {
        IASTExp psi = ExpUtils.removeParenthesis(exi.getRight());
        ASTVariable xVar = (ASTVariable) exi.getLeft();


        List<TransitionEdge> edges = new ArrayList<>();
        for (AASTTerm t : terms) {
            if (!(t instanceof ASTVariable yVar))
                continue;

            IASTExp psiXY = FOLReplaceExps.replace(psi, xVar, yVar);
            if ((!FOLWFFChecker.check(exp).isABoundedVariable(yVar) && !exp.equals(ExpUtils.BOT)) || (!exi.getLeft().equals(yVar)
                    && FOLWFFChecker.check(psi).isAnUnboundedVariable(yVar)) ||
                    !FOLReplaceExps.replace(psiXY, t, xVar).equals(psi))
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
        str += "Total nodes: " + graph.size() + "\n";
        str += "Total edges: " + graph.values().stream().mapToInt(Set::size).sum() + "\n";
        str += "Disjunctions: " + disjunctions + "\n";
        str += "Existentials: " + existentials + "\n";
        for (Map.Entry<IASTExp, Set<TransitionEdge>> entry : graph.entrySet()) {
            str += entry.getKey() + ":  \n";
            for (TransitionEdge transition : entry.getValue())
                str += "\t" + transition + "\n";
        }
        return str;
    }
}
