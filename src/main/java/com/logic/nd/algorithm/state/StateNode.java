package com.logic.nd.algorithm.state;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.exps.asts.others.ASTVariable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class StateNode {

    private final IFormula exp;
    private final Set<IFormula> premisses;
    private final Set<IFormula> hypotheses;
    private boolean isClosed;
    private int height;

    private final Set<IFormula> noFree;

    public StateNode(IFormula exp, Set<IFormula> premisses, Set<IFormula> hypotheses, int height) {
        this(exp, premisses, hypotheses, null, height, new HashSet<>());
    }

    public StateNode(IFormula exp, Set<IFormula> premisses, Set<IFormula> hypotheses, int height, Set<IFormula> noFree) {
        this(exp, premisses, hypotheses, null, height, noFree);
    }

    StateNode(IFormula exp, Set<IFormula> premisses, Set<IFormula> hypotheses, IFormula hypothesis, int height
            , Set<IFormula> noFree) {
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

    public void setHeight(int height) {
        this.height = height;
    }

    public IFormula getExp() {
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

    public Set<IFormula> getHypotheses() {
        return hypotheses;
    }

    public Set<IFormula> getPremisses() {
        return premisses;
    }

    public StateNode transit(IFormula exp, IFormula hypothesis, ASTVariable notFree) {
        Set<IFormula> noFree = notFree == null ? this.noFree : new HashSet<>(this.noFree);
        Set<IFormula> hypotheses = hypothesis == null ? this.hypotheses : new HashSet<>(this.hypotheses);

        if (notFree != null) {
            noFree.addAll(hypotheses.stream().filter(h -> ((IFOLFormula) h).isAFreeVariable(notFree)).toList());
            noFree.addAll(premisses.stream().filter(p -> ((IFOLFormula) p).isAFreeVariable(notFree)).toList());
        }

        if (hypothesis != null)
            return new StateNode(exp, premisses, hypotheses, hypothesis, height + 1, noFree);
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
