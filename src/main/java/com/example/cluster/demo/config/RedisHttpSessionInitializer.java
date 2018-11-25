package com.example.cluster.demo.config;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class RedisHttpSessionInitializer extends AbstractHttpSessionApplicationInitializer {

    public RedisHttpSessionInitializer() {
        super(RedisConfig.class);
    }
}
