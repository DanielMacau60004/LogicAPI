package com.logic.nd.algorithm.state;

import com.logic.api.IFormula;
import com.logic.nd.ERule;

import java.util.*;

public class StateEdge {

    private final ERule rule;
    private final List<StateTransitionEdge> transitions;

    public StateEdge(ERule rule, StateNode transition, IFormula produces) {
        this.rule = rule;
        this.transitions = new LinkedList<>();

        transitions.add(new StateTransitionEdge(transition, produces));
    }

    public StateEdge(ERule rule) {
        this.rule = rule;
        this.transitions = new LinkedList<>();
    }

    public void addTransition(StateNode to, IFormula produces) {
        transitions.add(new StateTransitionEdge(to, produces));
    }

    public int height() {
        return transitions.stream().mapToInt(i->i.getNode().getHeight()).sum();
    }

    public List<StateTransitionEdge> getTransitions() {return transitions;}

    public boolean isClosed() {return transitions.stream().allMatch(s->s.getNode().isClosed());}

    public ERule getRule() {
        return rule;
    }

    @Override
    public String toString() {
        return transitions.toString() + " closed: " + isClosed() + " rule: " + rule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateEdge edge = (StateEdge) o;
        return rule == edge.rule && Objects.equals(transitions, edge.transitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule, transitions);
    }


}
