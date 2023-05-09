package ttkx.un.util;

/**
 * @author Rain.Ye
 * @DATE 2023/5/4 9:57
 */
public class ClassUtil {

    public static String getClassName(String fullClassName) {
        String[] split = fullClassName.split("\\.");
        return split[split.length - 1];
    }

}
