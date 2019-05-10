package com.pinyougou.manager.web.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.user.api.auth.filter.JwtAuthorizationFilter;
import com.pinyougou.user.api.auth.filter.JwtSmsCodeAuthenticationFilter;
import com.pinyougou.user.api.auth.filter.JwtUsernamePasswordAuthenticationFilter;
import com.pinyougou.user.api.auth.handler.JwtAuthenticationEntryPoint;
import com.pinyougou.user.api.auth.handler.JwtDeniedHandler;
import com.pinyougou.user.api.auth.provider.SmsCodeAuthenticationProvider;
import com.pinyougou.user.api.auth.service.UserDetailsServiceImpl;
import com.pinyougou.user.api.interfaces.SmsCodeService;
import com.pinyougou.user.api.interfaces.UserService;
import com.pinyougou.user.api.pojo.JwtUser;
import com.pinyougou.user.api.pojo.User;
import com.pinyougou.web.common.exception.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import java.util.Map;

/**
 * @author 邱长海
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Reference(version = "1.0")
    private UserService userService;

    @Reference(version = "1.0")
    private SmsCodeService smsCodeService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/v1/login").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/user").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/user/captcha").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/smsLogin").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable().cors()
                .and().formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), userService, redisTemplate))
                .addFilterBefore(new JwtSmsCodeAuthenticationFilter(authenticationManager(), userService, redisTemplate), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userService, redisTemplate, true))
                .exceptionHandling().accessDeniedHandler(new JwtDeniedHandler())
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService userDetailsService = new UserDetailsServiceImpl(userService);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
                .and().authenticationProvider(new SmsCodeAuthenticationProvider(smsCodeService, userDetailsService));
    }

}
