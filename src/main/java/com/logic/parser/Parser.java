/* Generated By:JavaCC: Do not edit this line. Parser.java */
package com.logic.parser;

import com.logic.exps.asts.*;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.unary.*;
import com.logic.exps.asts.others.*;

import com.logic.nd.asts.*;
import com.logic.nd.asts.binary.*;
import com.logic.nd.asts.unary.*;
import com.logic.nd.asts.others.*;

public class Parser implements ParserConstants {

/*
* Parser for propositional logic expressions
*/
  final public IASTExp parsePL() throws ParseException {
    IASTExp e;
      token_source.curLexState = PL;
    e = atomicsPL();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AND:
    case OR:
    case CONDITIONAL:
    case BICONDITIONAL:
      e = operationsPL(e);
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DOT:
      jj_consume_token(DOT);
      break;
    case 0:
      jj_consume_token(0);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                                                             {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final private IASTExp operationsSingle() throws ParseException {
    IASTExp e1, e2;
    e1 = atomicsPL();
                      {if (true) return operationsPL(e1);}
    throw new Error("Missing return statement in function");
  }

  final private IASTExp operationsPL(IASTExp e1) throws ParseException {
    IASTExp e2;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AND:
      jj_consume_token(AND);
      e2 = atomicsPL();
                                 {if (true) return new ASTAnd(e1, e2);}
      break;
    case OR:
      jj_consume_token(OR);
      e2 = atomicsPL();
                                {if (true) return new ASTOr(e1, e2);}
      break;
    case CONDITIONAL:
      jj_consume_token(CONDITIONAL);
      e2 = atomicsPL();
                                         {if (true) return new ASTConditional(e1, e2);}
      break;
    case BICONDITIONAL:
      jj_consume_token(BICONDITIONAL);
      e2 = atomicsPL();
                                           {if (true) return new ASTBiconditional(e1, e2);}
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final private IASTExp atomicsPL() throws ParseException {
    Token t;
    IASTExp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TOP:
      jj_consume_token(TOP);
            {if (true) return new ASTTop();}
      break;
    case BOTTOM:
      jj_consume_token(BOTTOM);
               {if (true) return new ASTBottom();}
      break;
    case NOT:
      jj_consume_token(NOT);
      e = atomicsPL();
                            {if (true) return new ASTNot(e);}
      break;
    case LPAR:
      jj_consume_token(LPAR);
      e = atomicsPL();
      e = operationsPL(e);
      jj_consume_token(RPAR);
                                                        {if (true) return new ASTParenthesis(e);}
      break;
    case LITERAL:
      t = jj_consume_token(LITERAL);
                    {if (true) return new ASTLiteral(t.image);}
      break;
    case GREEK:
      t = jj_consume_token(GREEK);
                  {if (true) return new ASTArbitrary(t.image);}
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

/*
* Parser for first-order logic expressions
*/
  final public IASTExp parseFOL() throws ParseException {
    IASTExp e;
  token_source.curLexState = FOL;
    e = atomicsFOL();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AND:
    case OR:
    case CONDITIONAL:
    case BICONDITIONAL:
      e = operationsFOL(e);
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DOT:
      jj_consume_token(DOT);
      break;
    case 0:
      jj_consume_token(0);
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                                                               {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final private IASTExp operationsFOL(IASTExp e1) throws ParseException {
    IASTExp e2;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AND:
      jj_consume_token(AND);
      e2 = atomicsFOL();
                                 {if (true) return new ASTAnd(e1, e2);}
      break;
    case OR:
      jj_consume_token(OR);
      e2 = atomicsFOL();
                                 {if (true) return new ASTOr(e1, e2);}
      break;
    case CONDITIONAL:
      jj_consume_token(CONDITIONAL);
      e2 = atomicsFOL();
                                          {if (true) return new ASTConditional(e1, e2);}
      break;
    case BICONDITIONAL:
      jj_consume_token(BICONDITIONAL);
      e2 = atomicsFOL();
                                            {if (true) return new ASTBiconditional(e1, e2);}
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final private IASTExp atomicsFOL() throws ParseException {
    Token name, t;
    IASTExp e, e1;
    ASTPred pred;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TOP:
      jj_consume_token(TOP);
            {if (true) return new ASTTop();}
      break;
    case BOTTOM:
      jj_consume_token(BOTTOM);
               {if (true) return new ASTBottom();}
      break;
    case NOT:
      jj_consume_token(NOT);
      e = atomicsFOL();
                             {if (true) return new ASTNot(e);}
      break;
    case LPAR:
      jj_consume_token(LPAR);
      e = atomicsFOL();
      e = operationsFOL(e);
      jj_consume_token(RPAR);
                                                          {if (true) return new ASTParenthesis(e);}
      break;
    case GREEK:
      t = jj_consume_token(GREEK);
                  {if (true) return new ASTArbitrary(t.image);}
      break;
    case EXISTENTIAL:
      jj_consume_token(EXISTENTIAL);
      t = jj_consume_token(VARIABLE);
                                  e = new ASTVariable(t.image);
      e1 = atomicsFOL();
                                                                                     {if (true) return new ASTExistential(e, e1);}
      break;
    case UNIVERSAL:
      jj_consume_token(UNIVERSAL);
      t = jj_consume_token(VARIABLE);
                                e = new ASTVariable(t.image);
      e1 = atomicsFOL();
                                                                                   {if (true) return new ASTUniversal(e, e1);}
      break;
    case PREDICATE:
      name = jj_consume_token(PREDICATE);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LPAR:
        e = predicateFOL(name.image);
                                                      {if (true) return  e;}
        break;
      default:
        jj_la1[7] = jj_gen;
                                                                     {if (true) return new ASTLiteral(name.image);}
      }
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final private IASTExp predicateFOL(String name) throws ParseException {
    IASTExp e;
    ASTPred pred = new ASTPred(name);
    jj_consume_token(LPAR);
    e = termFOL();
                           pred.addTerm(e);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_1;
      }
      jj_consume_token(COMMA);
      e = termFOL();
                                                                        pred.addTerm(e);
    }
    jj_consume_token(RPAR);
                                                                                                      {if (true) return pred;}
    throw new Error("Missing return statement in function");
  }

  final private IASTExp termFOL() throws ParseException {
   Token t;
   IASTExp e;
   ASTFun fun;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case VARIABLE:
      t = jj_consume_token(VARIABLE);
                     {if (true) return new ASTVariable(t.image);}
      break;
    case FUNCTION:
      t = jj_consume_token(FUNCTION);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LPAR:
        e = functionFOL(t.image);
                                               {if (true) return e;}
        break;
      default:
        jj_la1[10] = jj_gen;
                                                               {if (true) return new ASTConstant(t.image);}
      }
      break;
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final private IASTExp functionFOL(String name) throws ParseException {
    IASTExp e;
    ASTFun fun = new ASTFun(name);
    jj_consume_token(LPAR);
    e = termFOL();
                           fun.addTerm(e);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_2;
      }
      jj_consume_token(COMMA);
      e = termFOL();
                                                                       fun.addTerm(e);
    }
    jj_consume_token(RPAR);
                                                                                                     {if (true) return fun;}
    throw new Error("Missing return statement in function");
  }

/*
* Parser for propositional logic natural deduction proofs
*/
  final public IASTND parseNDPL() throws ParseException {
    IASTND e;
     token_source.curLexState = PL;
    e = proofNDPL();
    jj_consume_token(0);
                            {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final private IASTND proofNDPL() throws ParseException {
     IASTExp exp;
     IASTND n1, n2, n3;
     Token m, n;
     token_source.curLexState = ND;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LRPAR:
      jj_consume_token(LRPAR);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IAND:
        jj_consume_token(IAND);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parsePL();
        n1 = proofNDPL();
        n2 = proofNDPL();
        jj_consume_token(RRPAR);
                                                                                          {if (true) return new ASTIConj(n1, n2, exp);}
        break;
      case ELAND:
        jj_consume_token(ELAND);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parsePL();
        n1 = proofNDPL();
        jj_consume_token(RRPAR);
                                                                             {if (true) return new ASTELConj(n1, exp);}
        break;
      case ERAND:
        jj_consume_token(ERAND);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parsePL();
        n1 = proofNDPL();
        jj_consume_token(RRPAR);
                                                                             {if (true) return new ASTERConj(n1, exp);}
        break;
      case ECOND:
        jj_consume_token(ECOND);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parsePL();
        n1 = proofNDPL();
        n2 = proofNDPL();
        jj_consume_token(RRPAR);
                                                                                              {if (true) return new ASTEImp(n1, n2, exp);}
        break;
      case ILOR:
        jj_consume_token(ILOR);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parsePL();
        n1 = proofNDPL();
        jj_consume_token(RRPAR);
                                                                            {if (true) return new ASTILDis(n1, exp);}
        break;
      case IROR:
        jj_consume_token(IROR);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parsePL();
        n1 = proofNDPL();
        jj_consume_token(RRPAR);
                                                                            {if (true) return new ASTIRDis(n1, exp);}
        break;
      case INEG:
        jj_consume_token(INEG);
        jj_consume_token(COMMA);
        m = jj_consume_token(NUMBER);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parsePL();
        n1 = proofNDPL();
        jj_consume_token(RRPAR);
          {if (true) return new ASTINeg(n1, exp, Integer.parseInt(m.toString()));}
        break;
      case ENEG:
        jj_consume_token(ENEG);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parsePL();
        n1 = proofNDPL();
        n2 = proofNDPL();
        jj_consume_token(RRPAR);
                                                                                             {if (true) return new ASTENeg(n1, n2, exp);}
        break;
      case EOR:
        jj_consume_token(EOR);
        jj_consume_token(COMMA);
        m = jj_consume_token(NUMBER);
        jj_consume_token(COMMA);
        n = jj_consume_token(NUMBER);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parsePL();
        n1 = proofNDPL();
        n2 = proofNDPL();
        n3 = proofNDPL();
        jj_consume_token(RRPAR);
              {if (true) return new ASTEDisj(n1, n2, n3, exp, Integer.parseInt(m.toString()), Integer.parseInt(n.toString()));}
        break;
      case ICOND:
        jj_consume_token(ICOND);
        jj_consume_token(COMMA);
        m = jj_consume_token(NUMBER);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parsePL();
        n1 = proofNDPL();
        jj_consume_token(RRPAR);
         {if (true) return new ASTIImp(n1, exp, Integer.parseInt(m.toString()));}
        break;
      case BOTTOM:
        jj_consume_token(BOTTOM);
        jj_consume_token(COMMA);
        m = jj_consume_token(NUMBER);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parsePL();
        n1 = proofNDPL();
        jj_consume_token(RRPAR);
         {if (true) return new ASTAbsurdity(n1, exp, Integer.parseInt(m.toString()));}
        break;
      case HYPOTHESIS:
        jj_consume_token(HYPOTHESIS);
        jj_consume_token(COMMA);
        m = jj_consume_token(NUMBER);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parsePL();
        jj_consume_token(RRPAR);
         {if (true) return new ASTHypothesis(exp, Integer.parseInt(m.toString()));}
        break;
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[14] = jj_gen;
         {if (true) throw new RuntimeException("Incomplete proof!");}
    }
    throw new Error("Missing return statement in function");
  }

/*
* Parser for first-order logic natural deduction proofs
*/
  final public IASTND parseNDFOL() throws ParseException {
    IASTND e;
     token_source.curLexState = FOL;
    e = proofNDFOL();
    jj_consume_token(0);
                             {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final private IASTND proofNDFOL() throws ParseException {
     IASTExp exp;
     IASTND n1, n2, n3;
     Token m, n;
     token_source.curLexState = ND;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LRPAR:
      jj_consume_token(LRPAR);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IAND:
        jj_consume_token(IAND);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        n2 = proofNDFOL();
        jj_consume_token(RRPAR);
                                                                                             {if (true) return new ASTIConj(n1, n2, exp);}
        break;
      case ELAND:
        jj_consume_token(ELAND);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        jj_consume_token(RRPAR);
                                                                               {if (true) return new ASTELConj(n1, exp);}
        break;
      case ERAND:
        jj_consume_token(ERAND);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        jj_consume_token(RRPAR);
                                                                               {if (true) return new ASTERConj(n1, exp);}
        break;
      case ECOND:
        jj_consume_token(ECOND);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        n2 = proofNDFOL();
        jj_consume_token(RRPAR);
                                                                                                 {if (true) return new ASTEImp(n1, n2, exp);}
        break;
      case ILOR:
        jj_consume_token(ILOR);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        jj_consume_token(RRPAR);
                                                                              {if (true) return new ASTILDis(n1, exp);}
        break;
      case IROR:
        jj_consume_token(IROR);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        jj_consume_token(RRPAR);
                                                                              {if (true) return new ASTIRDis(n1, exp);}
        break;
      case INEG:
        jj_consume_token(INEG);
        jj_consume_token(COMMA);
        m = jj_consume_token(NUMBER);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        jj_consume_token(RRPAR);
          {if (true) return new ASTINeg(n1, exp, Integer.parseInt(m.toString()));}
        break;
      case ENEG:
        jj_consume_token(ENEG);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        n2 = proofNDFOL();
        jj_consume_token(RRPAR);
                                                                                                {if (true) return new ASTENeg(n1, n2, exp);}
        break;
      case EOR:
        jj_consume_token(EOR);
        jj_consume_token(COMMA);
        m = jj_consume_token(NUMBER);
        jj_consume_token(COMMA);
        n = jj_consume_token(NUMBER);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        n2 = proofNDFOL();
        n3 = proofNDFOL();
        jj_consume_token(RRPAR);
              {if (true) return new ASTEDisj(n1, n2, n3, exp, Integer.parseInt(m.toString()), Integer.parseInt(n.toString()));}
        break;
      case ICOND:
        jj_consume_token(ICOND);
        jj_consume_token(COMMA);
        m = jj_consume_token(NUMBER);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        jj_consume_token(RRPAR);
         {if (true) return new ASTIImp(n1, exp, Integer.parseInt(m.toString()));}
        break;
      case BOTTOM:
        jj_consume_token(BOTTOM);
        jj_consume_token(COMMA);
        m = jj_consume_token(NUMBER);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        jj_consume_token(RRPAR);
         {if (true) return new ASTAbsurdity(n1, exp, Integer.parseInt(m.toString()));}
        break;
      case EUNI:
        jj_consume_token(EUNI);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        jj_consume_token(RRPAR);
                                                                              {if (true) return new ASTEUni(n1, exp);}
        break;
      case IEXIST:
        jj_consume_token(IEXIST);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        jj_consume_token(RRPAR);
                                                                                {if (true) return new ASTIExist(n1, exp);}
        break;
      case IUNI:
        jj_consume_token(IUNI);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        jj_consume_token(RRPAR);
                                                                              {if (true) return new ASTIUni(n1, exp);}
        break;
      case EEXIST:
        jj_consume_token(EEXIST);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        n1 = proofNDFOL();
        n2 = proofNDFOL();
        jj_consume_token(COMMA);
        m = jj_consume_token(NUMBER);
        jj_consume_token(RRPAR);
          {if (true) return new ASTEExist(n1, n2, exp, Integer.parseInt(m.toString()));}
        break;
      case HYPOTHESIS:
        jj_consume_token(HYPOTHESIS);
        jj_consume_token(COMMA);
        m = jj_consume_token(NUMBER);
        jj_consume_token(RRPAR);
        jj_consume_token(LRPAR);
        exp = parseFOL();
        jj_consume_token(RRPAR);
         {if (true) return new ASTHypothesis(exp, Integer.parseInt(m.toString()));}
        break;
      default:
        jj_la1[15] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[16] = jj_gen;
         {if (true) throw new RuntimeException("Incomplete proof!");}
    }
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public ParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[17];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xf000,0x20001,0xf000,0x8010e80,0xf000,0x20001,0xf000,0x80,0xc0010e80,0x400000,0x80,0x30000000,0x400000,0x400,0x40000,0x400,0x40000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x1,0x0,0x0,0x0,0x0,0x87fe,0x0,0xfffe,0x0,};
   }

  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 17; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 17; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 17; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 17; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 17; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 17; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List jj_expentries = new java.util.ArrayList();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[49];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 17; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 49; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
