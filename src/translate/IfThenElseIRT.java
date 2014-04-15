package translate;

import tree.ESEQ;
import tree.Exp;
import tree.JUMP;
import tree.LABEL;
import tree.MOVE;
import tree.SEQ;
import tree.Stm;
import tree.TEMP;

/**
 * FileName:     IfThenElseIRT.java
 * @Description: 
 *
 * @author Xunhu(Tiger) Sun
 *         email: sunx2013@my.fit.edu
 * @date Apr 14, 2014 9:03:06 PM 
 */
public class IfThenElseIRT extends TigerIRTree {

    private final TigerIRTree cond, e2, e3;
    final LABEL t    = new LABEL("if","then");
    final LABEL f    = new LABEL("if","else");
    final LABEL join = new LABEL("if","end");

    IfThenElseIRT (TigerIRTree c, TigerIRTree thenClause, TigerIRTree elseClause) {
       assert c!=null; assert thenClause!=null;
       cond = c; e2 = thenClause; e3 = elseClause;

    }
    
    @Override
    public Exp asExp() { 
       final TEMP result = TEMP.generateTEMP ();
       final Stm seq;
       if (e3 == null) {
      seq = new SEQ(cond.asCond(t, f),
            new SEQ(t,                            // T:  
            new SEQ(new MOVE(result, e2.asExp()),   //     result := then expr
                        f )));                        // F:
       } else {
      seq = new SEQ(cond.asCond(t, f),
            new SEQ(t,
            new SEQ(new MOVE(result, e2.asExp()),  //      result := then expr
            new SEQ(new JUMP(join.label),                          //      goto join
            new SEQ(f,                            // F:
            new SEQ(new MOVE(result, e3.asExp()),  //      result := else expr
                join ))))));                  // join:
       }
       return new ESEQ(seq, result);
    }
    @Override
    public Stm asStm(){
        
    }
}
