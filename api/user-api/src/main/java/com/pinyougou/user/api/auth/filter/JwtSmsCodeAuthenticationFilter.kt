package com.pinyougou.user.api.auth.filter

import com.pinyougou.user.api.auth.handler.JwtSmsCodeFailureHandler
import com.pinyougou.user.api.auth.handler.JwtSuccessHandler
import com.pinyougou.user.api.auth.token.SmsCodeAuthenticationToken
import com.pinyougou.user.api.interfaces.UserService
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author 邱长海
 */
class JwtSmsCodeAuthenticationFilter(manager: AuthenticationManager, userService: UserService,
                                     redis: StringRedisTemplate)
    : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/v1/smsLogin", "POST")) {

    init {
        authenticationManager = manager
        setAuthenticationSuccessHandler(JwtSuccessHandler(userService, redis))
        setAuthenticationFailureHandler(JwtSmsCodeFailureHandler())
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val username = request.getParameter("mobile")?.trim() ?: ""
        val smsCode = request.getParameter("smsCode")?.trim() ?: ""
        val token = SmsCodeAuthenticationToken(username, smsCode)
        return authenticationManager.authenticate(token)
    }
}