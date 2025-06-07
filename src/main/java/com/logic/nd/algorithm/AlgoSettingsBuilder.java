package com.logic.nd.algorithm;

import com.logic.api.IFormula;
import com.logic.nd.algorithm.state.StateGraphSettings;
import com.logic.nd.algorithm.state.strategies.IBuildStrategy;
import com.logic.nd.algorithm.state.strategies.ITrimStrategy;
import com.logic.nd.algorithm.state.strategies.LinearBuildStrategy;
import com.logic.nd.algorithm.state.strategies.WidthTrimStrategy;

import java.util.Set;

public class AlgoSettingsBuilder {

    private int heightLimit = 20;
    private int totalClosedNodes = 2000;
    private int hypothesesPerState = 5;
    private int totalNodes = Integer.MAX_VALUE;

    private long timeout = 5000; //In milliseconds

    private IBuildStrategy buildStrategy = new LinearBuildStrategy();
    private ITrimStrategy trimStrategy = new WidthTrimStrategy();

    public AlgoSettingsBuilder setHeightLimit(int heightLimit) {
        this.heightLimit = heightLimit;
        return this;
    }

    public AlgoSettingsBuilder setTotalClosedNodes(int totalClosedNodes) {
        this.totalClosedNodes = totalClosedNodes;
        return this;
    }

    public AlgoSettingsBuilder setHypothesesPerState(int hypothesesPerState) {
        this.hypothesesPerState = hypothesesPerState;
        return this;
    }

    public AlgoSettingsBuilder setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public AlgoSettingsBuilder setTotalNodes(int totalNodes) {
        this.totalNodes = totalNodes;
        return this;
    }

    public AlgoSettingsBuilder setBuildStrategy(IBuildStrategy buildStrategy) {
        this.buildStrategy = buildStrategy;
        return this;
    }

    public AlgoSettingsBuilder setTrimStrategy(ITrimStrategy trimStrategy) {
        this.trimStrategy = trimStrategy;
        return this;
    }

    public StateGraphSettings build() {
        return new StateGraphSettings(heightLimit, totalClosedNodes, totalNodes,
                hypothesesPerState, timeout, buildStrategy, trimStrategy);
    }

}

