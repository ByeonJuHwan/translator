package com.byeon.translator;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest
public abstract class AbstractContainerBaseTest {
    static final GenericContainer REDIS_CONTAINER;

    static {
        REDIS_CONTAINER = new GenericContainer<>("redis:latest")
                .withExposedPorts(6379);

        REDIS_CONTAINER.start();

        System.setProperty("spring.redis.host", REDIS_CONTAINER.getHost());
        System.setProperty("spring.redis.port", REDIS_CONTAINER.getMappedPort(6379).toString());
    }
}
