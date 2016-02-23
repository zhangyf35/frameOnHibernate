package org.webframe.common;

import java.util.List;

import org.webframe.tools.collects.BeansUtil;
import org.webframe.tools.systemUtil.StringUtil;

/**
 * 查询辅助拼装类
 * @author 张永葑
 *
 */
public class QuerySir {
	
	/** 语句 */
	private String resultQuery = "";
	
	/** 参数 */
	private List<Object> params = BeansUtil.newArrayList();
	
	public QuerySir() {}
	
	public QuerySir(String query, Object... params) {
		addQuery(query);
		setParams1(params);
	}

	/**
	 * 追加语句
	 * @param string
	 * @return QuerySir
	 */
	public QuerySir addQuery(String query) {
		StringUtil.join(resultQuery, query, " ");
		return this;
	}
	
	/**
	 * 添加参数
	 * @param params
	 * @return QuerySir
	 */
	public QuerySir setParams(Object... params) {
		return setParams1(params);
	}
	
	private QuerySir setParams1(Object[] params) {
		for (Object object : params) {
			this.params.add(object);
		}
		return this;
	}
	
	/**
	 * 得到结果语句
	 * @return String
	 */
	public String getResultQuery() {
		return resultQuery;
	}
	
	/**
	 * 得到参数
	 * @return List<Object>
	 */
	public List<Object> getParams() {
		return params;
	}
	
	/**
	 * 清除现有语句
	 * @return QuerySir
	 */
	public QuerySir clearQuery() {
		resultQuery = "";
		return this;
	}
	
	/**
	 * 清除现有参数
	 * @return QuerySir
	 */
	public QuerySir clearParams() {
		params.clear();
		return this;
	}
	
	/**
	 * 清除现有语句和参数
	 * @return QuerySir
	 */
	public QuerySir clearAll() {
		clearQuery();
		clearParams();
		return this;
	}
}
