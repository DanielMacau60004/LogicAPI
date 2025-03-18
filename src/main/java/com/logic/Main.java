package com.logic;

import com.logic.api.INDProof;
import com.logic.api.LogicAPI;
import com.logic.exps.asts.IASTExp;
import com.logic.nd.asts.IASTND;
import com.logic.nd.checkers.NDWWFExpsChecker;
import com.logic.nd.interpreters.NDInterpreter;
import com.logic.parser.Parser;

import java.io.*;
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

        ByteArrayInputStream stream = readFile("src/main/java/com/logic/code.logic");
        String result = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

        INDProof proofObj = LogicAPI.parseNDFOLProof(result);

        System.out.println(proofObj.size()+" " + proofObj.height());
        System.out.println(proofObj);

        //ByteArrayInputStream stream = new ByteArrayInputStream((proof).getBytes());
        //System.out.println(new Parser(stream).parseNDPL());

    }

}
