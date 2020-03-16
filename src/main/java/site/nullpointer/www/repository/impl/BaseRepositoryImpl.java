package site.nullpointer.www.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import site.nullpointer.utils.BeanUtils;

@Repository
public class BaseRepositoryImpl {
	@PersistenceContext
	private EntityManager em;

	/**
	 * <p>
	 * 功能描述 : 获取分页列表
	 * <p>
	 * 详细说明 : 无
	 *
	 * @param sql
	 * @param hashmap
	 * @param pageable
	 * @return
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2017年12月28日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> beanPage(String sql, Map<String, Object> hashmap, Pageable pageable, T obj) {
		// 创建原生SQL查询QUERY实例
		Query queryCount = em.createNativeQuery("SELECT COUNT(1) AS COUNT FROM (" + sql + ")AS T");
		for (Map.Entry<String, Object> entry : hashmap.entrySet()) {
			queryCount.setParameter(entry.getKey(), entry.getValue());// System.out.println("key= " + entry.getUrl() + "
																		// and value= " + entry.getValue());
		}
		// 返回的是Map的话，用起来会清晰的多，可惜的是JPA的API中并没有提供这样的设置。其实JPA的底层实现都是支持返回Map对象的,特么的费劲~~
		queryCount.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// 执行查询，返回的是实体结果集
		List<Map<String, Object>> listCount = queryCount.getResultList();
		Object objCount = listCount.get(0);
		Map<String, Object> rowCount = (HashMap<String, Object>) objCount;
		int count = Integer.parseInt(rowCount.get("COUNT").toString());
		Query query = em.createNativeQuery(sql);
		for (Map.Entry<String, Object> entry : hashmap.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());// System.out.println("key= " + entry.getUrl() + " and
																	// value= " + entry.getValue());
		}
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		// 返回的是Map的话，用起来会清晰的多，可惜的是JPA的API中并没有提供这样的设置。其实JPA的底层实现都是支持返回Map对象的,特么的费劲~~
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// 执行查询，返回的是实体结果集
		List<Map> list = query.getResultList();
		// map to bean
		List<T> beanlist = BeanUtils.mapListToBeanList(obj, list,
				BeanUtils.LogicEqualLevel.IGNORE_CASE_AND_UNDERLINE_EQUAL);
		// 组装page对象
		Page<T> pageList = new PageImpl<T>(beanlist, pageable, count);
		// 关闭EntityManager
		em.close();
		return pageList;
	}

	/**
	 * <p>
	 * 功能描述 : 执行查询
	 * <p>
	 * 详细说明 : 无
	 *
	 * @param sql
	 * @param params
	 * @return
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年7月13日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Map> execute(String sql, Map<String, Object> params) {
		// 创建原生SQL查询QUERY实例
		Query queryCount = em.createNativeQuery(sql);
		for (Map.Entry<String, Object> param : params.entrySet()) {
			queryCount.setParameter(param.getKey(), param.getValue());
		}
		// 返回的是Map
		queryCount.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// 执行查询，返回的是实体结果集
		return queryCount.getResultList();
	}
}
