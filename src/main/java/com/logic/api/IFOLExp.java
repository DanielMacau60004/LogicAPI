package com.logic.api;

import java.util.Iterator;

/**
 * The {@code IFOLExp} interface represents a first-order logic expression.
 * This interface provides methods to iterate over various components of a first-order logic expression,
 * such as functions, predicates, bounded variables, unbounded variables, and to check whether the expression
 * is a sentence or contains free variables.
 * An instance of {@code IFOLExp} can represent any first-order logic expression, which may include:
 * <ul>
 *     <li>Functions</li>
 *     <li>Predicates</li>
 *     <li>Bounded variables (quantified variables)</li>
 *     <li>Unbounded variables (free variables)</li>
 *     <li>Sentences (logical expressions with no free variables)</li>
 * </ul>
 *
 *
 * <p>
 * The methods in this interface allow for working with first-order logic expressions, such as determining the set of bounded and unbounded variables,
 * checking if a variable is bounded, and verifying whether an expression is a sentence (i.e., has no free variables).
 * </p>
 *
 * @author Daniel Macau
 * @version 1.0
 * @since 08-03-2025
 * @see LogicAPI
 */
public interface IFOLExp {

    /**
     * Iterates through all the function names used in the first-order logic expression.
     * A function is typically a mapping from a set of terms to another term, such as "f(x)" or "g(a, b)".
     *
     * @return An iterator over the functions in this expression.
     */
    Iterator<String> iterateFunctions();

    /**
     * Iterates through all the predicate names used in the first-order logic expression.
     * A predicate is a relation between terms, such as "P(x)" or "Father(x, y)".
     *
     * @return An iterator over the predicates in this expression.
     */
    Iterator<String> iteratePredicates();

    /**
     * Iterates through all the bounded variables in the first-order logic expression.
     * A bounded variable is a variable that is within the scope of a quantifier (e.g., "∀x" or "∃y").
     *
     * @return An iterator over the bounded variables in this expression.
     */
    Iterator<String> iterateBoundedVariables();

    /**
     * Checks if a given variable is a bounded variable in this first-order logic expression.
     * A bounded variable is one that is part of a quantifier's scope.
     *
     * @param variable The variable to check.
     * @return {@code true} if the variable is bounded, {@code false} otherwise.
     */
    boolean isABoundedVariable(String variable);

    /**
     * Iterates through all the unbounded variables in the first-order logic expression.
     * An unbounded (or free) variable is one that is not part of any quantifier's scope.
     *
     * @return An iterator over the unbounded variables in this expression.
     */
    Iterator<String> iterateUnboundedVariables();

    /**
     * Checks if this first-order logic expression is a sentence.
     * A sentence is a formula that contains no free (unbounded) variables.
     *
     * @return {@code true} if the expression is a sentence, {@code false} if it contains free variables.
     */
    boolean isASentence();
}
