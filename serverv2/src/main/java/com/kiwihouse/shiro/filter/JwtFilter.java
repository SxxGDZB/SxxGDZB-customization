package com.kiwihouse.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.utils.RedisUtil;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.AccountService;
import com.kiwihouse.shiro.token.JwtToken;
import com.kiwihouse.shiro.utils.ReqUtils;
import com.kiwihouse.shiro.utils.RespUtils;
import com.kiwihouse.support.factory.LogTaskFactory;
import com.kiwihouse.support.manager.LogExeManager;
import com.kiwihouse.util.IpUtil;
import com.kiwihouse.util.JsonWebTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * 支持restful url 的过滤链  JWT json web token 过滤器，无状态验证
 *
 * @author tomsun28
 * @date 0:04 2018/4/20
 */
public class JwtFilter extends AbstractPathMatchingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);


    private RedisUtil redisUtil;
    private AccountService accountService;


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);

        //记录调用api日志到数据库
//        LogExeManager.getInstance().executeLogTask(LogTaskFactory.bussinssLog(Integer.parseInt(WebUtils.toHttp(servletRequest).getHeader("appId")),
//                WebUtils.toHttp(servletRequest).getRequestURI(), WebUtils.toHttp(servletRequest).getMethod(), (short) 1, null));
        HttpServletRequest re = (HttpServletRequest) servletRequest;
		LOGGER.info(re.getServletPath());
		if (re.getMethod().equals("OPTIONS")){
            return true;
        }
        if (subject == null) {
            Response response = new Response().Fail(Code.REQUEST_ERROR, "jwt subject null");
            RespUtils.responseWrite(JSON.toJSONString(response), servletResponse);
            return false;
        }
        // 判断是否为JWT认证请求
        if (isJwtSubmission(servletRequest)) {
            AuthenticationToken token = createJwtToken(servletRequest);
            try {
                subject.login(token);
                return this.checkRoles(subject, mappedValue);
            } catch (ExpiredJwtException e) {
                String username = e.getClaims().getSubject();
                //                if (STR_EXPIRED.equals(e.getMessage())) {
//                    // 这里初始方案先抛出令牌过期，之后设计为在Redis中查询当前appId对应令牌，其设置的过期时间是JWT的两倍，此作为JWT的refresh时间
//                    // 当JWT的有效时间过期后，查询其refresh时间，refresh时间有效即重新派发新的JWT给客户端，
//                    // refresh也过期则告知客户端JWT时间过期重新认证
//
//                    // 当存储在redis的JWT没有过期，即refresh time 没有过期
//                    String appId = WebUtils.toHttp(servletRequest).getHeader("appId");
//                    String jwt = WebUtils.toHttp(servletRequest).getHeader("authorization");
//                    String refreshJwt = redisUtil.get("JWT-SESSION-" + appId);
//                    if (null != refreshJwt && refreshJwt.equals(jwt)) {
//                        // 重新申请新的JWT
//                        // 根据appId获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
//                        String roles = accountService.loadAccountRole(Integer.parseInt(appId));
//                        //seconds为单位,10 hours
//                        Integer refreshPeriodTime = 36000;
//                        Integer period = refreshPeriodTime >> 1;
//                        String newJwt = JsonWebTokenUtil.issueJWT(UUID.randomUUID().toString(), appId,
//                                "token-server", period, roles, null, SignatureAlgorithm.HS512);
//                        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
//                        redisUtil.set("JWT-SESSION-" + appId, newJwt, refreshPeriodTime);
//                        Response response = new Response().Success(1005, "new jwt").addData("jwt", newJwt);
//                        RespUtils.responseWrite(JSON.toJSONString(response), servletResponse);
//                        return false;
//
//
//                }
                Response response = new Response().Fail(Code.JWT_FAIL, e.getMessage() + ":"+ username);
                RespUtils.responseWrite(JSON.toJSONString(response), servletResponse);
                return false;
            } catch (AuthenticationException e) {
                // 其他的判断为JWT错误无效
                Response response = new Response().Fail(Code.JWT_FAIL, e.getMessage());
                RespUtils.responseWrite(JSON.toJSONString(response), servletResponse);
                return false;

            } catch (Exception e) {
                // 其他错误
                LOGGER.error(IpUtil.getIpFromRequest(WebUtils.toHttp(servletRequest)) + "--JWT认证失败" + e.getMessage(), e);
                // 告知客户端JWT错误1005,需重新登录申请jwt
                Response response = new Response().Fail(Code.JWT_FAIL, "error jwt");
                RespUtils.responseWrite(JSON.toJSONString(response), servletResponse);
                return false;
            }
        } else {
            // 请求未携带jwt 判断为无效请求
            Response response = new Response().Fail(Code.REQUEST_ERROR, "缺少Authorization请求头");
            RespUtils.responseWrite(JSON.toJSONString(response), servletResponse);
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);

        // 未认证的情况上面已经处理  这里处理未授权
        if (subject != null && subject.isAuthenticated()) {
            //  已经认证但未授权的情况
            // 告知客户端JWT没有权限访问此资源
            Response response = new Response().Fail(Code.PERMISSION_NO);
            RespUtils.responseWrite(JSON.toJSONString(response), servletResponse);
        }
        // 过滤链终止
        return false;
    }

    private boolean isJwtSubmission(ServletRequest request) {
        String jwt = ReqUtils.getHeader(request, "authorization");
        return (request instanceof HttpServletRequest)
                && !StringUtils.isEmpty(jwt);
    }

    private AuthenticationToken createJwtToken(ServletRequest request) {
        String ipHost = request.getRemoteAddr();
        String jwt = ReqUtils.getHeader(request, "authorization");
        String deviceInfo = ReqUtils.getHeader(request, "deviceInfo");
        return new JwtToken(ipHost, deviceInfo, jwt);
    }

    /**
     * description 验证当前用户是否属于mappedValue任意一个角色
     *
     * @param subject     1
     * @param mappedValue 2
     * @return boolean
     */
    private boolean checkRoles(Subject subject, Object mappedValue) {
        String[] rolesArray = (String[]) mappedValue;
        return rolesArray == null || rolesArray.length == 0 || Stream.of(rolesArray).anyMatch(role -> subject.hasRole(role.trim()));
    }


    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }


    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
