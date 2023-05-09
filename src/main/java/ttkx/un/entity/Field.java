package ttkx.un.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author un
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Field {

    public String type;
    public String name;
    /**
     * 字段描述
     */
    private String describe;


}