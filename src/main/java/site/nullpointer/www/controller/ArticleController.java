package site.nullpointer.www.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.nullpointer.www.constants.PageConstants;
import site.nullpointer.www.document.EsArticle;
import site.nullpointer.www.entity.ArticleCommentEntity;
import site.nullpointer.www.service.ArticleService;
import site.nullpointer.www.vo.RelativeArticle;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>类路径 : site.nullpointer.common.api.ArticleController
 * <p>类描述 : 文章内容控制器
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
@Controller
@RequestMapping("/article")
public class ArticleController {
	private Logger logger=LoggerFactory.getLogger(ArticleController.class);
	@Autowired
	private ArticleService articleService;
	/**
	 * <p>功能描述 : 文章详情
	 * <p>详细说明 : 无
	 *
	 * @param articleId
	 * @return 
	 * @author  王金灿
	 *<p>
	 *--------------------------------------------------------------<br>
	 * 修改履历：<br>
	 *        <li> 2018年7月13日，wangjc，创建方法；<br>
	 *--------------------------------------------------------------<br>
	 *</p>
	 */
	@GetMapping("/details/{id}")
	public ModelAndView articleDetails(@PathVariable(name="id")String articleId) {
        Map<String,Object> model=new HashMap<>();
		try {
			EsArticle article = articleService.queryArticleDetail(articleId);
			RelativeArticle relativeArticle = articleService.getRelativeArticle(articleId);
			Page<ArticleCommentEntity> comments=articleService.queryComments(articleId,"",PageConstants.DEFAULT_COMMENT_PAGE);
			model.put("details",article );
			model.put("comments",comments);
			model.put("prevArticle",relativeArticle.getPrevArticle() );
			model.put("nextArticle",relativeArticle.getNextArticle() );
		}catch (Exception e){
			e.printStackTrace();
			return new ModelAndView("redirect:/index",model);
		}
		return new ModelAndView("details",model);
	}
    /**
     * 新增评论
     * @param comment
     * @return
     */
    @PostMapping("/user/comment")
    public ModelAndView addComment(@RequestBody ArticleCommentEntity comment){
        Map<String,Object> model=new HashMap<>();
        Page<ArticleCommentEntity> comments = articleService.addComment(comment);
        if(StringUtils.isBlank(comment.getPid())){
            model.put("comments",comments);
            return new ModelAndView("details::#comments-list",model);
        }else{
            model.put("replys",comments);
            return new ModelAndView("layout/reply::reply-list",model);
        }
    }
    @DeleteMapping("/user/comment/{commentId}")
    public ModelAndView deleteComment(@PathVariable String commentId){
        Map<String,Object> model=new HashMap<>();
        articleService.deleteComment(commentId);
        return new ModelAndView("layout/reply::reply-list",model);
    }

}
