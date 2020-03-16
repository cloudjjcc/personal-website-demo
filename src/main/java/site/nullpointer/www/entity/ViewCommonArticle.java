package site.nullpointer.www.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import site.nullpointer.www.document.EsArticle;

@Entity
@Table(name = "view_common_article")
public class ViewCommonArticle {
	@Id
	@GeneratedValue(generator = "uuid2" )   //指定生成器名称
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )  //生成器名称，uuid生成类
	private String id;
	@Lob  // 大对象，映射 MySQL 的 Long Text 类型
	@Basic(fetch=FetchType.LAZY) // 懒加载
	private String content;
	@Column(name = "category")
	private String category;
	@Column(name = "source")
	private Integer source;
	@Column(name = "source_url")
	private String sourceUrl;
	@Column(name = "title")
	private String title;
	@Column(name = "publish_date")
	private Date publishDate;
	@Column(name = "like_count")
	private Integer likeCount;
	@Column(name = "visit_count")
	private Integer visitCount;
	@Column(name = "comment_count")
	private Integer commentCount;
	@Column(name = "author")
	private String author;
	@Column(name = "comment_disabled")
	private Boolean commentDisabled;
	@Column(name = "tags")
	private String tags;
	public ViewCommonArticle() {
		
	}
	public ViewCommonArticle(String id, String content, String category, Integer source, String sourceUrl, String title,
                             Date publishDate, Integer likeCount, Integer visitCount, Integer commentCount,Boolean commentDisabled, String author,String tags) {
		super();
		this.id = id;
		this.content = content;
		this.category = category;
		this.source = source;
		this.sourceUrl=sourceUrl;
		this.title = title;
		this.publishDate = publishDate;
		this.likeCount = likeCount;
		this.visitCount = visitCount;
		this.commentCount = commentCount;
		this.commentDisabled=commentDisabled;
		this.author = author;
		this.tags=tags;
	}

	public EsArticle toEsArticle() {
		return new EsArticle(null,title,content,id,category,source,sourceUrl,
				publishDate,likeCount,visitCount, commentCount,commentDisabled,author,tags);
	}
	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getCategoryId() {
		return category;
	}

	public void setCategoryId(String category) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getCommentDisabled() {
        return commentDisabled;
    }

    public void setCommentDisabled(Boolean commentDisabled) {
        this.commentDisabled = commentDisabled;
    }

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
