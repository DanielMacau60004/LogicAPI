package com.logic.nd.algorithm.state;

import com.logic.exps.asts.IASTExp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class StateNode {

    private final IASTExp exp;
    private final Set<IASTExp> premisses;
    private final Set<IASTExp> hypotheses;
    private boolean isClosed;
    private final int height;

    public StateNode(IASTExp exp, Set<IASTExp> premisses) {
        this(exp, premisses, new HashSet<>(),null, 0);
    }

    public StateNode(IASTExp exp, Set<IASTExp> premisses, Set<IASTExp> hypotheses) {
        this(exp, premisses, hypotheses, null, 0);
    }

    public StateNode(IASTExp exp, Set<IASTExp> premisses, Set<IASTExp> hypotheses, int height) {
        this(exp, premisses, hypotheses, null, height);
    }

    StateNode(IASTExp exp, Set<IASTExp> premisses, Set<IASTExp> hypotheses, IASTExp hypothesis, int height) {
        this.exp = exp;
        this.hypotheses = hypotheses;
        this.premisses = premisses;
        this.height = height;

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
        isClosed = hypotheses.contains(exp) || premisses.contains(exp);
    }

    public boolean hasHypothesis(IASTExp exp) {
        return hypotheses.contains(exp);
    }

    public Set<IASTExp> getHypotheses() {
        return hypotheses;
    }

    public StateNode transit(IASTExp exp, IASTExp hypothesis) {
        if (hypothesis != null) return new StateNode(exp,
                premisses, new HashSet<>(this.hypotheses), hypothesis, height + 1);
        return new StateNode(exp, premisses, this.hypotheses, height + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateNode stateNode = (StateNode) o;
        return Objects.equals(hypotheses, stateNode.hypotheses) && Objects.equals(exp, stateNode.exp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hypotheses, exp);
    }

    @Override
    public String toString() {
        return exp.toString();
    }
}
