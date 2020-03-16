package site.nullpointer.admin.dict_manager.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author wangjc
 * @Title: SysDictPK
 * @ProjectName demo
 * @Description: 主键类
 * @date 2018/7/2621:35
 */

/**
 *
 */
@Embeddable
public  class SysDictPK implements Serializable {
    /**
     * 对象名称
     */
    @Column(name = "object_name")
    private String objectName;
    /**
     * 数据key
     */
    @Column(name = "data_key")
    private String dataKey;

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public SysDictPK() {

    }

    public SysDictPK(String objectName, String dataKey) {
        super();
        this.objectName = objectName;
        this.dataKey = dataKey;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
