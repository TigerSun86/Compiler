package translate;

import tree.Stm;

/**
 * FileName:     NxIRT.java
 * @Description: 
 *
 * @author Xunhu(Tiger) Sun
 *         email: TigerSun86@gmail.com
 * @date Apr 15, 2014 3:47:55 PM
 */
public class NxIRT extends TigerIRTree {
    Stm stm;

    public NxIRT(Stm s) {
        stm = s;
    }

    @Override
    public Stm asStm () {
        return stm;
    }
}
