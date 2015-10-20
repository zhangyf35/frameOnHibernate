package org.webframe.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webframe.common.Pager;
import org.webframe.common.QueryAssister;
import org.webframe.dao.GlobalDao;

/**
 * sql查询service
 * @author 张永葑
 *
 */
@Service
@Transactional
public class SqlService {
	
	/**
	 * 全局dao
	 */
	@Resource
	protected GlobalDao globalDao;
	
	/**
	 * sql查询map列表<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)<br>
	 * 如:数据字段为user_id或USER_ID, 都会被转换成userId
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public List<Map<String, Serializable>> listMapBySql(QueryAssister sqlQueryAssister){
		return globalDao.findListMapBysql(sqlQueryAssister);
	}
	
	/**
	 * sql查询指定条数的map列表<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)<br>
	 * 如:数据字段为user_id或USER_ID, 都会被转换成userId
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public List<Map<String, Serializable>> listMapByCountSql(QueryAssister sqlQueryAssister, int count){
		return globalDao.findListMapByCount(sqlQueryAssister, count);
	}
	
	/**
	 * 查询分页
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)<br>
	 * 如:数据字段为user_id或USER_ID, 都会被转换成userId
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param page 当前页
	 * @param size 每页条数
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return 分页对象;该对象中包含当前页，每页条数，总条数，总页数，和查询出来的分页数据<br>
	 * (如果使用了该方法Pager中的list不可能为null,所以上层程序不用判断null)
	 */
	public Pager<Map<String,Serializable>> pageBySql(QueryAssister sqlQueryAssister, Pager<Map<String,Serializable>> pager) {
		return globalDao.findPageBySql(sqlQueryAssister, pager);
	}
	
	/**
	 * sql查询单一对象<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)<br>
	 * 如:数据字段为user_id或USER_ID, 都会被转换成userId
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的Map不可能为null,所以上层程序不用判断null)
	 */
	public Map<String, Serializable> uniqueMapBySql(QueryAssister sqlQueryAssister) {
		return globalDao.findMapBySql(sqlQueryAssister);
	}
	
	/**
	 * sql查询对象列表
	 * @param cla 需要查询的对象的class
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> listBysql(Class<T> cla, QueryAssister sqlQueryAssister) {
		return globalDao.findListBySql(cla, sqlQueryAssister);
	}
	
	/**
	 * sql查询单一对象 
	 * @param cla 需要查询的对象的class
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param params sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return 唯一的entity对象
	 */
	public <T> T uniqueObjectBysql(Class<T> cla, QueryAssister sqlQueryAssister) {
		return globalDao.findUniqueObjectBySql(cla, sqlQueryAssister);
	}
}
