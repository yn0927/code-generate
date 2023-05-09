package ttkx.un;

import cn.hutool.core.util.StrUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import ttkx.un.config.LoadConfigCenter;
import ttkx.un.entity.Field;
import ttkx.un.entity.Table;
import ttkx.un.enums.FieldTypeEnum;
import ttkx.un.generate.EntityGenerate;
import ttkx.un.generate.GenerateTemplate;
import ttkx.un.generate.MapperJavaGenerate;
import ttkx.un.generate.MapperXmlGenerate;
import ttkx.un.query.TableDataQuery;
import ttkx.un.util.PathUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rain.Ye
 * @DATE 2023/4/28 8:44
 */
public class StartUp {

    public static void main(String[] args) throws Exception {
        LoadConfigCenter.initPathConfig();
        initVelocity();
        List<Table> tableColumnList = TableDataQuery.getAllTable();
        Map<String, List<Table>> tableGroup =
                tableColumnList.stream().collect(Collectors.groupingBy(Table::getTableName));
        GenerateTemplate entityGenerate = new EntityGenerate();
        entityGenerate.generate(tableGroup);
        entityGenerate = new MapperJavaGenerate();
        entityGenerate.generate(tableGroup);
        entityGenerate = new MapperXmlGenerate();
        entityGenerate.generate(tableGroup);
    }

    public static void initVelocity() {
        Properties prop = new Properties();
        prop.put("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
    }

}
