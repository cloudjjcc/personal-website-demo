package site.nullpointer.admin.article_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import site.nullpointer.admin.article_manager.entity.ArticleEntity;

/**
 * <p>
 * 类路径 : site.nullpointer.admin.article_manager.repository.ArticleRepository
 * <p>
 * 类描述 : 方法功能描述
 * <p>
 * 类详情 : 无
 * 
 * @author 王金灿
 * @version 1.0.0
 *          <p>
 *          --------------------------------------------------------------<br>
 *          修改履历：<br>
 *          <li>2018年5月24日，wangjc，创建文件；<br>
 *          --------------------------------------------------------------<br>
 *          </p>
 */
@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, String> {
	@Modifying
	@Query(value = "update article set publish_state=?2 where id =?1 ", nativeQuery = true)
	void updateArticlePublishState(String id, int publishState);

	void deleteById(String id);

	@Modifying
	@Query(value = "update article set visit_count=visit_count+1 where id =?1 ", nativeQuery = true)
	void updateArticleVisitCount(String id);
	
	@Query(value = "select count(1) from article where publish_state=?1", nativeQuery = true)
	long countByPublishState(int publishState);

	/**
	 * 更新文章发布状态
	 * @param id
	 * @param publishState
	 */
	@Modifying
	@Query(value = "update article set publish_state=?2 where id=?1 ",nativeQuery = true)
    void updateArticlePubishStateById(String id, int publishState);
}
