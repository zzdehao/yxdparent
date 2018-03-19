package com.tf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//允许进入页面方法前检验
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider provider;//自定义验证

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth)
            throws Exception {

    }
    // 设置 HTTP 验证规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf验证
        http.csrf().disable() //jwt不需要csrf
                // 对请求进行认证
                .authorizeRequests()
                // 支付成功通知放行
                .antMatchers("/login","/swagger-ui.html","/webjars/**").permitAll()
                .antMatchers("/swagger",
                        "/v2/api-docs",//swagger api json
                        "/swagger-resources/configuration/ui",//用来获取支持的动作
                        "/swagger-resources",//用来获取api-docs的URI
                        "/swagger-resources/configuration/security",//安全选项
                        "/swagger-ui.html",
                        "/webjars/springfox-swagger-ui/images/*.png",
                        "/webjars/springfox-swagger-ui/lib/*.js",
                        "/webjars/springfox-swagger-ui/css/*.css",
                        "/webjars/springfox-swagger-ui/*.js").permitAll()

                .antMatchers("/check/**").permitAll()
                .antMatchers("/check/uploadImage").permitAll()
                .anyRequest().authenticated()

                .and()

                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new MyHttp403ForbiddenEntryPoint());

        // 禁用缓存
        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义身份验证组件
        auth.authenticationProvider(provider);
    }
}
