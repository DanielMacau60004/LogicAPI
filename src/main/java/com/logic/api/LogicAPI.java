package com.logic.api;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.checkers.FOLWFFChecker;
import com.logic.exps.checkers.PLWFFChecker;
import com.logic.nd.asts.IASTND;
import com.logic.nd.checkers.NDMarksChecker;
import com.logic.nd.checkers.NDSideCondChecker;
import com.logic.nd.checkers.NDWWFChecker;
import com.logic.nd.checkers.NDWWFExpsChecker;
import com.logic.nd.interpreters.NDInterpreter;
import com.logic.parser.Parser;

import java.io.ByteArrayInputStream;
import java.util.Map;

/**
 * The {@code LogicAPI} class provides utility methods for the API.
 *
 * <p>It offers methods for parsing PL and FOL expressions and checking their well-formedness.</p>
 *
 * @author Daniel Macau
 * @version 1.0
 * @since 08-03-2025
 */
public class LogicAPI {

    /**
     * Private constructor to prevent instantiation of the {@code LogicAPI} class.
     */
    private LogicAPI() {
    }

    /**
     * Parses a propositional logic (PL) expression and checks its well-formedness.
     *
     * <p>This method accepts a propositional logic expression in string form, parses it, and ensures that it is a well-formed formula
     * (WFF) before returning a parsed representation.</p>
     *
     * @param expression The propositional logic expression to parse and check.
     * @return The parsed {@code IPLExp} representation of the propositional logic expression.
     * @throws Exception If the expression is invalid or cannot be parsed.
     * @see Parser
     * @see PLWFFChecker
     */
    public static IPLFormula parsePL(String expression) throws Exception {
        Parser parser = new Parser(new ByteArrayInputStream((expression).getBytes()));
        return PLWFFChecker.check(parser.parsePL());
    }

    /**
     * Parses a first-order logic (FOL) expression and checks its well-formedness.
     *
     * <p>This method accepts a first-order logic expression in string form, parses it, and ensures that it is a well-formed formula
     * (WFF) before returning a parsed representation.</p>
     *
     * @param expression The first-order logic expression to parse and check.
     * @return The parsed {@code IFOLExp} representation of the first-order logic expression.
     * @throws Exception If the expression is invalid or cannot be parsed.
     * @see Parser
     * @see FOLWFFChecker
     */
    public static IFOLFormula parseFOL(String expression) throws Exception {
        Parser parser = new Parser(new ByteArrayInputStream((expression).getBytes()));
        return FOLWFFChecker.check(parser.parseFOL());
    }

    //TODO add documentation
    public static INDProof parseNDPLProof(String expression) throws Exception {
        IASTND proof = new Parser(new ByteArrayInputStream((expression).getBytes())).parseNDPL();

        Map<IASTExp, IFormula> formulas = NDWWFExpsChecker.checkPL(proof);
        NDWWFChecker.check(proof, formulas);
        Map<IASTExp, Integer> premises = NDMarksChecker.check(proof, formulas);
        return NDInterpreter.interpret(proof, formulas, premises);
    }

    //TODO add documentation
    public static INDProof parseNDFOLProof(String expression) throws Exception {
        IASTND proof = new Parser(new ByteArrayInputStream((expression).getBytes())).parseNDFOL();

        Map<IASTExp, IFormula> formulas = NDWWFExpsChecker.checkFOL(proof);
        NDWWFChecker.check(proof, formulas);
        Map<IASTExp, Integer> premises = NDMarksChecker.check(proof, formulas);
        NDSideCondChecker.check(proof, formulas, premises);
        return NDInterpreter.interpret(proof, formulas, premises);
    }

}

