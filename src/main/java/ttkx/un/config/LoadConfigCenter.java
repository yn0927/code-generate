package ttkx.un.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import ttkx.un.StartUp;
import ttkx.un.enums.QueryModelEnum;
import ttkx.un.util.PathUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取配置信息
 *
 * @author Rain.Ye
 * @DATE 2023/4/28 10:38
 */
@Slf4j
public class LoadConfigCenter {

    private static String projectPrefix = "";
    private static final String ROOT_PATH = "src/main/";
    private static final String JAVA_PATH = "java";
    private static final String RESOURCES_PATH = "resources";
    public static final String RESOURCE_NAME = "unGenerate.properties";

    public static String outPathPrefix = "un/ttkx/";
    public static String entityPathName = "entity";
    public static String mapperPathName = "mapper";
    public static String driver = null;
    public static String host = null;
    public static String schema = null;
    public static String username = null;
    public static String password = null;
    public static String url = null;
    public static QueryModelEnum model = QueryModelEnum.exclude;
    public static String includeTables = "";
    public static String excludeTables = "";

    public static String urlSuffix = "?characterEncoding=utf-8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=GMT%2B8";
    public static String entityJavaPath = null;
    public static String mapperJavaPath = null;
    public static String mapperResourcePath = null;

    public static void initPathConfig() {
        InputStream in = StartUp.class.getClassLoader().getResourceAsStream(RESOURCE_NAME);
        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tableScopeInit(props);
        mysqlConfigInit(props);
        outPathInit(props);
    }

    private static void tableScopeInit(Properties props) {
        String propsModel = props.getProperty("un.generate.model");
        if (StrUtil.isNotEmpty(propsModel) && QueryModelEnum.include.toString().equals(propsModel)) {
            model = QueryModelEnum.include;
        }
        includeTables = defaultOrNewPath(props.getProperty("un.generate.includeTables"), includeTables);
        excludeTables = defaultOrNewPath(props.getProperty("un.generate.excludeTables"), excludeTables);
        log.info("采用：{}模式，includeTables：{}，excludeTables：{}", model, includeTables, excludeTables);
    }

    private static void mysqlConfigInit(Properties props) {
        driver = props.getProperty("un.mysql.driver-class-name");
        host = props.getProperty("un.mysql.host");
        schema = props.getProperty("un.mysql.schema");
        username = props.getProperty("un.mysql.username");
        password = props.getProperty("un.mysql.password");
        if (!StrUtil.isAllNotEmpty(driver, host, schema, username, password)) {
            throw new IllegalArgumentException("mysql配置不得为空");
        }
        urlSuffix = defaultOrNewPath(props.getProperty("un.mysql.urlSuffix"), urlSuffix);
        url = "jdbc:mysql://" + host + "/information_schema" + urlSuffix;
        log.info("数据库连接url:{}, 后缀修改配置为：un.mysql.urlSuffix", url);
    }

    private static void outPathInit(Properties props) {
        projectPrefix = defaultOrNewPath(props.getProperty("un.generate.projectPrefix"), projectPrefix);
        outPathPrefix = defaultOrNewPath(props.getProperty("un.generate.outPathPrefix"), outPathPrefix);

        entityJavaPath = PathUtil.mergePath(projectPrefix, ROOT_PATH, JAVA_PATH, outPathPrefix, entityPathName);
        mapperJavaPath = PathUtil.mergePath(projectPrefix, ROOT_PATH, JAVA_PATH, outPathPrefix, mapperPathName);
        mapperResourcePath = PathUtil.mergePath(projectPrefix, ROOT_PATH, RESOURCES_PATH, outPathPrefix, mapperPathName);
        log.info("输出路径如下\n\t\t\t\t\tjavaEntity:{}\n\t\t\t\t\tjavaMapper:{}\n\t\t\t\t\tmapperResource:{}",
                entityJavaPath, mapperJavaPath, mapperResourcePath);
    }

    private static String defaultOrNewPath(String newPath, String defaultPath) {
        if (StrUtil.isEmpty(newPath)) {
            return defaultPath;
        }
        return newPath;
    }

}
