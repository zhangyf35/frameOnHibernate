package org.webframe.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.webframe.common.Pager;
import org.webframe.common.QuerySir;
import org.webframe.tools.reflect.MergeObject;

/**
 * 通用的操作类 dao<br>
 * 在spring-config.xml中配置该bean时sessonFactory使用构造注入
 * @author 张永葑
 */
@SuppressWarnings("unchecked")
public class GlobalDao extends SqlDao{
	
	/**
	 * 设置sessionFactory，为spring构造注入提供
	 * @param sessionFactory hibernate的SessionFactory
	 */
	public GlobalDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
	 * 添加一个对象
	 * @param object 映射模型
	 * @return 插入的对象的主键
	 */
	public Serializable insert(Object object) {
		return getSession().save(object);
	}

	/**
	 * 删除一个对象，对象中只包含对象的主键即可
	 * @param object 映射模型
	 */
	public void delete(Object object) {
		getSession().delete(object);
	}
	
	/**
	 * 更新一个对象,对对象中的全部属性进行修改
	 * 此更新方法如果对象属性为null，则数据库对应字段修改为null
	 * @param object
	 */
	public void updateForNull(Object object) {
		getSession().update(object);
	}
	
	/**
	 * 更新一个对象，此更新方法只更新对象中属性不为null的值，对象中的id为必须值<br>
	 * 如果hibernate设置了动态更新，则只发出修改不为null的属性的sql，如果不设置动态更新则发出修改全部属性的sql,但并不修改为null的属性的值<br>
	 * 动态更新参考(在entity的类上注解)：<br>
	 * hibernate 3.x：@org.hibernate.annotations.Entity(dynamicUpdate=true)<br>
	 * hibernate 4.x：@dynamicUpdate(true)
	 * @param object 映射模型
	 * @return 新的完整对象
	 */
	public <T> T update(Object object) {
		Object newObject = getSession().get(object.getClass(), MergeObject.getId(object));
		MergeObject.merge(newObject, object);
		getSession().update(newObject);
		return (T) newObject;
	}
	
	/**
	 * 根据 主键 查询某个对象
	 * @param cla 要查新的映射模型的Class
	 * @param id 要查新的映射模型的主键
	 * @return 查询的结果对象
	 */
	public <T> T findObjectById(Class<T> cla, Serializable id) {
		return (T) getSession().get(cla, id);
	}
	
	/**
	 * 根据条件查询唯一结果（可以是查询的唯一对象，也可以是查询的唯一其他结果，如：总条数）
	 * 注意:如果返回不是entity的类型请使用Serializable接收返回值，让兼容性更好
	 * @param hqlQuerySir 查询辅助类
	 * @return 任意对象，查询的什么返回什么
	 */
	public <T> T findUnique(QuerySir hqlQuerySir) {
		return (T) getQuery(hqlQuerySir).setFirstResult(0).setMaxResults(2).uniqueResult();
	}
	
	/**
	 * 执行一个增删改hql语句
	 * @param hqlQuerySir
	 * @return
	 */
	public int executeUpdate(QuerySir hqlQuerySir) {
		return getQuery(hqlQuerySir).executeUpdate();
	}
	
	/**
	 * 查询符合条件的对象列表
	 * @param hqlQuerySir 查询辅助类
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> findList(QuerySir hqlQuerySir) {
		return getQuery(hqlQuerySir).list();
	}
	
	/**
	 * 查询符合条件的对象列表
	 * @param hqlQuerySir 查询辅助类
	 * @param count 指定查询的条数
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> findListByAmount(QuerySir hqlQuerySir, int count) {
		return getQuery(hqlQuerySir).setFirstResult(0).setMaxResults(count).list();
	}
	
	/**
	 * 分页数据注入
	 * @param hqlQuerySir 查询辅助类
	 * @param pager 包含当前页和每页条数
	 * @return 分页对象;该对象中包含当前页，每页条数，总条数，总页数，和查询出来的分页数据<br>
	 * (如果使用了该方法Pager中的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> void pageInjection(QuerySir hqlQuerySir, Pager<T> pager) {
		long total = getDataTotal(hqlQuerySir);
		pager.setTotal(total);
		if(pager.getPage() > pager.getPageCount())
			pager.setPage(pager.getPageCount());
		if(total == 0)
			return;
		List<T> list = getQuery(hqlQuerySir)
				.setFirstResult((pager.getPage()-1) * pager.getSize())
				.setMaxResults(pager.getSize()).list();
		pager.setRows(list);
	}
	
	/**
	 * 查询分页查询条数
	 * @param hqlQuerySir 查询辅助类
	 * @return 总条数
	 */
	public Long getDataTotal(QuerySir hqlQuerySir) {
		String hql = hqlQuerySir.getResultQuery();
		QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql, 
				Collections.EMPTY_MAP, (SessionFactoryImplementor) super.getSessionFactory()); 
		queryTranslator.compile(Collections.EMPTY_MAP, false); 
		String tempSQL = queryTranslator.getSQLString(); 
		String countSQL = "select count(*) from (" + tempSQL + ") tmp_count_t";
		QuerySir sqlQuerySir = hqlQuerySir.clearQuery().addQuery(countSQL);
		BigInteger count = (BigInteger) super.getSqlQuery(sqlQuerySir).uniqueResult();
		hqlQuerySir.clearQuery().addQuery(hql);
		return count.longValue();
	}
	
	/**
	 * 获取Query对象并设置hql参数，此方法不开放
	 * @param hqlQuerySir 查询辅助类
	 * @return 获取Query对象
	 */
	private Query getQuery(QuerySir hqlQuerySir) {
		String hql = hqlQuerySir.getResultQuery();
		List<Object> params = hqlQuerySir.getParams();
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		return query;
	}
}
