package com.kiwihouse.controller.account;


import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.controller.account.params.UserParams;
import com.kiwihouse.controller.common.BaseController;
import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.dao.entity.AuthUserRole;
import com.kiwihouse.dao.mapper.AuthRoleResourceMapper;
import com.kiwihouse.dao.mapper.AuthUserMapper;
import com.kiwihouse.dao.mapper.AuthUserRoleMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.AccountService;
import com.kiwihouse.service.UserService;
import com.kiwihouse.support.factory.LogTaskFactory;
import com.kiwihouse.support.manager.LogExeManager;
import com.kiwihouse.util.CommonUtil;
import com.kiwihouse.util.IpUtil;
import com.kiwihouse.util.JsonWebTokenUtil;
import com.kiwihouse.util.Md5Util;
import com.kiwihouse.vo.entire.Log;

import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * post新增,get读取,put完整更新,patch部分更新,delete删除
 *
 * @author tomsun28
 * @date 14:40 2018/3/8
 */
@RestController
@RequestMapping("/account")
@Api(tags = "用户登录注册")
public class AccountController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;

    @Autowired
    private AuthRoleResourceMapper authRoleResourceMapper;
    
    @Value("${kiwihouse.enableEncryptPassword}")
    private boolean isEncryptPassword;
    @Autowired	
    private AuthUserMapper authUserMapper;
    /**
     * description 登录签发 JWT ,这里已经在 passwordFilter 进行了登录认证
     *
     * @param request  1
     * @param response 2
     * @return com.kiwihouse.domain.vo.Message
     */
    @ApiOperation(value = "用户登录", notes = "POST用户登录签发JWT")
    @PostMapping("/login")
    @ResponseBody
    public Response accountLogin(@RequestBody UserParams params, HttpServletRequest request, HttpServletResponse response) {
    	logger.info("用户登录>> {}",new Log().setIp(request.getRemoteAddr()).setParam(params.toString()));
        AuthUser authUser = userService.getUserByUsername(params.getUsername());
        authUser.setPassword(null);
        authUser.setSalt(null);
        AuthUserRole authUserRole = authUserRoleMapper.selectByUid(authUser.getUid());
        authUser.setRoleId(authUserRole.getRoleId());
        authUser.setRoleName(authUserRole.getRoleName());
        // 根据appId获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
        String roles = accountService.loadAccountRoleByUsername(params.getUsername());
        //根据roles 查询 相应的 权限资源
        //String perms = authRoleResourceMapper.selectRoleRulesByRole(roles);
        // 时间以秒计算,token有效刷新时间是token有效过期时间的2倍
        Integer refreshPeriodTime = 60 * 60;
        String jwt = JsonWebTokenUtil.issueJWT(UUID.randomUUID().toString(), params.getUsername(),
                "token-server", refreshPeriodTime >> 1, roles, null, SignatureAlgorithm.HS512);
        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
        //redisUtil.set("JWT-SESSION-" + params.getUsername(), jwt, refreshPeriodTime);
        LogExeManager.getInstance().executeLogTask(LogTaskFactory.loginLog(1, IpUtil.getIpFromRequest(WebUtils.toHttp(request)), (short) 1, "登录成功"));
        userService.updateByVxId(params);
        return new Response().Success(Code.LOGIN_SUCC, "issue jwt success").addData("jwt", jwt).addData("user", authUser);
    }

    /**
     * description 用户账号的注册
     *
     * @param request  1
     * @param response 2
     * @return com.kiwihouse.domain.vo.Message
     */
    @ApiOperation(value = "用户注册", notes = "POST用户注册")
    @ApiResponses({@ApiResponse(code = 0, message = "回调参数", response = UserParams.class)})
    @PostMapping("/register")
    public Response accountRegister(@Validated @RequestBody UserParams params,HttpServletRequest request, HttpServletResponse response) {
    	logger.info("用户注册>> {}",new Log().setIp(request.getRemoteAddr()).setParam(params.toString()));
        AuthUser authUser = new AuthUser();
        Integer uid = authUserMapper.selectMaxId() + 1;//Integer.parseInt(params.get("uid"));
        String password = params.getPassword();//params.get("password");
        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(password)) {
            // 必须信息缺一不可,返回注册账号信息缺失
        	logger.info("用户注册>> {账户信息缺失}",new Log().setIp(request.getRemoteAddr()).setParam(params.toString()));
            return new Response().Fail(1111, "账户信息缺失");
        }
        if (accountService.isAccountExistByUid(uid)) {
        	logger.info("用户注册>> {账户已存在}",new Log().setIp(request.getRemoteAddr()).setParam(params.toString()));
            // 账户已存在
            return new Response().Fail(1111, "账户已存在");
        }
        AuthUser au = authUserMapper.selectByUsername(params.getUsername());
        if(au != null) {
        	logger.info("用户注册>> {账户已存在}",new Log().setIp(request.getRemoteAddr()).setParam(params.toString()));
        	return new Response().Fail(1111, "账户已存在");
        }
        authUser.setUid(uid);
        if(params.getSalt() == null) {
        	params.setSalt(CommonUtil.getRandomString(6));
        }
        // 存储到数据库的密码为 MD5(原密码+盐值)
        authUser.setPassword(Md5Util.md5(password + params.getSalt()));
        authUser.setSalt(params.getSalt());
        authUser.setCreateTime(new Date());
        authUser.setUsername(params.getUsername());
        authUser.setStatus((byte) 1);
        if (accountService.registerAccount(authUser)) {
            LogExeManager.getInstance().executeLogTask(LogTaskFactory.registerLog(uid, IpUtil.getIpFromRequest(WebUtils.toHttp(request)), (short) 1, "注册成功"));
            logger.info("用户注册>> {注册成功}",new Log().setIp(request.getRemoteAddr()).setParam(authUser.toString()));
            return new Response().Success(2002, "注册成功");
        } else {
            LogExeManager.getInstance().executeLogTask(LogTaskFactory.registerLog(uid, IpUtil.getIpFromRequest(WebUtils.toHttp(request)), (short) 0, "注册失败"));
            logger.info("用户注册>> {注册失败}",new Log().setIp(request.getRemoteAddr()).setParam(params.toString()));
            return new Response().Success(1111, "注册失败");
        }
    }
    
    public void test() {
    	
    }
}
