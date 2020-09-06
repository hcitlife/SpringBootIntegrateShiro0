package com.hc.cfg;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.hc.shiro.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //1.创建realm对象，使用自定义类
    @Bean(name = "userRealm")
    public UserRealm myShiroRealm() { //将自己的验证方式加入容器
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    //2.创建DefaultWebSecurityManager
    @Bean(name = "dwSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //3.创建ShiroFilterFactoryBean
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("dwSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        Map<String, String> map = new LinkedHashMap<>();
        //拦截
        map.put("/user/add", "authc"); //必须认证了才能访问
        map.put("/user/update", "authc");
        //上面两个可以使用通配符  map.put("/user/*","authc");

        //授权，若当前登录用户未授权则会跳转到授权页面
        map.put("/user/add", "perms[user:add]");
        map.put("/user/update", "perms[user:update]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");

        return shiroFilterFactoryBean;
    }

    //Shiro整合thymeleaf
    @Bean
    public ShiroDialect getShiroDialect() {
        ShiroDialect shiroDialect = new ShiroDialect();
        return shiroDialect;
    }
}