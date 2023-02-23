package com.example.redisstudyspringboot.Config;

import com.example.redisstudyspringboot.Cache.MybatisRedisCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**********
 * 初始化配置类 将RedisTemplate给到  MybatisRedisCache
 */
@Configuration
public class MybatisConfiguration {
@Resource
    RedisTemplate<Object,Object> template;

@PostConstruct
    public void init(){
    MybatisRedisCache.setTemplate(template);
}
}
