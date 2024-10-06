package com.campus.mapper;

import com.utils.RedisIdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class testRedis {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private RedisIdUtil redisIdUtil;

    private ExecutorService es = Executors.newFixedThreadPool(500);

    @Test
    void testString() {
        redisTemplate.opsForValue().set("测试","测试结果");

        Object name = redisTemplate.opsForValue().get("测试");
        System.out.println("name=" + name);
    }


    @Test
    void testRedisId() throws InterruptedException {
        System.out.println("开始打印");
        CountDownLatch countDownLatch = new CountDownLatch(300);
        Runnable task = () -> {
           for(int i = 0; i < 100; i++) {
               long id = redisIdUtil.nextId("order");
               System.out.println("id = " + id);
           }
           countDownLatch.countDown();
        };
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 300; i++) {
            es.submit(task);
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("time = " + (end - begin));
    }
}
