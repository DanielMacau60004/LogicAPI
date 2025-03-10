package com.logic.exps;

import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.others.ASTLiteral;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.asts.unary.ASTParenthesis;
import com.logic.parser.Parser;

public class ExpsLogic {

    public static final IASTExp BOT = new ASTLiteral(Parser.BOTTOM);

    public static IASTExp negate(IASTExp exp) {
        if(exp instanceof ASTLiteral || exp instanceof ASTParenthesis)
            return new ASTNot(exp);
        return new ASTNot(new ASTParenthesis(exp));
    }

    public static IASTExp removeParenthesis(IASTExp exp) {
        while (exp instanceof ASTParenthesis par)
            exp = par.getExp();
        return exp;
    }

}
