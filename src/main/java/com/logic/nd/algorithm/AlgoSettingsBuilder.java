package com.logic.nd.algorithm;

import com.logic.api.IFormula;
import com.logic.nd.algorithm.state.StateGraphSettings;
import com.logic.nd.algorithm.state.strategies.HeightTrimStrategy;
import com.logic.nd.algorithm.state.strategies.IBuildStrategy;
import com.logic.nd.algorithm.state.strategies.ITrimStrategy;
import com.logic.nd.algorithm.state.strategies.ParallelBuildStrategy;

import java.util.Set;

public class AlgoSettingsBuilder {

    private AlgoProofStateBuilder state;

    private int heightLimit = 20;
    private int totalClosedNodes = 2000;
    private int hypothesesPerState = 5;

    private long timeout = 5000; //In milliseconds

    private IBuildStrategy buildStrategy = new ParallelBuildStrategy();
    private ITrimStrategy trimStrategy = new HeightTrimStrategy();

    public AlgoSettingsBuilder setInitialState(AlgoProofStateBuilder stateBuilder) {
        this.state = stateBuilder;
        return this;
    }

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

    public AlgoSettingsBuilder setBuildStrategy(IBuildStrategy buildStrategy) {
        this.buildStrategy = buildStrategy;
        return this;
    }

    public AlgoSettingsBuilder setTrimStrategy(ITrimStrategy trimStrategy) {
        this.trimStrategy = trimStrategy;
        return this;
    }

    public StateGraphSettings build(IFormula conclusion, Set<IFormula> premises) {
        if (state == null)
            state = new AlgoProofStateBuilder(conclusion);
        return new StateGraphSettings(state.build(premises), heightLimit, totalClosedNodes,
                hypothesesPerState, timeout, buildStrategy, trimStrategy);
    }

}

