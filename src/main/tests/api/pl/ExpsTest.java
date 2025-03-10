package api.pl;

import com.logic.api.LogicAPI;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ExpsTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "aa",
            "a a",
            "Z",
            "a ∧",
            "a ∧ bb",
            "a ∧ T",
            "a(",
            "((a(",
            "a ∧ a ∧ p",
            "a ∧ a → p",
            "a) ∧ (a → p",
    })
    void testIncorrect1Exps(String expression) {
        assertThrows(Throwable.class, ()->LogicAPI.parsePL(expression));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "p", "q", "p ∧ q", "(a ∧ b) ∧ c", "p ∧ q", "q ∧ p",
            "q ∧ p", "r", "p ∧ (r ∧ q)",
            "a ∧ b", "(c ∧ d) ∧ r", "(a ∧ d) ∧ r",
            "p ∧ (q ∧ r)", "(r ∧ p) ∧ q",

            "p → p", "p → (q → p)", "p → q", "q → r", "p → r",
            "p → ((p → q) → q)", "(p → q) → (p → r)", "q → (p → r)",
            "(p → q) → p", "q → p", "p → (q → r)", "q → (p → r)",
            "p → (q → r)", "p → q", "p → r", "(p → p) → q", "(q → r) → r",
            "(p → (q → r)) → ((p → q) → (p → r))",

            "p ∧ q", "p → q", "(p ∧ q) → p",
            "p → (q ∧ r)", "p → q",
            "((p ∧ q) → q) → (q → p)", "q → p",
            "(p ∧ q) → r", "p → (q → r)",
            "(p → q) ∧ (p → r)", "p → (q ∧ r)",
            "p → (q ∧ r)", "(p → q) ∧ (p → r)",

            "p ∨ q", "q ∨ p", "p ∨ q", "p ∨ (q ∨ r)",
            "(p ∨ q) ∨ r", "p ∨ (q ∨ r)",
            "(p ∨ q) ∨ (r ∨ a)", "(p ∨ a) ∨ (r ∨ q)",

            "p ∧ (q ∨ r)", "(p ∧ q) ∨ (p ∧ r)",
            "(p ∨ q) ∧ (p ∨ r)", "p ∨ (q ∧ r)",
            "(p ∧ q) ∨ (p ∧ r)", "p ∧ (q ∨ r)",
            "p ∨ (q ∧ r)", "(p ∨ q) ∧ (p ∨ r)",

            "(p → q) ∨ q", "p → q",
            "p ∨ q", "(p → q) → q",
            "(p → q) → (p → r)", "(p ∨ r) → (q → r)",
            "(p → q) ∨ (p → r)", "p → (q ∨ r)",

            "(p → q) ∧ (q → p)", "(p ∨ q) → (p ∧ q)",
            "(p ∨ q) → (p ∧ q)", "(p → q) ∧ (q → p)",
            "(q → r) ∧ (q ∨ p)", "(p → q) → (r ∧ q)",

            "p ↔ q", "q ↔ p", "p", "(p ↔ q) ↔ r", "q ↔ r",
            "(p ↔ q) ↔ (q ↔ p)",

            "(p ∨ q) ↔ q", "p → q", "(p ∧ q) ↔ p", "p → q",
            "p → q", "(p ∨ q) ↔ q", "p → q", "(p ∧ q) ↔ p",
            "(p → q) ∧ (q → p)", "p ↔ q", "(p ∧ q) → ((p → q) → p)",
            "((p → q) ↔ p) → (p ↔ q)",
            "((p ∨ q) ↔ q) ↔ p", "p ↔ q",
            "p → (q ↔ r)", "(p ∧ q) ↔ (p ∧ r)",
            "(p ∨ (q ∧ r)) ↔ ((p ∨ q) ∧ (p ∨ r))",

            "p", "¬¬p", "¬p", "¬(p ∧ q)", "p → ¬p", "¬p",
            "¬(p → q)", "¬q", "¬(p ∧ q)", "p → ¬q",
            "p → q", "¬q → ¬p", "¬((p ∧ ¬p) ∨ (q ∧ ¬q))",
            "¬(p ∨ q)", "¬p ∧ ¬q", "¬p ∨ ¬q", "¬(p ∧ q)",

            "¬p", "p → q", "p ∧ ¬p", "q",
            "p ∨ q", "¬p → q", "p → q", "p ∧ ¬q", "r",
            "p ∨ q", "p ↔ q", "¬(p ∧ q)", "r",

            "¬¬p", "p", "p ∨ ¬p", "¬(¬p ∨ ¬q)", "p ∧ q",
            "¬(p ∧ q)", "¬p ∨ ¬q",

            "¬(p → q)", "p", "(p → q) → p", "p",
            "p ↔ ¬¬q", "p ↔ q", "(p → q) → q", "¬q → p",
            "¬p ∧ ¬q", "¬(p ∨ q)", "p ∨ (p → q)",
            "(p → q) ∨ (q → r)",
            "¬p → q", "r ∨ ¬q", "p → (a ∨ b)", "¬r ∧ ¬b", "a",
            "p → (q ∨ r)", "(p → q) ∨ (p → r)",
            "¬(p ∧ q) ↔ (¬p ∨ ¬q)"

    })
    void testCorrect1Exps(String expression) {
        assertDoesNotThrow(()->LogicAPI.parsePL(expression));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "p", "q", "r", "a", "b", "x", "y", "z", "⊤", "⊥",

            "¬p", "¬q", "¬r", "¬(p ∧ q)", "¬(p ∨ q)", "¬(p → q)", "¬(p ↔ q)",

            "p ∧ q", "p ∨ q", "p → q", "p ↔ q",
            "(p ∧ q) ∨ (r ∧ s)", "(p → q) ∧ (q → p)", "(p ↔ q) → r",
            "¬(p ∧ q) ∨ r", "¬(p ∧ q) ∧ (p → q)",
            "p ∧ (q ∨ r)", "(p ∧ q) ∨ (r ∧ s)", "(p → q) ∧ (r → s)",
            "((p ∧ q) → r) ∧ ((r ∨ s) → t)",
            "(p → (q → r)) ↔ (p → r)", "p ↔ (q ∨ r)",
            "(p ∧ q) → (r ∨ (s ∧ t))", "(p → q) ∧ (q → p)",
            "(p ↔ q) → (r → s)", "(p → q) → (p ∨ r)",
            "(¬p) ∨ (q ∧ r)", "¬(p ∧ (q ∨ r))",
            "(p → q) → (r ∨ s)", "p ↔ (q ∨ r)", "(p → (q → r))",
            "p → (q ∨ (r ∧ s))", "(p ∨ (q ∧ r)) → p",
            "(p → q) → ((p → q) → r)", "¬(p ∨ q) ∨ (p ∧ q)"
    })
    void testCorrect2Exps(String expression) {
        assertDoesNotThrow(() -> LogicAPI.parsePL(expression));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "((p ∧ q) ∨ r)", "(p ∨ (q ∧ r))", "(p → (q → r))", "(p → (q → r)) → p",
            "(p → q)", "(p → q) → r", "(p → (q → r)) → (p → r)",
            "(p ∧ q) → ((r → s) ∨ (t → u))",

            "¬¬p", "¬¬¬p", "¬(¬p)", "¬(¬(¬p))",
            "¬(¬p ∧ q)", "¬(p ∨ ¬q)", "¬(p ∧ q) ∧ ¬(r ∨ s)",

            "p ↔ p", "p ↔ q", "p ↔ (q ↔ p)", "(p ↔ q) ↔ p", "(p ∨ q) ↔ p",
            "(p ↔ (q ∧ r)) → s", "(p ∧ q) ↔ (r ∨ s)",

            "p ↔ (q → r)", "p ↔ (q ∧ r)", "(p ↔ q) ↔ (r ∧ s)", "(p ↔ q) ↔ (p ∨ q)",
            "(p ↔ (q → r)) ∧ (r → s)", "(p ↔ q) → (r → s)", "(p ↔ q) ∨ r"
    })
    void testCorrectBoundaryExps(String expression) {
        assertDoesNotThrow(() -> LogicAPI.parsePL(expression));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "a a", "p → → q", "p → (q", "(p ∧ q", "p ∨", "∧ p", "p ∨ (", "p → q)",
            "((a ∧ b)", "p ↔", "p ∧ (q ∨ r", "(p → q ∨", "p ∧ (¬q ∨", "⊥ → (p →",
            "(p ∧ q ∨ r", "p → (q →", "¬(p → (q →"
    })
    void testIncorrect2Exps(String expression) {
        assertThrows(Throwable.class, () -> LogicAPI.parsePL(expression));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "(p → (q ∨ (r ∧ s)))", "((p → q) ↔ (r → s)) ∧ (p ∧ q)", "(p → (q → (r ∨ s)))",
            "(¬p → q) ↔ (r → (s ∧ t))", "(p → (q ↔ r)) ∨ ((s → t) ∧ r)",
            "(p ∧ (q → r)) ∨ ((p → q) → (r ∨ s))", "(p → q) ∨ ((r → s) ↔ (p → q))",
            "(p ↔ (q ∧ r)) → ((s ∨ t) ↔ (u → v))"
    })
    void testComplexExps(String expression) {
        assertDoesNotThrow(() -> LogicAPI.parsePL(expression));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "φ ∧ ψ", "α ∨ β", "γ → δ", "λ ∧ μ", "ρ → σ", "τ ↔ ω",
            "(φ ∧ (ψ → α))", "(τ → ω) ∧ (λ ↔ μ)", "(β ∧ γ) → δ"
    })
    void testGreekSymbolsExps(String expression) {
        assertDoesNotThrow(() -> LogicAPI.parsePL(expression));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "⊤", "⊥", "⊤ ∧ ⊥", "⊥ ∨ ⊤", "⊤ → ⊥", "⊥ ↔ ⊤",
            "(⊥ ∧ ⊤) → (⊥ ∨ ⊤)", "(⊥ → ⊤) ↔ (⊥ ∨ ⊤)", "⊤ → (⊥ ∨ ⊤)",
            "(⊥ → (⊤ ∧ ⊥)) ↔ ⊥"
    })
    void testSpecialLogicSymbolsExps(String expression) {
        assertDoesNotThrow(() -> LogicAPI.parsePL(expression));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "(φ ∧ ψ) → (α ∨ β)",
            "(λ ∨ μ) → (ρ ∧ σ)",
            "(τ → ω) ↔ (λ ∨ (ρ ∧ σ))",
            "(α ↔ β) ∧ (γ → δ)",
            "((φ → (ψ ↔ α)) ∨ (β ∧ (γ → δ)))",
            "(λ ∧ μ) ↔ ((ρ ∨ σ) → (τ ∧ ω))",
            "(α ∨ (β ∧ (γ → δ))) ↔ (ε → φ)",
            "((τ ∧ ω) → (λ ∨ μ)) ↔ (ρ ∧ σ)",
            "(τ → (ρ ∧ (σ ∨ λ))) ↔ (μ → α)",
            "(φ ∧ ψ) → ((λ → μ) ∧ (ρ ∨ σ))"
    })
    void testComplexGreekSymbolsExps(String expression) {
        assertDoesNotThrow(() -> LogicAPI.parsePL(expression));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "(p ∧ φ) → (q ∨ ψ)",
            "(r → (α ∧ τ)) ↔ (β ∨ λ)",
            "(a ∧ b) → (γ ∨ (δ ∧ ε))",
            "(x ∨ (φ ∧ ψ)) → (y ∧ (α → β))",
            "(p → (λ ∧ μ)) ↔ ((r ∨ s) ∧ (τ ∨ ω))",
            "(z ∧ φ) → (p ∨ (q ∧ α))",
            "(p ↔ (r ∨ τ)) → (q ∧ (μ → φ))",
            "(x ∨ (y ∧ (α → β))) → (λ ∧ φ)",
            "(a → (β ∨ γ)) ∧ (δ → (τ ∨ ω))",
            "(p ∧ (q ∨ (r → τ))) ↔ (α ∧ (β ∨ (γ → δ)))"
    })
    void testMixGreekAndLettersExps(String expression) {
        assertDoesNotThrow(() -> LogicAPI.parsePL(expression));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "(p ∧ φ) → (q ∨ ψ).dwadawd",
            "(r → (α ∧ b)) ∨ (β ∨ λ).WDAWDAWDC ADAW DA DA",
    })
    void testEOF(String expression) {
        assertDoesNotThrow(() -> System.out.println(LogicAPI.parsePL(expression)));
    }

}
