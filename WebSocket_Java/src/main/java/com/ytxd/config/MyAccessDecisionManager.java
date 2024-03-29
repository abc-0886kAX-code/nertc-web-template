package com.ytxd.config;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;


@Service("MyAccessDecisionManager")
public class MyAccessDecisionManager implements AccessDecisionManager {

	/**
	 * decide 方法是判定是否拥有权限的决策方法，authentication是CustomUserService
	 * 中循环添加到 GrantedAuthority 对象中的权限信息集合,object 包含客户端发起的请求的requset信息，
	 * 可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
	 * configAttributes为MyFilterInvocationSecurityMetadataSource的getAttributes(Object object)
	 * 这个方法返回的结果.
	 * 
	 */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if(null== configAttributes || configAttributes.size() <=0) {
            return;
        }
        ConfigAttribute c;
        String needRole;
        for(Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
            c = iter.next();
            needRole = c.getAttribute();
            String authority="";
            for(GrantedAuthority ga : authentication.getAuthorities()) {//authentication 为在注释1 中循环添加到 GrantedAuthority 对象中的权限信息集合
            	authority = ga.getAuthority();
            	if(authority.indexOf(".")!=-1){
            		authority=authority.substring(0, authority.indexOf("."));
        		}
                if(needRole.trim().equals(authority)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

