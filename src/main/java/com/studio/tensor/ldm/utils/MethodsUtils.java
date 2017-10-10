package com.studio.tensor.ldm.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.studio.tensor.ldm.anno.RegisterToAPI;

public class MethodsUtils
{
	public static List<Method> getAnnoMethods(Class<?> scanClass, Class<? extends Annotation> annoClass)
	{
		List<Method> result = new ArrayList<>();
		Method[] methods = scanClass.getDeclaredMethods();
		for (Method m : methods)
		{
			if (m.isAnnotationPresent(annoClass))
			{
				result.add(m);
			}
		}
		return result;
	}
	
	public static List<Method> getClassesAnnoMethods(List<Class<?>> scanClasses, Class<? extends Annotation> annoClass)
	{
		List<Method> result = new ArrayList<>();
		
		for(Class<?> scanClass : scanClasses)
		{
			Method[] methods = scanClass.getDeclaredMethods();
			for (Method m : methods)
			{
				if (m.isAnnotationPresent(annoClass))
				{
					result.add(m);
				}
			}
		}
		return result;
	}

	public static List<RegisterToAPI> getAnno(List<Class<?>> scanClasses,
			Class<? extends Annotation> annoClass)
	{
		List<RegisterToAPI> result = new ArrayList<>();
		
		for(Class<?> scanClass : scanClasses)
		{
			Method[] methods = scanClass.getDeclaredMethods();
			for (Method m : methods)
			{
				if (m.isAnnotationPresent(annoClass))
				{
					result.add((RegisterToAPI)m.getAnnotation(annoClass));
				}
			}
		}
		return result;
	}
}
