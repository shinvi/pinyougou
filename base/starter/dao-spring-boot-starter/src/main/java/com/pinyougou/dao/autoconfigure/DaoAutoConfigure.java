package com.pinyougou.dao.autoconfigure;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * @author 邱长海
 */

@Configuration
@ConditionalOnClass({DruidDataSource.class})
@AutoConfigureBefore({DruidDataSourceAutoConfigure.class})
@EnableConfigurationProperties({DataSourceProperties.class, DaoProperties.class})
public class DaoAutoConfigure {

    public DaoAutoConfigure(DataSourceProperties dataSourceProperties, DaoProperties daoProperties) {
        if (Strings.isBlank(dataSourceProperties.getUrl())) {
            dataSourceProperties.setUrl(daoProperties.getJdbc().getUrl());
        }
        if (Strings.isBlank(dataSourceProperties.getDriverClassName())) {
            dataSourceProperties.setDriverClassName(daoProperties.getJdbc().getDriverClassName());
        }
        if (Strings.isBlank(dataSourceProperties.getUsername())) {
            dataSourceProperties.setUsername(daoProperties.getJdbc().getUsername());
        }
        if (Strings.isBlank(dataSourceProperties.getPassword())) {
            dataSourceProperties.setPassword(daoProperties.getJdbc().getPassword());
        }
        if (dataSourceProperties.getType() == null) {
            dataSourceProperties.setType(daoProperties.getJdbc().getType());
        }
    }

    @Bean(initMethod = "init")
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
