package com.hc.shiro;

import com.hc.domain.User;
import com.hc.fake.DB;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("===================doGetAuthorizationInfo=============================");

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //模拟从数据库中查询出权限
        Set<String> permissions = DB.getAuths(user.getId());

        simpleAuthorizationInfo.setStringPermissions(permissions);//添加权限

        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("===================doGetAuthenticationInfo=============================");

        //模拟从数据库中获取用户名密码

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        User user = DB.getUserByUsername(userToken.getUsername());
        if (null == user) { //用户不存在
            return null; //抛出异常UnknownAccountException
        }

        //用户登录成功，将用户信息保存在Shiro的session中
        SecurityUtils.getSubject().getSession().setAttribute("currentLoginedUser",user);

        //不能自己做密码认证，系统会自己做
        // 第一个参数为用户信息，方便授权时查找用户的权限
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getUserpwd(), "");//参数：认证，密码，用户名
        return simpleAuthenticationInfo;
    }
}