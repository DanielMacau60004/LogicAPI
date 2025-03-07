package com.logic.api;

import com.logic.parser.ExpressionsParser;
import com.logic.parser.ParseException;

import java.io.ByteArrayInputStream;

public class LogicAPI {

    LogicAPI() {}

    public static Exp parsePL(String expression) throws ParseException {
        ExpressionsParser parser = new ExpressionsParser(new ByteArrayInputStream((expression).getBytes()));
        return parser.parsePL();
    }

    public static Exp parseFOL(String expression) throws ParseException {
        ExpressionsParser parser = new ExpressionsParser(new ByteArrayInputStream((expression).getBytes()));
        return parser.parseFOL();
    }

}
