package site.nullpointer.www.document;

import java.util.Arrays;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import site.nullpointer.admin.article_manager.entity.ArticleEntity;
import site.nullpointer.www.entity.ViewCommonArticle;
import sun.applet.Main;

/**
 * <p>类路径 : site.nullpointer.common.entity.document.EsArticle
 * <p>类描述 : Es文章实体
 * <p>类详情 : 无
 * 
 * @author  王金灿
 * @version 1.0.0
 *<p>
 *--------------------------------------------------------------<br>
 * 修改履历：<br>
 *        <li> 2018年7月12日，wangjc，创建文件；<br>
 *--------------------------------------------------------------<br>
 *</p>
 */
@Document(indexName="articles",type="article")
public class EsArticle {
	@Id
	private String id;
	@Field(analyzer="ik_max_word")
	private String title;
	@Field(analyzer="ik_max_word")
	private String content;
	@Field(index=false)
	private String articleId;
	@Field(index=false)
	private String category;
	@Field(index=false)
	private Integer source;
	@Field(index=false)
	private String sourceUrl;
	@Field(index=false)
	private Date publishDate;
	@Field(index=false)
	private Integer likeCount;
	@Field(index=false)
	private Integer visitCount;
	@Field(index=false)
	private Integer commentCount;
	@Field(index=false)
	private Boolean commentDisabled;
	@Field(index=false)
	private String author;
	@Field(index=false)
	private String[] tags;
	public EsArticle() {
		
	}
	
	public EsArticle(String id, String title, String content, String articleId, String category, Integer source,String sourceUrl,
			Date publishDate, Integer likeCount, Integer visitCount, Integer commentCount,Boolean commentDisabled,String author,String tags) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.articleId = articleId;
		this.category = category;
		this.source = source;
		this.sourceUrl=sourceUrl;
		this.publishDate = publishDate;
		this.likeCount = likeCount;
		this.visitCount = visitCount;
		this.commentCount = commentCount;
		this.commentDisabled=commentDisabled;
		this.author=author;
		this.tags=tags.split(",");
	}
	public EsArticle(ArticleEntity articleEntity) {
		super();
		this.title = articleEntity.getTitle();
		this.content = articleEntity.getContent();
		this.articleId = articleEntity.getId();
		this.category = articleEntity.getCategory();
		this.source = articleEntity.getSource();
		this.sourceUrl=articleEntity.getSourceUrl();
		this.publishDate = articleEntity.getPublishDate();
		this.likeCount = articleEntity.getLikeCount();
		this.visitCount = articleEntity.getVisitCount();
		this.commentCount = articleEntity.getCommentCount();
		this.commentDisabled=articleEntity.getCommentDisabled();
		this.author=articleEntity.getAuthor();
		this.tags=articleEntity.getTags().split(",");
	}

	public ViewCommonArticle toCommonViewArticle(){
		return new ViewCommonArticle(articleId,content,category,source,sourceUrl,title,publishDate,
				likeCount,visitCount,commentCount,commentDisabled,author,getTagsString());
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArticleId() {
		return articleId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Integer getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	public Integer getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getTagsString(){
	    if(tags!=null&&tags.length>0){
            String strTags="";
            for (String tag : tags){
                strTags+=tag;
                strTags+=",";
            }
            return strTags.substring(0,strTags.length()-1);
        }else{
	        return "";
        }
    }
}
