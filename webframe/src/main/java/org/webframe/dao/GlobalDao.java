package org.webframe.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.webframe.tools.collects.BeansUtil;
import org.webframe.tools.reflect.MergeObject;

/**
 * 通用的操作类 dao
 * @author 张永峰
 * 
 */
@SuppressWarnings("unchecked")
public class GlobalDao extends SqlDao{
	
	public GlobalDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
	 * 获取session
	 * @return
	 */
	public Session getSession() {
		return super.getSession();
	}
	
	/**
	 * 添加一个对象
	 * @param obj 映射模型
	 * @return 插入的对象的主键
	 */
	public Serializable addObject(Object object) {
		return getSession().save(object);
	}

	/**
	 * 删除一个对象，对象中只包含对象的主键即可
	 * @param obj 映射模型
	 */
	public void deleteObject(Object object) {
		getSession().delete(object);
	}
	
	/**
	 * 更新一个对象,对对象中的全部属性进行修改
	 * 此更新方法如果对象属性为null，则数据库对应字段修改为null
	 * @param obj
	 */
	public void updateObject(Object object) {
		getSession().update(object);
	}
	
	/**
	 * 动态更新一个对象，此更新方法只更新对象中属性不为null的值，对象中的id为必须值<br>
	 * 如果hibernate设置了动态更新，则只发出不为null的属性的sql，如果不设置动态更新则发出修改全部属性的sql,但为null的属性不会得到修改<br>
	 * 动态更新参考(在entity的类上注解)：<br>
	 * hibernate 3.x：@org.hibernate.annotations.Entity(dynamicUpdate=true)<br>
	 * hibernate 4.x：@dynamicUpdate(true)
	 * @param obj 映射模型
	 * @return 新的完整对象
	 */
	public <T> T updateForDynamicObject(Object object) {
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
	 * 注意:如果返回普通类型请使用Serializable接收返回值
	 * @param hql hql语句   参数用?(英文状态下)表示
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return 
	 */
	public <T> T findUnique(String hql, Object[] params) {
		return (T) getQuery(hql, params).uniqueResult();
	}
	
	/**
	 * 查询符合条件的对象列表
	 * @param hql hql语句   参数用 ? (英文状态下)表示
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> findList(String hql, Object[] params) {
		List<T> list = getQuery(hql, params).list();
		return (List<T>) (list == null? BeansUtil.newArrayList():list);
	}
	
	/**
	 * 分页查询
	 * @param hql hql语句   参数用 ? (英文状态下)表示
	 * @param page 当前页
	 * @param size 每页条数
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return 分页列表(返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> findPage(String hql, int page, int size, Object[] params) {
		List<T> list = getQuery(hql, params).setFirstResult((page-1)*size).setMaxResults(size).list();
		return (List<T>) (list == null? BeansUtil.newArrayList():list);
	}
	
	/**
	 * 分页查询条数
	 * @param hql
	 * @param values
	 * @return
	 */
	public Long getDataTotal(String hql, Object[] values) { 
		QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql,Collections.EMPTY_MAP, (SessionFactoryImplementor) super.getSessionFactory()); 
		queryTranslator.compile(Collections.EMPTY_MAP, false); 
		String tempSQL = queryTranslator.getSQLString(); 
		String countSQL = "select count(*) from (" + tempSQL + ") tmp_count_t"; 
		Query query = this.getSession().createSQLQuery(countSQL);
		if(values != null) {
			for (int i = 0; i < values.length; i++) { 
				query.setParameter(i, values[i]);
			}
		}
		BigInteger count = (BigInteger) query.uniqueResult(); 
		return count.longValue();
	}
	
	/**
	 * 设置hql参数
	 * @param hql
	 * @param params
	 * @return
	 */
	private Query getQuery(String hql, Object[] params) {
		Query query = getSession().createQuery(hql);
		if(params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query;
	}
}
