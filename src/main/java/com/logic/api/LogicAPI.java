package com.logic.api;

import com.logic.asts.FOLExp;
import com.logic.asts.PLExp;
import com.logic.parser.ExpressionsParser;
import com.logic.parser.ParseException;

import java.io.ByteArrayInputStream;

public class LogicAPI {

    LogicAPI() {}

    public static IPLExp parsePL(String expression) throws ParseException {
        ExpressionsParser parser = new ExpressionsParser(new ByteArrayInputStream((expression).getBytes()));
        return new PLExp(parser.parsePL());
    }

    public static IFOLExp parseFOL(String expression) throws ParseException {
        ExpressionsParser parser = new ExpressionsParser(new ByteArrayInputStream((expression).getBytes()));
        return new FOLExp(parser.parseFOL());
    }

}
