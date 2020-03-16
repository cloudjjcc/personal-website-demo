package site.nullpointer.admin.dict_manager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.nullpointer.admin.dict_manager.dto.DictInfo;
import site.nullpointer.admin.dict_manager.entity.SysDictEntity;
import site.nullpointer.admin.dict_manager.entity.SysDictPK;

import java.util.List;

/**
 * @author wangjc
 * @Title: DictMangerService
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2610:11
 */
public interface DictMangerService {
    /**
     * 保存字典
     * @param dict
     */
    void saveDict(DictInfo dict);

    /**
     * 获取字典列表
     * @return
     */
    Page<DictInfo> getDictObjectList(String objectName, Pageable pageable);

    /**
     * 检查数据的key
     * @param dataKey
     * @param objectName
     */
    void checkDataKey(String dataKey, String objectName);

    /**
     * 删除指定id的字典
     * @param id
     */
    void deleteDict(SysDictPK id);

    /**
     * 获取对象名称
     * @return
     */
    List<String> geObjectNames();

    /**
     * 获取字典对象
     * @param objectName
     * @return
     */
    List<DictInfo> getObject(String objectName);
    /**
     * 保存字典对象
     */
    void saveObject(String objectName,String strVaues);
}
