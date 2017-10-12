package com.m.d.common.util;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Table {
	String tableName();

	String primaryKey() default "";
}