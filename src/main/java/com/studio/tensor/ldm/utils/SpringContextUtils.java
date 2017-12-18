package com.studio.tensor.ldm.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware 
{
    private static ApplicationContext applicationContext;

    /*
     * @param arg0
     * 
     * @throws BeansException
     * 
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }
    
    public static <T>T getBean(String id,Class<T> type){        
        return  applicationContext.getBean(id, type);
    }
}
