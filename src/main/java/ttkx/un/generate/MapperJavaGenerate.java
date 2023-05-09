package ttkx.un.generate;

import org.apache.ibatis.annotations.Mapper;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import ttkx.un.config.LoadConfigCenter;
import ttkx.un.entity.Table;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rain.Ye
 * @DATE 2023/5/4 10:10
 */
public class MapperJavaGenerate extends GenerateTemplate {
    @Override
    Template initTemplate() {
        return Velocity.getTemplate("mapperModel.vm");
    }

    @Override
    VelocityContext initVelocityContext(Map.Entry<String, List<Table>> entry) {
        String classPath = getPackagePath(LoadConfigCenter.mapperPathName);
        String className = getClassName(entry.getKey()) + "Mapper";

        Set<String> packageSet = new HashSet<>();
        packageSet.add(Mapper.class.getName());

        Set<String> annotationSet = new HashSet<>();
        annotationSet.add("Mapper");

        VelocityContext context = new VelocityContext();
        context.put(CLASS_PATH, classPath);
        context.put(PACKAGE_SET, packageSet);
        context.put(ANNOTATIONS_SET, annotationSet);
        context.put(CLASS_NAME, className);
        context.put(FILE_TYPE, "java");
        return context;
    }

    @Override
    String getClassPath() {
        return LoadConfigCenter.mapperJavaPath;
    }
}
