package org.webframe.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webframe.common.Pager;

/**
 * 全局service，包含所有基本操作<br>
 * 在spring-config.xml中配置该bean
 * @author 张永葑
 */
@Service
@Transactional
@SuppressWarnings("unchecked")
public class GlobalService extends SqlService{
	
	/**
	 * 添加一个对象
	 * @param object 需要添加的对象
	 * @return 返回插入新对象的主键
	 */
	public <T> Serializable save(T object) {
		return globalDao.addObject(object);
	}

	/**
	 * 删除一个对象，对象中只包含对象的主键即可
	 * @param object 映射模型
	 */
	public <T> void remove(T object) {
		globalDao.deleteObject(object);
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
		return globalDao.updateObject(object);
	}
	
	/**
	 * 更新一个对象,对对象中的全部属性进行修改，对象中的id为必须值<br>
	 * 此更新方法如果对象属性为null，则数据库对应字段修改为null
	 * @param object
	 */
	public void modifyForNull(Object object) {
		globalDao.updateObjectForNull(object);
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
	 * @param hql hql语句   参数用?(英文状态下)表示
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return 任意对象，查询的什么返回什么
	 */
	public <T> T unique(String hql, Object[] params) {
		return (T) globalDao.findUnique(hql, params);
	}
	
	/**
	 * 查找某个对象全部列表
	 * @param cla 要查询的对象的class
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> findAll(Class<T> cla) {
		return (List<T>) globalDao.findList("from " + cla.getSimpleName(), new Object[]{});
	}
	
	/**
	 * 用hql对象查询的对象集合
	 * @param hql hql语句   参数用 ? (英文状态下)表示
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> list(String hql, Object[] params){
		return (List<T>) globalDao.findList(hql, params);
	}
	
	/**
	 * 用hql对象查询返回指定条数的对象集合
	 * @param hql hql语句   参数用 ? (英文状态下)表示
	 * @param count 指定查询条数(如果指定条数大于数据库存储的数据条数,查出数据以数据库条数为准)
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> listLimitCount(String hql, Object[] params, int count){
		return (List<T>) globalDao.findPage(hql, params, 1, count);
	}
	
	/**
	 * 查询分页列表
	 * @param hql hql语句   参数用 ? (英文状态下)表示
	 * @param page 当前页
	 * @param size 每页条数
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return 分页对象;该对象中包含当前页，每页条数，总条数，总页数，和查询出来的分页数据<br>
	 * (如果使用了该方法Pager中的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> Pager<T> page(String hql, Object[] params, int page, int size) {
		return globalDao.findPage(hql, params, page, size);
	}
	
}
