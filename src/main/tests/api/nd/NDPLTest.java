package api.nd;

import com.logic.api.INDProof;
import com.logic.api.LogicAPI;
import com.logic.others.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NDPLTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "[→I, 1] [(a ∨ a) → a. [∨E, 3, 3] [a. [H, 1] [a ∨ a.] [H, 3] [a.] [H, 3] [a.]]]",
            "[→I, 1] [(a ∨ a) → a. [∨E, , 3] [a. [H, 1] [a ∨ a.] [H, 3] [a.] [H, 3] [a.]]]",
            "[→I, 1] [(a ∨ a) → a. [∨E, , ] [a. [H, 1] [a ∨ a.] [H, 2] [a.] [H, 3] [a.]]]"
    })
    void testSingle1(String exp) {

        Assertions.assertDoesNotThrow(() -> {
            INDProof proof = LogicAPI.parseNDPLProof(exp);
            System.out.print("{");
            proof.getPremises().forEachRemaining(i -> System.out.print(Utils.printFormattedExp(i + ".")));
            System.out.println("} |= " + proof.getConclusion());
            //System.out.println("Size: " + proof.size() + " Height: " + proof.height());
            //System.out.println(proof);
        });
    }

}
