package org.webframe.mapping;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
	public List<Map<String, Serializable>> getMaps(ResultSet rs) throws SQLException {
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
		if(list.size()>0){
			return list;
		}
		return null;
	}
}
