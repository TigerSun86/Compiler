package global;

import syntax.And;
import syntax.ArrayAssign;
import syntax.ArrayLength;
import syntax.ArrayLookup;
import syntax.Assign;
import syntax.Block;
import syntax.BooleanType;
import syntax.Call;
import syntax.ClassDecl;
import syntax.ExtendingClassDecl;
import syntax.False;
import syntax.Formal;
import syntax.Identifier;
import syntax.IdentifierExp;
import syntax.IdentifierType;
import syntax.If;
import syntax.IntArrayType;
import syntax.IntegerLiteral;
import syntax.IntegerType;
import syntax.LessThan;
import syntax.MainClass;
import syntax.MethodDecl;
import syntax.Minus;
import syntax.NewArray;
import syntax.NewObject;
import syntax.Not;
import syntax.Plus;
import syntax.Print;
import syntax.Program;
import syntax.SimpleClassDecl;
import syntax.Statement;
import syntax.SyntaxTreeVisitor;
import syntax.This;
import syntax.Times;
import syntax.True;
import syntax.VarDecl;
import syntax.While;

/**
 * FileName:     TypeChecker.java
 * @Description: 
 *
 * @author Xunhu(Tiger) Sun
 *         email: TigerSun86@gmail.com
 * @date Mar 16, 2014 10:04:32 PM 
 */
public class TypeChecker implements SyntaxTreeVisitor <String>  {
    private final String INT = "int";
    private final String INTARRAY = "int[]";
    private final String BOOLEAN = "boolean";
    private final String NULL = "null";
    
    private final SymbolTable st;
    private final ErrorMsg error;
    private final Dbg dbg;
    
    private ClassEntry curClass =null;
    private MethodEntry curMethod=null;
    
    public TypeChecker (SymbolTable st,Dbg dbg) {this(st, new ErrorMsg(dbg),dbg);}
    public TypeChecker (SymbolTable st, ErrorMsg error,Dbg dbg) {this.st = st;this.error = error;this.dbg = dbg;}
    
    private boolean isSameType(final String desType, final String srcType){
        // Judge if 2 value are the same type (or extended type).
        String sType = srcType;
        while(!desType.equals(sType)){
            ClassEntry c= st.classes.get(sType);
            if (c==null){return false;}
            sType = c.ext;
            if (sType.equals(NULL)){return false;}
        }
        
        return true;
    }
    
    // Subcomponents of Program:  MainClass m; List<ClassDecl> cl;
    public String visit (Program n) {
       if(n!=null &&n.m!=null) {
          // Traversal.
          n.m.accept (this);
          for (ClassDecl c: n.cl) c.accept (this);
       }
       error.flush();
       return NULL;
    }
   
    // Subcomponents of MainClass:  Identifier i1, i2; Statement s;
    public String visit (MainClass n) {
        // No need to check class here.
       // n.i1.accept (this);  // identifier:  name of class
       
        final String className = n.i1.s;
        curClass = st.classes.get(className);
        assert (curClass != null);
        
       curMethod = curClass.methods.get("main"); //public static void main (String [] 
       assert (curMethod != null);
       
       // No need to check 
       // n.i2.accept (this);  // identifier:  name of arguments
       
       n.s.accept (this);   // statement:  body of main 
       
       curMethod = null;
       curClass =null;
       return NULL;
    }

    // Subcomponents of SimpleClassDecl: Identifier i; List<VarDecl> vl; List<MethodDecl> ml;
    public String visit (final SimpleClassDecl n) {
        // No need to check class here.
       // n.i.accept (this);
       
        final String className = n.i.s;
        curClass = st.classes.get(className);
        assert (curClass != null);
        
       for (VarDecl v: n.vl) v.accept (this);
       for (MethodDecl m: n.ml) m.accept (this);
       
       curClass = null;
       return NULL;
    }
  
    // Subcomponents of ExtendingClassDecl: Identifier i, j; List<VarDecl> vl; List<MethodDecl> ml;
    public String visit (final ExtendingClassDecl n) {
       // No need to check class here.
       // n.i.accept (this);

       final String className = n.i.s;
       curClass = st.classes.get(className);
       assert (curClass != null);
       
       // No need to check extending here.
       //n.j.accept (this);

       for (VarDecl v: n.vl) v.accept (this);
       for (MethodDecl m: n.ml) m.accept (this);

       curClass = null;
       return null;
    }

    // Subcomponents of VarDecl:  Type t; Identifier i;
    public String visit (VarDecl n) {
       // No need to check anything.
       //n.t.accept (this);   // Type t: no new line
       //n.i.accept (this);   // Identifier i: no new line
       return NULL;
    }

