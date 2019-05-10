package com.pinyougou.user.api.auth.provider

import com.pinyougou.user.api.auth.token.SmsCodeAuthenticationToken
import com.pinyougou.user.api.interfaces.SmsCodeService
import com.pinyougou.user.api.interfaces.UserService
import com.pinyougou.user.api.pojo.JwtUser
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.util.Assert

/**
 * @author 邱长海
 */
class SmsCodeAuthenticationProvider(private val smsCodeService: SmsCodeService,
                                    private val userDetailsService: UserDetailsService) : AuthenticationProvider {

    override fun authenticate(auth: Authentication): Authentication {
        Assert.isInstanceOf(SmsCodeAuthenticationToken::class.java, auth,
                "Only SmsCodeAuthenticationToken is supported")

        val mobile = auth.principal.toString().trim()
        val code = auth.credentials.toString().trim()

        val user = userDetailsService.loadUserByUsername(mobile)

        if (smsCodeService.verifySmsCode(mobile, code)) {
            return SmsCodeAuthenticationToken(user as JwtUser, user.authorities)
        } else {
            throw BadCredentialsException("验证码效验失败")
        }
    }

    override fun supports(clazz: Class<*>?): Boolean {
        return SmsCodeAuthenticationToken::class.java.isAssignableFrom(clazz)
    }

}