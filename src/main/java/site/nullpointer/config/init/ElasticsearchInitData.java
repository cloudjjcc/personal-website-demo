package site.nullpointer.config.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import site.nullpointer.www.document.EsArticle;
import site.nullpointer.www.entity.ViewCommonArticle;
import site.nullpointer.www.repository.CommonViewArticleRepository;
import site.nullpointer.www.repository.EsArticleRepository;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>类路径 : site.nullpointer.config.init.ElasticsearchInitData
 * <p>类描述 : 将数据初始化到ElasticSearch中
 * <p>类详情 : 无
 * 
 * @author  王金灿
 * @version 1.0.0
 *<p>
 *--------------------------------------------------------------<br>
 * 修改履历：<br>
 *        <li> 2018年7月14日，wangjc，创建文件；<br>
 *--------------------------------------------------------------<br>
 *</p>
 */
@Component
public class ElasticsearchInitData implements CommandLineRunner {
	private static final int CONFIG_PAGE_SIZE = 5000;
	@Autowired
	private EsArticleRepository esArticleRepository;
	@Autowired
	private CommonViewArticleRepository commonViewArticleRepository;
	private final Logger logger=LoggerFactory.getLogger(ElasticsearchInitData.class);
	@Override
	public void run(String... args) {
		long start=System.currentTimeMillis();
		/**
		 * 清空es中的文章数据，避免重复添加
		 */
		esArticleRepository.deleteAll();
		logger.info("清空es中的文章数据共花费："+(System.currentTimeMillis()-start)/1000+" s");
		/**
		 * 每次从数据库中转移CONFIG_PAGE_SIZE条数据到es中
		 */
		int totalPages=0;
		long totalCount=0;
		int i=0;
		List<EsArticle> esArticles;
		Page<ViewCommonArticle> articles;
		do {
			Pageable pageable=PageRequest.of(i++, CONFIG_PAGE_SIZE);
			articles = commonViewArticleRepository.findAll(pageable);
			totalPages = articles.getTotalPages();
			totalCount=articles.getTotalElements();
			esArticles = new ArrayList<>();
			for (ViewCommonArticle commonViewArticle : articles) {
				esArticles.add(commonViewArticle.toEsArticle());
			}
			if(esArticles!=null&&esArticles.size()>0){
				esArticleRepository.saveAll(esArticles);
			}
			esArticles.clear();
		}while(i<totalPages);
		logger.info("初始化es共转移："+totalCount+"条数据");
		logger.info("初始化es中的文章数据共花费："+(System.currentTimeMillis()-start)/1000+" s");
		esArticles=null;
		articles=null;
		System.gc();
	}

}
