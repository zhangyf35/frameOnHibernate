package org.webframe.tools.json.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 自动过滤对象中的懒加载字段
 * @author 张永葑
 *
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface JsonFilterAuto {}
