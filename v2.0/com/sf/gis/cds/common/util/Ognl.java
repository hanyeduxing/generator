package com.sf.gis.cds.common.util;

import java.util.Collection;
import java.util.Map;

public class Ognl {
	
	/**
	 * 判断对象是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if(obj == null) {
			return true;
		}else if(obj instanceof String) {
			if(((String)obj).trim().length() == 0) {
				return true;
			}
		}else if(obj instanceof Collection) {
			if(((Collection<?>)obj).isEmpty()) {
				return true;
			}
		}else if(obj.getClass().isArray()) {
			if(((Object[])obj).length == 0) {
				return true;
			}
		}else if(obj instanceof Map) {
			if(((Map<?,?>) obj).isEmpty()) {
				return true;
			}
		}else {
			return false;
		}
		
		return false;
	}
	
	/**
	 * 判断对象是否为不空
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

}
