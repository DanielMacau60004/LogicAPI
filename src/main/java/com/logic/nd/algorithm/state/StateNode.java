package com.logic.nd.algorithm.state;

import com.logic.api.IFOLFormula;
import com.logic.api.IFormula;
import com.logic.exps.asts.others.ASTVariable;

import java.util.*;

public class StateNode {

    protected final BitGraphHandler handler; //TODO this is object does not change

    private final int exp;
    private final BitSet hypotheses;
    private boolean isClosed;
    private int height;

    private final BitSet noFree;

    public StateNode(Integer exp, BitSet hypotheses, int height, BitSet noFree, BitGraphHandler handler) {
        this(exp, hypotheses, null, height, noFree, handler);
    }

    StateNode(Integer exp, BitSet hypotheses, Integer hypothesis, int height
            , BitSet noFree, BitGraphHandler handler) {
        this.exp = exp;
        this.hypotheses = hypotheses;
        this.height = height;
        this.noFree = noFree;
        this.handler = handler;

        if (hypothesis != null)
            hypotheses.set(hypothesis);

        resetClose();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public IFormula getExp() {
        return handler.get(exp);
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed() {
        isClosed = true;
    }

    public void resetClose() {
        isClosed = (hypotheses.get(exp) || handler.getPremises().get(exp)) && (noFree == null || !noFree.get(exp));
    }

    public Integer numberOfHypotheses() {
        return hypotheses.cardinality();
    }

    public Set<IFormula> getHypotheses() {
        return handler.fromBitSet(hypotheses);
    }

    public StateNode transit(IFormula exp, IFormula hypothesis, ASTVariable notFree) {
        BitSet noFree = this.noFree;

        if (notFree != null) {
            noFree = (BitSet) this.noFree.clone();

            List<Integer> toAdd = new LinkedList<>();
            for (int i = hypotheses.nextSetBit(0); i >= 0; i = hypotheses.nextSetBit(i + 1))
                if (((IFOLFormula) handler.get(i)).appearsFreeVariable(notFree))
                    toAdd.add(i);

            BitSet premises = handler.getPremises();
            for (int i = premises.nextSetBit(0); i >= 0; i = premises.nextSetBit(i + 1))
                if (((IFOLFormula) handler.get(i)).appearsFreeVariable(notFree))
                    toAdd.add(i);

            for (Integer i : toAdd) noFree.set(i);
        }

        BitSet hypotheses = this.hypotheses;
        if (hypothesis != null) {
            hypotheses = (BitSet) this.hypotheses.clone();
            hypotheses.set(handler.getIndex(hypothesis));
        }

        if (hypothesis != null)
            return new StateNode(handler.getIndex(exp), hypotheses, handler.getIndex(hypothesis),
                    height + 1, noFree, handler);
        return new StateNode(handler.getIndex(exp), hypotheses, height + 1, noFree, handler);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateNode stateNode = (StateNode) o;
        return Objects.equals(hypotheses, stateNode.hypotheses) && Objects.equals(exp, stateNode.exp)
                && Objects.equals(noFree, stateNode.noFree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hypotheses, exp, noFree);
    }

}
