package org.webframe.common;

import java.util.List;

import org.webframe.tools.collects.BeansUtil;

/**
 * 查询辅助拼装类
 * @author 张永葑
 *
 */
public class QueryAssister {
	
	/** 语句 */
	private StringBuilder stringBuilder = BeansUtil.newStringBuilder();
	
	/** 参数 */
	private List<Object> params = BeansUtil.newArrayList();
	
	public QueryAssister() {}
	
	public QueryAssister(String query, Object... params) {
		addQuery(query);
		setParams1(params);
	}
	
	public QueryAssister(String query, List<Object> params) {
		addQuery(query);
		this.params = params;
	}

	/**
	 * 追加语句
	 * @param string
	 * @return QueryAssister
	 */
	public QueryAssister addQuery(String query) {
		stringBuilder.append(query).append(" ");
		return this;
	}
	
	/**
	 * 添加参数
	 * @param params
	 * @return QueryAssister
	 */
	public QueryAssister setParams(Object... params) {
		setParams1(params);
		return this;
	}
	
	private void setParams1(Object[] params) {
		for (Object object : params) {
			this.params.add(object);
		}
	}
	
	/**
	 * 得到结果语句
	 * @return String
	 */
	public String getResultQuery() {
		return stringBuilder.toString();
	}
	
	/**
	 * 得到参数
	 * @return Object[]
	 */
	public List<Object> getParams() {
		return params;
	}
	
	/**
	 * 清除现有语句
	 * @return QueryAssister
	 */
	public QueryAssister clearQuery() {
		stringBuilder.delete(0, stringBuilder.length());
		return this;
	}
	
	/**
	 * 清除现有参数
	 * @return QueryAssister
	 */
	public QueryAssister clearParams() {
		params.clear();
		return this;
	}
	
	/**
	 * 清除现有语句和参数
	 * @return QueryAssister
	 */
	public QueryAssister clearAll() {
		clearQuery();
		clearParams();
		return this;
	}
}
