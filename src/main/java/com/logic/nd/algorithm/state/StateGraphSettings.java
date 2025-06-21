package com.logic.nd.algorithm.state;

import com.logic.nd.algorithm.state.strategies.IBuildStrategy;
import com.logic.nd.algorithm.state.strategies.ITrimStrategy;


public class StateGraphSettings {

    private final int heightLimit;
    private final int totalClosedNodesLimit;
    private final int totalNodesLimit;
    private final int hypothesesPerStateLimit;

    private final long timeout;

    private final IBuildStrategy buildStrategy;
    private final ITrimStrategy trimStrategy;

    public StateGraphSettings(int heightLimit, int totalClosedNodesLimit, int totalNodesLimit,
                              int hypothesesPerStateLimit, long timeout, IBuildStrategy buildStrategy,
                              ITrimStrategy trimStrategy) {

        this.heightLimit = heightLimit;
        this.totalNodesLimit = totalNodesLimit;
        this.totalClosedNodesLimit = totalClosedNodesLimit;
        this.hypothesesPerStateLimit = hypothesesPerStateLimit;
        this.timeout = timeout;
        this.buildStrategy = buildStrategy;
        this.trimStrategy = trimStrategy;
    }

    public int getHeightLimit() {
        return heightLimit;
    }

    public int getTotalNodesLimit() {
        return totalNodesLimit;
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

