package global;

import java.util.LinkedHashMap;

/**
 * FileName:     MethodEntry.java
 * @Description: 
 *
 * @author Xunhu(Tiger) Sun
 *         email: TigerSun86@gmail.com
 * @date Mar 16, 2014 4:19:21 PM 
 */
public class MethodEntry {
    public String type;
    public String id;
    public LinkedHashMap<String, VarEntry> formals = new LinkedHashMap<String, VarEntry>();
    public LinkedHashMap<String, VarEntry> locals = new LinkedHashMap<String, VarEntry>();
    
    @Override
    public String toString(){
        final StringBuffer sb = new StringBuffer();
        sb.append(id +"[ret type: "+type+"; "); 
        
        sb.append("formals: {");
        for (VarEntry v: formals.values()){
            sb.append(v.toString()+" ");
        }
        sb.append("}; ");
        
        sb.append("locals: {");
        for (VarEntry v: locals.values()){
            sb.append(v.toString()+" ");
        }
        sb.append("}]");
        
        return sb.toString();
    }
}
