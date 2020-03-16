package site.nullpointer.www.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import site.nullpointer.auth.entity.SysUser;

/**
 * <p>类路径 : site.nullpointer.www.entity.ArticleLikeEntity
 * <p>类描述 : 文章点赞实体
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
@Table(name = "article_like")
public class ArticleLikeEntity implements Serializable{
	/**long serialVersionUID: TODO属性声明*/
	private static final long serialVersionUID = 2592569720684924589L;
	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(generator = "uuid2" )   //指定生成器名称
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )  //生成器名称，uuid生成类
	private String id;
	/**
	 * 文章id
	 */
	@Column(name="article_id")
	private String articleId;
	/**
	 * 点赞时间
	 */
	@Column(name="date",nullable = false)
	@org.hibernate.annotations.CreationTimestamp  // 由数据库自动创建时间
	private Date date;
	/**
	 * 点赞用户
	 */
	@OneToOne(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private SysUser user;
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
	@Override
	public String toString() {
		return "ArticleLikeEntity [id=" + id + ", articleId=" + articleId + ", date=" + date + ", user=" + user + "]";
	}
	
	protected ArticleLikeEntity() {
		
	}
	public ArticleLikeEntity(SysUser user) {
		this.user=user;
	}
	
}
