package com.logic.exps;

import com.logic.api.IFormula;
import com.logic.exps.asts.IASTExp;
import com.logic.exps.asts.others.ASTBottom;
import com.logic.exps.asts.others.ASTLiteral;
import com.logic.exps.asts.unary.ASTNot;
import com.logic.exps.interpreters.FOLWFFInterpreter;

public class ExpUtils {

    public static final IASTExp BOT = new ASTBottom();
    public static final IFormula BOTF = FOLWFFInterpreter.check(new ASTBottom());

    public static boolean isLiteral(IASTExp exp) {
        if(exp instanceof ASTLiteral) return true;
        if(exp instanceof ASTNot not) return  isLiteral(not.getExp());
        return false;
    }

    public static IASTExp negate(IASTExp exp) {
        return new ASTNot(exp);
    }

    public static IASTExp invert(IASTExp exp) {
        if (exp instanceof ASTNot not)
            return not.getExp();
        return new ASTNot(exp);
    }

}
