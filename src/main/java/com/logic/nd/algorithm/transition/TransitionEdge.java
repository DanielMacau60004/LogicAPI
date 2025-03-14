package com.logic.nd.algorithm.transition;

import com.logic.exps.asts.IASTExp;
import com.logic.nd.ERule;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TransitionEdge {

    private final ERule rule;
    private final List<TransitionNode> transitions;

    TransitionEdge(ERule rule, IASTExp to, IASTExp produces) {
        this(rule);
        addTransition(to, produces);
    }

    TransitionEdge(ERule rule, IASTExp to) {
        this(rule);
        addTransition(to, null);
    }

    TransitionEdge(ERule rule) {
        this.rule = rule;
        this.transitions = new LinkedList<>();
    }

    public TransitionEdge addTransition(IASTExp to, IASTExp produces) {
        transitions.add(new TransitionNode(to, produces));
        return this;
    }

    public TransitionEdge addTransition(IASTExp to) {
        transitions.add(new TransitionNode(to, null));
        return this;
    }

    public List<TransitionNode> getTransitions() {
        return transitions;
    }

    public ERule getRule() {
        return rule;
    }

    @Override
    public String toString() {
        String str = rule.name() + " ";
        for (TransitionNode transition : transitions)
            str += "[" + transition.getTo() + "," + transition.getProduces() + "] ";
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransitionEdge that = (TransitionEdge) o;
        return rule == that.rule && Objects.equals(transitions, that.transitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule, transitions);
    }


}
