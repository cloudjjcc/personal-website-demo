package site.nullpointer.admin.article_manager.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import site.nullpointer.auth.entity.SysUser;
import site.nullpointer.www.entity.ArticleCommentEntity;
import site.nullpointer.www.entity.ArticleLikeEntity;

/**
 * <p>
 * 类路径 : ArticleEntity
 * <p>
 * 类描述 : 文章实体
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
@Entity
@Table(name = "article")
public class ArticleEntity implements Serializable {
	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(generator = "uuid2" )   //指定生成器名称
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )  //生成器名称，uuid生成类
	private String id;
	/**
	 * 文章分类
	 */
	@Column(name = "category")
	private String category;
	/**
	 * 文章内容
	 */
	@Column(name = "content")
	private String content;
	/**
	 * 文章来源
	 */
	@Column(name = "source")
	private Integer source;
	/**
	 * 文章出处
	 */
	@Column(name = "source_url")
	private String sourceUrl;
	/**
	 * 文章标题
	 */
	@Column(name = "title")
	private String title;
	/**
	 * 文章用户
	 */
	@OneToOne(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private SysUser user;
	/**
	 * 发布状态
	 */
	@Column(name = "publish_state")
	private int publishState;
	/**
	 * 发布时间
	 */
	@Column(name = "publish_date")
	private Date publishDate;
	/**
	 * 更新时间
	 */
	@Column(name = "update_date")
	private Date updateDate;
	@Column(name="tags")
	private String tags;
	/**
	 * 所有点赞
	 */
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
	private List<ArticleLikeEntity> likes;
	/**
	 * 所有评论
	 */
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	private List<ArticleCommentEntity> comments;
	/**
	 * 点赞量
	 */
	@Column(name = "like_count")
	private Integer likeCount;
	/**
	 * 阅读量
	 */
	@Column(name = "visit_count")
	private Integer visitCount;
	/**
	 * 评论量
	 */
	@Column(name = "comment_count")
	private Integer commentCount;
	/**
	 * 禁止评论
	 */
	@Column(name = "comment_disabled")
	private Boolean commentDisabled;
    /**
     * 作者
     */
    @Column(name = "author")
	private String author;
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public int getPublishState() {
		return publishState;
	}

	public void setPublishState(int publishState) {
		this.publishState = publishState;
	}

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

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public List<ArticleLikeEntity> getLikes() {
		return likes;
	}

	public void setLikes(List<ArticleLikeEntity> likes) {
		this.likes = likes;
		this.likeCount=likes.size();
	}

	public List<ArticleCommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<ArticleCommentEntity> comments) {
		this.comments = comments;
		this.commentCount=comments.size();
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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
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

    /**
	 * <p>功能描述 : 点赞
	 * <p>详细说明 : 无
	 *
	 * @param like
	 * @author  王金灿
	 *<p>
	 *--------------------------------------------------------------<br>
	 * 修改履历：<br>
	 *        <li> 2018年7月13日，wangjc，创建方法；<br>
	 *--------------------------------------------------------------<br>
	 *</p>
	 */
	public boolean addLike(ArticleLikeEntity like){
		for (ArticleLikeEntity alike : likes) {
			if(StringUtils.equals(alike.getUser().getId(),like.getUser().getId())) {
				return false;
			}
		}
		this.likes.add(like);
		this.likeCount=this.likes.size();
		return true;
	}
	/**
	 * <p>功能描述 : 评论
	 * <p>详细说明 : 无
	 *
	 * @param comment 
	 * @author  王金灿
	 *<p>
	 *--------------------------------------------------------------<br>
	 * 修改履历：<br>
	 *        <li> 2018年7月13日，wangjc，创建方法；<br>
	 *--------------------------------------------------------------<br>
	 *</p>
	 */
	public void addComment(ArticleCommentEntity comment) {
	    int lastFloor= (int) comments.stream().filter(c->StringUtils.isBlank(c.getPid())).count();
	    if(StringUtils.isNoneBlank(comment.getPid())){
            comment.setFloor(lastFloor);
        }else{
            comment.setFloor(lastFloor+1);
        }
		this.comments.add(comment);
		this.commentCount=this.comments.size();
	}

    @Override
    public String toString() {
        return "ArticleEntity{" +
                "id='" + id + '\'' +
                ", category=" + category +
                ", content='" + content + '\'' +
                ", source=" + source +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", title='" + title + '\'' +
                ", user=" + user +
                ", publishState=" + publishState +
                ", publishDate=" + publishDate +
                ", updateDate=" + updateDate +
                ", tags='" + tags + '\'' +
                ", likes=" + likes +
                ", comments=" + comments +
                ", likeCount=" + likeCount +
                ", visitCount=" + visitCount +
                ", commentCount=" + commentCount +
                ", commentDisabled=" + commentDisabled +
                '}';
    }
}
