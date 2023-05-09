package ttkx.un.generate;

import cn.hutool.core.util.StrUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import ttkx.un.config.LoadConfigCenter;
import ttkx.un.entity.Table;
import ttkx.un.util.PathUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rain.Ye
 * @DATE 2023/5/4 8:41
 */
public abstract class GenerateTemplate {

    public static final String CLASS_PATH = "class_path";
    public static final String PACKAGE_SET = "package_set";
    public static final String ANNOTATIONS_SET = "annotations_set";
    public static final String CLASS_NAME = "class_name";
    public static final String FIELD_LIST = "field_list";
    public static final String FILE_TYPE = "file_type";

    public final void generate(Map<String, List<Table>> tableGroup) throws IOException {
        Template template = initTemplate();
        for (Map.Entry<String, List<Table>> entry : tableGroup.entrySet()) {
            VelocityContext context = initVelocityContext(entry);
            mergeDataAndOut(template, context);
        }
    }

    public final String getPackagePath(String packageName) {
        String classPath = LoadConfigCenter.outPathPrefix + packageName;
        return PathUtil.coverPathRemoveLast(classPath, "/", ".");
    }

    public final String getClassName(String key) {
        return StrUtil.upperFirst(StrUtil.toCamelCase(key));
    }

    public final void addPackage(Set<String> list, String fullClassName) {
        if (!fullClassName.startsWith("java.lang")) {
            list.add(fullClassName);
        }
    }

    private void mergeDataAndOut(Template template, VelocityContext context) throws IOException {
        String className = (String) context.get(CLASS_NAME);
        String fileType = (String) context.get(FILE_TYPE);
        String classPath = getClassPath();
        File file = new File(classPath + className + "." + fileType);
        boolean mkdirs = new File(classPath).mkdirs();
        FileOutputStream outStream = new FileOutputStream(file);
        OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, StandardCharsets.UTF_8);
        BufferedWriter writer = new BufferedWriter(outStreamWriter);
        template.merge(context, writer);
        writer.flush();
        writer.close();
        outStreamWriter.close();
        outStream.close();
    }

    /**
     * 初始化模板
     *
     * @return 模板
     */
    abstract Template initTemplate();

    /**
     * 填充上下文
     *
     * @param entry 表和字段信息
     * @return 上下文信息
     */
    abstract VelocityContext initVelocityContext(Map.Entry<String, List<Table>> entry);

    /**
     * 获取类路径
     *
     * @return path
     */
    abstract String getClassPath();

}
