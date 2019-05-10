package com.pinyougou.sellergoods.impl;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 邱长海
 */
@SpringBootApplication
@DubboComponentScan(basePackages = "com.pinyougou.sellergoods.impl.service")
public class SellerGoodsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellerGoodsServiceApplication.class, args);
    }

}

