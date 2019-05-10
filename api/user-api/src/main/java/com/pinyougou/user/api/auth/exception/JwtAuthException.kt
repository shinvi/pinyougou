package com.pinyougou.user.api.auth.exception

import org.springframework.security.core.AuthenticationException

/**
 * @author 邱长海
 */
class JwtAuthException(msg: String, t: Throwable?) : AuthenticationException(msg, t) {
    constructor(msg: String) : this(msg, null)
}