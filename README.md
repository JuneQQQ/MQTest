# 压测数据

> 环境
>
> - Apple M1 16G MacOS Ventura 13.2.1
> - 中间件无特殊说明均通过docker单机部署，无性能限制





## RabbitMQ



### 1. fanout 模式插入

- 全单环境：一个binding、exchange、queue
- 无持久化

```java
@Test
void contextLoads() {
    // 先建立连接，避免误差
    rabbitTemplate.convertAndSend("e1", "b1", "test");

    long l0 = System.currentTimeMillis();
    long l1 = System.currentTimeMillis();
    for (int i = 0; i < 500_0000; i++) {
        // 每条消息 48byte
        rabbitTemplate.convertAndSend("e1", "b1", RandomString.make(12));
        if (i % 10_0000 == 0) {
            log.info("当前已插入数量 : " + i + "条");
            log.info("插入最近十万条耗时 : " + (System.currentTimeMillis() - l1) + "ms");
            log.info("整体平均值 : " + i / (System.currentTimeMillis() - l0) + "条/ms 条");
            l1 = System.currentTimeMillis();
        }
    }
    log.info("total-cost:" + (System.currentTimeMillis() - l0) + " ms");
}
```



<img src="assets/image-20230328230603002.png" alt="image-20230328230603002" style="zoom:50%;" />

20k~60k 之间浮动，平均30k/s，与网上测试数据一致



