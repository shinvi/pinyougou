package com.pinyougou.rpc.autoconfigure;

import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author 邱长海
 */
@Component
@PropertySource("classpath:/META-INF/rpc-config.properties")
@ConfigurationProperties(prefix = "rpc")
public class RpcProperties {

    private RpcDubboProperties dubbo;

    public RpcDubboProperties getDubbo() {
        return dubbo;
    }

    public void setDubbo(RpcDubboProperties dubbo) {
        this.dubbo = dubbo;
    }

    public static class RpcDubboProperties {
        private RpcDubboRegistryProperties registry;
        private RpcDubboConsumerProperties consumer;
        private RpcDubboProviderProperties provider;

        public RpcDubboProviderProperties getProvider() {
            return provider;
        }

        public void setProvider(RpcDubboProviderProperties provider) {
            this.provider = provider;
        }

        public RpcDubboConsumerProperties getConsumer() {
            return consumer;
        }

        public void setConsumer(RpcDubboConsumerProperties consumer) {
            this.consumer = consumer;
        }

        public RpcDubboRegistryProperties getRegistry() {
            return registry;
        }

        public void setRegistry(RpcDubboRegistryProperties registry) {
            this.registry = registry;
        }

        public static class RpcDubboRegistryProperties extends RegistryConfig {

        }

        public static class RpcDubboConsumerProperties extends ConsumerConfig {

        }

        public static class RpcDubboProviderProperties extends ProviderConfig {
        }

    }

}