    // Subcomponents of MethodDecl:
    // Type t; Identifier i; List<Formal> fl; List<VarDecl> vl; List<Statement>t sl; Expression e;
    public String visit (MethodDecl n) {
        // No need to check.
       //n.t.accept (this);
       //n.i.accept (this);
       
       final String methodName = n.i.s;
       curMethod = curClass.methods.get(methodName);
       assert curMethod !=null;
              
       for (Formal f: n.fl) f.accept (this);
       for (VarDecl v: n.vl) v.accept (this);
       for (Statement s: n.sl) s.accept(this);

       // Return statement
       String retType = n.e.accept (this);
       if (!isSameType(curMethod.type, retType)){
           error.complain(n.e, "Cannot convert type "+retType+" to type "+curMethod.type+".");
           retType = curMethod.type; // For return.
       }
       curMethod =null;
       return retType;
    }

    // Subcomponents of Formal:  Type t; Identifier i;
    public String visit (Formal n) {
        // No need to check anything.
       //n.t.accept (this);
       //n.i.accept (this);
       
       return NULL;
    }

    public String visit (IntArrayType n) {
       return INTARRAY;
    }

    public String visit (BooleanType n) {
       return BOOLEAN;
    }

    public String visit (IntegerType n) {
       return INT;
    }

    // String s;
    public String visit (IdentifierType n) {
       return n.s;
    }

    // Subcomponents of Block statement:  StatementList sl;
    public String visit (Block n) {
       for (Statement s: n.sl) s.accept (this);
       return NULL;
    }

    // Subcomponents of If statement: Expression e; Statement s1,s2;
    public String visit (If n) {
        if (!(n.e.accept(this).equals(BOOLEAN))){
            error.complain(n.e, "If condition must be of type boolean.");
        }
       n.s1.accept (this);
       n.s2.accept (this);
       return NULL;
    }

    // Subcomponents of While statement: Expression e, Statement s
    public String visit (final While n) {
        if (!(n.e.accept(this).equals(BOOLEAN))){
            error.complain(n.e, "Loop condition must be of type boolean.");
        }
       n.s.accept (this);
       return NULL;
    }

    // Subcomponents of Print statement:  Expression e;
    public String visit (Print n) {
        final String expType = n.e.accept (this);
        if (!expType.equals(INT)&&!expType.equals(BOOLEAN)&&!expType.equals(INTARRAY)){
            error.complain(n.e, "Cannot print type "+expType+".");
        }
       return NULL;
    }
   
    // subcomponents of Assignment statement:  Identifier i; Expression e;
    public String visit (Assign n) {
       final String varType = n.i.accept (this);
       final String expType = n.e.accept (this);
       if (!isSameType(varType, expType)){
           error.complain(n.i, "Cannot convert type "+expType+" to type "+varType+".");
       }
       return NULL;
    }

    // Identifier i; Expression e1,e2;
    public String visit (ArrayAssign n) {
        if (!(n.i.accept(this).equals(INTARRAY))){
            error.complain(n.i, "Left side of '[]' must be of type integer array.");
        }
        if (!(n.e1.accept(this).equals(INT))){
            error.complain(n.e1, "Index of integer array must be of type integer.");
        }
        final String expType = n.e2.accept (this);
        if (!(expType.equals(INT))){
            error.complain(n.e2, "Cannot convert type "+expType+" to type "+INT+".");
        }
       return NULL;
    }

    // Expression e1,e2;
    public String visit(And n) {
        if (!(n.e1.accept(this).equals(BOOLEAN))){
            error.complain(n.e1, "Left side of And must be of type boolean.");
        }
        if (!(n.e2.accept(this).equals(BOOLEAN))){
            error.complain(n.e2, "Right side of And must be of type boolean.");
        }
       return BOOLEAN;
    }

    // Expression e1,e2;
    public String visit(LessThan n) {
        if (!(n.e1.accept(this).equals(INT))){
            error.complain(n.e1, "Left side of LessThan must be of type integer.");
        }
        if (!(n.e2.accept(this).equals(INT))){
            error.complain(n.e2, "Right side of LessThan must be of type integer.");
        }
       return BOOLEAN;
    }

    // Expression e1,e2;
    public String visit(Plus n) {
       if (!(n.e1.accept(this).equals(INT))){
           error.complain(n.e1, "Left side of Plus must be of type integer.");
       }
       if (!(n.e2.accept(this).equals(INT))){
           error.complain(n.e2, "Right side of Plus must be of type integer.");
       }
       return INT;
    }

    // Expression e1,e2;
    public String visit(Minus n) {
        if (!(n.e1.accept(this).equals(INT))){
            error.complain(n.e1, "Left side of Minus must be of type integer.");
        }
        if (!(n.e2.accept(this).equals(INT))){
            error.complain(n.e2, "Right side of Minus must be of type integer.");
        }
       return INT;
    }

    // Expression e1,e2;
    public String visit(Times n) {
        if (!(n.e1.accept(this).equals(INT))){
            error.complain(n.e1, "Left side of Times must be of type integer.");
        }
        if (!(n.e2.accept(this).equals(INT))){
            error.complain(n.e2, "Right side of Times must be of type integer.");
        }
       return INT;
    }

    // Expression e1,e2;
    public String visit(ArrayLookup n) {
        if (!(n.e1.accept(this).equals(INTARRAY))){
            error.complain(n.e1, "Left side of '[]' must be of type integer array.");
        }
        if (!(n.e2.accept(this).equals(INT))){
            error.complain(n.e1, "Index of integer array must be of type integer.");
        }
       return INT;
    }

