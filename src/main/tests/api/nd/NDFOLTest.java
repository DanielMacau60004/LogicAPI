package api.nd;

import com.logic.api.INDProof;
import com.logic.api.LogicAPI;
import com.logic.exps.asts.IASTExp;
import com.logic.nd.algorithm.state.ParallelStateGraph;
import com.logic.nd.algorithm.state.StateGraph;
import com.logic.nd.algorithm.state.StateSolution;
import com.logic.nd.algorithm.transition.TransitionGraphFOL;
import com.logic.nd.algorithm.transition.TransitionGraphPL;
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

    public static IASTExp createExpression(String expression) throws ParseException {
        Parser parser = new Parser(new ByteArrayInputStream((expression + ".").getBytes()));
        return parser.parseFOL();
    }


    @ParameterizedTest
    @ValueSource(strings = {
            " [∀E] [∃y (P(y) ∧ P(y)). [H, 1] [∀x∃y(P(x) ∧ P(y)).]]",
            " [∃I] [∃y∀x Eq(y,x). [H, 1] [∀x Eq(x,x).]]",
            " [∀I] [∀x Q(x). [→E] [ Q(x). [H,2] [P(x).] [∀E] [P(x) → Q(x). [H,1] [ ∀y (P(y) → Q(y)).]]]]",
            " [∀I] [ ∀x P(x). [H,1] [P(y).]]",
            " [∃I] [∃y ∀x (P(x) ∧ P(y)). [∀I] [∀x (P(x) ∧ P(y)). [∀E] [P(y) ∧ P(y). [H,1] [ ∀y (P(y) ∧ P(y)).]]]]",
            " [∀I] [ ∀x Par(sq(x)). [∃E, 3] [ Par(sq(x)). [H,1] [∃x Par(x).] [→E] [ Par(sq(x)). [H,3] [Par(x).] [∀E] [ Par(x) → Par(sq(x)). [H,2] [∀x (Par(x) → Par(sq(x))).]]]]]",
            " [∃E,3] [ ∃x (P(x) ∧Q(x)). [∃I] [ ∃x P(x). [H,1] [P(a).]] [∃I] [ ∃x (P(x) ∧Q(x)). [∧I] [ P(x) ∧Q(x). [H,3] [P(x).] [H,2] [Q(x).]]]]",
            " [∃E,2] [∃z(Par(z) ∧ P(z)). [H,1] [∃x (Par(x) ∧ P(y)).] [∃I] [∃z (Par(z) ∧ P(z)). [H,2] [Par(y) ∧ P(y).]]]",
            " [∀I] [∀x ∃y φ. [∃I] [∃y φ. [∀E] [φ. [∃E,1] [∀x φ. [H,2] [∃y ∀x φ.] [H,3] [∀x φ.]]]]]",
            " [∀E] [φ. [∃E,1] [∀x φ. [H,2] [∃y ∀x φ.] [H,1] [∀x φ.]]]"
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
            " [∀I] [∀y ∀x P(y,x). [∀I] [∀x P(z,x). [∀E] [ P(z,x). [∀E] [ ∀y P(z,y). [H,1] [ ∀x ∀y P(x,y).]]]]]",
            " [∃E,2] [∀x ∃y φ. [H,1] [∃y ∀x φ.] [∀I] [∀x ∃y φ. [∃I] [∃y φ. [∀E] [φ. [H,2] [∀x φ.]]]]]",
            " [∃E,2] [∀x ∃y φ. [H,1] [∃y ∀x φ.] [∀I] [∀x ∃y φ. [∃I] [∃y φ. [∀E] [φ. [H,2] [∀x φ.]]]]]",
            " [→I,2] [¬∃x P(x) → ∀x ¬P(x). [∀I] [∀x ¬P(x). [¬I,1] [¬P(x). [¬E] [⊥. [H,2] [¬∃x P(x).] [∃I] [∃x P(x). [H,1] [P(x).]]]]]]",
            " [→I,2] [∀x φ → ¬∃x ¬φ. [¬I,1] [¬∃x ¬φ. [∃E, 3] [⊥. [H,1] [∃x ¬φ.] [¬E] [⊥. [H,3] [¬φ.] [∀E] [φ. [H,2] [∀x φ.]]]]]]",
            " [→I,22] [∀x φ → ¬∃x ¬φ. [¬I,11] [¬∃x ¬φ. [∃E, 3] [⊥. [H,10] [∃x ¬φ.] [¬E] [⊥. [H,3] [¬φ.] [∀E] [φ. [H,20] [∀x φ.]]]]]]"
    })
    void testSuccess(String proof) {
        Assertions.assertDoesNotThrow(() -> {
            INDProof ndProof = LogicAPI.parseNDFOLProof(proof);

            System.out.print("{");
            ndProof.getPremises().forEachRemaining(i -> System.out.print(Utils.getToken(i + ".")));
            System.out.println("} |= " + ndProof.getConclusion());
        });
    }


    @ParameterizedTest
    @ValueSource(strings = {
            /*1*/"∀x P(x) ∨ ∀x Q(x). ∀x (P(x) ∨ Q(x))",
            /*2*/"∀x (P(x) ∧ Q(x)). ∀x P(x) ∧ ∀x Q(x)",
            /*3*/"(∀x P(x) ∧ ∀x Q(x)) → ∀x (P(x) ∧ Q(x))",
            /*4*/"∃x (P(x) ∧ Q(x)). ∃x P(x) ∧ ∃x Q(x)",
            /*5*/"(∃x P(x) ∨ ∃x Q(x)) → ∃x (P(x) ∨ Q(x))",
            /*6*/"∃x (P(x) ∨ Q(x)). ∃x P(x) ∨ ∃x Q(x)",
            /*7*/"∀x (P(x) → Q(x)). ∀x P(x) → ∀x Q(x)",
            /*8*/"∃y∀x φ. ∀x∃y φ",
            /*9*/"∃x ¬P(x) → ¬∀x P(x)",
            /*10*/"¬∀x P(x) → ∃x ¬P(x)",
            /*11*/"∀x ¬P(x) → ¬∃x P(x)",
            /*12*/"¬∃x P(x) → ∀x ¬P(x)",
            /*13*/"∃x φ → ¬∀x ¬φ",
            /*14*/"¬∀x ¬φ. ∃x φ",
            /*15*/"∀x φ → ¬∃x ¬φ",
            /*17*///"((∀x φ ∧ ψ) → ∀x (φ ∧ ψ)) ∧ (∀x (φ ∧ ψ) → (∀x φ ∧ ψ))", //Only works if we add x in the set of notfree
            /*23*///"(∀x (φ → ψ) → (∃x φ → ψ)) ∧ ((∃x φ → ψ) → ∀x (φ → ψ))", //Only works if we add x in the set of notfree

            /*2*/"∀y(C(y) ∨ D(y)). ∀x(C(x) → L(x)). ∃x¬L(x). ∃x D(x)",
            /*3*/"∀x(C(x) → S(x)). ∀x(¬A(x,b) → ¬S(x)). ∀x((C(x)∨S(x)) → A(x,b))",
            /*4*///"L(a,b). ∀x(∃y(L(y,x) ∨ L(x,y)) → L(x,x)). ∃x L(x,a)", //Is not computable
            /*5*/"∀x ∀y (L(x,y) → L(y,x)). ∃x ∀y L(x,y). ∀x ∃y L(x,y)", //Require aux variables but is computable
    })
    void testAlgorithm(String premissesAndExpression) throws ParseException {
        String[] parts = premissesAndExpression.split("\\.");
        String expression = parts[parts.length - 1].trim();

        Set<IASTExp> premisses = new HashSet<>();
        for (int i = 0; i < parts.length - 1; i++) {
            premisses.add(createExpression(parts[i].trim()));
        }

        TransitionGraphFOL t = new TransitionGraphFOL(createExpression(expression), premisses);
        t.build();
        StateGraph s = new ParallelStateGraph(t, 10, 2000, 5);

        System.out.println(Utils.getToken(t+""));

        Assertions.assertTrue(s.isSolvable(), "Not solvable!");
        Assertions.assertDoesNotThrow(() -> {
            INDProof proof = new StateSolution(s, true).findSolution();
            System.out.println("Size: " + proof.size() + " Height: " + proof.height());
            System.out.println(proof);
        });
    }

}
