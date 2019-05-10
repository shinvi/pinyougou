package com.pinyougou.sellergoods.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import com.pinyougou.sellergoods.impl.dao.SpecificationMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerGoodsServiceApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private MybatisProperties mybatisProperties;

    @Autowired
    private PageHelperProperties pageHelperProperties;

    @Autowired
    private SpecificationMapper specificationMapper;

    @Test
    public void contextLoads() {
        System.out.println(((DruidDataSource) dataSource).getUrl());
        System.out.println(((DruidDataSource) dataSource).getMaxActive());
        System.out.println(Arrays.toString(mybatisProperties.getMapperLocations()));
        System.out.println(pageHelperProperties.getHelperDialect());
    }


    @Test
    public void testMapper(){

    }

}

