package com.pinyougou.dao.autoconfigure;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * @author 邱长海
 */
@Component
@PropertySource("classpath:/META-INF/dao-config.properties")
@ConfigurationProperties(prefix = "dao")
class DaoProperties {

    private DaoJdbcProperties jdbc;
    private DaoDruidProperties druid;
    private DaoMyBatisProperties mybatis;
    private DaoPageHelperProperties pagehelper;

    public DaoDruidProperties getDruid() {
        return druid;
    }

    public void setDruid(DaoDruidProperties druid) {
        this.druid = druid;
    }

    public DaoJdbcProperties getJdbc() {
        return jdbc;
    }

    public void setJdbc(DaoJdbcProperties jdbc) {
        this.jdbc = jdbc;
    }

    public DaoMyBatisProperties getMybatis() {
        return mybatis;
    }

    public void setMybatis(DaoMyBatisProperties mybatis) {
        this.mybatis = mybatis;
    }

    public DaoPageHelperProperties getPagehelper() {
        return pagehelper;
    }

    public void setPagehelper(DaoPageHelperProperties pagehelper) {
        this.pagehelper = pagehelper;
    }

    static class DaoJdbcProperties extends DataSourceProperties {
    }

    static class DaoDruidProperties extends DruidDataSource {
        public DaoDruidProperties() {
            maxActive = 0;
            minIdle = 0;
        }
    }

    static class DaoMyBatisProperties extends MybatisProperties {
    }

    static class DaoPageHelperProperties extends PageHelperProperties {

    }
}
