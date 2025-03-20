package com.logic.nd.algorithm;

public abstract class AAlgoProofBuilder<T> {

    protected AlgoProofStateBuilder state;

    protected int heightLimit = 20;
    protected int totalClosedNodesLimit = 2000;
    protected int hypothesesPerStateLimit = 5;

    protected long timeout = 5000; //In milliseconds

    protected boolean useParallel = true;

    protected abstract T self();

    public AAlgoProofBuilder(AlgoProofStateBuilder state) {
        this.state = state;
    }

    public T setInitialState(AlgoProofStateBuilder stateBuilder) {
        this.state = stateBuilder;
        return self();
    }

    public T setHeightLimit(int heightLimit) {
        this.heightLimit = heightLimit;
        return self();
    }

    public T setTotalClosedNodesLimit(int totalClosedNodesLimit) {
        this.totalClosedNodesLimit = totalClosedNodesLimit;
        return self();
    }

    public T setHypothesesPerStateLimit(int hypothesesPerStateLimit) {
        this.hypothesesPerStateLimit = hypothesesPerStateLimit;
        return self();
    }

    public T setTimeout(long timeout) {
        this.timeout = timeout;
        return self();
    }

    public T setUseParallel(boolean useParallel) {
        this.useParallel = useParallel;
        return self();
    }

}

