package site.nullpointer.admin.article_manager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import site.nullpointer.admin.article_manager.dto.ArticleInfo;
import site.nullpointer.admin.article_manager.service.ArticleManagerService;
import site.nullpointer.admin.base.api.AdminBaseApi;
import site.nullpointer.admin.dict_manager.service.DictMangerService;
import site.nullpointer.common.dto.ResponseEntity;

import java.util.NoSuchElementException;

/**
 * @author wangjc
 * @Title: ArticleManagerApi
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2516:00
 */
@RestController
public class ArticleManagerApi extends AdminBaseApi {
    @Autowired
    private ArticleManagerService articleManagerService;
    @Autowired
    private DictMangerService dictMangerService;
    /**
     * 保存文章信息
     * @return
     */
    @PostMapping("/article")
    public ResponseEntity saveArticle(@RequestBody ArticleInfo articleInfo) {
        dictMangerService.saveObject("article_tag", articleInfo.getTags());
        articleManagerService.saveArticle(articleInfo);
        return new ResponseEntity().success(true);
    }

    /**
     * 修改文章信息
     * @return
     */
    @PutMapping("/article/{id}")
    public ResponseEntity updateArticle(@RequestBody ArticleInfo articleInfo,@PathVariable String id) {
        try {
            articleInfo.setId(id);
            dictMangerService.saveObject("article_tag", articleInfo.getTags());
            articleManagerService.updateArticle(articleInfo);
            return new ResponseEntity().success(true);
        }catch (NoSuchElementException e){
            return new ResponseEntity().success(false).addKeyValuePair("cause","找不到对应文章");
        }
    }

    /**
     * 删除文章信息
     * @return
     */
    @DeleteMapping("/article/{id}")
    public ResponseEntity deleteArticle(@PathVariable String id){
        articleManagerService.deleteArticle(id);
        return new ResponseEntity().success(true);
    }
    /**
     * 获取文章内容
     * @param id
     * @return
     */
    @GetMapping("/article/{id}")
    public ResponseEntity getArticle(@PathVariable String id){
        try {
            return new ResponseEntity().success(true).data(articleManagerService.getArticleById(id));
        } catch (NoSuchElementException e) {
           return new ResponseEntity().success(false).addKeyValuePair("cause","找不到对应文章");
        }
    }

    /**
     * 获取文章列表
     * @return
     */
    @GetMapping("/articles")
    public ResponseEntity getArticleList(@RequestParam(name = "title",defaultValue = "") String title,
                                         @RequestParam(name="category",defaultValue = "")String category,
                                         @RequestParam(name="source",defaultValue = "-1")int source,
                                         @RequestParam(name="publishState",defaultValue = "-1") int publishState,
                                         Pageable pageable){
        return new ResponseEntity().success(true).data(articleManagerService.getArticleList(title,category,source,publishState,pageable));
    }

    /**
     *更新文章状态
     * @return
     */
    @PutMapping("/article/{id}/publishState/{publishState}")
    public ResponseEntity updatePulishState(@PathVariable String id,@PathVariable int publishState){
        articleManagerService.updatePulishState(id,publishState);
        return new ResponseEntity().success(true);
    }
}
