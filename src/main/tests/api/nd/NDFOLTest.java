package api.nd;

import com.logic.api.INDProof;
import com.logic.api.LogicAPI;
import com.logic.exps.asts.IASTExp;
import com.logic.nd.algorithm.state.ParallelStateGraph;
import com.logic.nd.algorithm.state.StateGraph;
import com.logic.nd.algorithm.transition.TransitionGraph;
import com.logic.others.Utils;
import com.logic.parser.ParseException;
import com.logic.parser.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

public class NDFOLTest {

    @ParameterizedTest
    @ValueSource(strings = {
            " [∀E] [∃y (P(y) ∧ P(y)). [H, 1] [∀x∃y(P(x) ∧ P(y)).]]",
            " [∃I] [∃y∀x Eq(y,x). [H, 1] [∀x Eq(x,x).]]",
            " [∀I] [∀x Q(x). [→E] [ Q(x). [H,2] [P(x).] [∀E] [P(x) → Q(x). [H,1] [ ∀y (P(y) → Q(y)).]]]]",
            " [∀I] [ ∀x P(x). [H,1] [P(y).]]",
            " [∃I] [∃y ∀x (P(x) ∧ P(y)). [∀I] [∀x (P(x) ∧ P(y)). [∀E] [P(y) ∧ P(y). [H,1] [ ∀y (P(y) ∧ P(y)).]]]]",
            " [∀I] [ ∀x Par(sq(x)). [∃E, 3] [ Par(sq(x)). [H,1] [∃x Par(x).] [→E] [ Par(sq(x)). [H,3] [Par(x).] [∀E] [ Par(x) → Par(sq(x)). [H,2] [∀x (Par(x) → Par(sq(x))).]]]]]",
            " [∃E,3] [ ∃x (P(x) ∧Q(x)). [∃I] [ ∃x P(x). [H,1] [P(a).]] [∃I] [ ∃x (P(x) ∧Q(x)). [∧I] [ P(x) ∧Q(x). [H,3] [P(x).] [H,2] [Q(x).]]]]",
            " [∃E,2] [∃z(Par(z) ∧ P(z)). [H,1] [∃x (Par(x) ∧ P(y)).] [∃I] [∃z (Par(z) ∧ P(z)). [H,2] [Par(y) ∧ P(y).]]]"
    })
    void testFail(String proof) {
        Throwable thrown = Assertions.assertThrows(Throwable.class, () -> {
            LogicAPI.parseNDFOLProof(proof);
        });
        System.out.println(Utils.getToken(thrown.getMessage()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            " [→E] [Q(a).[H,2] [P(a).][∀E] [P(a) → Q(a).[H,1] [∀x (P(x) → Q(x)).]]]",
            " [∃I] [∃x Q(x). [→E] [Q(a). [H,2] [P(a).] [∀E] [P(a) → Q(a). [H,1] [∀x (P(x) → Q(x)).]]]]",
            " [∀I] [∀x Q(x). [→E] [ Q(x). [∀E] [P(x). [H,2] [∀y P(y).]] [∀E] [P(x) → Q(x). [H,1] [ ∀y (P(y) → Q(y)).]]]]",
            " [∀I] [∀x P(x). [⊥, 3] [P(x). [¬E] [⊥. [H,2] [ ¬∃x¬P(x).] [∃I] [ ∃x ¬P(x). [H,3] [¬P(x).]]]]]",
            " [∀I] [∀y ∀x P(y,x). [∀I] [∀x P(z,x). [∀E] [ P(z,x). [∀E] [ ∀y P(z,y). [H,1] [ ∀x ∀y P(x,y).]]]]]"

    })
    void testSuccess(String proof) {
        Assertions.assertDoesNotThrow(() -> {
            INDProof ndProof = LogicAPI.parseNDFOLProof(proof);

            System.out.print("{");
            ndProof.getPremises().forEachRemaining(i-> System.out.print(Utils.getToken(i+".")));
            System.out.println("} |= " + ndProof.getConclusion());
        });
    }

}
