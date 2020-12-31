package com.kiwihouse.shiro.filter;


import com.kiwihouse.common.utils.RedisUtil;
import com.kiwihouse.service.AccountService;
import com.kiwihouse.shiro.config.RestPathMatchingFilterChainResolver;
import com.kiwihouse.shiro.provider.ShiroFilterRulesProvider;
import com.kiwihouse.shiro.rule.RolePermRule;
import com.kiwihouse.support.SpringContextHolder;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Filter 管理器
 *
 * @author tomsun28
 * @date 11:16 2018/2/28
 */
@Component
public class FilterChainManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterChainManager.class);

    private final ShiroFilterRulesProvider shiroFilterRulesProvider;
    private final RedisUtil redisUtil;
    private final AccountService accountService;

    @Value("${kiwihouse.enableEncryptPassword}")
    private boolean isEncryptPassword;

    @Autowired
    public FilterChainManager(ShiroFilterRulesProvider shiroFilterRulesProvider, RedisUtil redisUtil, AccountService accountService) {
        this.shiroFilterRulesProvider = shiroFilterRulesProvider;
        this.redisUtil = redisUtil;
        this.accountService = accountService;
    }

    /**
     * description 初始化获取过滤链
     *
     * @return java.util.Map<java.lang.String, javax.servlet.Filter>
     */
    public Map<String, Filter> initGetFilters() {
        Map<String, Filter> filters = new LinkedHashMap<>();
        PasswordFilter passwordFilter = new PasswordFilter();
        passwordFilter.setEncryptPassword(isEncryptPassword);
        passwordFilter.setRedisUtil(redisUtil);
        filters.put("auth", passwordFilter);
        JwtFilter jwtFilter = new JwtFilter();
        jwtFilter.setRedisUtil(redisUtil);
        jwtFilter.setAccountService(accountService);
        filters.put("jwt", jwtFilter);
        return filters;
    }

    /**
     * description 初始化获取过滤链规则
     *
     * @return java.util.Map<java.lang.String, java.lang.String>
     */
    public Map<String, String> initGetFilterChain() {
        Map<String, String> filterChain = new LinkedHashMap<>();
        
     
        // -------------anon 默认过滤器忽略的URL
        List<String> defalutAnon = Arrays.asList("/css/**", "/js/**","/api/**","/images/**","/lib/**");
        defalutAnon.forEach(ignored -> filterChain.put(ignored, "anon"));
        //--swagger默认忽略
        filterChain.put("/swagger-ui.html", "anon");
        filterChain.put("/webjars/**", "anon");
        filterChain.put("/v2/**", "anon");
        filterChain.put("/swagger-resources/**", "anon");
        filterChain.put("/admin/**", "anon");
        filterChain.put("/favicon.ico","anon");
        filterChain.put("/*/common/download","anon");
        filterChain.put("/webSocket/**","anon");
        filterChain.put("/decrypt/decodeUserInfo", "anon");
        filterChain.put("/resource/static", "anon");
        filterChain.put("/jwt/**", "anon");
        // -------------auth 默认需要认证过滤器的URL 走auth--PasswordFilter
        List<String> defalutAuth = Arrays.asList("/account/**");
        defalutAuth.forEach(auth -> filterChain.put(auth, "auth"));
        // -------------dynamic 动态URL
        if (shiroFilterRulesProvider != null) {
            List<RolePermRule> rolePermRules = this.shiroFilterRulesProvider.loadRolePermRules();
            if (null != rolePermRules) {
                rolePermRules.forEach(rule -> {
                    StringBuilder chain = rule.toFilterChain();
                    System.out.println("-------------->" + rule.getUrl());
                    if (null != chain) {
                        filterChain.putIfAbsent(rule.getUrl(), chain.toString());
                    }
                });
            }
        }
        //其他链接全部进入jwt拦截链
        filterChain.put("/**", "jwt");
        return filterChain;
    }

    /**
     * description 动态重新加载过滤链规则
     */
    public void reloadFilterChain() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = SpringContextHolder.getBean(ShiroFilterFactoryBean.class);
        AbstractShiroFilter abstractShiroFilter = null;
        try {
            abstractShiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            RestPathMatchingFilterChainResolver filterChainResolver = (RestPathMatchingFilterChainResolver) abstractShiroFilter.getFilterChainResolver();
            DefaultFilterChainManager filterChainManager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            filterChainManager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(this.initGetFilterChain());
            shiroFilterFactoryBean.getFilterChainDefinitionMap().forEach((k, v) -> filterChainManager.createChain(k, v));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
