package com.purang.SpringBoot.config;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.purang.SpringBoot.config.dbconfig.MybatisConfiguration;
import com.purang.SpringBoot.domain.SysPermissionInit;
import com.purang.SpringBoot.service.SysPermissionInitService;

@Configuration
@AutoConfigureAfter(MybatisConfiguration.class)
public class ShiroConfig {
	@Autowired
	private SysPermissionInitService sysPermissionInitService;
	
	@Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;
	
	@Bean
	public ShiroFilterFactoryBean shirFilter(@Qualifier("securityManager") SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		//配置登录的url和登录成功的url
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        /*
        filterChainDefinitionMap.put("/login*", "anon"); //表示可以匿名访问
        filterChainDefinitionMap.put("/loginUser", "anon"); 
        filterChainDefinitionMap.put("/logout*","anon");
        filterChainDefinitionMap.put("/error/*","anon");
        filterChainDefinitionMap.put("/index*","authc");
        filterChainDefinitionMap.put("/*", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/*.*", "authc");
        */
        //通过数据库存储动态的访问权限
        List<SysPermissionInit> list = sysPermissionInitService.getAll();
        for (SysPermissionInit sysPermissionInit : list) {
            filterChainDefinitionMap.put(sysPermissionInit.getUrl(), sysPermissionInit.getPermissionInit());
        }
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        
		//未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
	
	//配置核心安全事务管理器
    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") MyShiroRealm authRealm) {
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        // 自定义缓存实现 使用redis
//        manager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
//        manager.setSessionManager(SessionManager());
        return manager;
    }
    
    //配置自定义的权限登录器
    @Bean(name="authRealm")
    public MyShiroRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
    		MyShiroRealm authRealm=new MyShiroRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }
    
    //配置自定义的密码比较器
    @Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }
    
    /**
     * 配置shiro redisManager
     * 
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(1800);// 配置过期时间
        // redisManager.setTimeout(timeout);
        // redisManager.setPassword(password);
        return redisManager;
    }
    
    /**
     * cacheManager 缓存 redis实现
     * 
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
    
    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     */
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }
    
    /**
     * shiro session的管理
     */
    public DefaultWebSessionManager SessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }
    
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }
    
//    @Bean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
//        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
//        creator.setProxyTargetClass(true);
//        return creator;
//    }
    
	/**
	 *  开启shiro aop注解支持.
	 *  使用代理方式;所以需要开启代码支持;
	 * @param securityManager
	 * @return
	 */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
//        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(manager);
//        return advisor;
//    }

//	/**
//	 * 凭证匹配器
//	 * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
//	 * ）
//	 * @return
//	 */
//	@Bean
//	public HashedCredentialsMatcher hashedCredentialsMatcher(){
//		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//		hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
//		hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
//		return hashedCredentialsMatcher;
//	}
//
//	@Bean
//	public MyShiroRealm myShiroRealm(){
//		MyShiroRealm myShiroRealm = new MyShiroRealm();
//		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//		return myShiroRealm;
//	}
//
//
//	@Bean
//	public SecurityManager securityManager(){
//		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
//		securityManager.setRealm(myShiroRealm());
//		return securityManager;
//	}
//
//	/**
//	 *  开启shiro aop注解支持.
//	 *  使用代理方式;所以需要开启代码支持;
//	 * @param securityManager
//	 * @return
//	 */
//	@Bean
//	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
//		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//		return authorizationAttributeSourceAdvisor;
//	}
//
//	@Bean(name="simpleMappingExceptionResolver")
//	public SimpleMappingExceptionResolver
//	createSimpleMappingExceptionResolver() {
//		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
//		Properties mappings = new Properties();
//		mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
//		mappings.setProperty("UnauthorizedException","403");
//		r.setExceptionMappings(mappings);  // None by default
//		r.setDefaultErrorView("500");    // No default
//		r.setExceptionAttribute("ex");     // Default is "exception"
//		//r.setWarnLogCategory("example.MvcLogger");     // No default
//		return r;
//	}
}