package com.logic.nd.algorithm.transition;

import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.ASTAnd;
import com.logic.exps.asts.binary.ASTBiconditional;
import com.logic.exps.asts.binary.ASTConditional;
import com.logic.exps.asts.binary.ASTOr;
import com.logic.exps.asts.others.ASTLiteral;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.asts.unary.ASTParenthesis;
import com.logic.nd.ERule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TransitionGraph {

    private final IASTExp conclusion;
    private final Map<IASTExp, Set<TransitionEdge>> transitions;
    private final Map<IASTExp, Boolean> explored;

    private final Set<ASTOr> disjunctions;
    private final Set<IASTExp> premisses;

    public TransitionGraph(IASTExp conclusion, Set<IASTExp> premisses) {
        this.conclusion = conclusion;
        this.premisses = premisses;
        this.explored = new HashMap<>();

        transitions = new HashMap<>();
        disjunctions = new HashSet<>();

        //Add all nodes necessary to generate the sub nodes
        addNode(ExpUtils.BOT, true);
        addNode(conclusion, true);
        premisses.forEach(p -> addNode(p, true));

        //Add the disjunction rules to each node
        transitions.forEach((e,ts)-> ts.addAll(disjunctions.stream().map(or -> disjunctionERule(e, or)).toList()));
    }

    public IASTExp getConclusion() {
        return conclusion;
    }

    public Set<IASTExp> getPremisses() {
        return premisses;
    }

    private void addNode(IASTExp node, boolean canGen) {
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

    private void addEdge(IASTExp from, TransitionEdge edge, boolean canGen) {
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

    private void biconditionalIRule(IASTExp exp, ASTBiconditional eq) {
        addEdge(exp, new TransitionEdge(ERule.INTRO_CONJUNCTION)
                        .addTransition(new ASTConditional(eq.getRight(), eq.getLeft()))
                        .addTransition(new ASTConditional(eq.getLeft(), eq.getRight()))
                ,true);
    }

    private void biconditionalERule(IASTExp exp, ASTBiconditional eq) {
        addEdge(new ASTAnd(
                new ASTParenthesis(new ASTConditional(eq.getLeft(), eq.getRight())),
                new ASTParenthesis(new ASTConditional(eq.getRight(), eq.getLeft()))),
                new TransitionEdge(ERule.INTRO_CONJUNCTION)
                        .addTransition(exp),true);
    }

    private void genBottomUp(IASTExp exp) {
        exp = ExpUtils.removeParenthesis(exp);
        absurdityRule(exp);

        if (exp instanceof ASTConditional imp)
            implicationIRule(exp, imp);
        else if (exp instanceof ASTNot not)
            negationIRule(exp, not);
    }

    private void genTopDown(IASTExp exp) {
        exp = ExpUtils.removeParenthesis(exp);

        if (exp instanceof ASTConditional imp)
            implicationERule(exp, imp);
        else if (exp instanceof ASTAnd and) {
            conjunctionERule(exp, and);
            conjunctionIRule(exp, and);
        } else if (exp instanceof ASTOr or)
            disjunctionIRule(exp, or);
        //Uncomment to allow biconditional
        /*else if(exp instanceof ASTBiconditional eq) {
            biconditionalIRule(exp, eq);
            biconditionalERule(exp, eq);
        }*/
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
