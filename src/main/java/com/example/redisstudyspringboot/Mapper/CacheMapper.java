package com.example.redisstudyspringboot.Mapper;

import com.example.redisstudyspringboot.Cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@CacheNamespace(implementation = MybatisRedisCache.class)
@Mapper
public interface CacheMapper {
    @Select("select name from student where sid =1")
    String getSid();
}
