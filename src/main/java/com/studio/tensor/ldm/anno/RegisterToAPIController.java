package com.studio.tensor.ldm.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//ElementType.TYPE用于标识类、接口(包括内注自身)、枚举  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface RegisterToAPIController
{
}
