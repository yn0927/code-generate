package ttkx.un.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Rain.Ye
 * @DATE 2023/4/30 23:01
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
public enum FieldTypeEnum {

    ARRAY(2003, "ARRAY", Object.class.getName()),
    INT(2003, "INT", Integer.class.getName()),
    DATETIME(2003, "DATETIME", Date.class.getName()),
    TEXT(2003, "TEXT", String.class.getName()),
    BIGINT(-5, "BIGINT", Long.class.getName()),
    BINARY(-2, "BINARY", "byte[]"),
    BIT(-7, "BIT", Boolean.class.getName()),
    BLOB(2004, "BLOB", "byte[]"),
    BOOLEAN(16, "BOOLEAN", Boolean.class.getName()),
    CHAR(1, "CHAR", String.class.getName()),
    CLOB(2005, "CLOB", String.class.getName()),
    DATALINK(70, "DATALINK", Object.class.getName()),
    DATE(91, "DATE", Date.class.getName()),
    DECIMAL(3, "DECIMAL", BigDecimal.class.getName()),
    DISTINCT(2001, "DISTINCT", Object.class.getName()),
    DOUBLE(8, "DOUBLE", Double.class.getName()),
    FLOAT(6, "FLOAT", Double.class.getName()),
    INTEGER(4, "INTEGER", Integer.class.getName()),
    JAVA_OBJECT(2000, "JAVA_OBJECT", Object.class.getName()),
    LONGNVARCHAR(-16, "LONGNVARCHAR", String.class.getName()),
    LONGVARBINARY(-4, "LONGVARBINARY", "byte[]"),
    LONGVARCHAR(-1, "LONGVARCHAR", String.class.getName()),
    NCHAR(-15, "NCHAR", String.class.getName()),
    NCLOB(2011, "NCLOB", String.class.getName()),
    NVARCHAR(-9, "NVARCHAR", String.class.getName()),
    NULL(0, "NULL", Object.class.getName()),
    NUMERIC(2, "NUMERIC", BigDecimal.class.getName()),
    OTHER(1111, "OTHER", Object.class.getName()),
    REAL(7, "REAL", Float.class.getName()),
    REF(2006, "REF", Object.class.getName()),
    SMALLINT(5, "SMALLINT", Short.class.getName()),
    STRUCT(2002, "STRUCT", Object.class.getName()),
    TIME(92, "TIME", Date.class.getName()),
    TIMESTAMP(93, "TIMESTAMP", Date.class.getName()),
    TINYINT(-6, "TINYINT", Byte.class.getName()),
    VARBINARY(-3, "VARBINARY", "byte[]"),
    VARCHAR(12, "VARCHAR", String.class.getName()),
    TIME_WITH_TIMEZONE(2013, "TIME_WITH_TIMEZONE", "java.time.OffsetTime"),
    TIMESTAMP_WITH_TIMEZONE(2014, "TIMESTAMP_WITH_TIMEZONE", "java.time.OffsetDateTime");

    /**
     * code
     */
    private final Integer code;
    /**
     * jdbcType
     */
    private final String jdbcType;
    /**
     * javaTypeName
     */
    private final String javaTypeName;

    public static String getJavaTypeName(String jdbcType) {
        jdbcType = jdbcType.toUpperCase();
        for (FieldTypeEnum value : FieldTypeEnum.values()) {
            if (value.getJdbcType().equals(jdbcType)) {
                return value.getJavaTypeName();
            }
        }
        return "Object";
    }

}
