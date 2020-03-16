package site.nullpointer.www.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.nullpointer.common.dto.ResponseEntity;
import site.nullpointer.www.entity.ArticleLikeEntity;
import site.nullpointer.www.service.ArticleService;

/**
 * <p>
 * 类路径 : site.nullpointer.common.api.ArticleApi
 * <p>
 * 类描述 : 文章信息api接口
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
@RestController
@RequestMapping("/api/article")
public class ArticleApi {
	@Autowired
	private ArticleService articleService;

	/**
	 * <p>
	 * 功能描述 : 获取文章列表
	 * <p>
	 * 详细说明 : 无
	 *
	 * @return
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年7月13日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	@GetMapping(value = "")
	public ResponseEntity queryArticle() {
		return new ResponseEntity().success(true).data(articleService.queryArticles(null, null,null,null));
	}

	/**
	 * <p>
	 * 功能描述 : 获取文章详情
	 * <p>
	 * 详细说明 : 无
	 *
	 * @param articleId
	 * @return
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年7月13日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	@GetMapping(value = "/details/{articleId}")
	public ResponseEntity queryArticleDetail(@PathVariable(name = "articleId") String articleId) {
		return new ResponseEntity().success(true).data(articleService.queryArticleDetail(articleId));
	}

	/**
	 * <p>
	 * 功能描述 : 点赞文章
	 * <p>
	 * 详细说明 : 无
	 * 
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年7月13日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	@PostMapping("/user/like")
	public ResponseEntity addLike(@RequestBody ArticleLikeEntity like) {
		articleService.addLike(like);
		return new ResponseEntity().success(true);
	}
}
