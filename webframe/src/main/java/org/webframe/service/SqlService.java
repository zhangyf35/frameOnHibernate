package org.webframe.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webframe.common.Pager;
import org.webframe.common.QuerySir;
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
	 * @param sqlQuerySir 查询辅助类
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public List<Map<String, Serializable>> listMapBySql(QuerySir sqlQuerySir){
		return globalDao.findListMapBysql(sqlQuerySir);
	}
	
	/**
	 * sql查询指定条数的map列表<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)<br>
	 * 如:数据字段为user_id或USER_ID, 都会被转换成userId
	 * @param sqlQuerySir 查询辅助类
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public List<Map<String, Serializable>> listMapByAmountSql(QuerySir sqlQuerySir, int count){
		return globalDao.findListMapByCount(sqlQuerySir, count);
	}
	
	/**
	 * 查询分页
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)<br>
	 * 如:数据字段为user_id或USER_ID, 都会被转换成userId
	 * @param sqlQuerySir 查询辅助类
	 * @param pager 分页对象
	 * @return 分页对象;该对象中包含当前页，每页条数，总条数，总页数，和查询出来的分页数据<br>
	 * (如果使用了该方法Pager中的list不可能为null,所以上层程序不用判断null)
	 */
	public void pageInjectionBySql(QuerySir sqlQuerySir, Pager<Map<String,Serializable>> pager) {
		globalDao.pageInjectionBySql(sqlQuerySir, pager);
	}
	
	/**
	 * sql查询单一对象<br>
	 * (map中的key为格式化后的key,该key为查询出的结果字段经过驼峰命名格式化后的key)<br>
	 * 如:数据字段为user_id或USER_ID, 都会被转换成userId
	 * @param sqlQuerySir 查询辅助类
	 * @return (返回的Map不可能为null,所以上层程序不用判断null)
	 */
	public Map<String, Serializable> uniqueMapBySql(QuerySir sqlQuerySir) {
		return globalDao.findMapBySql(sqlQuerySir);
	}
	
	/**
	 * sql查询对象列表
	 * @param cla 需要查询的对象的class
	 * @param sqlQuerySir 查询辅助类
	 * @return (返回的list不可能为null,所以上层程序不用判断null)
	 */
	public <T> List<T> listBysql(Class<T> cla, QuerySir sqlQuerySir) {
		return globalDao.findListBySql(cla, sqlQuerySir);
	}
	
	/**
	 * sql查询单一对象 
	 * @param cla 需要查询的对象的class
	 * @param sqlQuerySir 查询辅助类
	 * @return 唯一的entity对象
	 */
	public <T> T uniqueBysql(Class<T> cla, QuerySir sqlQuerySir) {
		return globalDao.findUniqueBySql(cla, sqlQuerySir);
	}
}
