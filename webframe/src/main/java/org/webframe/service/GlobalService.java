package org.webframe.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webframe.common.Pager;
import org.webframe.tools.collects.BeansUtil;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class GlobalService extends SqlService{
	
	/**
	 * 添加一个对象
	 * @param obj 需要添加的对象
	 * @return 返回插入新对象的主键
	 */
	public <T> Serializable save(T obj) {
		return globalDao.addObject(obj);
	}

	/**
	 * 删除一个对象，对象中只包含对象的主键即可
	 * @param obj 映射模型
	 */
	public <T> void remove(T obj) {
		globalDao.deleteObject(obj);
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
	public <T> T modifyForDynamic(T obj) {
		return globalDao.updateForDynamicObject(obj);
	}
	
	/**
	 * 更新一个对象,对对象中的全部属性进行修改
	 * 此更新方法如果对象属性为null，则数据库对应字段修改为null
	 * @param obj
	 */
	public void modify(Object obj) {
		globalDao.updateObject(obj);
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
	 * @return 
	 */
	public <T> T unique(String hql, Object[] hqlParams) {
		return (T) globalDao.findUnique(hql, hqlParams);
	}
	
	/**
	 * 查找某个对象全部列表
	 * @param cla 要查询的对象的class
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> findAll(Class<T> cla) {
		return (List<T>) globalDao.findList("from " + cla.getSimpleName(),new Object[]{});
	}
	
	/**
	 * 用hql对象查询返回指定条数的对象集合
	 * @param hql hql语句   参数用 ? (英文状态下)表示
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> list(String hql, Object[] hqlParams){
		return (List<T>) globalDao.findList(hql, hqlParams);
	}
	
	/**
	 * 用hql对象查询返回指定条数的对象集合
	 * @param hql hql语句   参数用 ? (英文状态下)表示
	 * @param count 指定查询条数(如果指定条数大于数据库存储的数据条数,查出数据以数据库条数为准)
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> listLimitCount(int count, String hql, Object[] hqlParams){
		return (List<T>) globalDao.findPage(hql, 1, count, hqlParams);
	}
	
	/**
	 * 查询分页列表
	 * @param hql hql语句   参数用 ? (英文状态下)表示
	 * @param page 当前页
	 * @param size 每页条数
	 * @param params hql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return 查询的数据包含在 Pager中的rows属性中,为 list类型<br>
	 * 	(如果使用了该方法Pager中的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> Pager<T> page(String hql, int page, int size, Object[] hqlParams) {
		Pager<T> pager = new Pager<T>(page, size);
		long total = globalDao.getDataTotal(hql, hqlParams);
		pager.setTotal(total);
		if(total == 0){
			pager.setRows((List<T>) BeansUtil.newArrayList());
			return pager;
		}
		List<T> Objects = (List<T>) globalDao.findPage(hql, page, size, hqlParams);
		pager.setRows(Objects);
		return pager;
	}
	
}
