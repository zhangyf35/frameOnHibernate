package org.webframe.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.webframe.Exception.NotUniqueException;
import org.webframe.db.MapWork;
import org.webframe.tools.collects.BeansUtil;
import org.webframe.tools.systemUtil.JudgeUtil;

@SuppressWarnings("unchecked")
public class SqlDao {
	
	private SessionFactory sessionFactory;
	
	public SqlDao(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}
	
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * sql查询map列表<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param parames sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
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
	 * @param parames sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
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
	 * @param page 当前页
	 * @param size 每页条数
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return 分页列表(返回的list不可能为null,所以上层程序不用判断null)
	 */
	public List<Map<String, Serializable>> findPageCamelKeyBySql(String sql, int page, int size, Object[] params) {
		StringBuffer sb = new StringBuffer();
		sb.append(sql).append(" limit ").append((page-1)*size).append(",").append(size);
		MapWork work = new MapWork(sb.toString(), params);
		getSession().doWork(work);
		return work.getMaps();
	}
	
	/**
	 * sql查询对象列表
	 * @param cla 需要查询的对象的class
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param parames sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
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
	 * @param parames sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return
	 */
	public <T> T findUniqueBySql(Class<T> cla, String sql, Object[] params) {
		return (T) getSqlQuery(sql, params).addEntity(cla).uniqueResult();
	} 
	
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
