package site.nullpointer.admin.dict_manager.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.nullpointer.admin.dict_manager.dto.DictInfo;
import site.nullpointer.admin.dict_manager.entity.SysDictPK;
import site.nullpointer.admin.dict_manager.exception.DataKeyExistException;
import site.nullpointer.admin.dict_manager.repository.DictRepository;
import site.nullpointer.admin.dict_manager.service.DictMangerService;
import site.nullpointer.www.repository.impl.BaseRepositoryImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjc
 * @Title: DictMangagerServiceImpl
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2610:12
 */
@Service
@Transactional
public class DictManagerServiceImpl implements DictMangerService {
    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private BaseRepositoryImpl baseRepository;

    @Override
    public void saveDict(DictInfo dict) {
        dictRepository.save(dict.toDictEntity());
    }

    @Override
    public Page<DictInfo> getDictObjectList(String objectName, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT object_name,data_key,data_value FROM sys_dict WHERE 1=1 ");
        Map<String, Object> params = new HashMap<>();
        /**
         * 对象名称模糊搜索
         */
        if (StringUtils.isNoneBlank(objectName)) {
            sql.append(" AND object_name LIKE :object_name ");
            params.put("object_name", "%" + objectName.trim() + "%");
        }
        return baseRepository.beanPage(sql.toString(), params, pageable, new DictInfo());
    }

    @Override
    public void checkDataKey(String dataKey, String objectName) {
        if (dictRepository.findById(new SysDictPK(objectName, dataKey)) != null) {
            throw new DataKeyExistException();
        }
    }

    @Override
    public void deleteDict(SysDictPK id) {
        dictRepository.deleteById(id);
    }

    @Override
    public List<String> geObjectNames() {
        return dictRepository.getObjectNames();
    }

    @Override
    public List<DictInfo> getObject(String objectName) {
        List<DictInfo> object = new ArrayList<>();
        dictRepository.findByObjectName(objectName).forEach(attr -> object.add(new DictInfo(attr)));
        return object;
    }

    @Override
    public void saveObject(String objectName,String strVaues) {
        dictRepository.saveAll(DictInfo.obtainDictObject(objectName,strVaues ));
    }

}
