package com.logic.nd.algorithm.state;

import com.logic.api.IFormula;
import com.logic.others.Utils;

import java.util.*;

public class BitGraphHandler {

    private final Map<IFormula, Integer> indexedFormulas;
    private final Map<Integer, IFormula> formulas;

    private final BitSet premises;

    public BitGraphHandler(Set<IFormula> premises, Set<IFormula> formulas) {
        this.indexedFormulas  = new HashMap<>(formulas.size());
        this.formulas  = new HashMap<>(premises.size());

        int index = 1;
        for(IFormula formula : formulas) {
            this.indexedFormulas.put(formula, index);
            this.formulas.put(index++, formula);
        }

        this.premises = toBitSet(premises);
    }

    public Set<IFormula> fromBitSet(BitSet bitSet) {
        Set<IFormula> result = new HashSet<>();
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            result.add(formulas.get(i));
        }
        return result;
    }
    public BitSet toBitSet(Set<IFormula> set) {
        BitSet bitSet = new BitSet(formulas.size());
        for (IFormula f : set) {
            bitSet.set(indexedFormulas.get(f));
        }
        return bitSet;
    }

    public BitSet getPremises() {return premises;}

    public int getIndex(IFormula formula) {return indexedFormulas.get(formula);}
    public IFormula get(int index) {return formulas.get(index);}

}
