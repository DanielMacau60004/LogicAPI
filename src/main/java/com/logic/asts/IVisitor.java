package com.logic.asts;


import com.logic.asts.binary.ASTAnd;
import com.logic.asts.binary.ASTBiconditional;
import com.logic.asts.binary.ASTConditional;
import com.logic.asts.binary.ASTOr;
import com.logic.asts.binary.ASTExistential;
import com.logic.asts.others.*;
import com.logic.asts.unary.ASTParenthesis;
import com.logic.asts.unary.ASTNot;
import com.logic.asts.binary.ASTUniversal;

public interface IVisitor<T, E> {


    T visit(ASTTop e, E env);

    T visit(ASTBottom e, E env);


    T visit(ASTConstant e, E env);

    T visit(ASTLiteral e, E env);

    T visit(ASTArbitrary e, E env);

    T visit(ASTVariable e, E env);


    T visit(ASTNot e, E env);

    T visit(ASTAnd e, E env);

    T visit(ASTOr e, E env);

    T visit(ASTConditional e, E env);

    T visit(ASTBiconditional e, E env);


    T visit(ASTFun e, E env);

    T visit(ASTPred e, E env);

    T visit(ASTExistential e, E env);

    T visit(ASTUniversal e, E env);


    T visit(ASTParenthesis e, E env);

}
