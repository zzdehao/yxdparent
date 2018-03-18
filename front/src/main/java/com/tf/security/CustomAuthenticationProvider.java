package com.tf.security;
import com.github.pagehelper.util.StringUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String openid = authentication.getName();
        Object userId = authentication.getCredentials();
        //后台校验

        if (StringUtil.isNotEmpty(openid)) {
            // 生成令牌
            Authentication auth = new PreAuthenticatedAuthenticationToken(openid, userId);
            return auth;
        } else {
            throw new BadCredentialsException("密码错误~");
        }
    }
    // 是否可以提供输入类型的认证服务
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
