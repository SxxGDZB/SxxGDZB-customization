package com.kiwihouse.shiro.realm;


import com.kiwihouse.domain.vo.Account;
import com.kiwihouse.shiro.provider.AccountProvider;
import com.kiwihouse.shiro.token.PasswordToken;
import com.kiwihouse.util.Md5Util;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 这里是登录认证realm
 *
 * @author tomsun28
 * @date 21:08 2018/2/10
 */
public class PasswordRealm extends AuthorizingRealm {


    private AccountProvider accountProvider;

    /**
     * description 此Realm只支持PasswordToken
     *
     * @return java.lang.Class<?>
     */
    @Override
    public Class<?> getAuthenticationTokenClass() {
        return PasswordToken.class;
    }


    /**
     * description 这里只需要认证登录，成功之后派发 json web token 授权在那里进行
     *
     * @param principalCollection 1
     * @return org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (!(authenticationToken instanceof PasswordToken)) {
            return null;
        }

        if (null == authenticationToken.getPrincipal() || null == authenticationToken.getCredentials()) {
            throw new UnknownAccountException();
        }
        String username = (String) authenticationToken.getPrincipal();
        
        Account account = accountProvider.loadAccountByUsername(username);
        if (account != null) {
            // 用盐对密码进行MD5加密
            ((PasswordToken) authenticationToken).setPassword(Md5Util.md5(((PasswordToken) authenticationToken).getPassword() + account.getSalt()));
            String name =  getName();
            return new SimpleAuthenticationInfo(username, account.getPassword(),name);
        } else {
            return new SimpleAuthenticationInfo(username, "", getName());
        }
//        if (account != null) {
//        	try {
//        		//获取加密后的密码 
//        		String password = ((PasswordToken) authenticationToken).getPassword();
//        		//解码
//        		String pw = AesUtils.aesDecrypt(password);
//				// 用盐对密码进行MD5加密
//				((PasswordToken) authenticationToken).setPassword(Md5Util.md5(pw + account.getSalt()));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				//e.printStackTrace();
//				logger.error("=============>密码解码发生异常");
//			}
//            String name =  getName();
//            System.out.println(account.getPassword());
//            return new SimpleAuthenticationInfo(username, account.getPassword(),name);
//        } else {
//            return new SimpleAuthenticationInfo(username, "", getName());
//        }

    }

    public void setAccountProvider(AccountProvider accountProvider) {
        this.accountProvider = accountProvider;
    }
    public static void main(String[] args) {
    	System.out.println(Md5Util.md5("e10adc3949ba59abbe56e057f20f883em0lps7"));
	}
}
