package global;

import java.util.LinkedHashMap;

/**
 * FileName:     SymbolTable.java
 * @Description: 
 *
 * @author Xunhu(Tiger) Sun
 *         email: TigerSun86@gmail.com
 * @date Mar 16, 2014 4:24:19 PM 
 */
public class SymbolTable {
    public LinkedHashMap<String, ClassEntry> classes = new LinkedHashMap<String, ClassEntry>();
    @Override
    public String toString(){
        final StringBuffer sb = new StringBuffer();
        for (ClassEntry c: classes.values()){
            sb.append(c.toString());
            sb.append(Dbg.NEW_lINE);
        }
        return sb.toString();
    }
}
