package global;

import java.io.PrintWriter;

import syntax.AST;

/**
 * FileName:     ErrorMsg.java
 * @Description: 
 *
 * @author Xunhu(Tiger) Sun
 *         email: TigerSun86@gmail.com
 * @date Mar 16, 2014 2:12:23 AM 
 */
public class ErrorMsg {
    private final PrintWriter pw;
    private final Dbg dbg;
    public ErrorMsg (PrintWriter pw, Dbg dbg) {
       this.pw = pw;
       this.dbg = dbg;
    }
    public ErrorMsg (Dbg dbg) { this (new PrintWriter(System.err), dbg);}

    public int errCount = 0;
    public void complain(AST ast, String msg){
        errCount++;
        int l = ast.lineNumber;
        int c = ast.columnNumber;
        pw.println(l+"."+c+": Error "+msg);
        dbg.print(l+"."+c+": Error "+msg);
    }
    public void flush () {
        pw.flush();
    }
}
