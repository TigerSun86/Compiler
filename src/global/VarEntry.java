package global;

/**
 * FileName:     VarEntry.java
 * @Description: 
 *
 * @author Xunhu(Tiger) Sun
 *         email: TigerSun86@gmail.com
 * @date Mar 16, 2014 4:21:36 PM 
 */
public class VarEntry {
    public String type;
    public String id;
    
    @Override
    public String toString(){
        return id+"="+type;
    }
}
