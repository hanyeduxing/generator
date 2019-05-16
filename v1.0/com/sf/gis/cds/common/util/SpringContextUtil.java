package com.sf.gis.cds.common.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {
	// Spring应用上下文环境
	@Autowired
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param applicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext context) {
		if(SpringContextUtil.applicationContext == null) {
			SpringContextUtil.applicationContext = context;  
		}
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null)
			throw new IllegalStateException(
					"applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
		return applicationContext;
	}
	

	/**
	 * 根据bean的名称和class类型 获取bean对象。
	 * 
	 * @param name
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		return applicationContext.getBean(name, clazz);
	}
	
	/**
	 * 根据bean的名称获取bean对象。
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
	/**
	 * 根据class类型 获取bean对象。
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	/**
	 * 根据接口类名获得唯一的实现类的实例 
	 * 没有发现接口的实现类返回null值
	 * 超过一个实现类,随机返回一个实现类的实例
	 * @param clazz
	 * @return
	 */
	public static <T> T getUniqueBean(Class<T> clazz) {
		Map<String, T> map = applicationContext.getBeansOfType(clazz);
		Collection<T> list = map.values();
		if (list.iterator().hasNext()) {
			return list.iterator().next();
		}
		return null;
	}
	
	public static <T> T getUniqueBean(ApplicationContext context, Class<T> clazz) {
        Map<String, T> map = context.getBeansOfType(clazz);
        Collection<T> list = map.values();
        if (list.iterator().hasNext()) {
            return list.iterator().next();
        }
        return null;
    }

	/**
	 * 请使用getBean(String name)
	 * @param name
	 * @return
	 */
	@Deprecated
	public static Object getBeanByName(String name) {
		return applicationContext.getBean(name);
	}
}