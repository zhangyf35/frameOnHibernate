package org.webframe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.webframe.tools.systemUtil.StringUtil;

/**
 * 数据转换对象<br>
 * 将表的DDL小括号中的信息复制到你定义的文件中，然后转换成hibernate标准的entity
 * @author 张永葑
 *
 */
public class EntityBuilder {
	
	/**
	 * 转换方法
	 * @param filePath 你定义的文件路径，该文件中包含了表信息
	 * @throws IOException
	 */
	public static void produceEntity(String filePath) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = null;
		StringBuffer columns = new StringBuffer();
		StringBuffer getAndSetter = new StringBuffer()
			.append("/**\r\n * getter and setter\r\n */\r\n");
		
		while ((line = reader.readLine()) != null) {
			//System.out.println(line);
			int p1 = line.indexOf('`') + 1;
			int p2 = line.indexOf('`', p1);
			String field = line.substring(p1, p2);
			line = line.substring(p2 + 2);
			String type = null;
			if (line.startsWith("int")) {
				type = "Integer";
			} else if (line.startsWith("char")) {
				type = "String";
			} else if (line.startsWith("varchar")) {
				type = "String";
			} else if (line.startsWith("text")) {
				type = "String";
			} else if (line.startsWith("date")) {
				type = "Date";
			} else if (line.startsWith("datetime")) {
				type = "Date";
			} else if (line.startsWith("double")) {
				type = "Double";
			} else if (line.startsWith("decimal")) {
				type = "BigDecimal";
			}
			int p3 = line.indexOf("COMMENT '") + 9;
			int p4 = line.indexOf('\'', p3);
			String comment = null;
			try {
				comment = line.substring(p3, p4);
			} catch (Exception e) {}
			
			String camelField = StringUtil.toCamel(field);
			if(comment != null && !"".equals(comment)){
				columns.append("/** "+ comment + " */").append("\r\n");
			}
			columns.append("@Column(name = \""+field+"\")\r\n")
				.append("private "+ type + " " + camelField + ";\r\n\r\n");
			
			getAndSetter.append("public void set")
				.append(StringUtil.firstToUpperCase(camelField))
				.append("("+ type + " " + camelField +") {\r\n")
				.append("\tthis.").append(camelField).append(" = ").append(camelField+";\r\n")
				.append("}\r\n\r\n")
				.append("public "+ type +" get")
				.append(StringUtil.firstToUpperCase(camelField))
				.append("() {\r\n")
				.append("\treturn this.").append(camelField).append(";\r\n")
				.append("}\r\n\r\n");
		}
		System.out.print(columns.toString());
		System.out.print(getAndSetter.toString());
		reader.close();
	}
}
