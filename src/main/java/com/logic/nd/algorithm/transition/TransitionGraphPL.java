package com.logic.nd.algorithm.transition;

import com.logic.api.IFormula;
import com.logic.exps.ExpUtils;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.binary.ASTAnd;
import com.logic.exps.asts.binary.ASTConditional;
import com.logic.exps.asts.binary.ASTOr;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.checkers.PLWFFChecker;
import com.logic.nd.ERule;

import java.util.*;

public class TransitionGraphPL implements ITransitionGraph {

    protected final Map<IASTExp, IFormula> formulas;

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

        formulas = new HashMap<>();
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
            graph.forEach((e, ts) -> ts.addAll(disjunctions.stream().map(d -> disjunctionERule(e, d)).toList()));
    }

    protected IFormula getFormula(IASTExp exp) {
        formulas.putIfAbsent(exp, PLWFFChecker.check(exp));
        return formulas.get(exp);
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
        edge.getTransitions().forEach(t -> addNode(t.getTo().getFormula(), canGen));
        graph.get(from).add(edge);
    }

    private void absurdityRule(IASTExp exp) {
        if (exp.equals(ExpUtils.BOT))
            return;

        IFormula neg = getFormula(ExpUtils.negate(exp));
        addEdge(exp, new TransitionEdge(ERule.ABSURDITY, ExpUtils.BOTF, neg), true);

        addEdge(ExpUtils.BOT, new TransitionEdge(ERule.ELIM_NEGATION)
                        .addTransition(getFormula(exp))
                        .addTransition(neg)
                , false);
    }

    private void implicationIRule(ASTConditional imp) {
        IFormula right = getFormula(ExpUtils.removeParenthesis(imp.getRight()));
        IFormula left = getFormula(ExpUtils.removeParenthesis(imp.getLeft()));
        addEdge(imp, new TransitionEdge(ERule.INTRO_IMPLICATION, right, left), true);
    }

    private void negationIRule(ASTNot not) {
        IFormula notNeg = getFormula(ExpUtils.invert(not));

        addEdge(not, new TransitionEdge(ERule.INTRO_NEGATION, ExpUtils.BOTF, notNeg), true);
        addEdge(ExpUtils.BOT, new TransitionEdge(ERule.ELIM_NEGATION)
                        .addTransition(getFormula(not))
                        .addTransition(notNeg)
                , true);
    }

    private void disjunctionIRule(ASTOr or) {
        IFormula right = getFormula(ExpUtils.removeParenthesis(or.getRight()));
        IFormula left = getFormula(ExpUtils.removeParenthesis(or.getLeft()));

        addEdge(or, new TransitionEdge(ERule.INTRO_DISJUNCTION_RIGHT, left), true);
        addEdge(or, new TransitionEdge(ERule.INTRO_DISJUNCTION_LEFT, right), true);
    }

    private TransitionEdge disjunctionERule(IASTExp exp, ASTOr or) {
        IFormula orExp = getFormula(ExpUtils.removeParenthesis(or));
        IFormula right = getFormula(ExpUtils.removeParenthesis(or.getRight()));
        IFormula left = getFormula(ExpUtils.removeParenthesis(or.getLeft()));

        IFormula expF = getFormula(exp);

        return new TransitionEdge(ERule.ELIM_DISJUNCTION)
                .addTransition(orExp)
                .addTransition(expF, left)
                .addTransition(expF, right);
    }

    private void implicationERule(ASTConditional imp) {
        IASTExp right = ExpUtils.removeParenthesis(imp.getRight());
        IFormula left = getFormula(ExpUtils.removeParenthesis(imp.getLeft()));
        IFormula expF = getFormula(imp);

        addEdge(right, new TransitionEdge(ERule.ELIM_IMPLICATION)
                .addTransition(left)
                .addTransition(expF), true);
    }

    private void conjunctionERule(ASTAnd and) {
        IASTExp right = ExpUtils.removeParenthesis(and.getRight());
        IASTExp left = ExpUtils.removeParenthesis(and.getLeft());
        IFormula expF = getFormula(and);

        addEdge(left, new TransitionEdge(ERule.ELIM_CONJUNCTION_RIGHT, expF), true);
        addEdge(right, new TransitionEdge(ERule.ELIM_CONJUNCTION_LEFT, expF), true);
    }

    private void conjunctionIRule(ASTAnd and) {
        IFormula right = getFormula(ExpUtils.removeParenthesis(and.getRight()));
        IFormula left = getFormula(ExpUtils.removeParenthesis(and.getLeft()));

        addEdge(and, new TransitionEdge(ERule.INTRO_CONJUNCTION)
                .addTransition(left)
                .addTransition(right), true);
    }

    protected void genBottomUp(IASTExp exp) {
        exp = ExpUtils.removeParenthesis(exp);
        absurdityRule(exp);

        if (exp instanceof ASTConditional imp)
            implicationIRule(imp);
        else if (exp instanceof ASTNot not)
            negationIRule(not);
    }

    protected void genTopDown(IASTExp exp) {
        exp = ExpUtils.removeParenthesis(exp);

        if (exp instanceof ASTConditional imp)
            implicationERule(imp);
        else if (exp instanceof ASTAnd and) {
            conjunctionERule(and);
            conjunctionIRule(and);
        } else if (exp instanceof ASTOr or)
            disjunctionIRule(or);

    }

    @Override
    public Set<TransitionEdge> getEdges(IASTExp exp) {
        return graph.get(exp);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Total nodes: ").append(graph.size()).append("\n");
        str.append("Total edges: ").append(graph.values().stream().mapToInt(Set::size).sum()).append("\n");
        str.append("Disjunctions: ").append(disjunctions).append("\n");
        for (Map.Entry<IASTExp, Set<TransitionEdge>> entry : graph.entrySet()) {
            str.append(entry.getKey()).append(":  \n");
            for (TransitionEdge transition : entry.getValue())
                str.append("\t").append(transition).append("\n");
        }
        return str.toString();
    }
}
