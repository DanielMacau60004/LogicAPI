package com.logic.api;

import com.logic.exps.Expressions;
import com.logic.exps.exceptions.ExpException;
import com.logic.nd.NDProofs;
import com.logic.nd.exceptions.NDRuleException;

import java.util.Set;

/**
 * The {@code LogicAPI} class provides utility methods for parsing and verifying logical expressions and natural deduction proofs.
 * <p>
 * It supports:
 * <ul>
 *     <li>Parsing and checking well-formedness of propositional logic (PL) formulas.</li>
 *     <li>Parsing and checking well-formedness of first-order logic (FOL) formulas.</li>
 *     <li>Parsing and validating natural deduction (ND) proofs for both PL and FOL.</li>
 * </ul>
 * </p>
 *
 * <p>This class is not intended to be instantiated.</p>
 *
 * @author Daniel Macau
 * @version 1.1
 * @since 08-03-2025
 */
public class LogicAPI {

    /**
     * Private constructor to prevent instantiation of the {@code LogicAPI} class.
     */
    private LogicAPI() {
    }

    /**
     * Parses a propositional logic (PL) formula and checks if it is well-formed.
     *
     * <p>
     * This method takes a propositional logic expression as a string, parses it, and ensures that it is a well-formed formula (WFF)
     * before returning the parsed representation.
     * </p>
     *
     * <p>Propositional Logic Syntax:</p>
     * <ul>
     *     <li><b>Literals:</b> Lowercase characters (a-z).</li>
     *     <li><b>Generics:</b> φ, δ, ψ, α, β, γ.</li>
     *     <li><b>Logical Symbols:</b> ¬ (negation), → (implication), ↔ (biconditional), ∧ (conjunction), ∨ (disjunction), ⊥ (false), ⊤ (true).</li>
     * </ul>
     *
     * @param expression The propositional logic formula to parse and check.
     * @return The parsed {@code IPLFormula} representation of the formula.
     * @throws ExpException If the formula return an error.
     */
    public static IPLFormula parsePL(String expression) throws ExpException {
        return Expressions.parsePLFormula(expression);
    }

    /**
     * Parses a first-order logic (FOL) formula and checks if it is well-formed.
     *
     * <p>
     * This method takes a first-order logic expression as a string, parses it, and ensures that it is a well-formed formula (WFF)
     * before returning the parsed representation.
     * </p>
     *
     * <p>First-Order Logic Syntax:</p>
     * <ul>
     *     <li><b>Generics:</b> φ, δ, ψ, α, β, γ (optionally followed by a number, e.g., φ1, φ2).</li>
     *     <li><b>Logical Symbols:</b> ¬, →, ↔, ∧, ∨, ⊥, ⊤.</li>
     *     <li><b>Variables:</b> Lowercase letters (w-z) with optional numbers (e.g., x1, x2).</li>
     *     <li><b>Predicates:</b> Uppercase words (e.g., Father(...), Adult(...), L(...), L).</li>
     *     <li><b>Functions:</b> Lowercase words (e.g., height(...), weight(...), f(...), daniel).</li>
     * </ul>
     *
     * @param expression The first-order logic formula to parse and check.
     * @return The parsed {@code IFOLFormula} representation of the formula.
     * @throws ExpException If the formula return an error.
     */
    public static IFOLFormula parseFOL(String expression) throws ExpException {
        return Expressions.parseFOLFormula(expression);
    }

    /**
     * Parses and validates a natural deduction (ND) proof for propositional logic (PL).
     *
     * <p>
     * This method takes a structured ND proof as a string, parses it, and ensures its correctness by:
     * </p>
     * <ul>
     *     <li>Checking that all expressions in the proof are well-formed formulas (WFFs).</li>
     *     <li>Validating the correctness of rule applications.</li>
     *     <li>Ensuring that proof structure follows ND inference rules.</li>
     * </ul>
     *
     * <p><b>Supported ND Inference Rules:</b></p>
     * <p>Each rule is represented in the format: <code>[Rule Name, optional marks] [formula. [rule references]]</code></p>
     * <ul>
     *     <li><b>Absurdity (⊥ Introduction):</b> <code>[⊥, m] [formula. [rule]]</code></li>
     *     <li><b>Negation Introduction (¬I):</b> <code>[¬I, m] [formula. [rule]]</code></li>
     *     <li><b>Negation Elimination (¬E):</b> <code>[¬E] [⊥. [rule1] [rule2]]</code></li>
     *     <li><b>Conjunction Elimination Left (∧El):</b> <code>[∧El] [formula. [rule]]</code></li>
     *     <li><b>Conjunction Elimination Right (∧Er):</b> <code>[∧Er] [formula. [rule]]</code></li>
     *     <li><b>Conjunction Introduction (∧I):</b> <code>[∧I] [formula. [rule1] [rule2]]</code></li>
     *     <li><b>Disjunction Introduction Left (∨Il):</b> <code>[∨Il] [formula. [rule]]</code></li>
     *     <li><b>Disjunction Introduction Right (∨Ir):</b> <code>[∨Ir] [formula. [rule]]</code></li>
     *     <li><b>Disjunction Elimination (∨E):</b> <code>[∨E, m, n] [formula. [rule1] [rule2] [rule3]]</code></li>
     *     <li><b>Implication Introduction (→I):</b> <code>[→I, m] [formula. [rule]]</code></li>
     *     <li><b>Implication Elimination (→E):</b> <code>[→E] [formula. [rule1] [rule2]]</code></li>
     * </ul>
     *
     * <p><b>Example of a Valid ND Proof:</b></p>
     * <pre>
     * [→I, 1] [(p ∨ q) → (q ∨ p).
     *     [∨E, 2, 3] [q ∨ p.
     *         [H, 1] [p ∨ q.]
     *         [∨Il] [q ∨ p.
     *             [H, 2] [p.]]
     *         [∨Ir] [q ∨ p.
     *             [H, 3] [q.]]]]
     * </pre>
     *
     * @param expression The string representation of the propositional logic ND proof.
     * @return The parsed and validated {@code INDProof} object.
     * @throws ExpException If the proof contains an invalid expression.
     * @throws NDRuleException If the proof uses a rule incorrectly.
     */
    public static INDProof parseNDPLProof(String expression) throws ExpException, NDRuleException {
        return NDProofs.parseNDPLProof(expression);
    }


