package asgn1;

import java.io.IOException;
import java.util.HashMap;

public class Interp {
    public static void interp (Stm s) {
        interpStm(s, new HashMap<String, Integer>());
    }

    public static int maxargs (Stm s) {
        return maxStm(s);
    }

    public static void main (Stm s) throws IOException {
        System.out.println(maxargs(s));
        interp(s);
    }

    public static void main (String args[]) throws IOException {
        main(Example.a_program);
        main(Example.another_program);
    }

    /* interp Begin ********************************* */
    private static class IntAndTable {
        public final int i;
        public final HashMap<String, Integer> t;

        public IntAndTable(final int ii, final HashMap<String, Integer> tt) {
            i = ii;
            t = tt;
        }
    }

    private static HashMap<String, Integer> interpStm (final Stm s,
            final HashMap<String, Integer> table) {
        final HashMap<String, Integer> newTable;

        if (s instanceof CompoundStm) {
            newTable = interpCompStm((CompoundStm) s, table);
        } else if (s instanceof AssignStm) {
            newTable = interpAsgnStm((AssignStm) s, table);
        } else { // if (s instanceof PrintStm){
            assert (s instanceof PrintStm);
            newTable = interpPrintStm((PrintStm) s, table);
        }

        return newTable;
    }

    private static HashMap<String, Integer> interpCompStm (final CompoundStm s,
            final HashMap<String, Integer> table) {
        final HashMap<String, Integer> tableAfter1 = interpStm(s.stm1, table);
        final HashMap<String, Integer> tableAfter2 =
                interpStm(s.stm2, tableAfter1);
        return tableAfter2;
    }

    private static HashMap<String, Integer> interpAsgnStm (final AssignStm s,
            final HashMap<String, Integer> table) {
        final IntAndTable iT = interpExp(s.exp, table);

        final HashMap<String, Integer> newTable =
                new HashMap<String, Integer>();
        newTable.putAll(iT.t);
        newTable.put(s.id, iT.i);
        return newTable;
    }

    private static HashMap<String, Integer> interpPrintStm (final PrintStm s,
            final HashMap<String, Integer> table) {
        final HashMap<String, Integer> newTable = interpExpList(s.exps, table);
        System.out.println();
        return newTable;
    }

    private static HashMap<String, Integer> interpExpList (final ExpList eL,
            final HashMap<String, Integer> t) {
        final HashMap<String, Integer> newTable;
        if (eL instanceof PairExpList) {
            final PairExpList list = (PairExpList) eL;
            final IntAndTable iT = interpExp(list.head, t);
            System.out.print(iT.i);
            System.out.print(" ");
            newTable = interpExpList(list.tail, iT.t);
        } else { // if (eL instanceof LastExpList)
            assert (eL instanceof LastExpList);
            final LastExpList list = (LastExpList) eL;
            final IntAndTable iT = interpExp(list.head, t);
            System.out.print(iT.i);
            newTable = iT.t;
        }
        return newTable;
    }

    private static IntAndTable interpOpExp (final OpExp e,
            final HashMap<String, Integer> t) {
        final IntAndTable leftIT = interpExp(e.left, t);
        final IntAndTable rightIT = interpExp(e.right, leftIT.t);
        final int value;
        if (e.oper == OpExp.Plus) {
            value = leftIT.i + rightIT.i;
        } else if (e.oper == OpExp.Minus) {
            value = leftIT.i - rightIT.i;
        } else if (e.oper == OpExp.Times) {
            value = leftIT.i * rightIT.i;
        } else { // if (e.oper == OpExp.Div){
            assert (e.oper == OpExp.Div);
            value = leftIT.i / rightIT.i;
        }
        return new IntAndTable(value, rightIT.t);
    }

    private static IntAndTable interpExp (final Exp e,
            final HashMap<String, Integer> t) {
        final IntAndTable newIT;
        if (e instanceof IdExp) {
            final String id = ((IdExp) e).id;
            Integer value = t.get(id);
            if (value == null) {
                System.err.printf("There's no such id: %s%n", id);
                value = Integer.MIN_VALUE;
            }
            newIT = new IntAndTable(value.intValue(), t);
        } else if (e instanceof NumExp) {
            newIT = new IntAndTable(((NumExp) e).num, t);
        } else if (e instanceof OpExp) {
            newIT = interpOpExp((OpExp) e, t);
        } else { // if (e instanceof EseqExp) {
            assert (e instanceof EseqExp);
            final HashMap<String, Integer> newTable =
                    interpStm(((EseqExp) e).stm, t);
            newIT = interpExp(((EseqExp) e).exp, newTable);
        }
        return newIT;
    }

    /* interp End ********************************* */

    /* maxargs Begin ********************************* */
    private static int maxStm (final Stm s) {
        final int max;
        if (s instanceof CompoundStm) {
            max = maxCompStm((CompoundStm) s);
        } else if (s instanceof AssignStm) {
            max = maxAsgnStm((AssignStm) s);
        } else { // if (s instanceof PrintStm){
            assert (s instanceof PrintStm);
            max = maxPrintStm((PrintStm) s);
        }
        return max;
    }

    private static int maxCompStm (final CompoundStm s) {
        final int max1 = maxStm(s.stm1);
        final int max2 = maxStm(s.stm2);
        return Math.max(max1, max2);
    }

    private static int maxAsgnStm (final AssignStm s) {
        return maxExp(s.exp);
    }

    private static int maxPrintStm (final PrintStm s) {
        ExpList list = s.exps;
        int counter = 1;
        while (list instanceof PairExpList) {
            counter++;
            // Get next Explist.
            list = ((PairExpList) list).tail;
        }
        return counter;
    }

    private static int maxExp (final Exp e) {
        final int max;
        if (e instanceof IdExp) {
            max = 0;
        } else if (e instanceof NumExp) {
            max = 0;
        } else if (e instanceof OpExp) {
            final int max1 = maxExp(((OpExp) e).left);
            final int max2 = maxExp(((OpExp) e).right);
            max = Math.max(max1, max2);
        } else { // if (e instanceof EseqExp) {
            assert (e instanceof EseqExp);
            final int max1 = maxStm(((EseqExp) e).stm);
            final int max2 = maxExp(((EseqExp) e).exp);
            max = Math.max(max1, max2);
        }
        return max;
    }
    /* maxargs End ********************************* */
}
