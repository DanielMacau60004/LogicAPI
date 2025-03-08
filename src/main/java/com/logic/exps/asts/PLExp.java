package com.logic.exps.asts;

import com.logic.api.IPLExp;
import com.logic.exps.interpreters.PLInterpreter;

import java.util.*;

public class PLExp implements IPLExp {

    private final IASTExp exp;
    private final Set<String> literals;

    public PLExp(IASTExp exp, Set<String> literals) {
        this.exp = exp;
        this.literals = literals;
    }

    @Override
    public Iterator<String> iterateLiterals() {
        return literals.iterator();
    }

    @Override
    public boolean interpret(Map<String, Boolean> interpretation) {
        return PLInterpreter.interpret(exp, interpretation);
    }

    @Override
    public Map<Map<String, Boolean>, Boolean> getTruthTable() {
        List<String> literalList = new ArrayList<>(literals);
        int numCombinations = (int) Math.pow(2, literals.size());

        Map<Map<String, Boolean>, Boolean> table = new LinkedHashMap<>();

        for (int i = 0; i < numCombinations; i++) {
            Map<String, Boolean> interpretation = new TreeMap<>();
            for (int j = 0; j < literalList.size(); j++) {
                boolean truthValue = (i & (1 << j)) != 0;
                interpretation.put(literalList.get(j), truthValue);
            }
            table.put(interpretation, interpret(interpretation));
        }

        return table;
    }

    private Map<Map<String, Boolean>, Boolean> getShortenTruthTable(
            Map<Map<String, Boolean>, Boolean> table, Set<String> literals) {
        Set<Map<String, Boolean>> interpretations = new HashSet<>(table.keySet());

        for (Map<String, Boolean> interpretation : interpretations) {
            Boolean bool = table.get(interpretation);
            table.remove(interpretation);

            interpretation.keySet().retainAll(literals);
            Boolean newBool = table.get(interpretation);

            if(newBool == null)
                table.put(interpretation, bool);
            else if(newBool != bool)
                return null;
        }

        return table;
    }

    @Override
    public boolean isEquivalentTo(IPLExp other) {
        Set<String> intersection = new TreeSet<>(literals);
        Iterator<String> it = other.iterateLiterals();

        Set<String> iteratedBounded = new HashSet<>();
        it.forEachRemaining(iteratedBounded::add);
        intersection.retainAll(iteratedBounded);

        Map<Map<String, Boolean>, Boolean> truthTable = getShortenTruthTable(getTruthTable(), intersection);
        if(truthTable == null)
            return false;

        Map<Map<String, Boolean>, Boolean> truthTableOther = getShortenTruthTable(other.getTruthTable(), intersection);
        if(truthTableOther == null)
            return false;

        return truthTable.equals(truthTableOther);
    }

    @Override
    public String toString() {
        return exp.toString();
    }
}
