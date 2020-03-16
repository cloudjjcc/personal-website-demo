package site.nullpointer.www.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import site.nullpointer.admin.dict_manager.repository.DictRepository;
import site.nullpointer.utils.BeanUtils;
import site.nullpointer.www.entity.ArticleCommentEntity;
import site.nullpointer.admin.article_manager.entity.ArticleEntity;
import site.nullpointer.www.entity.ArticleLikeEntity;
import site.nullpointer.admin.article_manager.repository.ArticleRepository;
import site.nullpointer.auth.entity.SysUser;
import site.nullpointer.auth.service.UserBaseInfoService;
import site.nullpointer.www.document.EsArticle;
import site.nullpointer.www.repository.ArticleCommentRepository;
import site.nullpointer.www.repository.EsArticleRepository;
import site.nullpointer.www.repository.impl.BaseRepositoryImpl;
import site.nullpointer.www.service.ArticleService;
import site.nullpointer.www.vo.ArticleLinkVO;
import site.nullpointer.www.vo.CategoryVO;
import site.nullpointer.www.vo.RelativeArticle;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static site.nullpointer.www.constants.PageConstants.DEFAULT_COMMENT_PAGE;

/**
 * <p>
 * 类路径 : site.nullpointer.common.service.ArticleServiceImpl
 * <p>
 * 类描述 : 文章内容服务
 * <p>
 * 类详情 : 无
 * 
 * @author 王金灿
 * @version 1.0.0
 *          <p>
 *          --------------------------------------------------------------<br>
 *          修改履历：<br>
 *          <li>2018年7月13日，wangjc，创建文件；<br>
 *          --------------------------------------------------------------<br>
 *          </p>
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService, UserBaseInfoService {
    @Autowired
	private EsArticleRepository esArticleRepository;
	@Autowired
	private ArticleRepository adminArticleRepository;
    @Autowired
	private ArticleCommentRepository articleCommentRepository;
	@Autowired
	private BaseRepositoryImpl baseRepository;

    @Autowired
	private DictRepository dictRepository;
	/**
	 * <p>功能描述 :
	 * <p>详细说明 : 查询文章分类及分类下的文章数量
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
	public List<CategoryVO> queryCategoryAndArticleCount(){
		StringBuilder sql=new StringBuilder();
		sql.append(" SELECT DISTINCT ");
		sql.append(" 	category AS name , ");
		sql.append(" 	count(id) AS count ");
		sql.append(" FROM ");
		sql.append(" 	view_common_article  ");
		sql.append(" GROUP BY ");
		sql.append(" 	category");
		return BeanUtils.mapListToBeanList(new CategoryVO(), baseRepository.execute(sql.toString(), new HashMap<String,Object>()));
	}
	/**
	 * <p>功能描述 : 获取当前文章的上一篇，下一篇文章
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
	public RelativeArticle getRelativeArticle(String articleId) {
		StringBuilder sql=new StringBuilder();
		sql.append(" ( ");
		sql.append(" 	SELECT ");
		sql.append(" 		id, ");
		sql.append(" 		title, ");
		sql.append(" 		'true' AS is_prev ");
		sql.append(" 	FROM ");
		sql.append(" 		view_common_article ");
		sql.append(" 	WHERE ");
		sql.append(" 		category = ( ");
		sql.append(" 			SELECT ");
		sql.append(" 				category ");
		sql.append(" 			FROM ");
		sql.append(" 				view_common_article ");
		sql.append(" 			WHERE ");
		sql.append(" 				id = :id ");
		sql.append(" 		) ");
		sql.append(" 	AND id < :id ");
		sql.append(" 	ORDER BY ");
		sql.append(" 		id ");
		sql.append(" 	LIMIT 1 ");
		sql.append(" ) ");
		sql.append(" UNION ");
		sql.append(" 	( ");
		sql.append(" 		SELECT ");
		sql.append(" 			id, ");
		sql.append(" 			title, ");
		sql.append(" 			'' ");
		sql.append(" 		FROM ");
		sql.append(" 			view_common_article ");
		sql.append(" 		WHERE ");
		sql.append(" 			category = ( ");
		sql.append(" 				SELECT ");
		sql.append(" 					category ");
		sql.append(" 				FROM ");
		sql.append(" 					view_common_article ");
		sql.append(" 				WHERE ");
		sql.append(" 					id = :id ");
		sql.append(" 			) ");
		sql.append(" 		AND id > :id ");
		sql.append(" 		ORDER BY ");
		sql.append(" 			id ");
		sql.append(" 		LIMIT 1 ");
		sql.append(" 	) ");
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("id", articleId);
		return new RelativeArticle(BeanUtils.mapListToBeanList(new ArticleLinkVO(), baseRepository.execute(sql.toString(), params),BeanUtils.LogicEqualLevel.IGNORE_CASE_AND_UNDERLINE_EQUAL));
	}
	@Override
	public EsArticle queryArticleDetail(String articleId) {
		adminArticleRepository.updateArticleVisitCount(articleId);
		Optional<EsArticle> oEsArticle = esArticleRepository.findByArticleId(articleId);
		EsArticle esArticle = oEsArticle.get();
		esArticle.setVisitCount(esArticle.getVisitCount() + 1);
		return esArticleRepository.save(esArticle);
	}

	@Override
	public Page<EsArticle> queryArticles(String keyword, String category, String tag, Pageable pageable) {
		/**
		 * 关键字查询
		 */
		if (StringUtils.isNoneBlank(keyword)) {
			QueryBuilder query = QueryBuilders.disMaxQuery().add(QueryBuilders.matchQuery("title", keyword))
					.add(QueryBuilders.matchQuery("content", keyword)).tieBreaker(0.3f);
			return esArticleRepository.search(query, pageable);
			// return
			// esArticleRepository.findDistinctByTitleOrContent(keyword,keyword,pageable);
		}
		/**
		 * 文章分类查询
		 */
		if (StringUtils.isNoneBlank(category)) {
			/**
			 * 标签查询
			 */
//			if(StringUtils.isNoneBlank(tag)){
//				return esArticleRepository.findDistinctByCategoryAndTags(category,tag, pageable);
//			}
			/**
			 * 文章分类查询
			 */
			return esArticleRepository.findDistinctByCategory(category, pageable);
		}
		return esArticleRepository.findAll(pageable);
	}

    /**
     * 点赞
     * @param like
     */
	@Override
	public void addLike(ArticleLikeEntity like) {
		SysUser currentUser = getCurrentUser();
		ArticleEntity originArticle = adminArticleRepository.findById(like.getArticleId()).get();
		like.setUser(currentUser);
		if (originArticle.addLike(like)) {
			adminArticleRepository.save(originArticle);
		} else {
			throw new IllegalArgumentException("已经点赞过此文章");
		}
	}
    /**
     * 添加评论
     * @param comment
     */
    @Override
    public Page<ArticleCommentEntity> addComment(ArticleCommentEntity comment) {
        SysUser currentUser = getCurrentUser();
        ArticleEntity articleEntity = adminArticleRepository.findById(comment.getArticleId()).get();
        comment.setUser(currentUser);
        boolean isReply=false;
        if(StringUtils.isNoneBlank(comment.getPid())){
            isReply=true;
            String rowContent=comment.getContent();
            int bound=rowContent.lastIndexOf(">");
            comment.setContent(rowContent.substring(bound>-1?bound+1:0));
        }
		articleEntity.addComment(comment);
        ArticleEntity article = adminArticleRepository.save(articleEntity);
		EsArticle esArticle = esArticleRepository.findByArticleId(article.getId()).get();
		esArticle.setCommentCount(article.getCommentCount());
		esArticleRepository.save(esArticle);
        List<ArticleCommentEntity> rawComments =article.getComments();
        List<ArticleCommentEntity> comments;
        if(isReply){
            comments = rawComments .stream().filter((c)->StringUtils.equals(c.getPid(),comment.getPid())).collect(Collectors.toList());
        }else{
            comments = rawComments .stream().filter((c)->StringUtils.isBlank(c.getPid())).collect(Collectors.toList());
        }
        Collections.reverse(comments);
        List<ArticleCommentEntity> pageContent =comments.stream().limit(10).collect(Collectors.toList());//comments .subList(0, comments.size()>10?10:comments.size());
        return new PageImpl<ArticleCommentEntity>(pageContent, DEFAULT_COMMENT_PAGE, comments.size());
    }

    @Override
    public Page<ArticleCommentEntity> queryComments(String articleId, String pid, Pageable pageable) {
            return articleCommentRepository.findByArticleIdAndPid( articleId, pid,pageable);
    }

    @Override
    public void deleteComment(String commentId) {
        articleCommentRepository.deleteById(commentId);
		ArticleCommentEntity commentEntity = articleCommentRepository.findById(commentId).get();
        ArticleEntity articleEntity = adminArticleRepository.findById(commentEntity.getArticleId()).get();
        esArticleRepository.save(new EsArticle(articleEntity));
    }

	@Override
	public List<String> queryTopTags(int i) {
		return dictRepository.getDataKeyRandom("article_tag",i);
	}
}
