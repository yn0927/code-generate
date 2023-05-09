package ttkx.un.query;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import ttkx.un.config.LoadConfigCenter;
import ttkx.un.entity.Table;
import ttkx.un.enums.QueryModelEnum;
import ttkx.un.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rain.Ye
 * @DATE 2023/4/28 14:42
 */
@Slf4j
public class TableDataQuery {

    public static List<Table> getAllTable() throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "SELECT TABLE_NAME, COLUMN_NAME, COLUMN_TYPE, COLUMN_COMMENT " +
                "FROM COLUMNS WHERE TABLE_SCHEMA = ?";
        sql = dealSqlForTableScope(sql);
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, LoadConfigCenter.schema);
        ResultSet resultSet = prepareStatement.executeQuery();
        List<Table> tableList = new ArrayList<>();
        while (resultSet.next()) {
            Table table = new Table();
            table.setTableName(resultSet.getString("TABLE_NAME"));
            table.setColumnName(resultSet.getString("COLUMN_NAME"));
            table.setColumnType(resultSet.getString("COLUMN_TYPE"));
            table.setColumnComment(resultSet.getString("COLUMN_COMMENT"));
            tableList.add(table);
        }
        return tableList;
    }

    private static String dealSqlForTableScope(String sql) {
        String condition = null;
        if (QueryModelEnum.exclude.equals(LoadConfigCenter.model)
                && StrUtil.isNotEmpty(LoadConfigCenter.excludeTables)) {
            condition = dealStringForSql(LoadConfigCenter.excludeTables);
            sql += " AND TABLE_NAME NOT IN " + condition;
        } else if (QueryModelEnum.include.equals(LoadConfigCenter.model)
                && StrUtil.isNotEmpty(LoadConfigCenter.includeTables)) {
            condition = dealStringForSql(LoadConfigCenter.includeTables);
            sql += " AND TABLE_NAME IN " + condition;
        }
        log.info("最终的查询SQL：{}", sql);
        return sql;
    }

    /**
     * TODO：SQL注入是否考虑
     */
    private static String dealStringForSql(String arg) {
        String[] args = arg.split(",");
        StringBuilder res = new StringBuilder("(");
        int len = args.length;
        for (int i = 0; i < len; i++) {
            res.append("'").append(args[i]).append("'");
            if (i != len - 1) {
                res.append(",");
            }
        }
        res.append(")");
        return res.toString();
    }

}
