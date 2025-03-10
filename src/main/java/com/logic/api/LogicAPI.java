package com.logic.api;

import com.logic.exps.checkers.FOLWFFChecker;
import com.logic.exps.checkers.PLWFFChecker;
import com.logic.parser.Parser;

import java.io.ByteArrayInputStream;

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
    private LogicAPI() {}

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
    public static IPLExp parsePL(String expression) throws Exception {
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
    public static IFOLExp parseFOL(String expression) throws Exception {
        Parser parser = new Parser(new ByteArrayInputStream((expression).getBytes()));
        return FOLWFFChecker.check(parser.parseFOL());
    }

}

