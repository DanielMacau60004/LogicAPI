package com.logic.api;

import java.util.Map;
import java.util.Set;

public interface IPLExp {
    Set<String> getLiterals();

    boolean interpret(Map<String, Boolean> interpretation);

    Map<Map<String, Boolean>, Boolean> getTruthTable();

    boolean isEquivalentTo(IPLExp other);
}
