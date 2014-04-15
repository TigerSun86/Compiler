package translate;

import tree.Stm;

/**
 * FileName:     StmIRT.java
 * @Description: 
 *
 * @author Xunhu(Tiger) Sun
 *         email: sunx2013@my.fit.edu
 * @date Apr 14, 2014 10:04:26 PM 
 */
public class StmIRT extends TigerIRTree {
    private final Stm stm;
    public StmIRT(Stm s){
        stm = s;
    }

    @Override
    public Stm asStm () {
        return stm;
    }
}
