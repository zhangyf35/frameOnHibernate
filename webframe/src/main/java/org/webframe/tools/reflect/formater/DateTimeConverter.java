package org.webframe.tools.reflect.formater;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.beanutils.Converter;

/**
 * 注册时间
 * @author 张永葑
 *
 */
public class DateTimeConverter implements Converter {
	private final String DATE = "yyyy-MM-dd";
	private final String DATETIME = "yyyy-MM-dd HH:mm:ss";
	private final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

	@SuppressWarnings("rawtypes")
	public Object convert(Class type, Object value) {
		return toDate(type, value);
	}

	@SuppressWarnings("rawtypes")
	public Object toDate(Class type, Object value) {
		if (value == null || "".equals(value))
			return null;
		if (value instanceof String) {
			String dateValue = value.toString().trim();
			int length = dateValue.length();
			if (type.equals(java.util.Date.class)) {
				try {
					DateFormat formatter = null;
					if (length <= 10) {
						formatter = new SimpleDateFormat(DATE,
								new DateFormatSymbols(Locale.CHINA));
						return formatter.parse(dateValue);
					}
					if (length <= 19) {
						formatter = new SimpleDateFormat(DATETIME,
								new DateFormatSymbols(Locale.CHINA));
						return formatter.parse(dateValue);
					}
					if (length <= 23) {
						formatter = new SimpleDateFormat(TIMESTAMP,
								new DateFormatSymbols(Locale.CHINA));
						return formatter.parse(dateValue);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
}