    /**
     * Parses and validates a natural deduction (ND) proof for first-order logic (FOL).
     *
     * <p>
     * This method takes a structured ND proof as a string, parses it, and ensures its correctness by:
     * </p>
     * <ul>
     *     <li>Checking that all expressions in the proof are well-formed formulas (WFFs).</li>
     *     <li>Validating the correctness of rule applications.</li>
     *     <li>Ensuring that proof structure follows ND inference rules.</li>
     *     <li>Verifying side conditions specific to first-order logic.</li>
     * </ul>
     *
     * <p><b>Supported ND Inference Rules:</b></p>
     * <p>Each rule is represented in the format: <code>[Rule Name, optional marks] [formula. [rule references]]</code></p>
     *
     * <p>The following rules are valid in FOL, including all the propositional logic (PL) rules:</p>
     * <ul>
     *     <li><b>Universal Introduction (∀I):</b> <code>[∀I] [formula. [rule]]</code></li>
     *     <li><b>Universal Elimination (∀E):</b> <code>[∀E] [formula. [rule]]</code></li>
     *     <li><b>Existential Introduction (∃I):</b> <code>[∃I] [formula. [rule]]</code></li>
     *     <li><b>Existential Elimination (∃E):</b> <code>[∃E, m] [formula. [rule1] [rule2]]</code></li>
     * </ul>
     *
     * <p><b>Example of a Valid ND Proof:</b></p>
     * <pre>
     * [→I, 2] [¬∃x P(x) → ∀x ¬P(x).
     *     [∀I] [∀x ¬P(x).
     *         [¬I, 1] [¬P(x).
     *             [¬E] [⊥.
     *                 [H, 2] [¬∃x P(x).]
     *                 [∃I] [∃x P(x).
     *                     [H, 1] [P(x).]]]]]]
     * </pre>
     *
     * @param expression The string representation of the first-order logic ND proof.
     * @return The parsed and validated {@code INDProof} object.
     * @throws ExpException If the proof contains an invalid expression.
     * @throws NDRuleException If the proof uses a rule incorrectly.
     */
    public static INDProof parseNDFOLProof(String expression) throws ExpException, NDRuleException {
        return NDProofs.parseNDFOLProof(expression);
    }

    /**
     * Validates a complete natural deduction (ND) proof against a set of premises and an expected conclusion.
     *
     * <p>
     * This method checks whether the given ND proof:
     * </p>
     * <ul>
     *     <li>Is correctly derived using valid ND inference rules.</li>
     *     <li>Properly uses the provided premises.</li>
     *     <li>Successfully derives the expected conclusion as the final line of the proof.</li>
     * </ul>
     *
     * <p><b>Use Case:</b></p>
     * <p>This method is ideal for validating that a user's proof actually solves a logical problem (e.g., exercises or automated grading).</p>
     *
     * @param proof The structured and validated ND proof object.
     * @param premises The set of initial premises the proof must be based on.
     * @param conclusion The formula that should be concluded from the premises.
     * @return The same {@code INDProof} object if the proof solves the problem.
     * @throws ExpException If any expressions in the proof are invalid.
     * @throws NDRuleException If the proof is structurally incorrect or does not derive the conclusion.
     */
    public static INDProof checkNDProblem(INDProof proof, Set<IFormula> premises, IFormula conclusion) throws ExpException, NDRuleException {
        return NDProofs.checkNDProblem(proof, premises, conclusion);
    }

}
