package org.webframe.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webframe.common.Pager;
import org.webframe.common.QuerySir;

/**
 * 全局service，包含所有基本操作<br>
 * 在spring-config.xml中配置该bean
 * @author 张永葑
 */
@Service
@Transactional
public class GlobalService extends SqlService{
	
	/**
	 * 添加一个对象
	 * @param object 需要添加的对象
	 * @return 返回插入新对象的主键
	 */
	public <T> Serializable save(T object) {
		return globalDao.insert(object);
	}

	/**
	 * 删除一个对象，对象中只包含对象的主键即可
	 * @param object 映射模型
	 */
	public <T> void remove(T object) {
		globalDao.delete(object);
	}

	/**
	 * 更新一个对象，此更新方法只更新对象中属性不为null的值，对象中的id为必须值<br>
	 * 如果hibernate设置了动态更新，则只发出不为null的属性的sql，如果不设置动态更新则发出修改全部属性的sql<br>
	 * 为了提高插入效率建议开启动态更新<br>
	 * 动态更新参考(在entity的类上注解)：<br>
	 * hibernate 3.x：@org.hibernate.annotations.Entity(dynamicUpdate=true)<br>
	 * hibernate 4.x：@dynamicUpdate(true)
	 * @param object 映射模型
	 * @return 新的完整对象
	 */
	public <T> T modify(T object) {
		return globalDao.update(object);
	}
	
	/**
	 * 更新一个对象,对对象中的全部属性进行修改，对象中的id为必须值<br>
	 * 此更新方法如果对象属性为null，则数据库对应字段修改为null
	 * @param object
	 */
	public void modifyForNull(Object object) {
		globalDao.updateForNull(object);
	}
	
	/**
	 * 根据 主键 查询某个对象
	 * @param cla 要查新的映射模型的Class
	 * @param id 要查新的映射模型的主键
	 * @return 查询的结果对象
	 */
	public <T> T load(Class<T> cla, Serializable id) {
		return globalDao.findObjectById(cla, id);
	}

	/**
	 * 根据条件查询唯一结果（可以是查询的唯一对象，也可以是查询的唯一其他结果，如：总条数）<br>
	 * 注意:如果返回普通类型请使用Serializable接收返回值
	 * @param hqlQuerySir 查询辅助类
	 * @return 任意对象，查询的什么返回什么
	 */
	public <T> T unique(QuerySir hqlQuerySir) {
		return globalDao.findUnique(hqlQuerySir);
	}
	
	/**
	 * 执行一条增删改hql语句
	 * @param hqlQuerySir
	 * @return
	 */
	public int execute(QuerySir hqlQuerySir) {
		return globalDao.executeUpdate(hqlQuerySir);
	}
	
	/**
	 * 查找某个对象全部列表
	 * @param cla 要查询的对象的class
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> findAll(Class<T> cla) {
		QuerySir hqlQuerySir = new QuerySir();
		hqlQuerySir.addQuery("from " + cla.getSimpleName());
		return globalDao.findList(hqlQuerySir);
	}
	
	/**
	 * 用hql对象查询的对象集合
	 * @param hqlQuerySir 查询辅助类
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> list(QuerySir hqlQuerySir){
		return globalDao.findList(hqlQuerySir);
	}
	
	/**
	 * 用hql对象查询返回指定条数的对象集合
	 * @param hqlQuerySir 查询辅助类
	 * @param count 指定查询条数(如果指定条数大于数据库存储的数据条数,查出数据以数据库条数为准)
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> listByAmount(QuerySir hqlQuerySir, int count){
		return globalDao.findListByAmount(hqlQuerySir, count);
	}
	
	/**
	 * 将分页数据注入分页对象
	 * @param hqlQuerySir 查询辅助类
	 * @param pager 分页对象
	 * @return 分页对象;该对象中包含当前页，每页条数，总条数，总页数，和查询出来的分页数据<br>
	 * (如果使用了该方法Pager中的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> void pageInjection(QuerySir hqlQuerySir, Pager<T> pager) {
		globalDao.pageInjection(hqlQuerySir, pager);
	}
	
}
