package com.tdeado.apidoc.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @author leaf
 * @date 2017-03-09 15:29
 */
public class ApiDocConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "apidoc", name = "enable", matchIfMissing = true)
    public ApiDocController xDocController() {
        return new ApiDocController();
    }
}
