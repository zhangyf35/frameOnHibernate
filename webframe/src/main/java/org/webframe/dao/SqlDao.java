package org.webframe.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.webframe.common.Pager;
import org.webframe.common.QueryAssister;
import org.webframe.mapping.ListMapWork;
import org.webframe.mapping.MapWork;
import org.webframe.tools.collects.BeansUtil;

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
	 * sql查询单一对象<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的Map不可能为null,所以上层程序不用判断null)
	 */
	public Map<String, Serializable> findMapBySql(QueryAssister sqlQueryAssister) {
		MapWork work = new MapWork(sqlQueryAssister);
		getSession().doWork(work);
		return work.getMaps();
	}
	
	/**
	 * sql查询map列表<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public List<Map<String, Serializable>> findListMapBysql(QueryAssister sqlQueryAssister){
		ListMapWork work = new ListMapWork(sqlQueryAssister);
		getSession().doWork(work);
		return work.getMaps();
	}
	
	/**
	 * 查询指定条数的List<Map<String, Serializable>>
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @param count 查询的条数
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public List<Map<String, Serializable>> findListMapByCount(QueryAssister sqlQueryAssister, int count) {
		return findListMapLimit(sqlQueryAssister, 1, count);
	}
	
	/**
	 * 获取指定页，指定条数的listMap
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @param page 当前页
	 * @param size 当前条数
	 * @return List<Map<String, Serializable>>
	 */
	protected List<Map<String, Serializable>> findListMapLimit(QueryAssister sqlQueryAssister, int page, int size) {
		sqlQueryAssister.addQuery(" limit "+(page-1)*size+","+size);
		ListMapWork work = new ListMapWork(sqlQueryAssister);
		getSession().doWork(work);
		return work.getMaps();
	}
	
	/**
	 * sql分页查询
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @param pager 包含当前页和每页条数
	 * @return 分页对象;该对象中包含当前页，每页条数，总条数，总页数，和查询出来的分页数据
	 */
	public Pager<Map<String,Serializable>> findPageBySql(QueryAssister sqlQueryAssister, Pager<Map<String,Serializable>> pager) {
		String newSql = "select count(*) as count from ("+sqlQueryAssister.getResultQuery()+") as newTable";
		sqlQueryAssister.clearQuery();
		sqlQueryAssister.addQuery(newSql);
		long total = Long.parseLong(String.valueOf(findMapBySql(sqlQueryAssister).get("count"))) ;
		pager.setTotal(total);
		if(pager.getPage() > pager.getPageCount()) {
			pager.setPage(pager.getPageCount());
		}
		if(total == 0){
			return pager;
		}
		pager.setRows(findListMapLimit(sqlQueryAssister, pager.getPage(), pager.getSize()));
		return pager;
	}
	
	/**
	 * sql查询对象列表
	 * @param cla 需要查询的对象的class
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> findListBySql(Class<T> cla, QueryAssister sqlQueryAssister) {
		List<T> list = getSqlQuery(sqlQueryAssister).addEntity(cla).list();
		return (List<T>) (list == null? BeansUtil.newArrayList():list);
	}
	
	/**
	 * sql查询单一对象 
	 * @param cla 需要查询的对象的class
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return entity的对象
	 */
	public <T> T findUniqueObjectBySql(Class<T> cla, QueryAssister sqlQueryAssister) {
		return (T) getSqlQuery(sqlQueryAssister).addEntity(cla).uniqueResult();
	} 
	
	/**
	 * 获取SqlQuery,获取的同时将参数设置了,此方法不提供公用
	 * @param sql 
	 * @param params
	 * @return SQLQuery
	 */
	protected SQLQuery getSqlQuery(QueryAssister sqlQueryAssister) {
		String sql = sqlQueryAssister.getResultQuery();
		List<Object> params = sqlQueryAssister.getParams();
 		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		if(params != null) {
			for (int i = 0; i < params.size(); i++) {
				sqlQuery.setParameter(i, params.get(i));
			}
		}
		return sqlQuery;
	}
}
