package com.logic;

import com.logic.api.Exp;
import com.logic.parser.ExpressionsParser;
import com.logic.parser.ParseException;

import java.io.ByteArrayInputStream;

public class Main {

    public static Exp parsePL(String expression) throws ParseException {
        ExpressionsParser parser = new ExpressionsParser(new ByteArrayInputStream((expression).getBytes()));
        return parser.parsePL();
    }

    public static Exp parseFOL(String expression) throws ParseException {
        ExpressionsParser parser = new ExpressionsParser(new ByteArrayInputStream((expression).getBytes()));
        return parser.parseFOL();
    }

    public static void main(String[] args) throws ParseException {
        System.out.println("Evaluating:");
        System.out.println(parseFOL(
                """
                
                SF = {m/2, ba/3}
                SP = {P/3, Party/2}
                
                ∃x D(a,aa, a(a), a(a(a(a,b))),c) ∧ D(ab, pedro)
                """));
    }

}
