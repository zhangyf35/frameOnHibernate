package org.webframe.tools.json;

import net.sf.json.JSONNull;
import net.sf.json.processors.DefaultValueProcessor;

/**
 * 数字类型的默认值格式化
 * @author 张永葑
 *
 */
public class NumberDefaultValueProcessor implements DefaultValueProcessor{
	@SuppressWarnings("rawtypes")
	public Object getDefaultValue(Class arg0) {
		return JSONNull.getInstance();
	}
}
