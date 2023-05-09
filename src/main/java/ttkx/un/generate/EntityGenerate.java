package ttkx.un.generate;

import cn.hutool.core.util.StrUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import ttkx.un.config.LoadConfigCenter;
import ttkx.un.entity.Field;
import ttkx.un.entity.Table;
import ttkx.un.enums.FieldTypeEnum;
import ttkx.un.util.ClassUtil;
import ttkx.un.util.PathUtil;

import java.util.*;

/**
 * @author Rain.Ye
 * @DATE 2023/5/4 9:10
 */
public class EntityGenerate extends GenerateTemplate {

    @Override
    Template initTemplate() {
        return Velocity.getTemplate("entityModel.vm");
    }

    @Override
    VelocityContext initVelocityContext(Map.Entry<String, List<Table>> entry) {
        String classPath = getPackagePath(LoadConfigCenter.entityPathName);
        String className = getClassName(entry.getKey());
        int size = entry.getValue().size();
        Field[] fields = new Field[size];
        Set<String> packageSet = new HashSet<>();
        for (int i = 0; i < size; i++) {
            Table table = entry.getValue().get(i);
            String fieldName = StrUtil.toCamelCase(table.getColumnName());
            String jdbcType = table.getColumnType().split("\\(")[0];
            String fullClassName = FieldTypeEnum.getJavaTypeName(jdbcType);

            addPackage(packageSet, fullClassName);

            Field field = new Field(ClassUtil.getClassName(fullClassName), fieldName, table.getColumnComment());
            fields[i] = field;
        }
        VelocityContext context = new VelocityContext();
        context.put(CLASS_PATH, classPath);
        context.put(PACKAGE_SET, packageSet);
        context.put(ANNOTATIONS_SET, "");
        context.put(CLASS_NAME, className);
        context.put(FIELD_LIST, fields);
        context.put(FILE_TYPE, "java");
        return context;
    }

    @Override
    String getClassPath() {
        return LoadConfigCenter.entityJavaPath;
    }

}
