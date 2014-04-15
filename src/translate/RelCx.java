package translate;

import tree.CJUMP;
import tree.Exp;
import tree.LABEL;
import tree.Stm;

/**
 * FileName: RelCx.java
 * @Description:
 * 
 * @author Xunhu(Tiger) Sun
 *         email: sunx2013@my.fit.edu
 * @date Apr 14, 2014 10:17:31 PM
 */
public class RelCx extends CxIRT {
    private final int operator;
    private final TigerIRTree left;
    private final TigerIRTree right;

    public RelCx(int op, TigerIRTree e1, TigerIRTree e2) {
        operator = op;
        left = e1;
        right = e2;
    }

    public Exp asExp () {
        throw new UnsupportedOperationException();
    }                  // ESEQ (asStm(), CONST(0))

    public Stm asStm () {
        throw new UnsupportedOperationException();
    }                   // EVAL (asExp())

    public Stm asCond (LABEL t, LABEL f) {
        if (operator == CJUMP.LT){
            return new CJUMP(operator, left.asExp(), right.asExp(), t.label, f.label);
        } else if (operator == CJUMP.LT){
            
        }

    }  // CJUMP (=, asExp(), CONST(0), t, f)

}
