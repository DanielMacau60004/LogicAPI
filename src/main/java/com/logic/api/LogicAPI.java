package com.logic.api;

import com.logic.checkers.FOLWFFChecker;
import com.logic.checkers.PLWFFChecker;
import com.logic.parser.ExpressionsParser;
import com.logic.parser.ParseException;

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
