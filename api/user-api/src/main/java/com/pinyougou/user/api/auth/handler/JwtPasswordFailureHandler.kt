package com.pinyougou.user.api.auth.handler

import com.pinyougou.common.util.JsonUtils
import com.pinyougou.user.api.auth.exception.JwtAuthException
import com.pinyougou.user.api.interfaces.UserService
import com.pinyougou.web.common.entity.ServerResponse
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author 邱长海
 */
class JwtPasswordFailureHandler : AuthenticationFailureHandler {
    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        response.contentType = "application/json;charset=UTF-8"

        val msg = if (exception is BadCredentialsException) "密码错误" else exception.message
        response.writer.write(JsonUtils.get().toJson(ServerResponse.error<Any>(msg)))
    }
}
