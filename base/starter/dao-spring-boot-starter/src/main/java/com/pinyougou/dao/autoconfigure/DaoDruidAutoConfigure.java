package com.pinyougou.dao.autoconfigure;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author 邱长海
 */
@Configuration
@AutoConfigureAfter({DruidDataSourceAutoConfigure.class})
@ConditionalOnClass({DruidDataSource.class})
@EnableConfigurationProperties({DaoProperties.class, DruidDataSourceProperties.class})
@ConditionalOnBean({DataSource.class})
@EnableTransactionManagement
public class DaoDruidAutoConfigure {
    public DaoDruidAutoConfigure(@Qualifier("dataSource") DataSource dataSource, DaoProperties daoProperties,
                                 DruidDataSourceProperties druidDataSourceProperties) {
        if (dataSource instanceof DruidDataSource) {
            if (druidDataSourceProperties.getMaxActive() == 0) {
                ((DruidDataSource) dataSource).setMaxActive(daoProperties.getDruid().getMaxActive());
            }
            if (druidDataSourceProperties.getMinIdle() == 0) {
                ((DruidDataSource) dataSource).setMinIdle(daoProperties.getDruid().getMinIdle());
            }
        }
    }

    @Bean
    @ConditionalOnMissingBean({PlatformTransactionManager.class})
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setRollbackOnCommitFailure(true);
        return transactionManager;
    }
}
