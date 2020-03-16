package site.nullpointer.www.vo;
/**
 * <p>类路径 : site.nullpointer.www.vo.ArticleLinkVO
 * <p>类描述 : 相关文章（前后）
 * <p>类详情 : 无
 * 
 * @author  王金灿
 * @version 1.0.0
 *<p>
 *--------------------------------------------------------------<br>
 * 修改履历：<br>
 *        <li> 2018年7月15日，wangjc，创建文件；<br>
 *--------------------------------------------------------------<br>
 *</p>
 */
public class ArticleLinkVO {
	private String id;
	private String title;
	private String isPrev;
	private String isNext;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsPrev() {
		return isPrev;
	}
	public void setIsPrev(String isPrev) {
		this.isPrev = isPrev;
	}
	public String getIsNext() {
		return isNext;
	}
	public void setIsNext(String isNext) {
		this.isNext = isNext;
	}
	
}
