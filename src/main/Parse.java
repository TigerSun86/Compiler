package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import parser.MiniJavaParser;
import parser.ParseException;
import parser.Token;

public class Parse {
    private static final String DBG_FILE_NAME = "_ParserDebug.txt";
    private static final String ERROR_FORMAT = "%s:%03d.%03d: Syntax Error: ";
    private static final String SUM_FORMAT = "filename=%s, errors=%d";

    private static String fileName = null;
    private static String dbgFileName = null;
    private static boolean isDebug = false;

    public static void main (final String[] args) throws IOException {
        fileName = args[0];
        final MiniJavaParser parser =
                new MiniJavaParser(new FileInputStream(fileName));

        final PrintStream ps = System.out;
        final String verbose = System.getProperty("verbose");
        if (verbose != null) {
            isDebug = true;
            initDebug();
            parser.enable_tracing();
            System.setOut(new PrintStream(new FileOutputStream(dbgFileName)));
        } else {
            parser.disable_tracing();
        }

        int errorCount = 0;
        final ArrayList<String> errMsgList = new ArrayList<String>();
        try {
            parser.Program();
        } catch (ParseException e) {
            final String errMsg = generateErrMsg(e);
            errMsgList.add(errMsg);
            errorCount++;

            if (isDebug) {
                System.out.println(errMsg);
            }
        }

        if (isDebug) {
            System.setOut(ps);
        }

        for (String errMsg : errMsgList) {
            System.err.println(errMsg);
        }
        final String sumInfo = String.format(SUM_FORMAT, fileName, errorCount);
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

    private static void initDebug () throws IOException {
        final String path = getPath(fileName);
        dbgFileName = getDbgFileName(path);
        final File file = new File(dbgFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private static String getDbgFileName (final String path) {
        final File file = new File(fileName);
        final String name = file.getName();
        final String nameNoExt = name.substring(0, name.lastIndexOf("."));
        final String dbgFileName =
                path + File.separator + nameNoExt + DBG_FILE_NAME;
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
