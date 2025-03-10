package com.logic.nd.algorithm.transition;

import com.logic.exps.asts.IASTExp;
import java.util.Objects;

public class TransitionNode {

    private final IASTExp to;
    private final IASTExp produces;

    TransitionNode(IASTExp to, IASTExp produces) {
        this.to = to;
        this.produces = produces;
    }

    public IASTExp getTo() {
        return to;
    }

    public IASTExp getProduces() {
        return produces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransitionNode that = (TransitionNode) o;
        return Objects.equals(to, that.to) && Objects.equals(produces, that.produces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, produces);
    }
}
