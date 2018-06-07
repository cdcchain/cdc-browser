package com.browser.tools.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;


public class ConfigUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);

	private static final String CONF_PATH = "system.properties";

	/**
	 * 
	 * <p>Description:获取属性值</p>
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年5月25日 上午11:06:42
	 * @param key
	 * @return
	 */
	public static String getPropertyKey(String key) {
		Properties st = getConxtions();
		return st.getProperty(key);
	}

	/**
	 * 
	 * <p>Description:加载属性文件</p>
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年5月25日 上午11:07:26
	 * @return
	 */
	public static Properties getConxtions() {
		Properties properties = null;
		try {
			properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(CONF_PATH));
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
		return properties;
	}
}
