package com.logic.nd.algorithm.state;

import com.logic.api.IFormula;
import com.logic.api.INDProof;
import com.logic.exps.asts.IASTExp;
import com.logic.nd.NDProofs;
import com.logic.nd.asts.IASTND;
import com.logic.nd.asts.binary.ASTEExist;
import com.logic.nd.asts.binary.ASTEImp;
import com.logic.nd.asts.binary.ASTENeg;
import com.logic.nd.asts.binary.ASTIConj;
import com.logic.nd.asts.others.ASTEDis;
import com.logic.nd.asts.others.ASTHypothesis;
import com.logic.nd.asts.unary.*;
import com.logic.nd.exceptions.NDException;
import com.logic.others.Env;

import java.util.List;

//TODO remake this
public class StateSolution {

    private final IStateGraph graph;
    private int mark;

    private final boolean isFOL;

    public StateSolution(IStateGraph graph, boolean isFOL) {
        this.graph = graph;
        this.isFOL = isFOL;
    }

    //TODO Develop a solution using an incomplete proof
    public INDProof findSolution() throws NDException {
        //TODO we might want to specify which marks are associated with each premise
        //TODO will cause conflict with premises marks, they might not start with 1
        mark = 1;
        Env<IFormula, String> marks = new Env<>();
        for (IFormula e : graph.getPremises()) marks.bind(e, String.valueOf(mark++));
        for (IFormula e : graph.getInitialState().getHypotheses()) marks.bind(e, String.valueOf(mark++));

        StateNode node = graph.getInitialState();
        if (node == null || !node.isClosed())
            throw new RuntimeException("Solution not found!");

        System.out.println(node.getHeight());

        IASTND proof = rule(node, marks);
        if (proof == null)
            throw new RuntimeException("Solution not found!");

        if (isFOL) return NDProofs.verifyNDFOLProof(proof);
        return NDProofs.verifyNDPLProof(proof);
    }

    private IASTND rule(StateNode initState, Env<IFormula, String> marks) {
        StateEdge edge = graph.getEdge(initState);
        IASTExp exp = initState.getExp().getAST();

        if (edge == null)
            return new ASTHypothesis(exp, marks.findParent(initState.getExp()));

        List<StateTransitionEdge> transitions = edge.getTransitions();

        return switch (edge.getRule()) {
            case INTRO_CONJUNCTION -> introConjunction(transitions, marks, exp);
            case ELIM_CONJUNCTION_LEFT -> elimConjunctionLeft(transitions, marks, exp);
            case ELIM_CONJUNCTION_RIGHT -> elimConjunctionRight(transitions, marks, exp);
            case INTRO_DISJUNCTION_LEFT -> introDisjunctionLeft(transitions, marks, exp);
            case INTRO_DISJUNCTION_RIGHT -> introDisjunctionRight(transitions, marks, exp);
            case ELIM_DISJUNCTION -> elimDisjunction(transitions, marks, exp);
            case INTRO_IMPLICATION -> introImplication(transitions, marks, exp);
            case INTRO_NEGATION -> introNegation(transitions, marks, exp);
            case ELIM_IMPLICATION -> elimImplication(transitions, marks, exp);
            case ABSURDITY -> absurdity(transitions, marks, exp);
            case ELIM_NEGATION -> elimNegation(transitions, marks, exp);
            case ELIM_UNIVERSAL -> elimUniversal(transitions, marks, exp);
            case INTRO_EXISTENTIAL -> introExistential(transitions, marks, exp);
            case INTRO_UNIVERSAL -> introUniversal(transitions, marks, exp);
            case ELIM_EXISTENTIAL -> elimExistential(transitions, marks, exp);
            default -> null;
        };
    }

    private IASTND introConjunction(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        IASTND first = rule(transitions.get(0).getNode(), marks);
        IASTND second = rule(transitions.get(1).getNode(), marks);
        return new ASTIConj(first, second, exp);
    }

    private IASTND elimConjunctionLeft(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        IASTND first = rule(transitions.get(0).getNode(), marks);
        return new ASTELConj(first, exp);
    }

    private IASTND elimConjunctionRight(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        IASTND first = rule(transitions.get(0).getNode(), marks);
        return new ASTERConj(first, exp);
    }

    private IASTND introDisjunctionLeft(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        IASTND first = rule(transitions.get(0).getNode(), marks);
        return new ASTILDis(first, exp);
    }

    private IASTND introDisjunctionRight(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        IASTND first = rule(transitions.get(0).getNode(), marks);
        return new ASTIRDis(first, exp);
    }

    private IASTND elimDisjunction(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        Env<IFormula, String> envM = marks.beginScope();
        Env<IFormula, String> envN = marks.beginScope();
        envM.bind(transitions.get(1).getProduces(), String.valueOf(mark++));
        envN.bind(transitions.get(2).getProduces(), String.valueOf(mark++));
        IASTND first = rule(transitions.get(0).getNode(), marks);
        IASTND second = rule(transitions.get(1).getNode(), envM);
        IASTND third = rule(transitions.get(2).getNode(), envN);
        return new ASTEDis(first, second, third, exp,
                envM.findParent(transitions.get(1).getProduces()),
                envN.findParent(transitions.get(2).getProduces()));
    }

    private IASTND introImplication(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        Env<IFormula, String> envM = marks.beginScope();
        envM.bind(transitions.get(0).getProduces(), String.valueOf(mark++));
        IASTND first = rule(transitions.get(0).getNode(), envM);
        return new ASTIImp(first, exp, envM.findParent(transitions.get(0).getProduces()));
    }

    private IASTND introNegation(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        Env<IFormula, String> envM = marks.beginScope();
        envM.bind(transitions.get(0).getProduces(), String.valueOf(mark++));
        IASTND first = rule(transitions.get(0).getNode(), envM);
        return new ASTINeg(first, exp, envM.findParent(transitions.get(0).getProduces()));
    }

    private IASTND elimImplication(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        IASTND first = rule(transitions.get(0).getNode(), marks);
        IASTND second = rule(transitions.get(1).getNode(), marks);
        return new ASTEImp(first, second, exp);
    }

    private IASTND absurdity(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        Env<IFormula, String> envM = marks.beginScope();
        envM.bind(transitions.get(0).getProduces(), String.valueOf(mark++));
        IASTND first = rule(transitions.get(0).getNode(), envM);
        return new ASTAbsurdity(first, exp, envM.findParent(transitions.get(0).getProduces()));
    }

    private IASTND elimNegation(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        IASTND first = rule(transitions.get(0).getNode(), marks);
        IASTND second = rule(transitions.get(1).getNode(), marks);
        return new ASTENeg(first, second, exp);
    }

    private IASTND elimUniversal(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        IASTND first = rule(transitions.get(0).getNode(), marks);
        return new ASTEUni(first, exp);
    }

    private IASTND introExistential(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        IASTND first = rule(transitions.get(0).getNode(), marks);
        return new ASTIExist(first, exp);
    }

    private IASTND introUniversal(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        IASTND first = rule(transitions.get(0).getNode(), marks);
        return new ASTIUni(first, exp);
    }

    private IASTND elimExistential(List<StateTransitionEdge> transitions, Env<IFormula, String> marks, IASTExp exp) {
        Env<IFormula, String> envM = marks.beginScope();
        envM.bind(transitions.get(1).getProduces(), String.valueOf(mark++));
        IASTND first = rule(transitions.get(0).getNode(), marks);
        IASTND second = rule(transitions.get(1).getNode(), envM);
        return new ASTEExist(first, second, exp, envM.findParent(transitions.get(1).getProduces()));
    }
}
