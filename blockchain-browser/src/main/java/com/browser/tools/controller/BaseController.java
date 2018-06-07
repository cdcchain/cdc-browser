package com.browser.tools.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
* @ClassName: BaseController 
* @Description: 基础控制类
* @author David
* @date 2016年11月15日 下午5:02:37 
*
 */
public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	
	public Map<String,Object> getParams(HttpServletRequest request) {
		Map<String, String[]> reqMap = request.getParameterMap();
        Map<String, Object> resultMap = new HashMap<String, Object>(0);
        //resultMap.putAll(iPLocal());
        for (Entry<String, String[]> m : reqMap.entrySet()) {
            String key = m.getKey();
            Object[] obj = (Object[]) reqMap.get(key);
            resultMap.put(key, (obj.length > 1) ? obj : obj[0]);
        }
        return resultMap;
	}

	public Map<String, Object> iPLocal() {
		Map<String, Object> params = new HashMap<String, Object>();
		InetAddress ia;
		try {
			ia = InetAddress.getLocalHost();
			params.put("localIp", ia.getHostAddress());
			params.put("ip", ia.getLocalHost());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			logger.error("ip error:" + e.getMessage());
		}
		return params;
	} 
	
    public Map<String, Object> getIpAddr(HttpServletRequest request) { 
    	Map<String, Object> params = new HashMap<String, Object>();
        String ip = request.getHeader("x-forwarded-for"); 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        params.put("ip", ip);
        return params; 
    } 
}
