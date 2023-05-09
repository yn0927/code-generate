package ttkx.un.entity;

import lombok.Data;

/**
 * @author Rain.Ye
 * @DATE 2023/4/28 15:08
 */
@Data
public class Table {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段类型
     */
    private String columnType;

    /**
     * 字段描述
     */
    private String columnComment;

}
