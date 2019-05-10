package com.pinyougou.user.impl.util

import com.pinyougou.common.util.JsonUtils
import com.pinyougou.common.util.MD5Utils
import com.pinyougou.user.api.pojo.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * @author 邱长海
 */
@Configuration
@EnableConfigurationProperties(JwtProperties::class)
open class JwtHelper {

    @Autowired
    private lateinit var jwtProperties: JwtProperties

    fun createToken(user: User): String {
        user.password = null
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, jwtProperties.secret)
                .setClaims(mapOf(Pair("token", MD5Utils.getMD5(System.currentTimeMillis().toString())),
                        Pair("sub", JsonUtils.get().toJson(user))))
                .compact()
    }

    fun getUserFromToken(token: String): User {
        return JsonUtils.get().fromJson(Jwts.parser().setSigningKey(jwtProperties.secret)
                .parseClaimsJws(token).body.subject, User::class.java)
    }

    @Bean
    open fun encoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

@ConfigurationProperties("jwt")
data class JwtProperties(var secret: String = "")