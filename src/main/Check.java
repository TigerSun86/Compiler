package main;

import global.Dbg;
import global.ErrorMsg;
import global.SymbolTable;
import global.SymbolTableBuilder;
import global.TypeChecker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import parser.MiniJavaChecker;
import parser.ParseException;
import parser.Token;
import syntax.Program;

/**
 * FileName:     Check.java
 * @Description: 
 *
 * @author Xunhu(Tiger) Sun
 *         email: TigerSun86@gmail.com
 * @date Mar 14, 2014 3:36:16 PM 
 */
public class Check {
    private static final String DBG_PHASE_1 = "_ParserDebug.txt";
    private static final String DBG_PHASE_2 = "_CheckerDebug.txt";
    private static final String ERROR_FORMAT = "%s:%03d.%03d: Syntax Error: ";
    private static final String SUM_FORMAT = "filename=%s, errors=%d";

    private static String fileName = null;
    private static String dbgFileName = null;
    private static boolean isDebug = false;

    public static void main (final String[] args) throws IOException {
        fileName = args[0];
        final MiniJavaChecker checker =
                new MiniJavaChecker(new FileInputStream(fileName));

        final PrintStream ps = System.out;
        final String verbose = System.getProperty("verbose");
        if (verbose != null) {
            isDebug = true;
            initDebug(DBG_PHASE_1);
            System.setOut(new PrintStream(new FileOutputStream(dbgFileName)));
            checker.enable_tracing();
        } else {
            checker.disable_tracing();
        }
        
        final Dbg dbg = new Dbg(isDebug);
        final ErrorMsg eMsg = new ErrorMsg(dbg);
        int errorCount = 0;
        final ArrayList<String> errMsgList = new ArrayList<String>();
        try {
            // Parse.
            final Program p = checker.Program();
            
            if (isDebug){
                System.out.close();
                initDebug(DBG_PHASE_2);
                System.setOut(new PrintStream(new FileOutputStream(dbgFileName)));
            }

            // Symbol table.
            final SymbolTableBuilder stb = new SymbolTableBuilder(eMsg,dbg);
            p.accept(stb);
            final SymbolTable st = stb.getSymbolTable();
            // Type check.
            if (eMsg.errCount == 0){
                p.accept(new TypeChecker(st,eMsg, dbg));
            }

            dbg.print(Dbg.NEW_lINE);
            dbg.print(st.toString());
            
        } catch (ParseException e) {
            final String errMsg = generateErrMsg(e);
            errMsgList.add(errMsg);
            errorCount++;

            if (isDebug) {
                System.out.println(errMsg);
            }
        }

        if (isDebug) {
            System.out.close();
            System.setOut(ps);
        }

        for (String errMsg : errMsgList) {
            System.err.println(errMsg);
        }
        final String sumInfo = String.format(SUM_FORMAT, fileName, errorCount+eMsg.errCount);
        System.out.println(sumInfo);
    }

    private static String generateErrMsg (ParseException e) {
        final StringBuffer sb = new StringBuffer();
        final Token t = e.currentToken;
        sb.append(String.format(ERROR_FORMAT, fileName, t.beginLine,
                t.beginColumn));
        sb.append("Expecting ");
        int maxSize = 0;
        for (int i = 0; i < e.expectedTokenSequences.length; i++) {
            if (maxSize < e.expectedTokenSequences[i].length) {
                maxSize = e.expectedTokenSequences[i].length;
            }
            for (int j = 0; j < e.expectedTokenSequences[i].length; j++) {
                sb.append(e.tokenImage[e.expectedTokenSequences[i][j]]).append(
                        ' ');
            }

            if (i != e.expectedTokenSequences.length - 1) {
                sb.append("or ");
            }
        }

        return sb.toString();
    }

    private static void initDebug (final String dbgPostfix) throws IOException {
        final String path = getPath(fileName);
        dbgFileName = getDbgFileName(path, dbgPostfix);
        final File file = new File(dbgFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private static String getDbgFileName (final String path, final String dbgPostfix) {
        final File file = new File(fileName);
        final String name = file.getName();
        final String nameNoExt = name.substring(0, name.lastIndexOf("."));
        final String dbgFileName =
                path + File.separator + nameNoExt + dbgPostfix;
        return dbgFileName;
    }

    private static String getPath (final String fileName) {
        final File file = new File(fileName);
        final String absolutePath = file.getAbsolutePath();
        final String filePath =
                absolutePath.substring(0,
                        absolutePath.lastIndexOf(File.separator));
        return filePath;
    }
}
