package org.webframe.dao.Mapping;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class ScalarHandler {
	
	public SQLQuery addScalar(Session session, String sql) {
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		List<String> scalars = takeOutScalar(sql);
		for (String string : scalars) {
			sqlQuery.addScalar(string);
		}
		return sqlQuery;
	}
	
	public List<String> takeOutScalar(String sql) {
		String result  = sql.substring(sql.indexOf("select")+6,sql.indexOf("from"));
		String[] results = result.split(",");
		List<String> list = new ArrayList<String>();
		for (String string : results) {
			string = string.trim();
			if(string.contains(" ")) {
				list.add(string.substring(string.lastIndexOf(" ")+1).replaceAll("'", ""));
			} else {
				list.add(string.substring(string.lastIndexOf(".")+1));
			}
		}
		return list;
	}
}
