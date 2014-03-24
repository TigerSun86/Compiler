package global;

/**
 * FileName:     Dbg.java
 * @Description: 
 *
 * @author Xunhu(Tiger) Sun
 *         email: TigerSun86@gmail.com
 * @date Mar 16, 2014 4:58:37 PM 
 */
public class Dbg {
    public static final String NEW_lINE = String.format("%n");
    public boolean isDbg = false;
    public Dbg(boolean isDbg){
        this.isDbg = isDbg;
    }
    public void print(String s){
        if (isDbg) System.out.println(s);
    }
}
