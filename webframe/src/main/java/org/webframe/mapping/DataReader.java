package org.webframe.mapping;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.webframe.Exception.NotUniqueException;
import org.webframe.tools.collects.BeansUtil;
import org.webframe.tools.systemUtil.StringUtil;

/**
 * 结果集数据读取
 * @author 张永葑
 *
 */
public class DataReader {
	
	/**
	 * 通过结果集获得list<Map> 集合
	 * @param rs 结果集
	 * @return 将rs转换后的List<Map<String, Serializable>>
	 * @throws SQLException
	 */
	public List<Map<String, Serializable>> getMaps(ResultSet rs) {
		try {
			List<Map<String, Serializable>> list = BeansUtil.newArrayList();
			ResultSetMetaData data = rs.getMetaData();
			while(rs.next()){
				Map<String, Serializable> map = BeansUtil.newHashMap();
				for (int j = 1; j <= data.getColumnCount(); j++) {
					String columnName = StringUtil.toCamel(data.getColumnLabel(j));
					map.put(columnName, (Serializable)rs.getObject(data.getColumnLabel(j)));
				}
				list.add(map);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 通过结果集获得Map行映射，查询结果不唯一将会抛出NotUniqueException异常
	 * @param rs 结果集
	 * @return 将rs转换后的Map<String, Serializable>
	 * @throws SQLException
	 */
	public Map<String, Serializable> getMap(ResultSet rs) {
		try {
			Map<String, Serializable> map = BeansUtil.newHashMap();
			ResultSetMetaData data = rs.getMetaData();
			int count = 1;
			while(rs.next()){
				if(count > 1) {
					throw new NotUniqueException("query did not return a unique result");
				}
				for (int j = 1; j <= data.getColumnCount(); j++) {
					String columnName = StringUtil.toCamel(data.getColumnLabel(j));
					map.put(columnName, (Serializable)rs.getObject(data.getColumnLabel(j)));
				}
				count ++;
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
