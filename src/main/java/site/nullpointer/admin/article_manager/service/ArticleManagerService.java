package site.nullpointer.admin.article_manager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.nullpointer.admin.article_manager.dto.ArticleInfo;

/**
 * @author wangjc
 * @Title: ArticleManagerService
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2516:26
 */
public interface ArticleManagerService {
    /**
     * 保存文章信息
     * @param articleInfo
     */
    void saveArticle(ArticleInfo articleInfo);

    /**
     * 修改文章信息
     * @param articleInfo
     */
    void updateArticle(ArticleInfo articleInfo);

    /**
     * 根据id获取文章信息
     * @param articleId
     * @return
     */
    ArticleInfo getArticleById(String articleId);

    /**
     * 获取文章列表
     * @param title
     * @param category
     * @param source
     * @param publishState
     * @param pageable
     * @return
     */
    Page<ArticleInfo> getArticleList(String title, String category, int source, int publishState, Pageable pageable);

    /**
     * 删除文章信息
     * @param id
     */
    void deleteArticle(String id);

    /**
     * 更新文章状态
     * @param id
     * @param publishState
     */
    void updatePulishState(String id, int publishState);
}
