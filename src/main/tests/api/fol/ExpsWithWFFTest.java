package api.fol;

import com.logic.api.IFOLExp;
import com.logic.api.LogicAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ExpsWithWFFTest {

    @ParameterizedTest
    @CsvSource({
            "'∀x (φ → (α ∨ β))', '', ''",
            "'P(x) ∧ Father(x, y)', '', 'x,y'",
            "'P(x) ∧ Father(x, dani)', '', 'x'",
            "'(∃x (P(x) ∧ ∀y Father(x, y)))', 'x,y', ''",
            "'∀x (L(x) → (P(f(x)) ∧ Q(y)))', 'x', 'y'",
            "'∀x (L(x) → (P(dani) ∧ Q(y))) ∧ ∀y (L(x) → (P(dani) ∧ Q(y)))','x,y', 'x,y'",
    })
    void test(String expression, String boundedStr, String unboundedStr) {
        AtomicReference<IFOLExp> exp = new AtomicReference<>();
        assertDoesNotThrow(() -> exp.set(LogicAPI.parseFOL(expression)));

        if (!boundedStr.isEmpty()) {
            Set<String> bounded = new HashSet<>(Arrays.asList(boundedStr.split(",")));
            Iterator<String> it = exp.get().iterateBoundedVariables();
            Set<String> iteratedBounded = new HashSet<>();
            it.forEachRemaining(iteratedBounded::add);

            Assertions.assertEquals(iteratedBounded, bounded);
        }

        if (!unboundedStr.isEmpty()) {
            Set<String> unbounded = new HashSet<>(Arrays.asList(unboundedStr.split(",")));
            Iterator<String> it = exp.get().iterateUnboundedVariables();
            Set<String> iteratedUnbounded = new HashSet<>();
            it.forEachRemaining(iteratedUnbounded::add);

            Assertions.assertEquals(iteratedUnbounded, unbounded);
        }

        System.out.println(exp.get());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "P(x) ∧ P(x, y)",
            "P(f(x)) ∧ P(f(x, y))",
            "P ∧ P(x)",
            "L(dani, dani(x))"
    })
    void testComplexGreekPredicatesFOLOperations(String expression) {
        Throwable thrown = assertThrows(Throwable.class, () -> LogicAPI.parseFOL(expression));
        System.out.println("Thrown Exception: " + thrown.getMessage());
    }

}
