/* Generated By:JavaCC: Do not edit this line. MiniJavaChecker.java */
package parser;

import java.util.ArrayList;
import java.util.List;

import syntax.*;

public class MiniJavaChecker implements MiniJavaCheckerConstants {

  static final public Program Program() throws ParseException {
    trace_call("Program");
    try {
 Program p; MainClass m; List<ClassDecl> cl = new ArrayList<ClassDecl>(); ClassDecl c;
      m = MainClass();
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case CLASS:
          ;
          break;
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
        c = ClassDecl();
                          cl.add(c);
      }
      jj_consume_token(0);
         p = new Program(m, cl);{if (true) return p;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Program");
    }
  }

// statement one or multi?
  static final public MainClass MainClass() throws ParseException {
    trace_call("MainClass");
    try {
                       MainClass mc; Identifier ai1; Identifier ai2; Statement as;
      jj_consume_token(CLASS);
      ai1 = Identifier();
      jj_consume_token(LEFTBRACE);
      jj_consume_token(PUBLIC);
      jj_consume_token(STATIC);
      jj_consume_token(VOID);
      jj_consume_token(MAIN);
      jj_consume_token(LEFTPAREN);
      jj_consume_token(STRING);
      jj_consume_token(LEFTBRACKET);
      jj_consume_token(RIGHTBRACKET);
      ai2 = Identifier();
      jj_consume_token(RIGHTPAREN);
         List <Statement> asl = new ArrayList <Statement>();
      jj_consume_token(LEFTBRACE);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LEFTBRACE:
        case IF:
        case WHILE:
        case PRINT:
        case IDENTIFIER:
          ;
          break;
        default:
          jj_la1[1] = jj_gen;
          break label_2;
        }
         Statement s2;
        s2 = Statement();
         asl.add(s2);
      }
      jj_consume_token(RIGHTBRACE);
     Block b = new Block(asl); as = b;
      jj_consume_token(RIGHTBRACE);
         mc = new MainClass(ai1, ai2, as);{if (true) return mc;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("MainClass");
    }
  }

  static final public ClassDecl ClassDecl() throws ParseException {
    trace_call("ClassDecl");
    try {
                       ClassDecl cd;
      if (jj_2_1(3)) {
        cd = SimpleClassDecl();
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case CLASS:
          cd = ExtendingClassDecl();
          break;
        default:
          jj_la1[2] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      {if (true) return cd;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("ClassDecl");
    }
  }

  static final public SimpleClassDecl SimpleClassDecl() throws ParseException {
    trace_call("SimpleClassDecl");
    try {
                                   SimpleClassDecl sc;
      Identifier ai; List <VarDecl> avl= new ArrayList<VarDecl>(); List <MethodDecl> aml= new ArrayList<MethodDecl>();
      jj_consume_token(CLASS);
      ai = Identifier();
      jj_consume_token(LEFTBRACE);
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case INT:
        case BOOLEAN:
        case IDENTIFIER:
          ;
          break;
        default:
          jj_la1[3] = jj_gen;
          break label_3;
        }
      VarDecl vd;
        vd = VarDecl();
                                   avl.add(vd);
      }
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PUBLIC:
          ;
          break;
        default:
          jj_la1[4] = jj_gen;
          break label_4;
        }
      MethodDecl md;
        md = MethodDecl();
                                       aml.add(md);
      }
      jj_consume_token(RIGHTBRACE);
     sc = new SimpleClassDecl(ai, avl, aml); {if (true) return sc;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("SimpleClassDecl");
    }
  }

  static final public ExtendingClassDecl ExtendingClassDecl() throws ParseException {
    trace_call("ExtendingClassDecl");
    try {
                                         ExtendingClassDecl ec;
      Identifier ai; Identifier aj; List <VarDecl> avl= new ArrayList<VarDecl>(); List <MethodDecl> aml= new ArrayList<MethodDecl>();
      jj_consume_token(CLASS);
      ai = Identifier();
      jj_consume_token(EXTENDS);
      aj = Identifier();
      jj_consume_token(LEFTBRACE);
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case INT:
        case BOOLEAN:
        case IDENTIFIER:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_5;
        }
      VarDecl vd;
        vd = VarDecl();
                                   avl.add(vd);
      }
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PUBLIC:
          ;
          break;
        default:
          jj_la1[6] = jj_gen;
          break label_6;
        }
      MethodDecl md;
        md = MethodDecl();
                                       aml.add(md);
      }
      jj_consume_token(RIGHTBRACE);
     ec = new ExtendingClassDecl(ai, aj, avl, aml); {if (true) return ec;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("ExtendingClassDecl");
    }
  }

  static final public VarDecl VarDecl() throws ParseException {
    trace_call("VarDecl");
    try {
                   Type at; Identifier ai;
      at = Type();
      ai = Identifier();
      jj_consume_token(SEMICOLON);
     VarDecl vd = new VarDecl(at, ai); {if (true) return vd;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("VarDecl");
    }
  }

// Formal list may be problem;
  static final public MethodDecl MethodDecl() throws ParseException {
    trace_call("MethodDecl");
    try {
                         Type at; Identifier ai; List <Formal>afl= new ArrayList<Formal>(); List <VarDecl> avl= new ArrayList<VarDecl>(); List <Statement> asl= new ArrayList<Statement>(); Expression ae;
      jj_consume_token(PUBLIC);
      at = Type();
      ai = Identifier();
      jj_consume_token(LEFTPAREN);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INT:
      case BOOLEAN:
      case IDENTIFIER:
        afl = FormalList();
        break;
      default:
        jj_la1[7] = jj_gen;
        ;
      }
      jj_consume_token(RIGHTPAREN);
      jj_consume_token(LEFTBRACE);
     MethodBody mb;
      mb = MethodBody();
     avl = mb.vl; asl = mb.sl;
      jj_consume_token(RETURN);
      ae = Exp1();
      jj_consume_token(SEMICOLON);
      jj_consume_token(RIGHTBRACE);
     MethodDecl md = new MethodDecl (at, ai, afl, avl, asl, ae); {if (true) return md;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("MethodDecl");
    }
  }

  static final public List <Formal> FormalList() throws ParseException {
    trace_call("FormalList");
    try {
                            List <Formal> afl= new ArrayList<Formal>();
     Type t; Identifier i;
      t = Type();
      i = Identifier();
     Formal f = new Formal(t, i); afl.add(f);
      label_7:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[8] = jj_gen;
          break label_7;
        }
        f = FormalRest();
                       afl.add(f);
      }
     {if (true) return afl;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("FormalList");
    }
  }

  static final public Formal FormalRest() throws ParseException {
    trace_call("FormalRest");
    try {
                     Type t; Identifier i;
      jj_consume_token(COMMA);
      t = Type();
      i = Identifier();
     Formal f = new Formal(t, i); {if (true) return f;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("FormalRest");
    }
  }

  static final public MethodBody MethodBody() throws ParseException {
    trace_call("MethodBody");
    try {
                         List <VarDecl> avl = new ArrayList<VarDecl>(); List <Statement> asl = new ArrayList<Statement>();
      label_8:
      while (true) {
        if (jj_2_2(2)) {
          ;
        } else {
          break label_8;
        }
                       VarDecl vd;
        vd = VarDecl();
                                                    avl.add(vd);
      }
      label_9:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LEFTBRACE:
        case IF:
        case WHILE:
        case PRINT:
        case IDENTIFIER:
          ;
          break;
        default:
          jj_la1[9] = jj_gen;
          break label_9;
        }
      Statement s;
        s = Statement();
                                     asl.add(s);
      }
     MethodBody mb = new MethodBody(avl, asl); {if (true) return mb;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("MethodBody");
    }
  }

  static final public Type Type() throws ParseException {
    trace_call("Type");
    try {
      if (jj_2_3(2)) {
        jj_consume_token(INT);
        jj_consume_token(LEFTBRACKET);
        jj_consume_token(RIGHTBRACKET);
                                                     {if (true) return Type.THE_INT_ARRAY_TYPE;}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case INT:
          jj_consume_token(INT);
           {if (true) return Type.THE_INTEGER_TYPE;}
          break;
        case BOOLEAN:
          jj_consume_token(BOOLEAN);
                   {if (true) return Type.THE_BOOLEAN_TYPE;}
          break;
        case IDENTIFIER:
         Token t;
          t = jj_consume_token(IDENTIFIER);
                                     {if (true) return new IdentifierType(t.beginLine,t.beginColumn,t.image);}
          break;
        default:
          jj_la1[10] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Type");
    }
  }

  static final public Statement Statement() throws ParseException {
    trace_call("Statement");
    try {
                       Token t; Expression ae; Statement as1; Statement as2;
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LEFTBRACE:
         List <Statement> asl = new ArrayList <Statement>();
        jj_consume_token(LEFTBRACE);
        label_10:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case LEFTBRACE:
          case IF:
          case WHILE:
          case PRINT:
          case IDENTIFIER:
            ;
            break;
          default:
            jj_la1[11] = jj_gen;
            break label_10;
          }
          as1 = Statement();
             asl.add(as1);
        }
        jj_consume_token(RIGHTBRACE);
         Block b = new Block(asl); {if (true) return b;}
        break;
      case IF:
        t = jj_consume_token(IF);
        jj_consume_token(LEFTPAREN);
        ae = Exp1();
        jj_consume_token(RIGHTPAREN);
        as1 = Statement();
        jj_consume_token(ELSE);
        as2 = Statement();
         If i = new If (t.beginLine, t.beginColumn, ae, as1, as2);{if (true) return i;}
        break;
      case WHILE:
        t = jj_consume_token(WHILE);
        jj_consume_token(LEFTPAREN);
        ae = Exp1();
        jj_consume_token(RIGHTPAREN);
        as1 = Statement();
         While w = new While (t.beginLine, t.beginColumn, ae, as1); {if (true) return w;}
        break;
      case PRINT:
        t = jj_consume_token(PRINT);
        jj_consume_token(LEFTPAREN);
        ae = Exp1();
        jj_consume_token(RIGHTPAREN);
        jj_consume_token(SEMICOLON);
         Print p = new Print (t.beginLine, t.beginColumn, ae); {if (true) return p;}
        break;
      case IDENTIFIER:
        as1 = Assign();
         {if (true) return as1;}
        break;
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Statement");
    }
  }

  static final public Statement Assign() throws ParseException {
    trace_call("Assign");
    try {
      if (jj_2_4(2)) {
         Identifier ai; Expression ae;
        ai = Identifier();
        jj_consume_token(ASSIGN);
        ae = Exp1();
        jj_consume_token(SEMICOLON);
         Assign a = new Assign (ai.lineNumber, ai.columnNumber, ai, ae); {if (true) return a;}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IDENTIFIER:
         Identifier ai;  Expression ae1; Expression ae2;
          ai = Identifier();
          jj_consume_token(LEFTBRACKET);
          ae1 = Exp1();
          jj_consume_token(RIGHTBRACKET);
          jj_consume_token(ASSIGN);
          ae2 = Exp1();
          jj_consume_token(SEMICOLON);
         ArrayAssign aa = new ArrayAssign (ai.lineNumber, ai.columnNumber, ai, ae1, ae2); {if (true) return aa;}
          break;
        default:
          jj_la1[13] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Assign");
    }
  }

  static final public Expression Exp1() throws ParseException {
    trace_call("Exp1");
    try {
                   Expression e;
      e = AndExp();
     {if (true) return e;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Exp1");
    }
  }

  static final public Expression AndExp() throws ParseException {
    trace_call("AndExp");
    try {
                     Expression e1; Expression e2; Token t;
      e1 = LessThanExp();
      label_11:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case AND:
          ;
          break;
        default:
          jj_la1[14] = jj_gen;
          break label_11;
        }
        t = jj_consume_token(AND);
        e2 = LessThanExp();
     e1 = new And(t.beginLine, t.beginColumn, e1, e2);
      }
   {if (true) return e1;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("AndExp");
    }
  }

  static final public Expression LessThanExp() throws ParseException {
    trace_call("LessThanExp");
    try {
                          Expression e1; Expression e2; Token t;
      e1 = PlusMinusExp();
      label_12:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LT:
          ;
          break;
        default:
          jj_la1[15] = jj_gen;
          break label_12;
        }
        t = jj_consume_token(LT);
        e2 = PlusMinusExp();
     e1 = new LessThan(t.beginLine, t.beginColumn, e1, e2);
      }
   {if (true) return e1;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("LessThanExp");
    }
  }

  static final public Expression PlusMinusExp() throws ParseException {
    trace_call("PlusMinusExp");
    try {
                           Expression e1; Expression e2; Token t;
      e1 = TimesExp();
      label_13:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PLUS:
        case MINUS:
          ;
          break;
        default:
          jj_la1[16] = jj_gen;
          break label_13;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PLUS:
          t = jj_consume_token(PLUS);
          e2 = TimesExp();
                 e1 = new Plus(t.beginLine, t.beginColumn, e1, e2);
          break;
        case MINUS:
          t = jj_consume_token(MINUS);
          e2 = TimesExp();
                 e1 = new Minus(t.beginLine, t.beginColumn, e1, e2);
          break;
        default:
          jj_la1[17] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
   {if (true) return e1;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("PlusMinusExp");
    }
  }

  static final public Expression TimesExp() throws ParseException {
    trace_call("TimesExp");
    try {
                       Expression e1; Expression e2; Token t;
      e1 = NotExp();
      label_14:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case MULT:
          ;
          break;
        default:
          jj_la1[18] = jj_gen;
          break label_14;
        }
        t = jj_consume_token(MULT);
        e2 = NotExp();
     e1 = new Times(t.beginLine, t.beginColumn, e1, e2);
      }
   {if (true) return e1;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("TimesExp");
    }
  }

  static final public Expression NotExp() throws ParseException {
    trace_call("NotExp");
    try {
                     Expression e; Token t; List<Token> notList = new ArrayList<Token>();
      label_15:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case EXCLAMATION:
          ;
          break;
        default:
          jj_la1[19] = jj_gen;
          break label_15;
        }
        t = jj_consume_token(EXCLAMATION);
                        notList.add(t);
      }
      e = Exp2();
        while(!notList.isEmpty()){ // From back to front.
            t = notList.remove(notList.size()-1);
            e = new Not(t.beginLine, t.beginColumn, e);
        }
        {if (true) return e;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("NotExp");
    }
  }

  static final public Expression Exp2() throws ParseException {
    trace_call("Exp2");
    try {
                   Expression e; Selector s;
      e = ArrayLookupExp();
      label_16:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case DOT:
          ;
          break;
        default:
          jj_la1[20] = jj_gen;
          break label_16;
        }
        s = Selector();
            if (s == null){ // Array.Length
                e = new ArrayLength(e);
            } else {
                e = new Call(e, s.i, s.el);
            }
      }
     {if (true) return e;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Exp2");
    }
  }

  static final public Selector Selector() throws ParseException {
    trace_call("Selector");
    try {
      if (jj_2_5(2)) {
        jj_consume_token(DOT);
        jj_consume_token(LENGTH);
         {if (true) return null;}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case DOT:
         Identifier ai; List <Expression> ael = new ArrayList<Expression>();
          jj_consume_token(DOT);
          ai = Identifier();
          jj_consume_token(LEFTPAREN);
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case LEFTPAREN:
          case EXCLAMATION:
          case TRUE:
          case FALSE:
          case THIS:
          case NEW:
          case INTEGER_LITERAL:
          case IDENTIFIER:
            ael = ExpList();
            break;
          default:
            jj_la1[21] = jj_gen;
            ;
          }
          jj_consume_token(RIGHTPAREN);
         Selector s = new Selector(ai, ael); {if (true) return s;}
          break;
        default:
          jj_la1[22] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Selector");
    }
  }

  static final public List <Expression> ExpList() throws ParseException {
    trace_call("ExpList");
    try {
                             List <Expression> ael = new ArrayList<Expression>(); Expression e;
      e = Exp1();
     ael.add(e);
      label_17:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[23] = jj_gen;
          break label_17;
        }
        e = ExpRest();
                    ael.add(e);
      }
     {if (true) return ael;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("ExpList");
    }
  }

  static final public Expression ExpRest() throws ParseException {
    trace_call("ExpRest");
    try {
                      Expression e;
      jj_consume_token(COMMA);
      e = Exp1();
                            {if (true) return e;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("ExpRest");
    }
  }

  static final public Expression ArrayLookupExp() throws ParseException {
    trace_call("ArrayLookupExp");
    try {
                             Expression e1; Expression e2 =null;
      e1 = Primary();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LEFTBRACKET:
        jj_consume_token(LEFTBRACKET);
        e2 = Exp1();
        jj_consume_token(RIGHTBRACKET);
        break;
      default:
        jj_la1[24] = jj_gen;
        ;
      }
        if (e2 != null) {if (true) return new ArrayLookup(e1, e2);}
        else {if (true) return e1;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("ArrayLookupExp");
    }
  }

  static final public Expression Primary() throws ParseException {
    trace_call("Primary");
    try {
                      Token t;Expression ae;
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INTEGER_LITERAL:
        t = jj_consume_token(INTEGER_LITERAL);
         {if (true) return new IntegerLiteral(Integer.parseInt(t.image));}
        break;
      case TRUE:
        t = jj_consume_token(TRUE);
         {if (true) return new True(t.beginLine, t.beginColumn);}
        break;
      case FALSE:
        t = jj_consume_token(FALSE);
         {if (true) return new False(t.beginLine, t.beginColumn);}
        break;
      case IDENTIFIER:
        t = jj_consume_token(IDENTIFIER);
         {if (true) return new IdentifierExp(t.beginLine, t.beginColumn, t.image);}
        break;
      case THIS:
        t = jj_consume_token(THIS);
         {if (true) return new This(t.beginLine, t.beginColumn);}
        break;
      case NEW:
        ae = Creator();
         {if (true) return ae;}
        break;
      case LEFTPAREN:
        jj_consume_token(LEFTPAREN);
        ae = Exp1();
        jj_consume_token(RIGHTPAREN);
         {if (true) return ae;}
        break;
      default:
        jj_la1[25] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Primary");
    }
  }

  static final public Expression Creator() throws ParseException {
    trace_call("Creator");
    try {
      if (jj_2_6(2)) {
         Expression ae;
        jj_consume_token(NEW);
        jj_consume_token(INT);
        jj_consume_token(LEFTBRACKET);
        ae = Exp1();
        jj_consume_token(RIGHTBRACKET);
         NewArray na = new NewArray(ae);{if (true) return na;}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NEW:
         Identifier i;
          jj_consume_token(NEW);
          i = Identifier();
          jj_consume_token(LEFTPAREN);
          jj_consume_token(RIGHTPAREN);
         NewObject no = new NewObject(i); {if (true) return no;}
          break;
        default:
          jj_la1[26] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Creator");
    }
  }

  static final public Identifier Identifier() throws ParseException {
    trace_call("Identifier");
    try {
                         Token t;
      t = jj_consume_token(IDENTIFIER);
     Identifier i = new Identifier(t.beginLine, t.beginColumn, t.image); {if (true) return i;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("Identifier");
    }
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  static private boolean jj_2_4(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  static private boolean jj_2_5(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_5(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(4, xla); }
  }

  static private boolean jj_2_6(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_6(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(5, xla); }
  }

  static private boolean jj_3R_24() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  static private boolean jj_3R_23() {
    if (jj_scan_token(BOOLEAN)) return true;
    return false;
  }

  static private boolean jj_3_6() {
    if (jj_scan_token(NEW)) return true;
    if (jj_scan_token(INT)) return true;
    return false;
  }

  static private boolean jj_3R_22() {
    if (jj_scan_token(INT)) return true;
    return false;
  }

  static private boolean jj_3R_21() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_3()) {
    jj_scanpos = xsp;
    if (jj_3R_22()) {
    jj_scanpos = xsp;
    if (jj_3R_23()) {
    jj_scanpos = xsp;
    if (jj_3R_24()) return true;
    }
    }
    }
    return false;
  }

  static private boolean jj_3_3() {
    if (jj_scan_token(INT)) return true;
    if (jj_scan_token(LEFTBRACKET)) return true;
    return false;
  }

  static private boolean jj_3_2() {
    if (jj_3R_19()) return true;
    return false;
  }

  static private boolean jj_3R_18() {
    if (jj_scan_token(CLASS)) return true;
    if (jj_3R_20()) return true;
    if (jj_scan_token(LEFTBRACE)) return true;
    return false;
  }

  static private boolean jj_3R_20() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  static private boolean jj_3_4() {
    if (jj_3R_20()) return true;
    if (jj_scan_token(ASSIGN)) return true;
    return false;
  }

  static private boolean jj_3R_19() {
    if (jj_3R_21()) return true;
    if (jj_3R_20()) return true;
    return false;
  }

  static private boolean jj_3_1() {
    if (jj_3R_18()) return true;
    return false;
  }

  static private boolean jj_3_5() {
    if (jj_scan_token(DOT)) return true;
    if (jj_scan_token(LENGTH)) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public MiniJavaCheckerTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[27];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x1000,0x20000,0x1000,0xc000000,0x2000,0xc000000,0x2000,0xc000000,0x0,0x20000,0xc000000,0x20000,0x20000,0x0,0x20000000,0x40000000,0x80000000,0x80000000,0x0,0x0,0x0,0x80000,0x0,0x0,0x200000,0x80000,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x16800,0x0,0x10000,0x0,0x10000,0x0,0x10000,0x8,0x16800,0x10000,0x16800,0x16800,0x10000,0x0,0x0,0x1,0x1,0x2,0x10,0x4,0x18790,0x4,0x8,0x0,0x18780,0x400,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[6];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public MiniJavaChecker(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public MiniJavaChecker(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new MiniJavaCheckerTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 27; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 27; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public MiniJavaChecker(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new MiniJavaCheckerTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 27; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 27; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public MiniJavaChecker(MiniJavaCheckerTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 27; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(MiniJavaCheckerTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 27; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      trace_token(token, "");
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
      trace_token(token, " (in getNextToken)");
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[50];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 27; i++) {
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
    for (int i = 0; i < 50; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  static private int trace_indent = 0;
  static private boolean trace_enabled = true;

/** Enable tracing. */
  static final public void enable_tracing() {
    trace_enabled = true;
  }

/** Disable tracing. */
  static final public void disable_tracing() {
    trace_enabled = false;
  }

  static private void trace_call(String s) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.println("Call:   " + s);
    }
    trace_indent = trace_indent + 2;
  }

  static private void trace_return(String s) {
    trace_indent = trace_indent - 2;
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.println("Return: " + s);
    }
  }

  static private void trace_token(Token t, String where) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.print("Consumed token: <" + tokenImage[t.kind]);
      if (t.kind != 0 && !tokenImage[t.kind].equals("\"" + t.image + "\"")) {
        System.out.print(": \"" + t.image + "\"");
      }
      System.out.println(" at line " + t.beginLine + " column " + t.beginColumn + ">" + where);
    }
  }

  static private void trace_scan(Token t1, int t2) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.print("Visited token: <" + tokenImage[t1.kind]);
      if (t1.kind != 0 && !tokenImage[t1.kind].equals("\"" + t1.image + "\"")) {
        System.out.print(": \"" + t1.image + "\"");
      }
      System.out.println(" at line " + t1.beginLine + " column " + t1.beginColumn + ">; Expected token: <" + tokenImage[t2] + ">");
    }
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 6; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            case 3: jj_3_4(); break;
            case 4: jj_3_5(); break;
            case 5: jj_3_6(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

                              }

class MethodBody {
    public final List <VarDecl> vl;
    public final List <Statement> sl;

    public MethodBody (List <VarDecl> avl, List <Statement> asl) {
    vl=avl; sl=asl;
    }
}

class Selector{
    public final Identifier i;           // Name of method invoked
    public final List <Expression> el;

    public Selector (Identifier ai, List <Expression> ael) {
        i = ai; el = ael;
    }
}