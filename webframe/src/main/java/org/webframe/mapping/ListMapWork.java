package org.webframe.mapping;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.jdbc.Work;
import org.webframe.common.QueryAssister;

/**
 * hibernate jdbc work的实现类
 * @author 张永葑
 *
 */
public class ListMapWork implements Work{
	
	/**
	 * sql语句
	 */
	protected String sql = "";
	
	/**
	 * 转换后的数据
	 */
	protected List<Map<String, Serializable>> list = null;
	
	/**
	 * sql参数
	 */
	protected List<Object> params = null;
	
	/**
	 * 有参构造
	 * @param sql sql语句
	 * @param params sql参数
	 */
	public ListMapWork(QueryAssister queryAssister) {
		this.sql = queryAssister.getResultQuery();
		this.params = queryAssister.getParams();
	}
	
	/**
	 * 接口方法，获取数据库连接
	 */
	public void execute(Connection connection) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(sql);
		setParams(ps, params);
		ResultSet rs =  ps.executeQuery();
		this.list = new DataReader().getMaps(rs);
	}
	
	/**
	 * 获取转换后的list
	 * @return 转换后的list
	 */
	public List<Map<String, Serializable>> getMaps() {
		return this.list;
	}
	
	public void setParams(PreparedStatement ps, List<Object> params) throws SQLException {
		if(params != null) {
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
		}
	}
}