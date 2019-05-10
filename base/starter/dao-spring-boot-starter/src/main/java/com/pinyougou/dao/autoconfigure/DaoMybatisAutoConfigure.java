package com.pinyougou.dao.autoconfigure;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author 邱长海
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@ConditionalOnBean({DataSource.class})
@EnableConfigurationProperties({MybatisProperties.class, DaoProperties.class})
@AutoConfigureBefore({MybatisAutoConfiguration.class})
public class DaoMybatisAutoConfigure {
    public DaoMybatisAutoConfigure(MybatisProperties properties, DaoProperties daoProperties) {
        if (properties.getMapperLocations() == null || properties.getMapperLocations().length == 0) {
            properties.setMapperLocations(Arrays.copyOf(daoProperties.getMybatis().getMapperLocations(),
                    daoProperties.getMybatis().getMapperLocations().length));
        }
        if (properties.getConfiguration() == null) {
            properties.setConfiguration(daoProperties.getMybatis().getConfiguration());
        } else if (!properties.getConfiguration().isMapUnderscoreToCamelCase()) {
            properties.getConfiguration().setMapUnderscoreToCamelCase(true);
        }
    }
}
