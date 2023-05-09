package ttkx.un.generate;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import ttkx.un.config.LoadConfigCenter;
import ttkx.un.entity.Table;
import ttkx.un.util.PathUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Rain.Ye
 * @DATE 2023/5/4 16:36
 */
public class MapperXmlGenerate extends GenerateTemplate {
    @Override
    Template initTemplate() {
        return Velocity.getTemplate("mapperXmlModel.vm");
    }

    @Override
    VelocityContext initVelocityContext(Map.Entry<String, List<Table>> entry) {
        String className = getClassName(entry.getKey()) + "Mapper";
        String classPath = getPackagePath(LoadConfigCenter.mapperPathName)
                + PathUtil.POINT_PATH_SPLIT + className;

        VelocityContext context = new VelocityContext();
        context.put(CLASS_NAME, className);
        context.put(CLASS_PATH, classPath);
        context.put(FILE_TYPE, "xml");
        return context;
    }

    @Override
    String getClassPath() {
        return LoadConfigCenter.mapperResourcePath;
    }
}
