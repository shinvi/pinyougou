package com.pinyougou.user.api.interfaces

import com.pinyougou.user.api.pojo.User

/**
 * @author 邱长海
 */
interface UserService {
    fun register(user: User): User

    fun getUserByUsername(username: String): User?

    fun createJwtTokenByUser(user: User): String

    fun parseUserFromJwtToken(token: String): User?

}