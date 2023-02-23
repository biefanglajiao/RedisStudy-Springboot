package com.example.redisstudyspringboot.Service;

import com.example.redisstudyspringboot.entity.Student;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/****
 * 使用事务管理来进行 redis的使用
 *通过serializer实现json格式的存储
 * 在test测试中调用
 */
@Service
public class RedisService2 {
    @Resource
    RedisTemplate<Object ,Object> template;

    @PostConstruct
    public void init(){
        template.setEnableTransactionSupport(true);//开启事务
        template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));}//转json

    @Transactional
    public void aa(){
        template.multi();
        template.opsForValue().set("test3",new Student());
        template.exec();
    }

}
