package global;

import syntax.AST;
import syntax.And;
import syntax.ArrayAssign;
import syntax.ArrayLength;
import syntax.ArrayLookup;
import syntax.Assign;
import syntax.Block;
import syntax.BooleanType;
import syntax.Call;
import syntax.ClassDecl;
import syntax.Expression;
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
 * FileName:     SymbolTableBuilder.java
 * @Description: 
 *
 * @author Xunhu(Tiger) Sun
 *         email: TigerSun86@gmail.com
 * @date Mar 16, 2014 1:44:22 AM 
 */
public class SymbolTableBuilder implements SyntaxTreeVisitor <Void>  {
    private final TypeSet typeSet;
    private final SymbolTable st;
    private final ErrorMsg error;
    private final Dbg dbg;
    
    private ClassEntry curClass =null;
    private MethodEntry curMethod=null;
    
    public SymbolTableBuilder (Dbg dbg) {this(new ErrorMsg(dbg), dbg);}
    public SymbolTableBuilder (ErrorMsg error, Dbg dbg) 
    {typeSet = new TypeSet();st = new SymbolTable();this.error = error;this.dbg =dbg;}
    
    public SymbolTable getSymbolTable(){
        return st;
    }
    
    private void reservedCheck(AST ast, String id){
        if (typeSet.isReservedWord(id)){
            error.complain(ast, "Should not use reserved word: "+id);
        }
    }

    // Subcomponents of Program:  MainClass m; List<ClassDecl> cl;
    public Void visit (Program n) {
       if(n!=null &&n.m!=null) {
           // Initialize all Identifier Types.
          String className = n.m.i1.s;
          // If adding failed here, all kinds of error will be output later, so need not to print here.
          typeSet.add(className); 
          for (ClassDecl c: n.cl) {
              if (c instanceof SimpleClassDecl){
                  SimpleClassDecl sc = (SimpleClassDecl)c;
                  className = sc.i.s;
                  typeSet.add(className);
              } else {
                  assert(c instanceof ExtendingClassDecl);
                  ExtendingClassDecl ec = (ExtendingClassDecl)c;
                  className = ec.i.s;
                  typeSet.add(className);
              }
          }
          // Traversal.
          n.m.accept (this);
          for (ClassDecl c: n.cl) c.accept (this);
       }
       error.flush();
       return null;
    }
   
    // Subcomponents of MainClass:  Identifier i1, i2; Statement s;
    public Void visit (MainClass n) {
       n.i1.accept (this);  // identifier:  name of class
       
       final String className = n.i1.s;
       curClass = st.classes.get(className);
       if (curClass != null) {
           error.complain(n.i1, "Class redefined: "+className);
       } else {
           curClass = new ClassEntry();
           curClass.id = className;
           st.classes.put(className, curClass);
       }
       dbg.print("DEF:  declaration of (main) class '"+className+"'.");
       curMethod = new MethodEntry(); //public static void main (String [] 
       curMethod.type = "null";
       curMethod.id = "main";
       curClass.methods.put("main", curMethod);
       dbg.print("DEF:  declaration of method 'main' in class '"+className+"'.");
       n.i2.accept (this);  // identifier:  name of arguments
       // Don't add this parameter into formal table.
       n.s.accept (this);   // statement:  body of main 
       
       curMethod = null;
       curClass =null;
       return null;
    }

