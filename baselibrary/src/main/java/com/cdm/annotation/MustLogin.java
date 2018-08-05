package com.cdm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*******************************************************************
 * @文件名称: MustLogin
 * @作者 :
 * @文件描述: 定义注解标示当前模块必须登录
 * @创建时间: 2016/9/14
 * ******************************************************************
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MustLogin {
    boolean value() default false;
}
