package org.webframe.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.webframe.Exception.NotUniqueException;
import org.webframe.common.Pager;
import org.webframe.mapping.MapWork;
import org.webframe.tools.collects.BeansUtil;
import org.webframe.tools.systemUtil.JudgeUtil;

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
	 * 设置sessionFactory
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
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * sql查询map列表<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public List<Map<String, Serializable>> findListMapBysql(String sql, Object[] params){
		MapWork work = new MapWork(sql, params);
		getSession().doWork(work);
		return work.getMaps();
	}
	
	/**
	 * sql查询单一对象<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的Map不可能为null,所以上层程序不用判断null)
	 */
	public Map<String, Serializable> findMapCamelKeyBySql(String sql, Object[] params) {
		List<Map<String, Serializable>> list = findListMapBysql(sql, params);
		if (! JudgeUtil.listIsNullAndEmpty(list)) {
			if (list.size() > 1) {
				throw new NotUniqueException("query did not return a unique result: " +list.size());
			}
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * sql分页查询
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @param page 当前页
	 * @param size 每页条数
	 * @return 分页对象;该对象中包含当前页，每页条数，总条数，总页数，和查询出来的分页数据
	 */
	public Pager<Map<String,Serializable>> findPageCamelKeyBySql(String sql, Object[] params, int page, int size) {
		Pager<Map<String,Serializable>> pager = new Pager<Map<String,Serializable>>(page, size);
		String newSql = "select count(*) as count from ("+sql+") as newTable";
		long total = Long.parseLong(String.valueOf(findMapCamelKeyBySql(newSql, null).get("count"))) ;
		pager.setTotal(total);
		if(total == 0){
			return pager;
		}
		sql += "limit "+(page-1)*size + ","+ size;
		MapWork work = new MapWork(sql.toString(), params);
		getSession().doWork(work);
		pager.setRows(work.getMaps());
		return pager;
	}
	
	/**
	 * sql查询对象列表
	 * @param cla 需要查询的对象的class
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> findListBySql(Class<T> cla, String sql, Object[] params) {
		List<T> list = getSqlQuery(sql, params).addEntity(cla).list();
		return (List<T>) (list == null? BeansUtil.newArrayList():list);
	}
	
	/**
	 * sql查询单一对象 
	 * @param cla 需要查询的对象的class
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return entity的对象
	 */
	public <T> T findUniqueObjectBySql(Class<T> cla, String sql, Object[] params) {
		return (T) getSqlQuery(sql, params).addEntity(cla).uniqueResult();
	} 
	
	/**
	 * 获取SqlQuery,获取的同时将参数设置了,此方法不提供公用
	 * @param sql 
	 * @param params
	 * @return SQLQuery
	 */
	private SQLQuery getSqlQuery(String sql,Object[] params) {
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		if(params != null) {
			for (int i = 0; i < params.length; i++) {
				sqlQuery.setParameter(i, params[i]);
			}
		}
		return sqlQuery;
	}
}
