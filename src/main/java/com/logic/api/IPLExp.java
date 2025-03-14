package com.logic.api;

import java.util.Iterator;
import java.util.Map;

/**
 * The {@code IPLExp} interface represents a propositional logic expression.
 * This interface provides methods to interpret the expression, iterate over the literals, compute its truth table, and
 * check logical equivalence between different propositional logic expressions.
 * An instance of {@code IPLExp} can represent any propositional logic expression, such as:
 * <ul>
 *     <li>Atomic propositions</li>
 *     <li>Negations</li>
 *     <li>Conjunctions</li>
 *     <li>Disjunctions</li>
 *     <li>Implications</li>
 * </ul>
 *
 * <p>
 * The methods in this interface allow for evaluating logical expressions, generating truth tables, and determining equivalence
 * between two propositional expressions based on their truth values.
 * </p>
 *
 * @author Daniel Macau
 * @version 1.0
 * @since 08-03-2025
 * @see LogicAPI
 * @see Map
 */
public interface IPLExp {

    /**
     * Iterates through all literals in the propositional logic expression.
     * A literal is a propositional variable (e.g., "p" or "q").
     *
     * @return An iterator over the literals in this expression.
     */
    Iterator<String> iterateLiterals();

    /**
     * Interprets the propositional expression based on a given interpretation of the literals.
     * The interpretation is provided as a map, where each key is a literal and each value is its corresponding truth value (true/false).
     *
     * <p>
     * If the interpretation does not provide a truth value for every literal, or if the expression contains arbitrary expressions, this method will throw a {@link RuntimeException}.
     * </p>
     *
     * @param interpretation A map of literals and their corresponding truth values.
     * @return {@code true} if the expression evaluates to true under the given interpretation, {@code false} otherwise.
     * @throws RuntimeException If a literal does not have a truth value in the interpretation or if the expression contains arbitrary expressions.
     */
    boolean interpret(Map<String, Boolean> interpretation);

    /**
     * Generates and returns the truth table for the propositional expression.
     * The truth table is represented as a map where:
     * <ul>
     *     <li>The key represents an interpretation of the literals (a map of literals to their truth values).</li>
     *     <li>The value is the truth value of the expression for that particular interpretation.</li>
     * </ul>
     *
     * @return A map where each key is an interpretation (a map of literal-to-truth-value), and the corresponding value is the truth value of the expression for that interpretation.
     */
    Map<Map<String, Boolean>, Boolean> getTruthTable();

    /**
     * Checks if this propositional expression is logically equivalent to another expression.
     *
     * <p>
     * To check equivalence, this method first intersects the set of literals from both expressions.
     * It then reduces the truth tables of both expressions to the set of intersected literals and groups interpretations
     * based on those intersected literals. If the expressions have different truth values for the same grouped interpretation,
     * they are not equivalent.
     * </p>
     *
     * <p>
     * The method compares the truth tables of the reduced expressions. If they match for all possible interpretations,
     * the expressions are equivalent. Otherwise, they are not.
     * </p>
     *
     * @param other The other propositional expression to check for equivalence.
     * @return {@code true} if the two expressions are logically equivalent, {@code false} otherwise.
     * @throws RuntimeException If any of the expressions contain arbitrary expressions.
     */
    boolean isEquivalentTo(IPLExp other);
}
