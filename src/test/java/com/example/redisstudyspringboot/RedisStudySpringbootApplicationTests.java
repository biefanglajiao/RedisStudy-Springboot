package com.example.redisstudyspringboot;

import com.example.redisstudyspringboot.Mapper.CacheMapper;
import com.example.redisstudyspringboot.Service.RedisService;
import com.example.redisstudyspringboot.Service.RedisService2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.core.StringRedisTemplate;


import javax.annotation.Resource;

@SpringBootTest
class RedisStudySpringbootApplicationTests {
    /******
     *使用
     *java进行对redis的操作
     */
    @Resource
    StringRedisTemplate template;  //string类型的封装数据

    @Test
    void contextLoads() {
//    template.opsFor**   封装了redis操作
        template.opsForValue().set("test1", "777777");//写入test1的值为777777
        System.out.println(template.opsForValue().get("test1"));

        template.delete("test1");  //删除test1
        System.out.println(template.hasKey("test1"));  //查询是否存在
    }

    /*****
     * 使用idbc的事务管理来进行 redis的事务管理操作
     */
    @Resource
    RedisService service;

    @Test
//使用事务管理redis
    void aa() {
        service.test();
    }

    /*****
     * 使用idbc的事务管理来进行 redis的事务管理操作
     * 传入json对象
     */
    @Resource
    RedisService2 service2;

    @Test
    void aaa() {
        service2.aa();
    }


    /****
     * 使用redis实现Mybatis二级缓存
     *
     */
    @Autowired
    CacheMapper mapper;
    @Test
    void cache(){
        mapper.getSid();
        mapper.getSid();
        mapper.getSid();
    }
}
