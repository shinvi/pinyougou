package com.pinyougou.dao.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author 邱长海
 */
@ConfigurationProperties(prefix = "spring.datasource.druid")
class DruidDataSourceProperties extends DaoProperties.DaoDruidProperties {
    public DruidDataSourceProperties(){

    }

}
