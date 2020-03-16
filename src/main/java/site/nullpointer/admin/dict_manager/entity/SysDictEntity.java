package site.nullpointer.admin.dict_manager.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author wangjc
 * @Title: SysDictEntity
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2610:28
 */
@Entity
@Table(name = "sys_dict")
@IdClass(SysDictPK.class)
public class SysDictEntity implements Serializable {
    /**
     * 对象名称
     */
    @Id
    @Column(name = "object_name")
    private String objectName;
    /**
     * 数据key
     */
    @Id
    @Column(name = "data_key")
    private String dataKey;

    /**
     * 数据值
     */
    private String dataValue;


    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public SysDictEntity() {
    }

    public SysDictEntity(String objectName, String dataKey, String dataValue) {
        super();
        this.objectName = objectName;
        this.dataKey = dataKey;
        this.dataValue = dataValue;
    }
}
