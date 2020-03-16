package site.nullpointer.www.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.nullpointer.www.entity.ArticleCommentEntity;

/**
 * @author wangjc
 * @Title: ArticleCommentRepository
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/1717:10
 */
@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleCommentEntity,String> {
    /**
     * 根据文章id和父id查询评论列表
     * @param articleId
     * @param pid
     * @param pageable
     * @return
     */
    Page<ArticleCommentEntity> findByArticleIdAndPid(String articleId, String pid, Pageable pageable);
}
