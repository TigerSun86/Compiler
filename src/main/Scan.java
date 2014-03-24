package main;

import global.FieldNames;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import parser.MiniJavaScanner;
import parser.Token;

public class Scan {
    private static final String DBG_FILE_NAME = "_Debug.txt";
    private static final String DBG_FORMAT_HEADER = "%s:%03d.%03d: %s";
    private static final String DBG_FORMAT_ERROR = " -- illegal character %s%n";
    private static final String DBG_FORMAT_VALUE = " \"%s\"%n";
    private static final String DBG_FORMAT_SUM =
            "filename=%s, errors=%d%n";
    
    private static String fileName = null;
    private static boolean isDebug = false;
    private static String dbgFileName = null;
    private static FieldNames fieldNames = null;

    public static void main (final String[] args) throws IOException {
        fileName = args[0];
        final String verbose = System.getProperty("verbose");
        if (verbose != null) {
            isDebug = true;
            initDebug();
        }
        fieldNames = new FieldNames("parser.MiniJavaScannerConstants");
        final MiniJavaScanner lexer =
                new MiniJavaScanner(new FileInputStream(fileName));
        int errorCount = 0;
        while (true) {
            final Token t = lexer.getNextToken();
            if (t.kind == MiniJavaScanner.ERROR) {
                // Error handle.
                errorCount++;
                final String dbgInfo = getDebugInfo(t);
                System.err.print(dbgInfo);
            }
            
            recordDebugInfo(t);

            if (t.kind == MiniJavaScanner.EOF) {
                break;
            }
        }
        final String sumInfo =
                String.format(DBG_FORMAT_SUM, fileName, errorCount);
        System.out.print(sumInfo);
        recordDebugInfo(sumInfo);
    }

    private static void initDebug () throws IOException {
        final String path = getPath(fileName);
        dbgFileName = getDbgFileName(path);
        final File file = new File(dbgFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private static void recordDebugInfo (final Token t) throws IOException {
        if (isDebug) {
            final String dbgInfo = getDebugInfo(t);
            writeDebugFile(dbgInfo);
        }
    }

    private static void recordDebugInfo (final String dbgInfo)
            throws IOException {
        if (isDebug) {
            writeDebugFile(dbgInfo);
        }
    }

    private static String getDebugInfo (final Token t) {
        final String tType = fieldNames.get(t.kind);
        final StringBuffer sb = new StringBuffer();
        sb.append(String.format(DBG_FORMAT_HEADER, fileName, t.beginLine,
                t.beginColumn, tType));

        if (t.kind == MiniJavaScanner.ERROR) {
            sb.append(String.format(DBG_FORMAT_ERROR, t.image));
        } else if (t.kind != MiniJavaScanner.EOF){
            sb.append(String.format(DBG_FORMAT_VALUE, t.image));
        } else { // if (t.kind == MiniJavaScanner.EOF)
            sb.append(String.format("%n"));
        }
        return sb.toString();
    }

    private static void writeDebugFile (final String dbgInfo)
            throws IOException {
        final BufferedWriter writer =
                new BufferedWriter(new FileWriter(new File(dbgFileName), true));
        writer.write(dbgInfo);
        writer.close();
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
