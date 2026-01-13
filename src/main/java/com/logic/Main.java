package com.logic;

import com.logic.api.LogicAPI;
import com.logic.exps.asts.IASTExp;
import com.logic.others.Utils;
import com.logic.parser.Parser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {

    public static ByteArrayInputStream readFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(
                filePath.replace("/", "\\")))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ByteArrayInputStream(contentBuilder.toString().getBytes());
    }

    public static IASTExp parsePL(String expression) throws Exception {
        Parser parser = new Parser(new ByteArrayInputStream((expression).getBytes()));
        return parser.parsePL();
    }


    public static void main(String[] args) throws Exception {

        System.out.println("Evaluating:");

        //TransitionGraph t = new TransitionGraph(
        //        parsePL("((p ∨ q) ∨ (r ∨ s)) → ((p ∨ s) ∨ (r ∨ q))"), new HashSet<>());
        //StateGraph s = new ParallelStateGraph(t, 20, 500);

        //System.out.println(s.findSolution());

        try {
            //System.out.println(Utils.getToken(LogicAPI.parsePL("a ?a b").toString()));
            ByteArrayInputStream stream = readFile("src/main/java/com/logic/code.logic");
            String result = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

            var a = LogicAPI.parseNDFOLProof(result);
            //System.out.println(Utils.getToken(a.getConclusion()+" " ));
            //a.getPremises().forEachRemaining(p->   System.out.println(Utils.getToken(p+" " )));
            System.out.println(Utils.printFormattedExp(a.toString()));

            //System.out.print("{");
            //proofObj.getPremises().forEachRemaining(i -> System.out.print(Utils.getToken(i + ".")));
            //System.out.println("} |= " + proofObj.getConclusion());
            //System.out.println(Utils.getToken(proofObj + ""));

            //LogicAPI.checkNDProblem(proofObj,
            //        Set.of(/*LogicAPI.parsePL("p ∧ q")),
             //       LogicAPI.parsePL("(a ∨ a) → a"));

        }catch (Exception e) {
            System.out.println(Utils.printFormattedExp(e.getMessage()));
        }

        /*try{
            System.out.println(LogicAPI.parseFOL(""));
        }catch (FeedbackException e) {
            for(FeedbackLevel l : FeedbackLevel.values()) {
                System.out.println(l.name()+":\n"+e.getFeedback(l)+"\n");
            }
        }*/
        //System.out.println(LogicAPI.parsePL("a ∨ (c ∨ d)."));
        //System.out.println(LogicAPI.parsePL("(a ∨ c) ∨ d."));

        //System.out.println(LogicAPI.parseNDFOLProof("[∀I] [∀x P(x).[⊥,3] [P(x).[¬E] [⊥.[∃I] [∃x ¬P(x).[H,1] [¬P(x).]][H,2] [¬∃x ¬P(x).]]]]"));

        //ByteArrayInputStream stream = new ByteArrayInputStream((proof).getBytes());
        //System.out.println(new Parser(stream).parseNDPL());

    }

}
