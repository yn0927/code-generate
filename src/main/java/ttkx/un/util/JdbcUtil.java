package ttkx.un.util;
 
import ttkx.un.config.LoadConfigCenter;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
 
/**
 * jdbc工具类
 *
 * @author Rain.Ye
 * @DATE 2023/4/28 10:38
 */
public class JdbcUtil {

    public static final String url = "jdbc:mysql://" + LoadConfigCenter.host +
            "/information_schema" + LoadConfigCenter.urlSuffix;

    static {
        try {
            Class.forName(LoadConfigCenter.driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取链接
     */
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, LoadConfigCenter.username, LoadConfigCenter.password);
    }

    /**
     * 释放连接资源
     */
    public static void release(Connection co, Statement st, ResultSet rs){
       if(rs!=null){
           try {
               rs.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (co != null) {
            try {
                co.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
 
        }
    }
 
 
}