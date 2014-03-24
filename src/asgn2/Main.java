package asgn2;

import java.util.Scanner;

public class Main {
    public static void main(final String[] args) {
        geneAsCode(ASCODE_HEADER);
        final Scanner s = new Scanner(System.in);
        while (s.hasNextLine()) {
            final String miniJCode = s.nextLine();
            final String asCode = compile(miniJCode);
            if (asCode != null) {
                geneAsCode(asCode);
            }
        }
        s.close();
        geneAsCode(ASCODE_TAIL);
    }

    /** Input mini java code, output assemble code */
    private static String compile(final String miniJCode) {
        if (miniJCode.isEmpty()) {
            /* Empty line. */
            return null;
        }

        final String[] codes = miniJCode.split(" ");
        // Get variable type.
        final String varibleName;
        int offset = -1;
        if (codes[0].charAt(0) >= 'a' && codes[0].charAt(0) <= 'c') {
            // Variable a, b, c.
            varibleName = getASVariable(codes[0]);
        } else {
            // Array y[10].
            assert (codes[0].charAt(0) >= '0' && codes[0].charAt(0) <= '9');
            varibleName = ASCODE_VAR_NAME_Y;

            final int index = Integer.parseInt(codes[0]);
            offset = index * AS_WORD_LENGTH;
        }

        final String retASCode;
        if (codes.length == 2) {
            /* Assignment statement. Something like "3 1" or "b 9" */
            final int value = Integer.parseInt(codes[1]);
            if (!varibleName.equals(ASCODE_VAR_NAME_Y)) {
                // Variable a, b, c.
                retASCode = String.format(ASCODE_STM_ASN_VAR, varibleName,
                        value);
            } else {
                // Array y[10].
                assert (offset != -1);
                retASCode = String.format(ASCODE_STM_ASN_ARR, varibleName,
                        offset, value);
            }
        } else {
            /* Print statement. Something like "5" or "a" */
            assert (codes.length == 1);
            if (!varibleName.equals(ASCODE_VAR_NAME_Y)) {
                // Variable a, b, c.
                retASCode = String.format(ASCODE_STM_PRT_VAR, varibleName);
            } else {
                // Array y[10].
                assert (offset != -1);
                retASCode = String.format(ASCODE_STM_PRT_ARR, varibleName,
                        offset);
            }
        }

        return retASCode;
    }

    private static String getASVariable(final String var) {
        if (var.equals("a")) {
            return ASCODE_VAR_NAME_A;
        } else if (var.equals("b")) {
            return ASCODE_VAR_NAME_B;
        } else {
            assert (var.equals("c"));
            return ASCODE_VAR_NAME_C;
        }
    }

    private static void geneAsCode(final String asCode) {
        System.out.print(asCode);
    }

    private static final int AS_WORD_LENGTH = 4; // One word equals to 4 bytes.
    
    private static final String ASCODE_VAR_NAME_A = "G_A";
    private static final String ASCODE_VAR_NAME_B = "G_B";
    private static final String ASCODE_VAR_NAME_C = "G_C";
    private static final String ASCODE_VAR_NAME_Y = "G_A_Y";
    
    private static final String ASCODE_STM_ASN_VAR = 
"        set %s,%%l0         ! Assign varible\n"+
"        set %d,%%l2\n"+
"        st %%l2,[%%l0]\n";

    private static final String ASCODE_STM_ASN_ARR = 
"        set %s,%%l0       ! Assign array\n"+
"        set %d,%%l1          ! Array offset\n"+
"        set %d,%%l2\n"+
"        st %%l2,[%%l0 + %%l1]\n";

    private static final String ASCODE_STM_PRT_VAR = 
"        print %s           ! Print varible\n";

    private static final String ASCODE_STM_PRT_ARR = 
"        print %s + %d    ! Print array\n";

    private static final String ASCODE_HEADER = 
"        .section        \".data\"\n"+
"        .align          4\n"+
"\n"+
"G_A:    .word           0\n"+
"G_B:    .word           0\n"+
"G_C:    .word           0\n"+
"G_A_Y:  .word           0\n"+
"        .word           0\n"+
"        .word           0\n"+
"        .word           0\n"+
"        .word           0\n"+
"        .word           0\n"+
"        .word           0\n"+
"        .word           0\n"+
"        .word           0\n"+
"        .word           0\n"+
"\n"+
"        .section        \".text\"\n"+
"        .align          4\n"+
"        .global         start\n"+
"start:\n";
    
    private static final String ASCODE_TAIL = 
"exit_program\n";
}
