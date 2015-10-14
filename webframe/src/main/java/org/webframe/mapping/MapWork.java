package org.webframe.mapping;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.jdbc.Work;

public class MapWork implements Work{
	
	protected String sql = "";
	protected List<Map<String, Serializable>> list = null;
	protected Object[] params = null;
	
	public MapWork(String sql, Object[] params) {
		this.sql = sql;
		this.params = params;
	}
	
	public void execute(Connection connection) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(sql);
		setParams(ps, params);
		ResultSet rs =  ps.executeQuery();
		this.list = new DataReader().getMaps(rs);
	}
	
	public List<Map<String, Serializable>> getMaps() {
		return this.list;
	}
	
	public void setParams(PreparedStatement ps, Object[] params) throws SQLException {
		if(params != null) {
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i+1, params[i]);
			}
		}
	}
}