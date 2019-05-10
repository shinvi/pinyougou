package com.pinyougou.web.common;

import com.pinyougou.web.common.aop.RequestValidatorAop;
import com.pinyougou.web.common.entity.ServerResponse;
import com.pinyougou.web.common.exception.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 解决ajax跨域问题
 *
 * @author 邱长海
 */
@Configuration
public class WebCommonAutoConfigure {

    public WebCommonAutoConfigure() {
    }

    @Bean
    public CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 2允许任何头
        corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter(CorsConfiguration config) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 4
        return new CorsFilter(source);
    }

    @Bean
    public RequestValidatorAop requestValidatorAop() {
        return new RequestValidatorAop();
    }

    @Configuration
    static class SrpingMvcConfig implements WebMvcConfigurer {


        @Override
        public void addInterceptors(InterceptorRegistry registry) {
        }
    }

    @Configuration
    @ControllerAdvice
    static class ControllerExceptionAdvice {

        private Logger logger = LoggerFactory.getLogger(getClass());

        @ExceptionHandler(value = Exception.class)
        @ResponseBody
        public ServerResponse handleException(Throwable e) {
            ServerResponse response;
            if (e instanceof ResponseException) {
                if (StringUtils.isEmpty(((ResponseException) e).getResponseCode().getDesc())) {
                    return ServerResponse.error(e.getMessage());
                }
                response = ServerResponse.error(((ResponseException) e).getResponseCode());
            } else {
                if (e instanceof UndeclaredThrowableException
                        && ((UndeclaredThrowableException) e).getUndeclaredThrowable() instanceof InvocationTargetException) {
                    e = ((InvocationTargetException) ((UndeclaredThrowableException) e).getUndeclaredThrowable()).getTargetException();
                }
                logger.error("Controller异常", e);
                String msg = e.getCause() == null ? e.getMessage() : e.getCause().getMessage();
                response = ServerResponse.error(msg);
            }
            return response;
        }
    }
}