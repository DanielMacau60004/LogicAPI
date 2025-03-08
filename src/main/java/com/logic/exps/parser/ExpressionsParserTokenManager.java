/* Generated By:JavaCC: Do not edit this line. ExpressionsParserTokenManager.java */
package com.logic.exps.parser;
import com.logic.exps.asts.*;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.unary.*;
import com.logic.exps.asts.others.*;

/** Token Manager. */
public class ExpressionsParserTokenManager implements ExpressionsParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private int jjMoveStringLiteralDfa0_2()
{
   return 1;
}
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 40:
         return jjStopAtPos(0, 7);
      case 41:
         return jjStopAtPos(0, 8);
      case 172:
         return jjStopAtPos(0, 11);
      case 8594:
         return jjStopAtPos(0, 14);
      case 8596:
         return jjStopAtPos(0, 15);
      case 8743:
         return jjStopAtPos(0, 12);
      case 8744:
         return jjStopAtPos(0, 13);
      case 8868:
         return jjStopAtPos(0, 9);
      case 8869:
         return jjStopAtPos(0, 10);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0x183e000000000000L, 0x35aL
};
static final long[] jjbitVec1 = {
   0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static final long[] jjbitVec3 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   //int[] nextStates; // not used
   int startsAt = 0;
   jjnewStateCnt = 14;
   int i = 1;
   jjstateSet[0] = startState;
   //int j; // not used
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if (curChar == 47)
                     jjAddStates(0, 1);
                  break;
               case 3:
                  if (curChar == 47)
                     jjCheckNAddStates(2, 4);
                  break;
               case 4:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddStates(2, 4);
                  break;
               case 5:
                  if ((0x2400L & l) != 0L && kind > 5)
                     kind = 5;
                  break;
               case 6:
                  if (curChar == 10 && kind > 5)
                     kind = 5;
                  break;
               case 7:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 8:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(9, 10);
                  break;
               case 9:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(9, 10);
                  break;
               case 10:
                  if (curChar == 42)
                     jjAddStates(5, 6);
                  break;
               case 11:
                  if ((0xffff7fffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(12, 10);
                  break;
               case 12:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(12, 10);
                  break;
               case 13:
                  if (curChar == 47 && kind > 6)
                     kind = 6;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffe00000000L & l) != 0L && kind > 17)
                     kind = 17;
                  break;
               case 4:
                  jjAddStates(2, 4);
                  break;
               case 9:
                  jjCheckNAddTwoStates(9, 10);
                  break;
               case 11:
               case 12:
                  jjCheckNAddTwoStates(12, 10);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (int)(curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 16)
                     kind = 16;
                  break;
               case 4:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjAddStates(2, 4);
                  break;
               case 9:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(9, 10);
                  break;
               case 11:
               case 12:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(12, 10);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 14 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private final int jjStopStringLiteralDfa_1(int pos, long active0)
{
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_1(int pos, long active0)
{
   return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
}
private int jjStartNfaWithStates_1(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_1(state, pos + 1);
}
private int jjMoveStringLiteralDfa0_1()
{
   switch(curChar)
   {
      case 40:
         return jjStopAtPos(0, 7);
      case 41:
         return jjStopAtPos(0, 8);
      case 44:
         return jjStopAtPos(0, 23);
      case 172:
         return jjStopAtPos(0, 11);
      case 8594:
         return jjStopAtPos(0, 14);
      case 8596:
         return jjStopAtPos(0, 15);
      case 8704:
         return jjStopAtPos(0, 21);
      case 8707:
         return jjStopAtPos(0, 22);
      case 8743:
         return jjStopAtPos(0, 12);
      case 8744:
         return jjStopAtPos(0, 13);
      case 8868:
         return jjStopAtPos(0, 9);
      case 8869:
         return jjStopAtPos(0, 10);
      default :
         return jjMoveNfa_1(0, 0);
   }
}
private int jjMoveNfa_1(int startState, int curPos)
{
   //int[] nextStates; // not used
   int startsAt = 0;
   jjnewStateCnt = 20;
   int i = 1;
   jjstateSet[0] = startState;
   //int j; // not used
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if (curChar == 47)
                     jjAddStates(7, 8);
                  break;
               case 2:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 5:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 19)
                     kind = 19;
                  jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 7:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 9:
                  if (curChar == 47)
                     jjCheckNAddStates(9, 11);
                  break;
               case 10:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddStates(9, 11);
                  break;
               case 11:
                  if ((0x2400L & l) != 0L && kind > 5)
                     kind = 5;
                  break;
               case 12:
                  if (curChar == 10 && kind > 5)
                     kind = 5;
                  break;
               case 13:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 14:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(15, 16);
                  break;
               case 15:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(15, 16);
                  break;
               case 16:
                  if (curChar == 42)
                     jjAddStates(12, 13);
                  break;
               case 17:
                  if ((0xffff7fffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(18, 16);
                  break;
               case 18:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(18, 16);
                  break;
               case 19:
                  if (curChar == 47 && kind > 6)
                     kind = 6;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffeL & l) != 0L)
                  {
                     if (kind > 20)
                        kind = 20;
                     jjCheckNAdd(7);
                  }
                  else if ((0x7fffffe00000000L & l) != 0L)
                     jjCheckNAdd(5);
                  if ((0xfffffe00000000L & l) != 0L)
                  {
                     if (kind > 19)
                        kind = 19;
                  }
                  else if ((0x700000000000000L & l) != 0L)
                  {
                     if (kind > 18)
                        kind = 18;
                     jjstateSet[jjnewStateCnt++] = 2;
                  }
                  break;
               case 1:
                  if ((0x700000000000000L & l) == 0L)
                     break;
                  if (kind > 18)
                     kind = 18;
                  jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 3:
                  if ((0xfffffe00000000L & l) != 0L && kind > 19)
                     kind = 19;
                  break;
               case 4:
                  if ((0x7fffffe00000000L & l) != 0L)
                     jjCheckNAdd(5);
                  break;
               case 5:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 19)
                     kind = 19;
                  jjCheckNAdd(5);
                  break;
               case 6:
                  if ((0x7fffffeL & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(7);
                  break;
               case 7:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 20)
                     kind = 20;
                  jjCheckNAdd(7);
                  break;
               case 10:
                  jjAddStates(9, 11);
                  break;
               case 15:
                  jjCheckNAddTwoStates(15, 16);
                  break;
               case 17:
               case 18:
                  jjCheckNAddTwoStates(18, 16);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (int)(curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 16)
                     kind = 16;
                  break;
               case 10:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjAddStates(9, 11);
                  break;
               case 15:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(15, 16);
                  break;
               case 17:
               case 18:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(18, 16);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 20 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   3, 8, 4, 5, 7, 11, 13, 9, 14, 10, 11, 13, 17, 19, 
};
private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 3:
         return ((jjbitVec0[i2] & l2) != 0L);
      default : 
         return false;
   }
}
private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec3[i2] & l2) != 0L);
      default : 
         if ((jjbitVec1[i1] & l1) != 0L)
            return true;
         return false;
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, "\50", "\51", "\u22a4", "\u22a5", 
"\254", "\u2227", "\u2228", "\u2192", "\u2194", null, null, null, null, null, 
"\u2200", "\u2203", "\54", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "PL", 
   "FOL", 
   "DEFAULT", 
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0xffff81L, 
};
static final long[] jjtoSkip = {
   0x7eL, 
};
protected JavaCharStream input_stream;
private final int[] jjrounds = new int[20];
private final int[] jjstateSet = new int[40];
protected char curChar;
/** Constructor. */
public ExpressionsParserTokenManager(JavaCharStream stream){
   if (JavaCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public ExpressionsParserTokenManager(JavaCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(JavaCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 20; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(JavaCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 3 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String tokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   tokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, tokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 2;
int defaultLexState = 2;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  //int kind;
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {   
   try   
   {     
      curChar = input_stream.BeginToken();
   }     
   catch(java.io.IOException e)
   {        
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   switch(curLexState)
   {
     case 0:
       try { input_stream.backup(0);
          while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
             curChar = input_stream.BeginToken();
       }
       catch (java.io.IOException e1) { continue EOFLoop; }
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_0();
       break;
     case 1:
       try { input_stream.backup(0);
          while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
             curChar = input_stream.BeginToken();
       }
       catch (java.io.IOException e1) { continue EOFLoop; }
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_1();
       break;
     case 2:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_2();
       break;
   }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else
        {
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}
