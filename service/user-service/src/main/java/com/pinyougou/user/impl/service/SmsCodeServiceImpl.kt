package com.pinyougou.user.impl.service

import com.alibaba.dubbo.config.annotation.Service
import com.pinyougou.common.util.MD5Utils
import com.pinyougou.user.api.interfaces.SmsCodeService
import com.pinyougou.user.api.pojo.SmsCode
import com.pinyougou.user.api.pojo.User
import com.pinyougou.user.impl.dao.SmsCodeMapper
import com.pinyougou.user.impl.util.URL
import com.pinyougou.web.common.exception.ResponseException
import com.pinyougou.web.common.util.logger
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.security.MessageDigest
import kotlin.random.Random
import kotlin.random.nextInt
import sun.security.krb5.Confounder.bytes
import java.nio.charset.Charset
import kotlin.experimental.and


/**
 * @author 邱长海
 */
@Service(version = "1.0")
@Transactional(rollbackForClassName = ["java.lang.Exception"])
open class SmsCodeServiceImpl : SmsCodeService {
//    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var httpClient: OkHttpClient

    @Autowired
    private lateinit var smsCodeMapper: SmsCodeMapper

    override fun sendSmsCode(mobile: String): Int {
        val code = (100000..999999).random()
        val appKey = "95d97b8d61c67e3c00f75dee471ccd6b"
        val curTime = System.currentTimeMillis().toString()
        val nonce = MD5Utils.getMD5(code.toString())
        val checkSum = MD5Utils.getSha1("$appKey$nonce$curTime")

        if (smsCodeMapper.insert(SmsCode(mobile = mobile, smsCode = code, use = false)) <= 0) {
            throw ResponseException("验证码发送失败,请稍后重试")
        }
        val response = httpClient.newCall(Request.Builder()
                .url(URL.SEND_SMS_CODE)
                .post(FormBody.Builder()
                        .add("mobile", mobile)
                        .add("authCode", code.toString()).build())
                .headers(Headers.of(mapOf("AppKey" to appKey,
                        "CurTime" to curTime,
                        "CheckSum" to checkSum
                ))).build()).execute()
        if (response.code() == 200) {
            logger.info(response.body()?.string())
            return code
        } else {
            throw ResponseException("验证码发送失败,请稍后重试")
        }
    }

    override fun verifySmsCode(mobile: String, code: String): Boolean {
        val codeByMobile = smsCodeMapper.selectByMobile(mobile)
        val success = code == codeByMobile?.smsCode?.toString()
        if (success) {
            smsCodeMapper.update(SmsCode(id = codeByMobile.id, use = true))
        }
        return success
    }

}