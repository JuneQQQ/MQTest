package com.example.mqtest;

import jakarta.annotation.Resource;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import scala.jdk.FunctionWrappers;

import java.util.logging.Logger;

@SpringBootTest
class RabbitMQTest {
    Logger log = Logger.getLogger("rabbitmq");
    @Resource
    RabbitTemplate rabbitTemplate;


    @Test
    void contextLoads() {
        // 先建立连接，避免误差
        rabbitTemplate.convertAndSend("e1", "b1", "test");

        long l0 = System.currentTimeMillis();
        long l1 = System.currentTimeMillis();
        for (int i = 0; i < 500_0000; i++) {
            // 每条消息 48byte
            rabbitTemplate.convertAndSend("e2", "b2", RandomString.make(12));
            if (i % 10_0000 == 0) {
                log.info("当前已插入数量 : " + i + "条");
                log.info("插入最近十万条耗时 : " + (System.currentTimeMillis() - l1) + "ms");
                log.info("整体平均值 : " + i / (System.currentTimeMillis() - l0) + "条/ms 条");
                l1 = System.currentTimeMillis();
            }
        }
        log.info("total-cost:" + (System.currentTimeMillis() - l0) + " ms");
    }

}
