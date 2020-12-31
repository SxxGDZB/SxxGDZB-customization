package com.kiwihouse.util;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.dao.entity.AuthUserRole;
import com.kiwihouse.dao.mapper.AuthUserMapper;
import com.kiwihouse.dao.mapper.AuthUserRoleMapper;
import com.kiwihouse.util.spring.SpringUtils;
 
public class HttpServletUtils {
 
	/**
	 * 获取request header中的值
	 * @param name
	 * @return
	 */
	public static String getRequestHeader(String name) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getHeader(name);
	}
	
	/**
	 * 获取cookie的值
	 * @param name
	 * @return
	 */
	public static String getCookie(String name) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Cookie[] cookies = request.getCookies();
		if(null == cookies || cookies.length == 0) {
			return null;
		}
		for(Cookie c : cookies) {
			if(c.getName().equals(name)) {
				return c.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 获取httpsession
	 * @return
	 */
	public static HttpSession getHttpSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		return session;
	}
	
	public static HttpServletRequest getRequset() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}
	
	/**
	 * 获取客户端ip地址
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for"); 
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("X-Real-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        } 
        return ip; 
	}
	
	public static AuthUser getAuthUser() {
		String authorization = getRequset().getHeader("authorization");
		String jwts = JsonWebTokenUtil.parseJwtPayload(authorization);
		JSONObject json = JSON.parseObject(jwts);
		AuthUserMapper authUserMapper = SpringUtils.getBean("authUserMapper");
		AuthUser authUser  = authUserMapper.selectByPrimaryKey(Integer.valueOf(json.getString("sub")));
		AuthUserRoleMapper authUserRoleMapper = SpringUtils.getBean("authUserRoleMapper");
		AuthUserRole authUserRole = authUserRoleMapper.selectByUid(authUser.getUid());
		authUser.setRoleId(authUserRole.getRoleId());
		return authUser;
	}
}