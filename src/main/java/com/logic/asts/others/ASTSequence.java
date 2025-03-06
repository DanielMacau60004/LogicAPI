package com.logic.asts.others;

import com.logic.asts.AASTExp;
import com.logic.asts.IASTExp;
import com.logic.interpreters.IInterpreter;

import java.util.LinkedList;
import java.util.List;

public class ASTSequence extends AASTExp {

    private List<IASTExp> sequence;

    public ASTSequence(IASTExp firstExp) {
        sequence = new LinkedList<>();
        sequence.add(firstExp);
    }

    public List<IASTExp> getSequence() {
        return sequence;
    }

    public void addExp(IASTExp exp) {
        sequence.add(exp);
    }

    @Override
    public <T, E> T interpret(IInterpreter<T, E> v, E env) {
        return v.visit(this, env);
    }

    @Override
    public String toString() {
        StringBuilder expression = new StringBuilder();
        for (IASTExp exp : sequence)
            expression.append(exp.toString()).append("\n");
        return expression.toString();
    }
}
