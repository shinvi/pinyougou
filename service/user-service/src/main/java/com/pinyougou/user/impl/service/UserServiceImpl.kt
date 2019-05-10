package com.pinyougou.user.impl.service

import com.alibaba.dubbo.config.annotation.Service
import com.pinyougou.user.api.interfaces.UserService
import com.pinyougou.user.api.pojo.User
import com.pinyougou.user.impl.dao.UserMapper
import com.pinyougou.user.impl.util.JwtHelper
import com.pinyougou.web.common.exception.ResponseException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

/**
 * @author 邱长海
 */
@Service(version = "1.0")
@Transactional(rollbackForClassName = ["java.lang.Exception"])
open class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var encoder: BCryptPasswordEncoder

    @Autowired
    private lateinit var jwtHelper: JwtHelper

    override fun register(user: User): User {
        if (userMapper.countByUsername(user.username) > 0) {
            throw ResponseException("用户名已存在")
        }
        user.password = encoder.encode(user.password)
        user.status = 1
        user.accountBalance = BigDecimal.ZERO
        user.isEmailCheck = 0
        user.isMobileCheck = 0
        user.userLevel = 1
        user.points = 0
        user.experienceValue = 0
        user.lastLoginTime = null
        if (userMapper.insert(user) <= 0) {
            throw ResponseException("用户注册失败")
        }
        return user
    }

    override fun getUserByUsername(username: String): User? {
        return userMapper.selectByUsernameOrPhone(username)
    }

    override fun createJwtTokenByUser(user: User): String {
        return jwtHelper.createToken(user)
    }

    override fun parseUserFromJwtToken(token: String): User? {
        return try {
            jwtHelper.getUserFromToken(token)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}