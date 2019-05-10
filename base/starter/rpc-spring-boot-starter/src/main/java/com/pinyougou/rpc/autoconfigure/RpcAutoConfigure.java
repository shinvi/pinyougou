package com.pinyougou.rpc.autoconfigure;

import com.alibaba.boot.dubbo.autoconfigure.DubboAutoConfiguration;
import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author 邱长海
 */
@Configuration
@ConditionalOnClass({AbstractConfig.class})
@AutoConfigureBefore({DubboAutoConfiguration.class})
@EnableConfigurationProperties({RpcProperties.class})
public class RpcAutoConfigure {

    public RpcAutoConfigure() {

    }

    @Bean
    @ConditionalOnMissingBean({RegistryConfig.class})
    public RegistryConfig registryConfig(RpcProperties rpcProperties) {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(rpcProperties.getDubbo().getRegistry().getAddress());
        registryConfig.setClient(rpcProperties.getDubbo().getRegistry().getClient());
        return registryConfig;
    }

    @Bean
    @ConditionalOnMissingBean({ConsumerConfig.class})
    public ConsumerConfig consumerConfig(RpcProperties rpcProperties) {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(rpcProperties.getDubbo().getConsumer().getTimeout());
        return consumerConfig;
    }

    @Bean
    @ConditionalOnMissingBean({ProviderConfig.class})
    public ProviderConfig providerConfig(RpcProperties rpcProperties) {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setFilter(rpcProperties.getDubbo().getProvider().getFilter());
        return providerConfig;
    }
}
