package translate;

import tree.LABEL;
import tree.Exp;
import tree.Stm;

/**
 * FileName: TigerIRTree.java
 * @Description:
 * 
 * @author Xunhu(Tiger) Sun
 *         email: sunx2013@my.fit.edu
 * @date Apr 14, 2014 8:43:37 PM
 */
public abstract class TigerIRTree {
    public Exp asExp () {
        throw new UnsupportedOperationException();
    }                  // ESEQ (asStm(), CONST(0))

    public Stm asStm () {
        throw new UnsupportedOperationException();
    }                   // EVAL (asExp())

    public Stm asCond (LABEL t, LABEL f) {
        throw new UnsupportedOperationException();
    }  // CJUMP (=, asExp(), CONST(0), t, f)

    @Override
    public String toString () {
        return String.format("IR: %s", asStm().toString());
    }
}
