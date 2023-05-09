package ttkx.un.util;

/**
 * @author Rain.Ye
 * @DATE 2023/4/28 11:33
 */
public class PathUtil {

    public static final String PATH_SPLIT = "/";
    public static final String POINT_PATH_SPLIT = ".";

    /**
     * 合并俩个路径，为相对路径模式
     *
     * @param args 路径数组
     * @return 相对路径
     */
    public static String mergePath(String... args) {
        StringBuilder res = new StringBuilder();
        for (String arg : args) {
            combinePath(res, arg, PATH_SPLIT, PATH_SPLIT);
        }
        return res.toString();
    }

    /**
     * 转换路径的分隔符 并去除最后一个分隔符
     *
     * @param path            路径
     * @param beforePathSplit 转换前的分隔符
     * @param pathSplit       转换后的分割符
     * @return 转换后的路径
     */
    public static String coverPathRemoveLast(String path, String beforePathSplit, String pathSplit) {
        String res = coverPath(path, beforePathSplit, pathSplit);
        if (res.endsWith(pathSplit)) {
            res = res.substring(0, res.length() - 1);
        }
        return res;
    }

    /**
     * 转换路径的分隔符
     *
     * @param path            路径
     * @param beforePathSplit 转换前的分隔符
     * @param pathSplit       转换后的分割符
     * @return 转换后的路径
     */
    public static String coverPath(String path, String beforePathSplit, String pathSplit) {
        StringBuilder res = new StringBuilder();
        combinePath(res, path, beforePathSplit, pathSplit);
        return res.toString();
    }

    public static void main(String[] args) {
        System.out.println();
        String s = coverPath("aa/fff/d/", "/", ".");
        if (s.endsWith(".")) {
            System.out.println(s.substring(0, s.length() - 1));
        }
    }

    private static void combinePath(StringBuilder res, String arg,
                                    String beforePathSplit, String pathSplit) {
        String[] args = arg.split(beforePathSplit);
        for (String temp : args) {
            if ("".equals(temp)) {
                continue;
            }
            res.append(temp).append(pathSplit);
        }
    }

}
