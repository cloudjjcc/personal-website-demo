package site.nullpointer.admin.dict_manager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import site.nullpointer.admin.base.api.AdminBaseApi;
import site.nullpointer.admin.dict_manager.dto.DictInfo;
import site.nullpointer.admin.dict_manager.entity.SysDictPK;
import site.nullpointer.admin.dict_manager.exception.DataKeyExistException;
import site.nullpointer.admin.dict_manager.service.DictMangerService;
import site.nullpointer.common.dto.ResponseEntity;

/**
 * @author wangjc
 * @Title: DictApi
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2610:07
 */
@RestController
public class DictManagerApi extends AdminBaseApi {
    @Autowired
    private DictMangerService dictMangerService;

    /**
     * 保存数据字典
     */
    @PostMapping("/dict")
    public ResponseEntity saveDict(@RequestBody DictInfo dict) {
        dictMangerService.saveDict(dict);
        return new ResponseEntity().success(true);
    }

    /**
     * 获取字典列表
     *
     * @return
     */
    @GetMapping("/dicts")
    public ResponseEntity getDictObjectList(@RequestParam(name="objectName",defaultValue = "") String objectName,
                                            Pageable pageable) {
        return new ResponseEntity().success(true).data(dictMangerService.getDictObjectList(objectName,pageable));
    }
    /**
     * 检查数据key
     */
    @PostMapping("/dict/check/{objectName}/{dataKey}")
    public ResponseEntity checkDataKey(@PathVariable(name = "dataKey") String dataKey,
                                       @PathVariable(name="objectName") String objectName){
        try {
            dictMangerService.checkDataKey(dataKey,objectName);
            return new ResponseEntity().success(true);
        } catch (DataKeyExistException e) {
            return new ResponseEntity().success(false).addKeyValuePair("cause","数据key已存在");
        }
    }

    /**
     * 删除字典
     * @param
     * @return
     */
    @DeleteMapping("/dict/{objectName}/{dataKey}")
    public ResponseEntity deleteDict(@PathVariable String objectName,@PathVariable String dataKey){
        dictMangerService.deleteDict(new SysDictPK(objectName,dataKey));
        return new ResponseEntity().success(true);
    }

    /**
     * 获取所有对象名称
     * @return
     */
    @GetMapping("/dict/objectNames")
    public ResponseEntity getObjectNames(){
     return new ResponseEntity().success(true).data(dictMangerService.geObjectNames());
    }

    /**
     * 获取字典对象
     * @param objectName
     * @return
     */
    @GetMapping("/dict/object")
    public ResponseEntity getObject(@RequestParam String objectName){
        return new ResponseEntity().success(true).data(dictMangerService.getObject(objectName));
    }
}
