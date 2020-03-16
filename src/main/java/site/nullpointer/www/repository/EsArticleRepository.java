package site.nullpointer.www.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import site.nullpointer.www.document.EsArticle;

/**
 * <p>
 * 类路径 : site.nullpointer.common.repository.EsArticleRepository
 * <p>
 * 类描述 : ES文章数据操作库
 * <p>
 * 类详情 : 无
 * 
 * @author 王金灿
 * @version 1.0.0
 *          <p>
 *          --------------------------------------------------------------<br>
 *          修改履历：<br>
 *          <li>2018年7月12日，wangjc，创建文件；<br>
 *          --------------------------------------------------------------<br>
 *          </p>
 */
@Repository
public interface EsArticleRepository extends ElasticsearchRepository<EsArticle, String> {
	Optional<EsArticle> findByArticleId(String articleId);

	/**
	 * <p>
	 * 功能描述 : 根据关键字查找文章
	 * <p>
	 * 详细说明 : 无
	 *
	 * @param title
	 * @param content
	 * @param pageable
	 * @return
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年7月14日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	Page<EsArticle> findDistinctByTitleOrContent(String title, String content, Pageable pageable);

	/**
	 * <p>
	 * 功能描述 : 根据文章分类查询
	 * <p>
	 * 详细说明 : 无
	 *
	 * @param category
	 * @param pageable
	 * @return
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年7月14日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	Page<EsArticle> findDistinctByCategory(String category, Pageable pageable);

	/**
	 * 根据文章id删除
	 * @param id
	 */
    void deleteByArticleId(String id);

	/**
	 * 根据文章分类和标签查询
	 * @param category
	 * @param tag
	 * @param pageable
	 * @return
	 */
	Page<EsArticle> findDistinctByCategoryAndTags(String category,String  tag, Pageable pageable);
}
