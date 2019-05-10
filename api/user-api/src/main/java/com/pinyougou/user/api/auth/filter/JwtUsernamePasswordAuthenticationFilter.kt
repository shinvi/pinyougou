package com.pinyougou.user.api.auth.filter

import com.pinyougou.user.api.auth.handler.JwtPasswordFailureHandler
import com.pinyougou.user.api.auth.handler.JwtSuccessHandler
import com.pinyougou.user.api.interfaces.UserService
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

/**
 * @author 邱长海
 */
class JwtUsernamePasswordAuthenticationFilter(manager: AuthenticationManager, private val userService: UserService,
                                              private val redis: StringRedisTemplate) : UsernamePasswordAuthenticationFilter() {
    init {
        authenticationManager = manager
        setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher("/v1/login", "POST"))
        setAuthenticationSuccessHandler(JwtSuccessHandler(userService, redis))
        setAuthenticationFailureHandler(JwtPasswordFailureHandler())
    }
}
