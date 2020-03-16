package site.nullpointer.admin.article_manager.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.nullpointer.admin.article_manager.dto.ArticleInfo;
import site.nullpointer.admin.article_manager.entity.ArticleEntity;
import site.nullpointer.admin.article_manager.repository.ArticleRepository;
import site.nullpointer.admin.article_manager.service.ArticleManagerService;
import site.nullpointer.auth.entity.SysUser;
import site.nullpointer.auth.service.UserBaseInfoService;
import site.nullpointer.www.document.EsArticle;
import site.nullpointer.www.repository.EsArticleRepository;
import site.nullpointer.www.repository.impl.BaseRepositoryImpl;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author wangjc
 * @Title: ArticleManagerServiceImpl
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2516:26
 */
@Service
@Transactional
public class ArticleManagerServiceImpl implements ArticleManagerService, UserBaseInfoService {
    private final Logger logger = LoggerFactory.getLogger(ArticleManagerServiceImpl.class);

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private EsArticleRepository esArticleRepository;

    @Autowired
    private BaseRepositoryImpl baseRepository;

    @Override
    public void saveArticle(ArticleInfo articleInfo) {
        ArticleEntity article = articleInfo.toArticleEntity();
        SysUser currentUser = getCurrentUser();
        article.setUser(currentUser);
        article.setAuthor(currentUser.getNickname());
        article.setUpdateDate(new Date());
        article.setLikeCount(0);
        article.setVisitCount(0);
        article.setCommentCount(0);
        esArticleRepository.save(new EsArticle(articleRepository.save(article)));
    }

    @Override
    public void updateArticle(ArticleInfo articleInfo) {
        ArticleEntity article = articleRepository.findById(articleInfo.getId()).get();
        int rowPublishState = article.getPublishState();
        article.setCategory(articleInfo.getCategory());//更新文章分类
        article.setTitle(articleInfo.getTitle());//更新文章标题
        article.setContent(articleInfo.getContent());//更新文章内容
        article.setUpdateDate(new Date());//更新时间
        article.setCommentDisabled(articleInfo.getCommentDisabled());//更新评论开关
        article.setTags(articleInfo.getTags());//更新文章标签
        article.setSource(articleInfo.getSource());//更新文章来源
        article.setSourceUrl(articleInfo.getSourceUrl());//更新文章来源url
        article.setPublishState(articleInfo.getPublishState());//更新文章状态
        article.setPublishDate(articleInfo.getPublishDate());//更新文章发布日期
        ArticleEntity articleEntity = articleRepository.save(article);//更新文章到数据库
        if (articleInfo.getPublishState() == 1) {
            EsArticle esArticle = new EsArticle(articleEntity);
            String id = null;
            try {
                id = esArticleRepository.findByArticleId(articleInfo.getId()).get().getId();
            } catch (NoSuchElementException e) {
                logger.error("Elasticsearch中没有找到文章", e);
            }
            esArticle.setId(id);
            esArticleRepository.save(esArticle);//更新文章到Elasticsearch
        } else {
            esArticleRepository.deleteByArticleId(articleEntity.getId());
        }
    }

    @Override
    public ArticleInfo getArticleById(String articleId) {
        return new ArticleInfo(articleRepository.findById(articleId).get());
    }

    @Override
    public Page<ArticleInfo> getArticleList(String title, String category, int source, int publishState, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT id,title,category,source,publish_state,publish_date,update_date,tags,source_url,comment_disabled FROM article WHERE 1=1 ");
        Map<String, Object> params = new HashMap<>();
        /**
         * 文章标题模糊查询
         */
        if (StringUtils.isNoneBlank(title)) {
            sql.append(" AND title LIKE :title");
            params.put("title", "%" + title + "%");
        }
        /**
         * 文章分类查询
         */
        if (StringUtils.isNoneBlank(category)) {
            sql.append(" AND category = :category ");
            params.put("category", category);
        }
        /**
         * 文章来源
         */
        if (source != -1) {
            sql.append(" AND source=:source");
            params.put("source", source);
        }
        /**
         * 发布转态
         */
        if (publishState != -1) {
            sql.append(" AND publish_state=:publish_state ");
            params.put("publish_state", publishState);
        }
        /**
         * 默认按更新时间倒序
         */
        sql.append(" ORDER BY update_date DESC ");
        return baseRepository.beanPage(sql.toString(), params, pageable, new ArticleInfo());
    }

    @Override
    public void deleteArticle(String id) {
        articleRepository.deleteById(id);
    }

    @Override
    public void updatePulishState(String id, int publishState) {
        ArticleEntity articleEntity = articleRepository.findById(id).get();
        int rowPublishState = articleEntity.getPublishState();
        if (rowPublishState != publishState) {
            articleEntity.setPublishState(publishState);
            articleRepository.save(articleEntity);
            if (publishState == 1) {
                esArticleRepository.save(new EsArticle(articleEntity));
            } else {
                esArticleRepository.deleteByArticleId(id);
            }
        }
    }
}
