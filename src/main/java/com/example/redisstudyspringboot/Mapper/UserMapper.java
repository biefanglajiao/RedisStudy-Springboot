package com.example.redisstudyspringboot.Mapper;

import com.example.redisstudyspringboot.Cache.MybatisRedisCache;
import com.example.redisstudyspringboot.entity.Account;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@CacheNamespace(implementation = MybatisRedisCache.class)
@Mapper
public interface UserMapper {
    @Select("select * from account where username =#{username}")
    Account getAccountByUsername(String username);
}
