package com.logic.asts.binary;


import com.logic.asts.IASTExp;
import com.logic.interpreters.IInterpreter;

public class ASTBiconditional extends AASTBinaryExp {

	public ASTBiconditional(IASTExp e1, IASTExp e2) {
		super(e1, e2);
	}

	@Override
	public <T, E> T interpret(IInterpreter<T, E> v, E env) {
		return v.visit(this, env);
	}

	@Override
	public String toString() {
		return left.toString() + " " + getToken(ExpressionsParser.BICONDITIONAL) + " " + right.toString();
	}

}
