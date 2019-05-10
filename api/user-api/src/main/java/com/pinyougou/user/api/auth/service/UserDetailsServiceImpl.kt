package com.pinyougou.user.api.auth.service

import com.pinyougou.user.api.auth.exception.JwtAuthException
import com.pinyougou.user.api.interfaces.UserService
import com.pinyougou.user.api.pojo.JwtUser
import com.pinyougou.web.common.exception.ResponseException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

/**
 * @author 邱长海
 */
class UserDetailsServiceImpl(private val userService: UserService) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        username ?: throw JwtAuthException("用户名不存在")
        val user = userService.getUserByUsername(username) ?: throw JwtAuthException("用户名不存在")
        return JwtUser(user)
    }
}