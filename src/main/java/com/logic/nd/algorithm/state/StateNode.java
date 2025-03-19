package com.logic.nd.algorithm.state;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.others.ASTVariable;
import com.logic.exps.checkers.FOLWFFChecker;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class StateNode {

    private final IASTExp exp;
    private final Set<IASTExp> premisses;
    private final Set<IASTExp> hypotheses;
    private boolean isClosed;
    private final int height;

    private final Set<IASTExp> noFree;

    public StateNode(IASTExp exp, Set<IASTExp> premisses) {
        this(exp, premisses, new HashSet<>(),null, 0, new HashSet<>());
    }

    public StateNode(IASTExp exp, Set<IASTExp> premisses, Set<IASTExp> hypotheses) {
        this(exp, premisses, hypotheses, null, 0, new HashSet<>());
    }

    public StateNode(IASTExp exp, Set<IASTExp> premisses, Set<IASTExp> hypotheses, Set<IASTExp> noFree) {
        this(exp, premisses, hypotheses, null, 0, noFree);
    }

    public StateNode(IASTExp exp, Set<IASTExp> premisses, Set<IASTExp> hypotheses, int height) {
        this(exp, premisses, hypotheses, null, height, new HashSet<>());
    }

    public StateNode(IASTExp exp, Set<IASTExp> premisses, Set<IASTExp> hypotheses, int height, Set<IASTExp> noFree) {
        this(exp, premisses, hypotheses, null, height, noFree);
    }

    StateNode(IASTExp exp, Set<IASTExp> premisses, Set<IASTExp> hypotheses, IASTExp hypothesis, int height
            , Set<IASTExp> noFree) {
        this.exp = exp;
        this.hypotheses = hypotheses;
        this.premisses = premisses;
        this.height = height;
        this.noFree = noFree;

        if (hypothesis != null)
            hypotheses.add(hypothesis);

        resetClose();
    }

    public int getHeight() {
        return height;
    }

    public IASTExp getExp() {
        return exp;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed() {
        isClosed = true;
    }

    public void resetClose() {
        isClosed = (hypotheses.contains(exp) || premisses.contains(exp)) && !noFree.contains(exp);
    }

    public Set<IASTExp> getHypotheses() {
        return hypotheses;
    }

    public StateNode transit(IASTExp exp, IASTExp hypothesis, ASTVariable notFree) {
        Set<IASTExp> noFree = notFree == null ? this.noFree : new HashSet<>(this.noFree);
        Set<IASTExp> hypotheses = hypothesis == null ? this.hypotheses : new HashSet<>(this.hypotheses);

        //TODO performance, we can store formulas
        if(notFree != null) {
            noFree.addAll(hypotheses.stream()
                    .filter(h-> !FOLWFFChecker.check(h).isABoundedVariable(notFree)).toList());
            noFree.addAll(premisses.stream()
                    .filter(p-> !FOLWFFChecker.check(p).isABoundedVariable(notFree)).toList());
        }

        if (hypothesis != null)
            return new StateNode(exp,premisses, hypotheses, hypothesis, height + 1, noFree);
        return new StateNode(exp, premisses, hypotheses, height + 1, noFree);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateNode stateNode = (StateNode) o;
        return Objects.equals(hypotheses, stateNode.hypotheses) && Objects.equals(exp, stateNode.exp)
                && Objects.equals(noFree, stateNode.noFree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hypotheses, exp, noFree);
    }

    @Override
    public String toString() {
        return exp.toString();
    }
}
