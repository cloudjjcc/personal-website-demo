package site.nullpointer.admin.article_manager.dto;

import site.nullpointer.admin.article_manager.entity.ArticleEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangjc
 * @Title: ArticleInfo
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2516:34
 */
public class ArticleInfo implements Serializable {
    /**
     * 主键id
     */
    private String id;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章来源
     */
    private Integer source;
    /**
     * 文章来源url
     */
    private String sourceUrl;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 发布状态
     */
    private int publishState;
    /**
     * 发布时间
     */
    private Date publishDate;
    /**
     * 更新日期
     */
    private Date updateDate;
    /**
     * 标签
     */
    private String tags;

    /**
     * 禁止评论
     */
    private Boolean commentDisabled;

    /**
     * 文章分类
     */
    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublishState() {
        return publishState;
    }

    public void setPublishState(int publishState) {
        this.publishState = publishState;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Boolean getCommentDisabled() {
        return commentDisabled;
    }

    public void setCommentDisabled(Boolean commentDisabled) {
        this.commentDisabled = commentDisabled;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 转化为文章实体对象
     *
     * @return
     */
    public ArticleEntity toArticleEntity() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(this.id);
        articleEntity.setPublishDate(publishDate);
        articleEntity.setUpdateDate(updateDate);
        articleEntity.setPublishState(publishState);
        articleEntity.setSource(this.source);
        articleEntity.setTags(this.tags);
        articleEntity.setTitle(this.title);
        articleEntity.setContent(this.content);
        articleEntity.setCategory(this.category);
        articleEntity.setCommentDisabled(this.commentDisabled);
        return articleEntity;
    }

    /**
     * 将文章实体转为文章信息对象
     * @param articleEntity
     */
    public ArticleInfo(ArticleEntity articleEntity){
        super();
        this.id=articleEntity.getId();
        this.category=articleEntity.getCategory();
        this.title=articleEntity.getTitle();
        this.content=articleEntity.getContent();
        this.commentDisabled=articleEntity.getCommentDisabled();
        this.publishDate=articleEntity.getPublishDate();
        this.updateDate=articleEntity.getUpdateDate();
        this.source=articleEntity.getSource();
        this.sourceUrl=articleEntity.getSourceUrl();
        this.tags=articleEntity.getTags();
        this.publishState=articleEntity.getPublishState();
    }

    public ArticleInfo(){

    }

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", source=" + source +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", title='" + title + '\'' +
                ", publishState=" + publishState +
                ", publishDate=" + publishDate +
                ", updateDate=" + updateDate +
                ", tags='" + tags + '\'' +
                ", commentDisabled=" + commentDisabled +
                ", category='" + category + '\'' +
                '}';
    }
}
