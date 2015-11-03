package org.webframe.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.webframe.common.Pager;
import org.webframe.common.QuerySir;
import org.webframe.mapping.ListMapWork;
import org.webframe.mapping.MapWork;

/**
 * sql查询dao<br>
 * @author 张永葑
 */
@SuppressWarnings("unchecked")
public class SqlDao {
	
	/**
	 * hibernate的SessionFactory
	 */
	private SessionFactory sessionFactory;
	
	/**
	 * 构造传入sessionFactory
	 * @param sessionFactory hibernate的SessionFactory
	 */
	public SqlDao(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}
	
	/**
	 * 获取sessionFactory
	 * @return hibernate的SessionFactory
	 */
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * 获取session,得到的是CurrentSession
	 * @return CurrentSession
	 */
	protected Session getSession() {
		return getSessionFactory().getCurrentSession();
	}
	
	/**
	 * sql查询单一对象<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)
	 * @param hqlQuerySir 查询辅助类
	 * @return (返回的Map不可能为null,所以上层程序不用判断null)
	 */
	public Map<String, Serializable> findMapBySql(QuerySir sqlQuerySir) {
		MapWork work = new MapWork(sqlQuerySir);
		getSession().doWork(work);
		return work.getMaps();
	}
	
	/**
	 * sql查询map列表<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)
	 * @param hqlQuerySir 查询辅助类
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public List<Map<String, Serializable>> findListMapBysql(QuerySir sqlQuerySir){
		ListMapWork work = new ListMapWork(sqlQuerySir);
		getSession().doWork(work);
		return work.getMaps();
	}
	
	/**
	 * 查询指定条数的List<Map<String, Serializable>>
	 * @param hqlQuerySir 查询辅助类
	 * @param count 查询的条数
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public List<Map<String, Serializable>> findListMapByCount(QuerySir sqlQuerySir, int count) {
		return findListMapLimit(sqlQuerySir, 1, count);
	}
	
	/**
	 * 获取指定页，指定条数的listMap
	 * @param hqlQuerySir 查询辅助类
	 * @param page 当前页
	 * @param size 当前条数
	 * @return List<Map<String, Serializable>>
	 */
	protected List<Map<String, Serializable>> findListMapLimit(QuerySir sqlQuerySir, int page, int size) {
		sqlQuerySir.addQuery(" limit "+(page-1)*size+","+size);
		ListMapWork work = new ListMapWork(sqlQuerySir);
		getSession().doWork(work);
		return work.getMaps();
	}
	
	/**
	 * sql分页查询
	 * @param hqlQuerySir 查询辅助类
	 * @param pager 包含当前页和每页条数
	 * @return 分页对象;该对象中包含当前页，每页条数，总条数，总页数，和查询出来的分页数据
	 */
	public void pageInjectionBySql(QuerySir sqlQuerySir, Pager<Map<String,Serializable>> pager) {
		String newSql = "select count(*) as count from ("+sqlQuerySir.getResultQuery()+") as newTable";
		sqlQuerySir.clearQuery().addQuery(newSql);
		long total = Long.parseLong(String.valueOf(findMapBySql(sqlQuerySir).get("count"))) ;
		pager.setTotal(total);
		if(pager.getPage() > pager.getPageCount())
			pager.setPage(pager.getPageCount());
		if(total == 0)
			return ;
		pager.setRows(findListMapLimit(sqlQuerySir, pager.getPage(), pager.getSize()));
	}
	
	/**
	 * sql查询对象列表
	 * @param cla 需要查询的对象的class
	 * @param hqlQuerySir 查询辅助类
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> findListBySql(Class<T> cla, QuerySir sqlQuerySir) {
		return getSqlQuery(sqlQuerySir).addEntity(cla).list();
	}
	
	/**
	 * sql查询单一对象 
	 * @param cla 需要查询的对象的class
	 * @param hqlQuerySir 查询辅助类
	 * @return entity的对象
	 */
	public <T> T findUniqueBySql(Class<T> cla, QuerySir sqlQuerySir) {
		return (T) getSqlQuery(sqlQuerySir).addEntity(cla).uniqueResult();
	} 
	
	/**
	 * 获取SqlQuery,获取的同时将参数设置了,此方法不提供公用
	 * @param hqlQuerySir 查询辅助类
	 * @return SQLQuery
	 */
	protected SQLQuery getSqlQuery(QuerySir sqlQuerySir) {
		String sql = sqlQuerySir.getResultQuery();
		List<Object> params = sqlQuerySir.getParams();
 		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		if(params != null) {
			for (int i = 0; i < params.size(); i++) {
				sqlQuery.setParameter(i, params.get(i));
			}
		}
		return sqlQuery;
	}
}
