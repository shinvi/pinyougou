package com.pinyougou.user.api.auth.handler

import com.pinyougou.common.Const
import com.pinyougou.common.util.JsonUtils
import com.pinyougou.user.api.interfaces.UserService
import com.pinyougou.user.api.pojo.JwtUser
import com.pinyougou.user.api.pojo.User
import com.pinyougou.user.api.util.JWT_HEADER_KEY
import com.pinyougou.user.api.util.JWT_HEADER_VALUE_PREFIX
import com.pinyougou.web.common.entity.ServerResponse
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.time.Duration
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author 邱长海
 */
class JwtSuccessHandler(private val userService: UserService,
                        private val redis: StringRedisTemplate) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val user = authentication.principal as JwtUser
        val token = userService.createJwtTokenByUser(user.user)
        redis.opsForValue().set("${Const.USER_TOKEN}.${user.username}", token, Duration.ofDays(30))
        response.contentType = "application/json;charset=UTF-8"
        response.setHeader(JWT_HEADER_KEY, "$JWT_HEADER_VALUE_PREFIX $token")
        response.writer.write(JsonUtils.get().toJson(ServerResponse.success<User>(user.user)))
    }
}