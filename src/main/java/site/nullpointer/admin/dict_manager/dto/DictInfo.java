package site.nullpointer.admin.dict_manager.dto;

import com.sun.mail.imap.protocol.ListInfo;
import site.nullpointer.admin.dict_manager.entity.SysDictEntity;
import site.nullpointer.admin.dict_manager.entity.SysDictPK;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangjc
 * @Title: DictInfo
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2610:27
 */
public class DictInfo implements Serializable {
    /**
     * 数据key
     */
    private String dataKey;
    /**
     * 数据值
     */
    private String dataValue;
    /**
     * 对象名称
     */
    private String objectName;


    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public SysDictEntity toDictEntity() {
        SysDictEntity dictEntity = new SysDictEntity();
        dictEntity.setDataValue(this.dataValue);
        dictEntity.setObjectName(this.objectName);
        dictEntity.setDataKey(this.dataKey);
        return dictEntity;
    }

    public DictInfo() {
    }

    public DictInfo(SysDictEntity dictEntity) {
        super();
        this.dataKey = dictEntity.getDataKey();
        this.dataValue = dictEntity.getDataValue();
        this.objectName = dictEntity.getObjectName();
    }

    /**
     * 生成字典对象
     *
     * @param objectName
     * @param strValues
     * @return
     */
    public static List<SysDictEntity> obtainDictObject(String objectName, String strValues) {
        List<SysDictEntity> object=new ArrayList<>();
        Arrays.stream(strValues.split(",")).forEach(attr->{
            object.add(new SysDictEntity(objectName,attr,attr));
        });
        return object;
    }
}
