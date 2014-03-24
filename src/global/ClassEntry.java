package global;

import java.util.LinkedHashMap;

/**
 * FileName:     ClassEntry.java
 * @Description: 
 *
 * @author Xunhu(Tiger) Sun
 *         email: TigerSun86@gmail.com
 * @date Mar 16, 2014 4:07:31 PM 
 */
public class ClassEntry {
    public String id;
    public String ext = "null";
    public LinkedHashMap<String, VarEntry> fields = new LinkedHashMap<String, VarEntry>();
    public LinkedHashMap<String, MethodEntry> methods = new LinkedHashMap<String, MethodEntry>();
    
    private static final String TAB = "  ";
    @Override
    public String toString(){
        final StringBuffer sb = new StringBuffer();
        sb.append("class " + id +" extends "+ ext+":"); 
        sb.append(Dbg.NEW_lINE);
        
        if (fields.size() != 0) sb.append(TAB+"fields: {");
        for (VarEntry v: fields.values()){
            sb.append(v.toString()+" ");
        }
        if (fields.size() != 0) sb.append("}"+Dbg.NEW_lINE);
        
        for (MethodEntry m: methods.values()){
            sb.append(TAB+m.toString());
            sb.append(Dbg.NEW_lINE);
        }
        return sb.toString();
    }
}
