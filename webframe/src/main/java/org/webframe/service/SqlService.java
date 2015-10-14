package org.webframe.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webframe.common.Pager;
import org.webframe.dao.GlobalDao;
import org.webframe.tools.collects.BeansUtil;

@Service
@Transactional
public class SqlService {
	
	@Resource
	protected GlobalDao globalDao;
	
	/**
	 * sql查询map列表<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)<br>
	 * 如:数据字段为user_id或USER_ID, 都会被转换成userId
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param parames sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public List<Map<String, Serializable>> listMapBysql(String sql, Object[] params){
		return globalDao.findListMapBysql(sql, params);
	}
	
	/**
	 * 查询分页
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)<br>
	 * 如:数据字段为user_id或USER_ID, 都会被转换成userId
	 * @param hql
	 * @param page
	 * @param size
	 * @param hqlParams
	 * @return
	 */
	public Pager<Map<String,Serializable>> pageBySql(String sql, int page, int size, Object[] params) {
		Pager<Map<String,Serializable>> pager = new Pager<Map<String,Serializable>>(page, size);
		String newSql = "select count(*) as count from ("+sql+") as newTable";
		long total = Long.parseLong(String.valueOf(globalDao.findMapCamelKeyBySql(newSql, null).get("count"))) ;
		pager.setTotal(total);
		if(total == 0){
			List<Map<String,Serializable>> listMap = BeansUtil.newArrayList();
			pager.setRows(listMap);
			return pager;
		}
		List<Map<String,Serializable>> Objects = (List<Map<String,Serializable>>) globalDao
				.findPageCamelKeyBySql(sql, page, size, params);
		pager.setRows(Objects);
		return pager;
	}
	
	/**
	 * sql查询单一对象<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)<br>
	 * 如:数据字段为user_id或USER_ID, 都会被转换成userId
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param parames sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的Map不可能为null,所以上层程序不用判断null)
	 */
	public Map<String, Serializable> mapBySql(String sql, Object[] params) {
		return globalDao.findMapCamelKeyBySql(sql, params);
	}
	
	/**
	 * sql查询对象列表
	 * @param cla 需要查询的对象的class
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param parames sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> listBysql(Class<T> cla, String sql, Object[] params) {
		return globalDao.findListBySql(cla, sql, params);
	}
	
	/**
	 * sql查询单一对象 
	 * @param cla 需要查询的对象的class
	 * @param sql sql语句   参数用 ? (英文状态下)表示
	 * @param parames sql语句中的参数，参数顺序为hql中的?顺序,没有参数则不传如此参数!
	 * @return
	 */
	public <T> T uniqueBysql(Class<T> cla, String sql, Object[] params) {
		return globalDao.findUniqueBySql(cla, sql, params);
	}
}
