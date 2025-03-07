package com.logic;

import com.logic.api.LogicAPI;
import com.logic.parser.ParseException;


public class Main {

    public static void main(String[] args) throws ParseException {
        System.out.println("Evaluating:");
        System.out.println(LogicAPI.parseFOL(
                """
                
                SF = {m/2, ba/3}
                SP = {P/3, Party/2}
                
                ∃x D(a,aa, a(a), a(a(a(a,b))),c) ∧ D(ab, pedro)
                """));
    }

}
