package com.logic;

import com.logic.nd.asts.IASTND;
import com.logic.nd.checkers.NDChecker;
import com.logic.nd.interpreters.NDInterpreter;
import com.logic.parser.Parser;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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


    public static void main(String[] args) throws Exception {
        System.out.println("Evaluating:");

        IASTND proof = new Parser(readFile("src/main/java/com/logic/code.logic")).parseNDPL();
        NDChecker.checkPL(proof);
        NDInterpreter.interpret(proof);

        System.out.println(proof);

        //ByteArrayInputStream stream = new ByteArrayInputStream((proof).getBytes());
        //System.out.println(new Parser(stream).parseNDPL());

    }

}
