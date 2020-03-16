package site.nullpointer.www.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import site.nullpointer.www.entity.ArticleCommentEntity;
import site.nullpointer.www.entity.ArticleLikeEntity;
import site.nullpointer.www.document.EsArticle;
import site.nullpointer.www.vo.CategoryVO;
import site.nullpointer.www.vo.RelativeArticle;
/**
 * <p>类路径 : site.nullpointer.common.service.ArticleService
 * <p>类描述 : 文章内容服务
 * <p>类详情 : 无
 * 
 * @author  王金灿
 * @version 1.0.0
 *<p>
 *--------------------------------------------------------------<br>
 * 修改履历：<br>
 *        <li> 2018年7月13日，wangjc，创建文件；<br>
 *--------------------------------------------------------------<br>
 *</p>
 */
public interface ArticleService {
	/**
	 * <p>
	 * 功能描述 : 查询文章列表
	 * <p>
	 * 详细说明 : 无
	 *
	 * @param keyword
	 * @param category
	 * @return
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年7月11日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	Page<EsArticle> queryArticles(String keyword,String category,String tag, Pageable pageable);

	/**
	 * <p>
	 * 功能描述 : 查看文章详情
	 * <p>
	 * 详细说明 : 无
	 *
	 * @param id
	 * @return
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年7月11日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	EsArticle queryArticleDetail(String id);

	/**
	 * <p>
	 * 功能描述 : 点赞
	 * <p>
	 * 详细说明 : 无
	 *
	 * @param like
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年7月13日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	void addLike(ArticleLikeEntity like);
	/**
	 * <p>功能描述 : 查询文章分类及分类下的文章数量
	 * <p>详细说明 : 无
	 *
	 * @return 
	 * @author  王金灿
	 *<p>
	 *--------------------------------------------------------------<br>
	 * 修改履历：<br>
	 *        <li> 2018年7月13日，wangjc，创建方法；<br>
	 *--------------------------------------------------------------<br>
	 *</p>
	 */
	List<CategoryVO> queryCategoryAndArticleCount();
	/**
	 * <p>功能描述 : 获取相关的文章
	 * <p>详细说明 : 无
	 *
	 * @param articleId
	 * @return 
	 * @author  王金灿
	 *<p>
	 *--------------------------------------------------------------<br>
	 * 修改履历：<br>
	 *        <li> 2018年7月15日，wangjc，创建方法；<br>
	 *--------------------------------------------------------------<br>
	 *</p>
	 */
	RelativeArticle getRelativeArticle(String articleId);

	/**
	 * 添加评论
	 * @param comment
	 */
    Page<ArticleCommentEntity> addComment(ArticleCommentEntity comment);

	/**
	 * 获取评论分页列表
	 * @param articleId
	 * @param pid
	 * @param pageable
	 * @return
	 */
	Page<ArticleCommentEntity> queryComments(String articleId, String pid, Pageable pageable);

    /**
     * 删除评论
     * @param commentId
     */
    void deleteComment(String commentId);

	/**
	 * 查询最热的标签
	 * @param i 前i个
	 * @return
	 */
    List<String> queryTopTags(int i);
}
