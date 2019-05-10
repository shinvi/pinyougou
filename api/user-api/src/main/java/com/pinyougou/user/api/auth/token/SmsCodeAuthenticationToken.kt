package com.pinyougou.user.api.auth.token

import com.pinyougou.user.api.pojo.JwtUser
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

/**
 * @author 邱长海
 */
class SmsCodeAuthenticationToken : AbstractAuthenticationToken {
    private val mobile: String
    private val code: String
    private val user: JwtUser?

    constructor(user: JwtUser, authorities: MutableCollection<out GrantedAuthority>) : super(authorities) {
        this.mobile = ""
        this.code = ""
        this.user = user
        isAuthenticated = true
    }

    constructor(mobile: String, code: String) : super(null) {
        this.mobile = mobile
        this.code = code
        this.user = null
        isAuthenticated = false
    }

    override fun getCredentials(): Any {
        return code
    }

    override fun getPrincipal(): Any {
        return user ?: mobile
    }

}