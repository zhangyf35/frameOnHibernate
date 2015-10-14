package org.webframe.tools.json.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;



@Target({})
@Retention(RUNTIME)
public @interface Filter {
	
	Class<?> clazz();
	
	String[] fields();
	
}
