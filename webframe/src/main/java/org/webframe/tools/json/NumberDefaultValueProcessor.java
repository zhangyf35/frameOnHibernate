package org.webframe.tools.json;

import net.sf.json.JSONNull;
import net.sf.json.processors.DefaultValueProcessor;

public class NumberDefaultValueProcessor implements DefaultValueProcessor{
	@SuppressWarnings("rawtypes")
	public Object getDefaultValue(Class arg0) {
		return JSONNull.getInstance();
	}
}
