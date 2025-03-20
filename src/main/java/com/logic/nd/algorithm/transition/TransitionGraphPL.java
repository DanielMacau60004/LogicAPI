package com.logic.nd.algorithm.transition;

import com.logic.api.IFormula;
import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.nd.ERule;

import java.util.*;

public class TransitionGraphPL implements ITransitionGraph {

    protected final IFormula conclusion;
    protected final Set<IFormula> premisses;
    protected final Set<ERule> forbiddenRules;

    protected final Map<IASTExp, Set<TransitionEdge>> graph;
    protected final Map<IASTExp, Boolean> explored;

    protected final Set<ASTOr> disjunctions;

    public TransitionGraphPL(IFormula conclusion, Set<IFormula> premisses, Set<ERule> forbiddenRules) {
        this.conclusion = conclusion;
        this.premisses = premisses;
        this.forbiddenRules = forbiddenRules;

        this.explored = new HashMap<>();

        graph = new HashMap<>();
        disjunctions = new HashSet<>();
    }

    @Override
    public void build() {
        //Add all nodes necessary to generate the sub nodes
        addNode(ExpUtils.BOT, true);
        addNode(conclusion.getFormula(), true);
        premisses.forEach(p -> addNode(p.getFormula(), true));

        //Add the disjunction rules to each node
        if (!forbiddenRules.contains(ERule.ELIM_DISJUNCTION))
            graph.forEach((e, ts) -> ts.addAll(disjunctions.stream().map(or -> disjunctionERule(e, or)).toList()));
    }

    protected void addNode(IASTExp node, boolean canGen) {
        if (explored.containsKey(node) && explored.get(node))
            return;

        explored.put(node, canGen);
        graph.put(node, new HashSet<>());
        if (!node.equals(conclusion) && node instanceof ASTOr or)
            disjunctions.add(or);

        if (canGen) {
            genBottomUp(node);
            genTopDown(node);
        }
    }

    protected void addEdge(IASTExp from, TransitionEdge edge, boolean canGen) {
        if (forbiddenRules.contains(edge.getRule())) return;

        addNode(from, true);
        edge.getTransitions().forEach(t -> addNode(t.getTo(), canGen));
        graph.get(from).add(edge);
    }

    private void absurdityRule(IASTExp exp) {
        if (exp.equals(ExpUtils.BOT))
            return;

        IASTExp neg = ExpUtils.negate(exp);
        addEdge(exp, new TransitionEdge(ERule.ABSURDITY, ExpUtils.BOT, neg), true);

        addEdge(ExpUtils.BOT, new TransitionEdge(ERule.ELIM_NEGATION)
                        .addTransition(exp)
                        .addTransition(neg)
                , false);
    }

    private void implicationIRule(IASTExp exp, ASTConditional imp) {
        IASTExp right = ExpUtils.removeParenthesis(imp.getRight());
        IASTExp left = ExpUtils.removeParenthesis(imp.getLeft());
        addEdge(exp, new TransitionEdge(ERule.INTRO_IMPLICATION, right, left), true);
    }

    private void negationIRule(IASTExp exp, ASTNot not) {
        IASTExp notNeg = ExpUtils.invert(not);

        addEdge(exp, new TransitionEdge(ERule.INTRO_NEGATION, ExpUtils.BOT, notNeg), true);
        addEdge(ExpUtils.BOT, new TransitionEdge(ERule.ELIM_NEGATION)
                        .addTransition(exp)
                        .addTransition(notNeg)
                , true);
    }

    private void disjunctionIRule(IASTExp exp, ASTOr or) {
        IASTExp right = ExpUtils.removeParenthesis(or.getRight());
        IASTExp left = ExpUtils.removeParenthesis(or.getLeft());

        addEdge(exp, new TransitionEdge(ERule.INTRO_DISJUNCTION_RIGHT, left), true);
        addEdge(exp, new TransitionEdge(ERule.INTRO_DISJUNCTION_LEFT, right), true);
    }

    private TransitionEdge disjunctionERule(IASTExp exp, ASTOr or) {
        IASTExp orExp = ExpUtils.removeParenthesis(or);
        IASTExp right = ExpUtils.removeParenthesis(or.getRight());
        IASTExp left = ExpUtils.removeParenthesis(or.getLeft());

        return new TransitionEdge(ERule.ELIM_DISJUNCTION)
                .addTransition(orExp)
                .addTransition(exp, left)
                .addTransition(exp, right);
    }

    private void implicationERule(IASTExp exp, ASTConditional imp) {
        IASTExp right = ExpUtils.removeParenthesis(imp.getRight());
        IASTExp left = ExpUtils.removeParenthesis(imp.getLeft());

        addEdge(right, new TransitionEdge(ERule.ELIM_IMPLICATION)
                .addTransition(left)
                .addTransition(exp), true);
    }

    private void conjunctionERule(IASTExp exp, ASTAnd and) {
        IASTExp right = ExpUtils.removeParenthesis(and.getRight());
        IASTExp left = ExpUtils.removeParenthesis(and.getLeft());

        addEdge(left, new TransitionEdge(ERule.ELIM_CONJUNCTION_RIGHT, exp), true);
        addEdge(right, new TransitionEdge(ERule.ELIM_CONJUNCTION_LEFT, exp), true);
    }

    private void conjunctionIRule(IASTExp exp, ASTAnd and) {
        IASTExp right = ExpUtils.removeParenthesis(and.getRight());
        IASTExp left = ExpUtils.removeParenthesis(and.getLeft());

        addEdge(exp, new TransitionEdge(ERule.INTRO_CONJUNCTION)
                        .addTransition(left)
                        .addTransition(right)
                , true);
    }

    protected void genBottomUp(IASTExp exp) {
        exp = ExpUtils.removeParenthesis(exp);
        absurdityRule(exp);

        if (exp instanceof ASTConditional imp)
            implicationIRule(exp, imp);
        else if (exp instanceof ASTNot not)
            negationIRule(exp, not);
    }

    protected void genTopDown(IASTExp exp) {
        exp = ExpUtils.removeParenthesis(exp);

        if (exp instanceof ASTConditional imp)
            implicationERule(exp, imp);
        else if (exp instanceof ASTAnd and) {
            conjunctionERule(exp, and);
            conjunctionIRule(exp, and);
        } else if (exp instanceof ASTOr or)
            disjunctionIRule(exp, or);

    }

    @Override
    public Set<TransitionEdge> getEdges(IASTExp exp) {
        return graph.get(exp);
    }

    @Override
    public String toString() {
        String str = "";
        str += "Total nodes: " + graph.size() + "\n";
        str += "Total edges: " + graph.values().stream().mapToInt(Set::size).sum() + "\n";
        str += "Disjunctions: " + disjunctions + "\n";
        for (Map.Entry<IASTExp, Set<TransitionEdge>> entry : graph.entrySet()) {
            str += entry.getKey() + ":  \n";
            for (TransitionEdge transition : entry.getValue())
                str += "\t" + transition + "\n";
        }
        return str;
    }
}
