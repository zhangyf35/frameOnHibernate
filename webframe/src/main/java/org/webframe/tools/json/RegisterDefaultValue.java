package org.webframe.tools.json;

import java.math.BigDecimal;

import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

@SuppressWarnings("rawtypes")
public class RegisterDefaultValue {
	
	private static Class[] clazzs 
		= {Integer.class,Long.class,BigDecimal.class,Short.class,Double.class,Short.class,Byte.class};
	
	
	public static void registerDefaultValue(JsonConfig config) {
		for (Class clazz : clazzs) {
			config.registerDefaultValueProcessor(clazz, new DefaultValueProcessor() {
				public Object getDefaultValue(Class arg0) { return ""; }
			});
		}
	}
	
}
