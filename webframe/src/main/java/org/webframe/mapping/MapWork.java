package org.webframe.mapping;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.jdbc.Work;
import org.webframe.common.QuerySir;

/**
 * hibernate jdbc work的实现类
 * @author 张永葑
 *
 */
public class MapWork implements Work{
	
	/**
	 * sql语句
	 */
	protected String sql = "";
	
	/**
	 * 转换后的数据
	 */
	protected Map<String, Serializable> map = null;
	
	/**
	 * sql参数
	 */
	protected List<Object> params = null;
	
	/**
	 * 有参构造
	 * @param sql sql语句
	 * @param params sql参数
	 */
	public MapWork(QuerySir sqlQuerySir) {
		this.sql = sqlQuerySir.getResultQuery();
		this.params = sqlQuerySir.getParams();
	}
	
	/**
	 * 接口方法，获取数据库连接
	 */
	public void execute(Connection connection) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(sql+ " limit 0,2");
		setParams(ps, params);
		ResultSet rs =  ps.executeQuery();
		this.map = new DataReader().getMap(rs);
	}
	
	/**
	 * 获取转换后的map
	 * @return 转换后的map
	 */
	public Map<String, Serializable> getMaps() {
		return this.map;
	}
	
	public void setParams(PreparedStatement ps, List<Object> params) throws SQLException {
		if(params != null) {
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
		}
	}
}