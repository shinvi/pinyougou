package com.pinyougou.dao.autoconfigure;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 邱长海
 */
@Configuration
@ConditionalOnBean({SqlSessionFactory.class})
@EnableConfigurationProperties({PageHelperProperties.class, DaoProperties.class})
@AutoConfigureBefore({PageHelperAutoConfiguration.class})
public class DaoPageHelperAutoConfigure {
    public DaoPageHelperAutoConfigure(PageHelperProperties pageHelperProperties, DaoProperties daoProperties) {
        if (Strings.isBlank(pageHelperProperties.getHelperDialect())) {
            pageHelperProperties.setHelperDialect(daoProperties.getPagehelper().getHelperDialect());
        }
        if (Strings.isBlank(pageHelperProperties.getReasonable())) {
            pageHelperProperties.setReasonable(daoProperties.getPagehelper().getReasonable());
        }
    }
}
