package com.logic.exps.asts;

import com.logic.exps.parser.ExpressionsParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AASTExp implements IASTExp {

    protected String getToken(int kind) {
        Pattern pattern = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");
        Matcher matcher = pattern.matcher(ExpressionsParser.tokenImage[kind].replace("\"",""));

        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String hexCode = matcher.group(1);
            int codePoint = Integer.parseInt(hexCode, 16);
            matcher.appendReplacement(result, String.valueOf((char) codePoint));
        }

        matcher.appendTail(result);
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AASTExp s)
            return this.toString().equals(s.toString());
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
