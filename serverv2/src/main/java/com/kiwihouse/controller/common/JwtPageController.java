package com.kiwihouse.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.dao.entity.AuthUserRole;
import com.kiwihouse.dao.entity.Option;
import com.kiwihouse.dao.entity.SysMenu;
import com.kiwihouse.dao.mapper.AuthUserMapper;
import com.kiwihouse.dao.mapper.AuthUserRoleMapper;
import com.kiwihouse.dao.mapper.SysMenuMapper;
import com.kiwihouse.domain.vo.JwtAccount;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.util.JsonWebTokenUtil;
import com.kiwihouse.util.spring.SpringUtils;

import io.jsonwebtoken.MalformedJwtException;
import io.swagger.annotations.Api;

@Api(tags = "页面跳转接口")
@RestController
@RequestMapping("/jwt")
public class JwtPageController {

	@Autowired
	SysMenuMapper sysMenuMapper;
	@RequestMapping(value = "/page")
	public ModelAndView returnPageTwo(@RequestParam String jwt,@RequestParam String url,HttpServletRequest request) {
		
		if(jwt != null) {
			HttpSession session = request.getSession();
			String payload = null;
			Response res = null;
			try{
	            // 预先解析Payload
	            // 没有做任何的签名校验
	            payload = JsonWebTokenUtil.parseJwtPayload(jwt);
	        } catch(MalformedJwtException e){
	            //令牌格式错误
	        	res = new Response().Fail(Code.JWT_FAIL,Code.JWT_FAIL.getMsg());
	        	session.setAttribute("jwt",JSON.toJSON(res));
//	            throw new AuthenticationException("errJwt");
	        } catch(Exception e){
	            //令牌无效
	        	res = new Response().Fail(Code.JWT_FAIL,Code.JWT_FAIL.getMsg());
	        	session.setAttribute("jwt",JSON.toJSON(res));
//	            throw new AuthenticationException("errsJwt");
	        }
			JwtAccount jwtAccount = JsonWebTokenUtil.parseJwt(jwt);
	        if(null == payload){
	            //令牌无效
	        	res = new Response().Fail(Code.JWT_FAIL,Code.JWT_FAIL.getMsg());
	        	session.setAttribute("jwt",JSON.toJSON(res));
//	            throw new AuthenticationException("errJwt");
	        }else if(jwtAccount == null){
	        	res = new Response().Fail(Code.JWT_FAIL,Code.JWT_FAIL.getMsg());
	        	session.setAttribute("jwt",JSON.toJSON(res));
	        }else {
	    		String jwts = JsonWebTokenUtil.parseJwtPayload(jwt);
	    		JSONObject json = JSON.parseObject(jwts);
	    		AuthUserMapper authUserMapper = SpringUtils.getBean("authUserMapper");
	    		AuthUser authUser  = authUserMapper.selectByPrimaryKey(Integer.valueOf(json.getString("sub")));
	    		if(authUser == null) {
	    			res = new Response().Fail(Code.JWT_FAIL,Code.JWT_FAIL.getMsg());
	    		}else {
	    			authUser.setPassword(null);
		            authUser.setSalt(null);
		            AuthUserRoleMapper authUserRoleMapper = SpringUtils.getBean("authUserRoleMapper");
		            AuthUserRole authUserRole = authUserRoleMapper.selectByUid(authUser.getUid());
		            
		            authUser.setRoleId(authUserRole.getRoleId());
		            authUser.setRoleName(authUserRole.getRoleName());
		            Option option = null;
		            if(!"/admin/index".equals(url)) {
		            	SysMenu sysMenu = sysMenuMapper.queryOneMenuByUrl(url);
		            	option = new Option(url, url, sysMenu.getName(), sysMenu.getId().toString(), false);
		            	url = "/admin/index#/"+ url;
		            }
		            res = new Response().Success(Code.JWT_SUCCESS,Code.JWT_SUCCESS.getMsg()).addData("data", jwt).addData("authUser", authUser).addData("url", url).addData("option", option);
	    		}
	    		session.setAttribute("jwt",res);
	        }
		}
		return new ModelAndView("jwt");
		
	}
}
