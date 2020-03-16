package site.nullpointer.www.constants;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * 页面常量
 */
public interface PageConstants {
    /**
     * 默认分页条数
     */
    int DEFAULT_PAGE_SIZE = 10;
    /**
     * 默认文章排序
     */
    Sort DEFAULT_ARTICLE_SORT = new Sort(Sort.Direction.DESC, "commentCount", "likeCount",
            "visitCount", "publishDate");
    /**
     * 最近发布排序
     */
    Sort LATEST_ARTICLE_SORT = new Sort(Sort.Direction.DESC, "publishDate","commentCount", "likeCount","visitCount");
    /**
     * 最受欢迎排序
     */
    Sort HOTTEST_ARTICLE_SORT = new Sort(Sort.Direction.DESC, "likeCount","commentCount","visitCount", "publishDate");
    /**
     * 文章排序数组
     */
    Sort[] ARTICLE_SORT_ARRAY = {DEFAULT_ARTICLE_SORT, LATEST_ARTICLE_SORT, HOTTEST_ARTICLE_SORT};
    /**
     * 默认文章分页
     */
    Pageable DEFAULT_ARTICLE_PAGE = PageRequest.of(0, DEFAULT_PAGE_SIZE, DEFAULT_ARTICLE_SORT);

    /**
     * 默认评论分页
     */
    Pageable DEFAULT_COMMENT_PAGE = PageRequest.of(0, DEFAULT_PAGE_SIZE, Sort.Direction.DESC, "date");


}
