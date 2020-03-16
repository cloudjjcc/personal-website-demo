package site.nullpointer.admin.dict_manager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.nullpointer.admin.dict_manager.entity.SysDictEntity;
import site.nullpointer.admin.dict_manager.entity.SysDictPK;

import java.util.List;

/**
 * @author wangjc
 * @Title: DictRepository
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2610:36
 */
@Repository
public interface DictRepository extends JpaRepository<SysDictEntity, SysDictPK> {
    /**
     * 更具字典名称查询
     *
     * @param objectName
     * @param pageable
     * @return
     */
    Page<SysDictEntity> findByObjectNameLike(String objectName, Pageable pageable);


    /**
     * 查找指定父id和数据key的数据对象
     *
     * @param dataKey
     * @param objectName
     * @return
     */
    SysDictEntity findByObjectNameAndDataKey(String objectName, String dataKey);

    /**
     * 查询对象名称
     *
     * @return
     */
    @Query(value = "SELECT DISTINCT object_name FROM sys_dict WHERE 1=1 ", nativeQuery = true)
    List<String> getObjectNames();

    /**
     * 查询对象
     *
     * @param objectName
     * @return
     */
    List<SysDictEntity> findByObjectName(String objectName);

    /**
     * 随机获取指定对象的n个key
     * @param objectName
     * @param n
     * @return
     */
    @Query(value = "select data_key from sys_dict where object_name=?1 order by rand() limit ?2", nativeQuery = true)
    List<String> getDataKeyRandom(String objectName, int n);
}
