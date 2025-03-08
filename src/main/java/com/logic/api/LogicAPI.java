package com.logic.api;

import com.logic.exps.checkers.FOLWFFChecker;
import com.logic.exps.checkers.PLWFFChecker;
import com.logic.exps.parser.ExpressionsParser;
import com.logic.exps.parser.ParseException;

import java.io.ByteArrayInputStream;

public class LogicAPI {

    LogicAPI() {}

    public static IPLExp parsePL(String expression) throws ParseException {
        ExpressionsParser parser = new ExpressionsParser(new ByteArrayInputStream((expression).getBytes()));
        return PLWFFChecker.check(parser.parsePL());
    }

    public static IFOLExp parseFOL(String expression) throws ParseException {
        ExpressionsParser parser = new ExpressionsParser(new ByteArrayInputStream((expression).getBytes()));
        return FOLWFFChecker.check(parser.parseFOL());
    }

}
