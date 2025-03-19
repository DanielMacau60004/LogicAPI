package com.logic.nd.algorithm.state;

import com.logic.api.IFormula;
import com.logic.api.INDProof;
import com.logic.exps.asts.IASTExp;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.binary.ASTEExist;
import com.logic.nd.asts.binary.ASTEImp;
import com.logic.nd.asts.binary.ASTENeg;
import com.logic.nd.asts.binary.ASTIConj;
import com.logic.nd.asts.others.ASTEDisj;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.*;
import com.logic.nd.checkers.NDMarksChecker;
import com.logic.nd.checkers.NDSideCondChecker;
import com.logic.nd.checkers.NDWWFChecker;
import com.logic.nd.checkers.NDWWFExpsChecker;
import com.logic.nd.interpreters.NDInterpreter;
import com.logic.others.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateSolution {

    private final StateGraph graph;
    private int mark;

    private boolean isFOL;

    public StateSolution(StateGraph graph, boolean isFOL) {
        this.graph = graph;
        this.isFOL = isFOL;
    }

    public INDProof findSolution() {
        return findSolution(graph.getInitState().getExp(), new HashMap<>());
    }

    public INDProof findSolution(IASTExp exp, Map<IASTExp, Integer> hypotheses) {
        if (!graph.isSolvable())
            return null;

        //TODO will cause conflict with premises marks, they might not start with 1
        int mark = 1;
        Map<IASTExp, Integer> marks = new HashMap<>(hypotheses);
        for(IASTExp e : graph.premisses) marks.put(e, mark++);

        this.mark = mark;
        IASTND proof = rule(new StateNode(exp, graph.premisses, hypotheses.keySet()), marks);


        System.out.println(Utils.getToken(proof.toString()));
        //TODO....
        Map<IASTExp, IFormula> formulas = isFOL ? NDWWFExpsChecker.checkFOL(proof)
                : NDWWFExpsChecker.checkPL(proof);
        NDWWFChecker.check(proof, formulas);
        Map<IASTExp, Integer> premises = NDMarksChecker.check(proof, formulas);
        NDSideCondChecker.check(proof, formulas, premises);
        return NDInterpreter.interpret(proof, formulas, premises);
    }

    private IASTND rule(StateNode initState, Map<IASTExp, Integer> marks) {
        StateEdge edge = graph.tree.get(initState);
        IASTExp exp = initState.getExp();

        marks = new HashMap<>(marks);

        if(edge == null)
            return new ASTHypothesis(exp, marks.get(exp));

        List<StateTransitionEdge> transitions = edge.getTransitions();

        for(StateTransitionEdge e : transitions)
            if(e.getProduces() != null) marks.put(e.getProduces(), mark++);

        IASTND first = !transitions.isEmpty() ? rule(transitions.get(0).getTransition(), marks) : null;
        IASTND second = transitions.size() > 1 ? rule(transitions.get(1).getTransition(), marks) : null;
        IASTND third = transitions.size() > 2 ? rule(transitions.get(2).getTransition(), marks) : null;

        return switch (edge.getRule()) {
            case INTRO_CONJUNCTION  -> new ASTIConj(first, second, exp);
            case ELIM_CONJUNCTION_LEFT  -> new ASTELConj(first, exp);
            case ELIM_CONJUNCTION_RIGHT -> new ASTERConj(first, exp);
            case INTRO_DISJUNCTION_LEFT -> new ASTILDis(first, exp);
            case INTRO_DISJUNCTION_RIGHT -> new ASTIRDis(first, exp);
            case ELIM_DISJUNCTION -> new ASTEDisj(first, second, third, exp,
                    marks.get(transitions.get(1).getProduces()), marks.get(transitions.get(2).getProduces()));
            case INTRO_IMPLICATION -> new ASTIImp(first, exp, marks.get(transitions.get(0).getProduces()));
            case INTRO_NEGATION -> new ASTINeg(first, exp, marks.get(transitions.get(0).getProduces()));
            case ELIM_IMPLICATION -> new ASTEImp(first, second, exp);
            case ABSURDITY -> new ASTAbsurdity(first, exp, marks.get(transitions.get(0).getProduces()));
            case ELIM_NEGATION -> new ASTENeg(first, second, exp);
            case ELIM_UNIVERSAL -> new ASTEUni(first, exp);
            case INTRO_EXISTENTIAL -> new ASTIExist(first, exp);
            case INTRO_UNIVERSAL -> new ASTIUni(first, exp);
            case ELIM_EXISTENTIAL -> new ASTEExist(first, second, exp, marks.get(transitions.get(1).getProduces()));
        };

    }

}
