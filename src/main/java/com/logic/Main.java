package com.logic;

import com.logic.api.IPLExp;
import com.logic.api.LogicAPI;
import com.logic.exps.parser.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {
        System.out.println("Evaluating:");

        IPLExp e1 = LogicAPI.parsePL("a → b");
        IPLExp e2 = LogicAPI.parsePL("b → ¬a");

        System.out.println(e1.isEquivalentTo(e2));

    }

}
