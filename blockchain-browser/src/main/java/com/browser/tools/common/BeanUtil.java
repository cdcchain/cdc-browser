package com.browser.tools.common;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
* @ClassName: BeanUtil 
* @Description: 
* @author David
* @date 2016年11月16日 上午11:28:12 
*
 */
public class BeanUtil {

	private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	public String beanLocations;

	public void setBeanLocations(String beanLocations) {
		this.beanLocations = beanLocations;
	}

	public static Map<String, Object> beanToMap(Object obj, Map<String, Object> mapKeys) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				String keyValue = null;
				if (mapKeys.containsKey(key)) {
					keyValue = (String) mapKeys.get(key);
					// 过滤class属性
					if (!key.equals("class")) {
						// 得到property对应的getter方法
						Method getter = property.getReadMethod();
						Object value = getter.invoke(obj);

						map.put(keyValue, value);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("beanToMap Error " + e);
		}

		return map;

	}

	public static Map<String, Object> beanToMap(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}

	public static void mapToBean(Map<String, Object> params, Object bean) {
		try {
			ConvertUtils.register(new Converter() { 
				@SuppressWarnings("unchecked")
				public Object convert(Class type, Object value) {
					String str = (String) value;
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					try {
						return df.parse(str);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}, Date.class);
			BeanUtils.populate(bean, params);
		} catch (IllegalAccessException e) {
			logger.error("时间转换失败:" + e);
		} catch (InvocationTargetException e) {
			logger.error("map数据转换失败:" + e);
		}
	}

}