    // Subcomponents of SimpleClassDecl: Identifier i; List<VarDecl> vl; List<MethodDecl> ml;
    public Void visit (final SimpleClassDecl n) {
       n.i.accept (this);
       
       final String className = n.i.s;
       curClass = st.classes.get(className);
       if (curClass != null) {
           // If class already exists, don't check any thing of it.
           error.complain(n.i, "Class redefined: "+className);
       } else {
           curClass = new ClassEntry();
           curClass.id = className;
           st.classes.put(className, curClass);
           
           dbg.print("DEF:  declaration of class '"+className+"'.");
           
           for (VarDecl v: n.vl) v.accept (this);

           for (MethodDecl m: n.ml) m.accept (this);
       }

       curClass = null;
       return null;
    }
    private boolean hasLoopInExtending(final String curClassType, final String exClassType){
        String exType = exClassType;
        while (!exType.equals(curClassType)){
            ClassEntry ex= st.classes.get(exType);
            if (ex == null){return false;}
            exType = ex.ext;  // Check parent class.
            if (exType.equals("null")) return false;
        }
        return true; // Extending looped back to current class.
    }
    // Subcomponents of ExtendingClassDecl: Identifier i, j; List<VarDecl> vl; List<MethodDecl> ml;
    public Void visit (final ExtendingClassDecl n) {
       // Class
       n.i.accept (this);

       final String className = n.i.s;
       curClass = st.classes.get(className);
       if (curClass != null) {
           // If class already exists, don't check any thing of it.
           error.complain(n.i, "Class redefined: "+className);
       } else {
           curClass = new ClassEntry();
           curClass.id = className;
           // Set extending.
           n.j.accept (this);
           final String exClass = n.j.s;
           if (!typeSet.contains(exClass)){ // No such class.
               curClass.ext = "null"; // Regard it as no extending class.
               error.complain(n.i, "No such class '"+exClass+"' to extend.");
           } else {
               if (hasLoopInExtending(className, exClass)){
                   curClass.ext = "null"; // Cycle in extending.
                   error.complain(n.i, "A cycle exists in the type hierarchy of "+className+".");
               } else {
                   curClass.ext = exClass;
               }
           }
           dbg.print("DEF:  declaration of class '"+className+"' extends '"+exClass+"'.");
           st.classes.put(className, curClass);
           
           for (VarDecl v: n.vl) v.accept (this);
           for (MethodDecl m: n.ml) m.accept (this);
       }
       
       curClass = null;
       return null;
    }

    // Subcomponents of VarDecl:  Type t; Identifier i;
    public Void visit (VarDecl n) {
       n.t.accept (this);   // Type t: no new line
       n.i.accept (this);   // Identifier i: no new line

       final String varName = n.i.s;
       if (curMethod == null){ // Class field.
           if(curClass.fields.containsKey(varName)) {
               error.complain(n.i, varName+"is already defined in "+ curClass.id);
           } else {
               final VarEntry v = new VarEntry();
               v.type = n.t.toString();
               v.id = varName;
               curClass.fields.put(v.id, v);
               dbg.print("DEF:  declaration of field '"+v.id+"' type '"
               +v.type+"' in class '"+ curClass.id +"'.");
           }
       } else { // Method local.
           if(curMethod.locals.containsKey(varName)) {
               error.complain(n.i, varName+"is already defined in "+  curClass.id+"."+curMethod.id);
           } else {
               final VarEntry v = new VarEntry();
               v.type = n.t.toString();
               v.id = varName;
               curMethod.locals.put(v.id, v);
               dbg.print("DEF:  declaration of local '"+v.id+"' type '"
               +v.type+"' in method '"+ curClass.id+"."+curMethod.id +"'.");
           }
       }
       // Does end with a newline
       return null;
    }

    // Subcomponents of MethodDecl:
    // Type t; Identifier i; List<Formal> fl; List<VarDecl> vl; List<Statement>t sl; Expression e;
    public Void visit (MethodDecl n) {
       n.t.accept (this);
       n.i.accept (this);
       
       final String methodName = n.i.s;
       curMethod = curClass.methods.get(methodName);
       if (curMethod != null) {
           // If method already exists, don't check any thing of it.
           error.complain(n.i, "Method redefined: "+methodName);
       } else {
           curMethod = new MethodEntry();
           curMethod.id = methodName;
           curMethod.type = n.t.toString();
           curClass.methods.put(methodName, curMethod);
           dbg.print("DEF:  declaration of method '"+curMethod.id+"' type '"
           +curMethod.type+"' in class '"+ curClass.id+"'.");
           
           for (Formal f: n.fl) f.accept (this);
           for (VarDecl v: n.vl) v.accept (this);
           for (Statement s: n.sl) s.accept(this);

           // Return statement
           n.e.accept (this);      // Expression e: no new line
       }
       
       curMethod =null;
       // Does end with a newline
       return null;
    }

