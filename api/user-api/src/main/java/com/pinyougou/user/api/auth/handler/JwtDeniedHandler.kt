package com.pinyougou.user.api.auth.handler

import com.pinyougou.common.util.JsonUtils
import com.pinyougou.web.common.ResponseCode
import com.pinyougou.web.common.entity.ServerResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author 邱长海
 */
class JwtDeniedHandler:AccessDeniedHandler{
    override fun handle(request: HttpServletRequest, response: HttpServletResponse, exception: AccessDeniedException) {
        response.contentType = "application/json;charset=UTF-8"
        response.writer.write(JsonUtils.get().toJson(ServerResponse.error<Any>(ResponseCode.PERMISSION_ERROR)))
    }
}