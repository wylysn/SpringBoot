package com.purang.SpringBoot.config;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.purang.SpringBoot.domain.SysPermission;
import com.purang.SpringBoot.domain.SysRole;
import com.purang.SpringBoot.domain.UserEntity;
import com.purang.SpringBoot.service.UserService;

/*
 * 类AuthRealm完成根据用户名去数据库的查询,并且将用户信息放入shiro中,供第二个类调用.CredentialsMatcher,完成对于密码的校验
 * 
 * @author imac
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserService userInfoService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
    		UserEntity userInfo=(UserEntity) principals.fromRealm(this.getClass().getName()).iterator().next();//获取session中的用户
    		for(SysRole role:userInfo.getRoleList()){
    			authorizationInfo.addRole(role.getRole());//将角色放入shiro中.
    			for(SysPermission p:role.getPermissions()){
    				authorizationInfo.addStringPermission(p.getPermission());//将权限放入shiro中.
    			}
	    }
      
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
    		UsernamePasswordToken utoken=(UsernamePasswordToken) token;//获取用户输入的token
        String username = utoken.getUsername();
        UserEntity userInfo = userInfoService.findByUserName(username);
        if (userInfo == null) {
			throw new UnknownAccountException();
		}
        return new SimpleAuthenticationInfo(userInfo, userInfo.getPassWord(),this.getClass().getName());//放入shiro.调用CredentialsMatcher检验密码
    }

}