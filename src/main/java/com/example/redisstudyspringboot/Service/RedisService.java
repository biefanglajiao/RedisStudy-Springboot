package com.example.redisstudyspringboot.Service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/****
 * 使用事务管理来进行 redis的使用
 *
 * 在test测试中调用
 */
@Service
public class RedisService {
    @Resource
    StringRedisTemplate template;

    @PostConstruct
    public void init(){
        template.setEnableTransactionSupport(true);//开启事务
    }
    @Transactional
    public  void test(){
        template.multi();
        template.opsForValue().set("test2","czh");
        template.exec();
    }
}
