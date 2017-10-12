package com.m.d.common.util.msgutil;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Listener {

	public static final int DEFAULT_WEIGHT = 10;

	int weight() default DEFAULT_WEIGHT;

	boolean async() default true;

	String[] action();
}