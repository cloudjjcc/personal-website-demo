package site.nullpointer.www.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.GenericGenerator;

import site.nullpointer.auth.entity.SysUser;
/**
 * <p>类路径 : site.nullpointer.www.entity.ArticleCommentEntity
 * <p>类描述 : 文章评论实体
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
@Entity
@Table(name="article_comment")
public class ArticleCommentEntity implements Serializable,Comparable<ArticleCommentEntity>{

	/**long serialVersionUID: TODO属性声明*/
	private static final long serialVersionUID = 1317205611403747027L;
	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(generator = "uuid2" )   //指定生成器名称
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )  //生成器名称，uuid生成类
	private String id;
	/**
	 * 文章
	 */
	@Column(name="article_id")
	private String articleId;
	/**
	 * 评论时间
	 */
	@Column(name="date",nullable = false)
	@org.hibernate.annotations.CreationTimestamp  // 由数据库自动创建时间
	private Date date;
	/**
	 * 评论用户
	 */
	@OneToOne(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private SysUser user;
	/**
	 * 评论父id
	 */
	@Column(name="pid")
	private String pid;
	/**
	 * 评论内容
	 */
	@Column(name="content")
	private String content;
    /**
     * 楼层
     */
	@Column(name="floor")
	private Integer floor;
    /**
     * 回复用户
     */
    @OneToOne(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name="to_user_id")
    private SysUser toUser;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public SysUser getToUser() {
        return toUser;
    }

    public void setToUser(SysUser toUser) {
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return "ArticleCommentEntity{" +
                "id='" + id + '\'' +
                ", articleId='" + articleId + '\'' +
                ", date=" + date +
                ", user=" + user +
                ",toUser="+toUser+
                ", pid='" + pid + '\'' +
                ", content='" + content + '\'' +
                ", floor=" + floor +
                '}';
    }

    @Override
    public int compareTo(ArticleCommentEntity o) {
        return date.compareTo(o.getDate());
    }
}