    // Expression e;
    public String visit(ArrayLength n) {
        if (!(n.e.accept(this).equals(INTARRAY))){
            error.complain(n.e, "Only array type can use length.");
        }
       return INT;
    }


    // Subcomponents of Call:  Expression e; Identifier i; ExpressionList el;
    // Check the existence of method (class), the number of formals, the type of each formals.
    public String visit (Call n) {
       final String type = n.e.accept (this);
       final String methodName = n.i.s;
       // No need to check.
       // n.i.accept (this);

       final ClassEntry c = st.classes.get(type);
       if (c == null){
           error.complain(n.i, "Left of dot must be a class.");
           return NULL;
       }
       
       MethodEntry m = c.methods.get(methodName);
       if (m ==null){         
           String exType = c.ext;
           ClassEntry ex= st.classes.get(exType);
           while (ex!=null){
               m = ex.methods.get(methodName); // Check method of parent class.
               if (m != null) {
                   break;
               } else {
                   exType = ex.ext;
                   ex= st.classes.get(exType);
               }
           }
       }
       
       if (m == null){
           error.complain(n.i, "No such method '"+methodName+"' in class '"+type+"'.");
       } else {
           if (m.formals.size() != n.el.size()){ // Check number of formals
               error.complain(n.i, "Method '"+methodName+"' must have "+m.formals.size()+" formal(s).");
           } else { // Correct number of formals.
               int index=0;
               for (VarEntry v: m.formals.values()){
                   final String formalType = n.el.get(index).accept(this);
                   if (!isSameType(v.type, formalType)) {
                       error.complain(n.el.get(index), "The "+(index+1)+"th formal in method '"+methodName+"' must be of type "+v.type+".");
                   }
                   index++;
               }
           }
       }

       if (m!=null){
           dbg.print("USE:  call method '"+m.id+"'. Ret type="+m.type+".");
           return m.type;
       } else {
           return NULL;
       }
    }


    public String visit (True n) {
       return BOOLEAN;
    }

    public String visit (False n) {
       return BOOLEAN;
    }

    public String visit (IntegerLiteral n) {
       return INT;
    }
    // Check the existence of variable.
    public String visit (IdentifierExp n) {
        final String varName = n.s;
        assert curMethod!=null;

        VarEntry v = curMethod.locals.get(varName); // Check local.
        if (v == null){
            v = curMethod.formals.get(varName); // Check formal.
            if (v == null){
                v = curClass.fields.get(varName); // Check field.
                if (v == null){
                    String exType = curClass.ext;
                    ClassEntry ex= st.classes.get(exType);
                    while (ex!=null){ // Check field of parent class.
                        v = ex.fields.get(varName);
                        if (v != null) {
                            break;
                        } else {
                            exType = ex.ext;
                            ex= st.classes.get(exType);
                        }
                    }
                }
            }
        }
        
        if (v == null){
            error.complain(n, "No such variable '"+varName+"'.");
            return NULL;
        } else {
            dbg.print("USE:  variable '"+v.id+"' in method '"+curMethod.id+"'.  Type="+v.type);
            return v.type;
        }
    }

    public String visit (This n) {
        assert curClass!=null;
        dbg.print("USE:  pseudo variable 'this' in method '"+curMethod.id+"'.  Type="+curClass.id);
       return curClass.id;
    }

    // Expression e;
    public String visit (NewArray n) {
        if (!(n.e.accept(this).equals(INT))){
            error.complain(n.e, "The size of new array must be of type integer.");
        }
        dbg.print("USE:  new int array.");
       return INTARRAY;
    }

    // Identifier i;
    public String visit (NewObject n) {
        dbg.print("USE:  instantiate class '"+n.i.s+"'.");
       return n.i.s;
    }

    // Expression e;
    public String visit (Not n) {
       if (!(n.e.accept(this).equals(BOOLEAN))){
           error.complain(n.e, "Right side of 'Not' must be of type boolean.");
       }
       return BOOLEAN;
    }

    // String s;
    // Check the existence of variable.
    public String visit (Identifier n) {
        final String varName = n.s;
        assert curMethod!=null;

        VarEntry v = curMethod.locals.get(varName); // Check local.
        if (v == null){
            v = curMethod.formals.get(varName); // Check formal.
            if (v == null){
                v = curClass.fields.get(varName); // Check field.
                if (v == null){
                    String exType = curClass.ext;
                    ClassEntry ex= st.classes.get(exType);
                    while (ex!=null){ // Check field of parent class.
                        v = ex.fields.get(varName);
                        if (v != null) {
                            break;
                        } else {
                            exType = ex.ext;
                            ex= st.classes.get(exType);
                        }
                    }
                }
            }
        }
        
        if (v == null){
            error.complain(n, "No such variable '"+varName+"'.");
            return NULL;
        } else {
            dbg.print("USE:  variable '"+v.id+"' in method '"+curMethod.id+"'.  Type="+v.type);
            return v.type;
        }
    }
 }
