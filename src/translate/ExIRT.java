package translate;

import tree.EVAL;
import tree.Exp;
import tree.Stm;

/**
 * FileName: ExIRT.java
 * @Description:
 * 
 * @author Xunhu(Tiger) Sun
 *         email: sunx2013@my.fit.edu
 * @date Apr 14, 2014 9:46:07 PM
 */
public class ExIRT extends TigerIRTree {
    Exp exp;

    public ExIRT(Exp e) {
        exp = e;
    }

    @Override
    public Exp asExp () {
        return exp;
    }

    @Override
    public Stm asStm () {
        return new EVAL(exp);
    }
}
