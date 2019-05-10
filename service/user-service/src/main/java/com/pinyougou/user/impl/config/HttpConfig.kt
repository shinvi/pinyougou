package com.pinyougou.user.impl.config

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author 邱长海
 */
@Configuration
open class HttpConfig {

    @Bean
    open fun okhttp(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}