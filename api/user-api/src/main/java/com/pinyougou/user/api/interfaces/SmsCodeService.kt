package com.pinyougou.user.api.interfaces


/**
 * @author 邱长海
 */
interface SmsCodeService {
    fun sendSmsCode(mobile: String): Int

    fun verifySmsCode(mobile: String, code: String): Boolean

}