    // Subcomponents of Formal:  Type t; Identifier i;
    public Void visit (Formal n) {
       n.t.accept (this);
       n.i.accept (this);
       
       final String varName = n.i.s;
       if(curMethod.formals.containsKey(varName)) {
           error.complain(n.i, varName+"is already defined in "+  curClass.id+"."+curMethod.id);
       } else {
           final VarEntry v = new VarEntry();
           v.type = n.t.toString();
           v.id = varName;
           curMethod.formals.put(v.id, v);
           dbg.print("DEF:  declaration of formal '"+v.id+"' type '"
           +v.type+"' in method '"+ curClass.id+"."+curMethod.id +"'.");
       }
       return null;
    }

    public Void visit (IntArrayType n) {
       return null;
    }

    public Void visit (BooleanType n) {
       return null;
    }

    public Void visit (IntegerType n) {
       return null;
    }

    // String s;
    public Void visit (IdentifierType n) {
        if (!typeSet.contains(n.s)) {
            error.complain(n, n.s+" is an undeclared type.");
        }
       return null;
    }

    // Subcomponents of Block statement:  StatementList sl;
    public Void visit (Block n) {
       for (Statement s: n.sl) s.accept (this);
       return null;
    }

    // Subcomponents of If statement: Expression e; Statement s1,s2;
    public Void visit (If n) {
       n.e.accept (this);
       n.s1.accept (this);
       n.s2.accept (this);
       return null;
    }

    // Subcomponents of While statement: Expression e, Statement s
    public Void visit (final While n) {
       n.e.accept (this);
       n.s.accept (this);
       return null;
    }

    // Subcomponents of Print statement:  Expression e;
    public Void visit (Print n) {
       n.e.accept (this);
       return null;
    }
   
    // subcomponents of Assignment statement:  Identifier i; Expression e;
    public Void visit (Assign n) {
       n.i.accept (this);
       n.e.accept (this);
       return null;
    }

    // Identifier i; Expression e1,e2;
    public Void visit (ArrayAssign n) {
       n.i.accept(this);
       n.e1.accept(this);
       n.e2.accept(this);
       return null;
    }

    // Expression e1,e2;
    public Void visit(And n) {
       n.e1.accept(this);
       n.e2.accept(this);
       return null;
    }

    // Expression e1,e2;
    public Void visit(LessThan n) {
       n.e1.accept(this);
       n.e2.accept(this);
       return null;
    }

    // Expression e1,e2;
    public Void visit(Plus n) {
       n.e1.accept(this);
       n.e2.accept(this);
       return null;
    }

    // Expression e1,e2;
    public Void visit(Minus n) {
       n.e1.accept(this);
       n.e2.accept(this);
       return null;
    }

    // Expression e1,e2;
    public Void visit(Times n) {
       n.e1.accept(this);
       n.e2.accept(this);
       return null;
    }

    // Expression e1,e2;
    public Void visit(ArrayLookup n) {
       n.e1.accept(this);
       n.e2.accept(this);
       return null;
    }

    // Expression e;
    public Void visit(ArrayLength n) {
       n.e.accept(this);
       return null;
    }


    // Subcomponents of Call:  Expression e; Identifier i; ExpressionList el;
    public Void visit (Call n) {
       n.e.accept (this);
       n.i.accept (this);
       for (Expression e: n.el) e.accept (this);

       return null;
    }

    public Void visit (True n) {
       return null;
    }

    public Void visit (False n) {
       return null;
    }

    public Void visit (IntegerLiteral n) {
       return null;
    }

    public Void visit (IdentifierExp n) {
       return null;
    }

    public Void visit (This n) {
       return null;
    }

    // Expression e;
    public Void visit (NewArray n) {
       n.e.accept (this);
       return null;
    }

    // Identifier i;
    public Void visit (NewObject n) {
        if (!typeSet.classContains(n.i.s)) {
            error.complain(n,n.i.s+" is an undeclared type.");
        }
       return null;
    }

    // Expression e;
    public Void visit (Not n) {
       n.e.accept (this);
       return null;
    }

    // String s;
    public Void visit (Identifier n) {
       reservedCheck(n, n.s);
       return null;
    }
 }
