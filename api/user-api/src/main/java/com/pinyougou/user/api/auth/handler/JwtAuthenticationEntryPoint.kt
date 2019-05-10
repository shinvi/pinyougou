package com.pinyougou.user.api.auth.handler

import com.pinyougou.common.util.JsonUtils
import com.pinyougou.user.api.auth.exception.JwtAuthException
import com.pinyougou.web.common.ResponseCode
import com.pinyougou.web.common.entity.ServerResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author 邱长海
 */
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        response.contentType = "application/json;charset=UTF-8"
        val responseBody =
                if (exception is JwtAuthException) {
                    ServerResponse.error<Any>(exception.message)
                } else {
                    ServerResponse.error<Any>(ResponseCode.NEED_LOGIN)
                }
        response.writer.write(JsonUtils.get().toJson(responseBody))
    }

}