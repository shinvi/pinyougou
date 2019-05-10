package com.pinyougou.user.api.auth.filter

import com.pinyougou.common.Const
import com.pinyougou.common.util.JsonUtils
import com.pinyougou.user.api.interfaces.UserService
import com.pinyougou.user.api.util.JWT_HEADER_VALUE_PREFIX
import com.pinyougou.web.common.ResponseCode
import com.pinyougou.web.common.entity.ServerResponse
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author 邱长海
 */
class JwtAuthorizationFilter(authenticationManager: AuthenticationManager, private val userService: UserService,
                             private val redis: StringRedisTemplate, private val singleLogin: Boolean) : BasicAuthenticationFilter(authenticationManager) {
    constructor(authenticationManager: AuthenticationManager, userService: UserService,
                redis: StringRedisTemplate) : this(authenticationManager, userService, redis, false)

    /**
     * 从header中取出jwt并与redis里存储的同一个用户的jwt进行验证,singleLogin为true时,只有两个jwt完全一致方可验证通过,false时,
     * 只要两个jwt中的username相同即可验证通过
     * singleLogin代表是否只允许只能有一处登录
     */
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        var token = request.getHeader("Authorization")
        if (token != null && token.startsWith(JWT_HEADER_VALUE_PREFIX)) {
            token = token.replace("$JWT_HEADER_VALUE_PREFIX ", "")
            val user = userService.parseUserFromJwtToken(token)
            user?.let {
                val inRedisToken = redis.opsForValue().get("${Const.USER_TOKEN}.${user.username}")
                if (inRedisToken == null || inRedisToken.isEmpty()) {
                    return needLoginResponse(response)
                }

                val inRedisUser = userService.parseUserFromJwtToken(inRedisToken)
                if (token == inRedisToken || (it.username == inRedisUser?.username && !singleLogin)) {
                    SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(it.username,
                            it.password, listOf())
                    request.setAttribute(Const.USER, inRedisUser)
                } else if (singleLogin) {
                    return needLoginResponse(response)
                }
            }
            chain.doFilter(request, response)
        } else {
            chain.doFilter(request, response)
        }
    }

    private fun needLoginResponse(response: HttpServletResponse) {
        response.contentType = "application/json;charset=UTF-8"
        response.writer.write(JsonUtils.get().toJson(ServerResponse.error<Any>(ResponseCode.NEED_LOGIN)))
    }
}