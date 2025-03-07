package com.logic;

import com.logic.api.LogicAPI;
import com.logic.parser.ParseException;


public class Main {

    public static void main(String[] args) throws ParseException {
        System.out.println("Evaluating:");
        System.out.println(LogicAPI.parseFOL(
                """
                
                ∃x P(marta,ba(marta,x), x)
                
                """));
    }

}
