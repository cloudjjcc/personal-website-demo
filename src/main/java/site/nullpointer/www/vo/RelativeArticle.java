package site.nullpointer.www.vo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 类路径 : site.nullpointer.www.vo.RelativeArticle
 * <p>
 * 类描述 : 相关文章
 * <p>
 * 类详情 : 无
 * 
 * @author 王金灿
 * @version 1.0.0
 *          <p>
 *          --------------------------------------------------------------<br>
 *          修改履历：<br>
 *          <li>2018年7月15日，wangjc，创建文件；<br>
 *          --------------------------------------------------------------<br>
 *          </p>
 */
public class RelativeArticle {
	/**
	 * 下一篇文章
	 */
	private ArticleLinkVO nextArticle;
	/**
	 * 上一篇文章
	 */
	private ArticleLinkVO prevArticle;
	
	public RelativeArticle(List<ArticleLinkVO> articles) {
		super();
		if(articles!=null&&articles.size()>0) {
			int size = articles.size();
			if(size==1) {
				ArticleLinkVO articleLinkVO = articles.get(0);
				if(StringUtils.equals(articleLinkVO.getIsPrev(),"true")) {
					prevArticle=articleLinkVO;
				}else {
					nextArticle=articleLinkVO;
				}
			}else {
				nextArticle=articles.get(1);
				prevArticle=articles.get(0);
			}
		}
	}

	public ArticleLinkVO getNextArticle() {
		return nextArticle;
	}

	public void setNextArticle(ArticleLinkVO nextArticle) {
		this.nextArticle = nextArticle;
	}

	public ArticleLinkVO getPrevArticle() {
		return prevArticle;
	}

	public void setPrevArticle(ArticleLinkVO prevArticle) {
		this.prevArticle = prevArticle;
	}
}
