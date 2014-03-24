package global;

import java.util.HashSet;

/**
 * FileName: TypeSet.java
 * @Description:
 * 
 * @author Xunhu(Tiger) Sun
 *         email: TigerSun86@gmail.com
 * @date Mar 16, 2014 1:46:16 AM
 */
public class TypeSet {
    private HashSet<String> types = new HashSet<String>(); // Basic type.
    private HashSet<String> illegal = new HashSet<String>();
    public HashSet<String> classTypes = new HashSet<String>(); // Defined class type.

    public TypeSet() {
        types.add("int");
        types.add("int[]");
        types.add("boolean");
        illegal.add("class");
        illegal.add("public");
        illegal.add("static");
        illegal.add("void");
        illegal.add("main");
        illegal.add("extends");
        illegal.add("return");
        // illegal.add("String[]"); // Impossible.
        illegal.add("true");
        illegal.add("false");
        illegal.add("this");
        illegal.add("new");
        illegal.add("if");
        illegal.add("else");
        illegal.add("while");
        illegal.add("private");
        illegal.add("protected");
        illegal.add("default");
        illegal.add("for");
        illegal.add("switch");
        illegal.add("case");
        illegal.add("do");
        illegal.add("break;");
        illegal.add("continue");
        illegal.add("goto");
        illegal.add("import");
        illegal.add("package");
        illegal.add("instanceof");
        illegal.add("try");
        illegal.add("throws");
        illegal.add("catch");
        illegal.add("finally");
        illegal.add("null");
    }

    public boolean contains (String t) {
        return types.contains(t) || classTypes.contains(t);
    }
    public boolean isReservedWord (String t) {
        return illegal.contains(t);
    }

    public boolean classContains (String t) {
        return classTypes.contains(t);
    }

    public boolean add (String e) {
        if (illegal.contains(e)) {
            // Here don't add the illegal one, because "null" stands for 
            // empty extending type, must not add it.
            return false;
        } else {
            if (!types.contains(e)) { // Not a bacis type.
                return classTypes.add(e);
            } else {
                return false;
            }
        }
    }
}
