/* Generated By:JavaCC: Do not edit this line. ParserTokenManager.java */
package com.logic.parser;
import com.logic.exps.asts.*;
import com.logic.exps.asts.binary.*;
import com.logic.exps.asts.unary.*;
import com.logic.exps.asts.others.*;
import com.logic.exps.exceptions.*;
import com.logic.nd.asts.*;
import com.logic.nd.asts.binary.*;
import com.logic.nd.asts.unary.*;
import com.logic.nd.asts.others.*;

/** Token Manager. */
public class ParserTokenManager implements ParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private int jjMoveStringLiteralDfa0_3()
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
      case 44:
         return jjStopAtPos(0, 22);
      case 46:
         return jjStopAtPos(0, 17);
      case 58:
         return jjStopAtPos(0, 25);
      case 91:
         return jjStopAtPos(0, 18);
      case 93:
         return jjStopAtPos(0, 19);
      case 123:
         return jjStopAtPos(0, 20);
      case 125:
         return jjStopAtPos(0, 21);
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
      case 8866:
         return jjStopAtPos(0, 24);
      case 8868:
         return jjStopAtPos(0, 9);
      case 8869:
         return jjStopAtPos(0, 10);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0x1e000000000000L, 0x140L
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
   jjnewStateCnt = 16;
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
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 23)
                        kind = 23;
                     jjCheckNAdd(2);
                  }
                  else if (curChar == 47)
                     jjAddStates(0, 1);
                  break;
               case 1:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 16)
                     kind = 16;
                  jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 2:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 23)
                     kind = 23;
                  jjCheckNAdd(2);
                  break;
               case 4:
                  if (curChar == 47)
                     jjAddStates(0, 1);
                  break;
               case 5:
                  if (curChar == 47)
                     jjCheckNAddStates(2, 4);
                  break;
               case 6:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddStates(2, 4);
                  break;
               case 7:
                  if ((0x2400L & l) != 0L && kind > 5)
                     kind = 5;
                  break;
               case 8:
                  if (curChar == 10 && kind > 5)
                     kind = 5;
                  break;
               case 9:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 10:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(11, 12);
                  break;
               case 11:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(11, 12);
                  break;
               case 12:
                  if (curChar == 42)
                     jjAddStates(5, 6);
                  break;
               case 13:
                  if ((0xffff7fffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(14, 12);
                  break;
               case 14:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(14, 12);
                  break;
               case 15:
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
                  if ((0x7fffffe00000000L & l) != 0L && kind > 26)
                     kind = 26;
                  break;
               case 6:
                  jjAddStates(2, 4);
                  break;
               case 11:
                  jjCheckNAddTwoStates(11, 12);
                  break;
               case 13:
               case 14:
                  jjCheckNAddTwoStates(14, 12);
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
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 16)
                     kind = 16;
                  jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 6:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjAddStates(2, 4);
                  break;
               case 11:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(11, 12);
                  break;
               case 13:
               case 14:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(14, 12);
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
      if ((i = jjnewStateCnt) == (startsAt = 16 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private final int jjStopStringLiteralDfa_2(int pos, long active0)
{
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_2(int pos, long active0)
{
   return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
}
private int jjStartNfaWithStates_2(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_2(state, pos + 1);
}
private int jjMoveStringLiteralDfa0_2()
{
   switch(curChar)
   {
      case 40:
         return jjStopAtPos(0, 7);
      case 41:
         return jjStopAtPos(0, 8);
      case 44:
         return jjStopAtPos(0, 22);
      case 46:
         return jjStopAtPos(0, 17);
      case 58:
         return jjStopAtPos(0, 25);
      case 72:
         return jjStopAtPos(0, 46);
      case 91:
         return jjStopAtPos(0, 18);
      case 93:
         return jjStopAtPos(0, 19);
      case 123:
         return jjStopAtPos(0, 20);
      case 125:
         return jjStopAtPos(0, 21);
      case 172:
         jjmatchedKind = 11;
         return jjMoveStringLiteralDfa1_2(0x300000000L);
      case 8594:
         jjmatchedKind = 14;
         return jjMoveStringLiteralDfa1_2(0x30000000000L);
      case 8596:
         return jjStopAtPos(0, 15);
      case 8704:
         return jjMoveStringLiteralDfa1_2(0xc0000000000L);
      case 8707:
         return jjMoveStringLiteralDfa1_2(0x300000000000L);
      case 8743:
         jjmatchedKind = 12;
         return jjMoveStringLiteralDfa1_2(0x1c00000000L);
      case 8744:
         jjmatchedKind = 13;
         return jjMoveStringLiteralDfa1_2(0xe000000000L);
      case 8866:
         return jjStopAtPos(0, 24);
      case 8868:
         return jjStopAtPos(0, 9);
      case 8869:
         return jjStopAtPos(0, 10);
      default :
         return jjMoveNfa_2(0, 0);
   }
}
private int jjMoveStringLiteralDfa1_2(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_2(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 69:
         if ((active0 & 0x200000000L) != 0L)
            return jjStopAtPos(1, 33);
         else if ((active0 & 0x8000000000L) != 0L)
            return jjStopAtPos(1, 39);
         else if ((active0 & 0x20000000000L) != 0L)
            return jjStopAtPos(1, 41);
         else if ((active0 & 0x80000000000L) != 0L)
            return jjStopAtPos(1, 43);
         else if ((active0 & 0x200000000000L) != 0L)
            return jjStopAtPos(1, 45);
         return jjMoveStringLiteralDfa2_2(active0, 0xc00000000L);
      case 73:
         if ((active0 & 0x100000000L) != 0L)
            return jjStopAtPos(1, 32);
         else if ((active0 & 0x1000000000L) != 0L)
            return jjStopAtPos(1, 36);
         else if ((active0 & 0x10000000000L) != 0L)
            return jjStopAtPos(1, 40);
         else if ((active0 & 0x40000000000L) != 0L)
            return jjStopAtPos(1, 42);
         else if ((active0 & 0x100000000000L) != 0L)
            return jjStopAtPos(1, 44);
         return jjMoveStringLiteralDfa2_2(active0, 0x6000000000L);
      default :
         break;
   }
   return jjStartNfa_2(0, active0);
}
private int jjMoveStringLiteralDfa2_2(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_2(0, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_2(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 108:
         if ((active0 & 0x400000000L) != 0L)
            return jjStopAtPos(2, 34);
         else if ((active0 & 0x2000000000L) != 0L)
            return jjStopAtPos(2, 37);
         break;
      case 114:
         if ((active0 & 0x800000000L) != 0L)
            return jjStopAtPos(2, 35);
         else if ((active0 & 0x4000000000L) != 0L)
            return jjStopAtPos(2, 38);
         break;
      default :
         break;
   }
   return jjStartNfa_2(1, active0);
}
private int jjMoveNfa_2(int startState, int curPos)
{
   //int[] nextStates; // not used
   int startsAt = 0;
   jjnewStateCnt = 15;
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
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 23)
                        kind = 23;
                     jjCheckNAdd(2);
                  }
                  else if (curChar == 47)
                     jjAddStates(7, 8);
                  break;
               case 1:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 16)
                     kind = 16;
                  jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 2:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 23)
                     kind = 23;
                  jjCheckNAdd(2);
                  break;
               case 3:
                  if (curChar == 47)
                     jjAddStates(7, 8);
                  break;
               case 4:
                  if (curChar == 47)
                     jjCheckNAddStates(9, 11);
                  break;
               case 5:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddStates(9, 11);
                  break;
               case 6:
                  if ((0x2400L & l) != 0L && kind > 5)
                     kind = 5;
                  break;
               case 7:
                  if (curChar == 10 && kind > 5)
                     kind = 5;
                  break;
               case 8:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 9:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(10, 11);
                  break;
               case 10:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(10, 11);
                  break;
               case 11:
                  if (curChar == 42)
                     jjAddStates(12, 13);
                  break;
               case 12:
                  if ((0xffff7fffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(13, 11);
                  break;
               case 13:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(13, 11);
                  break;
               case 14:
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
               case 5:
                  jjAddStates(9, 11);
                  break;
               case 10:
                  jjCheckNAddTwoStates(10, 11);
                  break;
               case 12:
               case 13:
                  jjCheckNAddTwoStates(13, 11);
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
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 16)
                     kind = 16;
                  jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 5:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjAddStates(9, 11);
                  break;
               case 10:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(10, 11);
                  break;
               case 12:
               case 13:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(13, 11);
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
      if ((i = jjnewStateCnt) == (startsAt = 15 - (jjnewStateCnt = startsAt)))
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
         return jjStopAtPos(0, 22);
      case 46:
         return jjStopAtPos(0, 17);
      case 58:
         return jjStopAtPos(0, 25);
      case 91:
         return jjStopAtPos(0, 18);
      case 93:
         return jjStopAtPos(0, 19);
      case 123:
         return jjStopAtPos(0, 20);
      case 125:
         return jjStopAtPos(0, 21);
      case 172:
         return jjStopAtPos(0, 11);
      case 8594:
         return jjStopAtPos(0, 14);
      case 8596:
         return jjStopAtPos(0, 15);
      case 8704:
         return jjStopAtPos(0, 30);
      case 8707:
         return jjStopAtPos(0, 31);
      case 8743:
         return jjStopAtPos(0, 12);
      case 8744:
         return jjStopAtPos(0, 13);
      case 8866:
         return jjStopAtPos(0, 24);
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
   jjnewStateCnt = 23;
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
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 23)
                        kind = 23;
                     jjCheckNAdd(2);
                  }
                  else if (curChar == 47)
                     jjAddStates(14, 15);
                  else if (curChar == 63)
                  {
                     if (kind > 27)
                        kind = 27;
                  }
                  break;
               case 1:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 16)
                     kind = 16;
                  jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 2:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 23)
                     kind = 23;
                  jjCheckNAdd(2);
                  break;
               case 4:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 27)
                     kind = 27;
                  jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 5:
                  if (curChar == 63 && kind > 27)
                     kind = 27;
                  break;
               case 8:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 28)
                     kind = 28;
                  jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 10:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 11:
                  if (curChar == 47)
                     jjAddStates(14, 15);
                  break;
               case 12:
                  if (curChar == 47)
                     jjCheckNAddStates(16, 18);
                  break;
               case 13:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddStates(16, 18);
                  break;
               case 14:
                  if ((0x2400L & l) != 0L && kind > 5)
                     kind = 5;
                  break;
               case 15:
                  if (curChar == 10 && kind > 5)
                     kind = 5;
                  break;
               case 16:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 15;
                  break;
               case 17:
                  if (curChar == 42)
                     jjCheckNAddTwoStates(18, 19);
                  break;
               case 18:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(18, 19);
                  break;
               case 19:
                  if (curChar == 42)
                     jjAddStates(19, 20);
                  break;
               case 20:
                  if ((0xffff7fffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(21, 19);
                  break;
               case 21:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(21, 19);
                  break;
               case 22:
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
                     if (kind > 29)
                        kind = 29;
                     jjCheckNAdd(10);
                  }
                  else if ((0x7fffffe00000000L & l) != 0L)
                     jjCheckNAdd(8);
                  if ((0x7ffffe00000000L & l) != 0L)
                  {
                     if (kind > 28)
                        kind = 28;
                  }
                  else if ((0x780000000000000L & l) != 0L)
                  {
                     if (kind > 27)
                        kind = 27;
                     jjstateSet[jjnewStateCnt++] = 4;
                  }
                  break;
               case 3:
                  if ((0x780000000000000L & l) == 0L)
                     break;
                  if (kind > 27)
                     kind = 27;
                  jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 6:
                  if ((0x7ffffe00000000L & l) != 0L && kind > 28)
                     kind = 28;
                  break;
               case 7:
                  if ((0x7fffffe00000000L & l) != 0L)
                     jjCheckNAdd(8);
                  break;
               case 8:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 28)
                     kind = 28;
                  jjCheckNAdd(8);
                  break;
               case 9:
                  if ((0x7fffffeL & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAdd(10);
                  break;
               case 10:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 29)
                     kind = 29;
                  jjCheckNAdd(10);
                  break;
               case 13:
                  jjAddStates(16, 18);
                  break;
               case 18:
                  jjCheckNAddTwoStates(18, 19);
                  break;
               case 20:
               case 21:
                  jjCheckNAddTwoStates(21, 19);
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
                  if (!jjCanMove_0(hiByte, i1, i2, l1, l2))
                     break;
                  if (kind > 16)
                     kind = 16;
                  jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 13:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjAddStates(16, 18);
                  break;
               case 18:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(18, 19);
                  break;
               case 20:
               case 21:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2))
                     jjCheckNAddTwoStates(21, 19);
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
      if ((i = jjnewStateCnt) == (startsAt = 23 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   5, 10, 6, 7, 9, 13, 15, 4, 9, 5, 6, 8, 12, 14, 12, 17, 
   13, 14, 16, 20, 22, 
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
"\254", "\u2227", "\u2228", "\u2192", "\u2194", null, "\56", "\133", "\135", "\173", 
"\175", "\54", null, "\u22a2", "\72", null, null, null, null, "\u2200", "\u2203", 
"\254\111", "\254\105", "\u2227\105\154", "\u2227\105\162", "\u2227\111", 
"\u2228\111\154", "\u2228\111\162", "\u2228\105", "\u2192\111", "\u2192\105", "\u2200\111", 
"\u2200\105", "\u2203\111", "\u2203\105", "\110", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "PL", 
   "FOL", 
   "ND", 
   "DEFAULT", 
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0x7fffffffff81L, 
};
static final long[] jjtoSkip = {
   0x7eL, 
};
protected JavaCharStream input_stream;
private final int[] jjrounds = new int[23];
private final int[] jjstateSet = new int[46];
protected char curChar;
/** Constructor. */
public ParserTokenManager(JavaCharStream stream){
   if (JavaCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public ParserTokenManager(JavaCharStream stream, int lexState){
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
   for (i = 23; i-- > 0;)
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
   if (lexState >= 4 || lexState < 0)
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

int curLexState = 3;
int defaultLexState = 3;
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
       try { input_stream.backup(0);
          while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
             curChar = input_stream.BeginToken();
       }
       catch (java.io.IOException e1) { continue EOFLoop; }
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_2();
       break;
     case 3:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_3();
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
