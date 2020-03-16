package site.nullpointer.www.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import site.nullpointer.www.constants.PageConstants;
import site.nullpointer.www.document.EsArticle;
import site.nullpointer.www.service.ArticleService;
import site.nullpointer.www.vo.CategoryVO;

/**
 * <p>
 * 类路径 : site.nullpointer.common.api.HomeController
 * <p>
 * 类描述 : 主页控制器
 * <p>
 * 类详情 : 无
 * 
 * @author 王金灿
 * @version 1.0.0
 *          <p>
 *          --------------------------------------------------------------<br>
 *          修改履历：<br>
 *          <li>2018年7月10日，wangjc，创建文件；<br>
 *          --------------------------------------------------------------<br>
 *          </p>
 */
@Controller
public class HomeController {
    private  final Logger logger=LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private ArticleService articleService;

	/**
	 * <p>
	 * 功能描述 : 默认首页
	 * <p>
	 * 详细说明 : 无
	 *
	 * @return
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年7月10日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	@GetMapping("/")
	public String index() {
		return "redirect:/index";
	}

	/**
	 * <p>
	 * 功能描述 : 首页
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
	@GetMapping("/index")
	public ModelAndView indexPage() {
		Map<String, Object> model = new HashMap<>();
		Page<EsArticle> articles=null;
        List<CategoryVO> categorys=null;
        List<String> tags=null;
        try {
            articles = articleService.queryArticles(null,null,null, PageConstants.DEFAULT_ARTICLE_PAGE);
            categorys = articleService.queryCategoryAndArticleCount();
            tags=articleService.queryTopTags(20);
        }catch (Exception e){
            logger.error("加载文章数据失败！",e);
        }
        model.put("articles",articles);
		model.put("categorys",categorys );
		model.put("tags",tags );
		return new ModelAndView("index", model);
	}
	/**
	 * <p>功能描述 : 获取文章列表
	 * <p>详细说明 : 无
	 *
	 * @param page
	 * @param size
	 * @return
	 * @author  王金灿
	 *<p>
	 *--------------------------------------------------------------<br>
	 * 修改履历：<br>
	 *        <li> 2018年7月13日，wangjc，创建方法；<br>
	 *--------------------------------------------------------------<br>
	 *</p>
	 */
	@GetMapping("/articles")
	public ModelAndView queryArticles(
			@RequestParam(name = "pageNo", required = true, defaultValue = "0") int page,
			@RequestParam(name = "pageSize", required = true, defaultValue = "10") int size,
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "tag", required = false) String tag,
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "sortValue", required = false,defaultValue = "0") Integer sortValue) {
		Map<String, Object> model = new HashMap<>();
		Page<EsArticle> articles=null;
		try {
			PageRequest pageable = PageRequest.of(page, size,PageConstants.ARTICLE_SORT_ARRAY[sortValue]);
			articles = articleService.queryArticles(keyword,category,tag, pageable);
		}catch (Exception e){
			logger.error("加载文章数据失败！",e);
		}
		model.put("articles",articles);
		return new ModelAndView("index::#article-list", model);
	}
}
