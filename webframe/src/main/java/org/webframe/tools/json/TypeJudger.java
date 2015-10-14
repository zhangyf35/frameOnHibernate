package org.webframe.tools.json;

import java.lang.reflect.Field;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class TypeJudger {
	
	public static Boolean isBasicClass (Object obj) {
		if( obj instanceof Byte || obj instanceof Short ||
			obj instanceof Integer || obj instanceof Long ||
			obj instanceof Float || obj instanceof Double ||
			obj instanceof Character || obj instanceof Boolean ){
			return true;
		}
		return false;
	}
	
	public static Boolean isAttentionAnnotationField(Class<?> cla, String fieldName) {
		try {
			Field field = cla.getDeclaredField(fieldName);
			if ( field.getAnnotation(OneToOne.class) != null  
                 || field.getAnnotation(OneToMany.class) != null  
                 || field.getAnnotation(ManyToOne.class) != null  
                 || field.getAnnotation(ManyToMany.class) != null) {
				return true;
            }
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
