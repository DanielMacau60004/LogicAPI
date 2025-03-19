package com.logic.nd.algorithm.transition;

import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.nd.ERule;

import java.util.*;

public class TransitionGraphPL {

    protected final IASTExp conclusion;
    protected final Map<IASTExp, Set<TransitionEdge>> transitions;
    protected final Map<IASTExp, Boolean> explored;

    protected final Set<ASTOr> disjunctions;
    protected final Set<IASTExp> premisses;

    //TODO manipulate the rules that can be used to generate
    public TransitionGraphPL(IASTExp conclusion, Set<IASTExp> premisses) {
        this.conclusion = conclusion;
        this.premisses = premisses;
        this.explored = new HashMap<>();

        transitions = new HashMap<>();
        disjunctions = new HashSet<>();
    }

    public void build() {
        //Add all nodes necessary to generate the sub nodes
        addNode(ExpUtils.BOT, true);
        addNode(conclusion, true);
        premisses.forEach(p -> addNode(p, true));

        //Add the disjunction rules to each node
        transitions.forEach((e, ts) -> ts.addAll(disjunctions.stream().map(or -> disjunctionERule(e, or)).toList()));
    }

    public IASTExp getConclusion() {
        return conclusion;
    }

    public Set<IASTExp> getPremisses() {
        return premisses;
    }

    protected void addNode(IASTExp node, boolean canGen) {
        if (explored.containsKey(node) && explored.get(node))
            return;

        explored.put(node, canGen);
        transitions.put(node, new HashSet<>());
        if (!node.equals(conclusion) && node instanceof ASTOr or)
            disjunctions.add(or);

        if (canGen) {
            genBottomUp(node);
            genTopDown(node);
        }
    }

    protected void addEdge(IASTExp from, TransitionEdge edge, boolean canGen) {
        addNode(from, true);
        edge.getTransitions().forEach(t -> addNode(t.getTo(), canGen));
        transitions.get(from).add(edge);
    }

    private void absurdityRule(IASTExp exp) {
        if (exp.equals(ExpUtils.BOT))
            return;

        IASTExp neg = ExpUtils.negate(exp);
        addEdge(exp, new TransitionEdge(ERule.ABSURDITY, ExpUtils.BOT, neg), true);

        addEdge(ExpUtils.BOT, new TransitionEdge(ERule.ELIM_NEGATION)
                        .addTransition(exp)
                        .addTransition(neg)
                ,false);
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
                ,true);
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

    public Set<TransitionEdge> getEdges(IASTExp exp) {
        return transitions.get(exp);
    }

    @Override
    public String toString() {
        String str = "";
        str += "Total nodes: " + transitions.size() + "\n";
        str += "Total edges: " + transitions.values().stream().mapToInt(Set::size).sum() + "\n";
        str += "Disjunctions: " + disjunctions + "\n";
        for (Map.Entry<IASTExp, Set<TransitionEdge>> entry : transitions.entrySet()) {
            str += entry.getKey() + ":  \n";
            for (TransitionEdge transition : entry.getValue())
                str += "\t" + transition+ "\n";
        }
        return str;
    }
}
