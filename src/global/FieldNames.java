package global;

import java.lang.reflect.Field;
import java.util.TreeMap;

public class FieldNames extends TreeMap<Integer, String> {

    private static final long serialVersionUID = 1L;

    public FieldNames(final String class_name) {
        try {
            final Class<?> c = Class.forName(class_name);
            final Field[] f = c.getDeclaredFields();
            for (int i = 0; i < f.length; i++) {
                if (f[i].getType() == int.class
                        && get(f[i].getInt(null)) == null)
                    put(f[i].getInt(null), f[i].getName());
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}