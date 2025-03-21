package com.logic.nd.algorithm.state;

import com.logic.nd.algorithm.state.strategies.IBuildStrategy;
import com.logic.nd.algorithm.state.strategies.ITrimStrategy;

public class StateGraphSettings {

    private final StateNode state;

    private final int heightLimit;
    private final int totalClosedNodesLimit;
    private final int hypothesesPerStateLimit;

    private final long timeout;

    private final IBuildStrategy buildStrategy;
    private final ITrimStrategy trimStrategy;

    public StateGraphSettings(StateNode state, int heightLimit, int totalClosedNodesLimit,
                              int hypothesesPerStateLimit, long timeout, IBuildStrategy buildStrategy,
                              ITrimStrategy trimStrategy) {

        this.state = state;
        this.heightLimit = heightLimit;
        this.totalClosedNodesLimit = totalClosedNodesLimit;
        this.hypothesesPerStateLimit = hypothesesPerStateLimit;
        this.timeout = timeout;
        this.buildStrategy = buildStrategy;
        this.trimStrategy = trimStrategy;

    }

    public StateNode getState() {
        return state;
    }

    public int getHeightLimit() {
        return heightLimit;
    }

    public int getTotalClosedNodesLimit() {
        return totalClosedNodesLimit;
    }

    public int getHypothesesPerStateLimit() {
        return hypothesesPerStateLimit;
    }

    public long getTimeout() {
        return timeout;
    }

    public IBuildStrategy getBuildStrategy() {
        return buildStrategy;
    }

    public ITrimStrategy getTrimStrategy() {
        return trimStrategy;
    }
}

