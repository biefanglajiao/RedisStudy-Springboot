package com.example.redisstudyspringboot.Cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.TimeUnit;


/***
 * redis实现mybatis的二级缓存
 */
public class MybatisRedisCache implements Cache {
    //初始化两个 参数
    private final String id;
    private static RedisTemplate<Object,Object> template;
    //构造方法必须带一个String类型的参数接受id
    public MybatisRedisCache(String id){
        this.id=id;
    }
    //初始化时通过配置类将RedisTemplate给过来    通过config配置
    public static void setTemplate(RedisTemplate<Object,Object> template){
        MybatisRedisCache.template=template;
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object o, Object o1) {
//这里直接向Redis数据库中丢数据，o为key，o1为value， 60秒过期
        template.opsForValue().set(o,o1,60, TimeUnit.SECONDS);
    }

    @Override
    public Object getObject(Object o) {
        //这里根据key获取值
        return template.opsForValue().get(o);
    }

    @Override
    public Object removeObject(Object o) {
        //这里根据key删除
        return template.delete(o);
    }

    @Override
    public void clear() {
    //template中没有封装清空  借助connecion对象来实现清空数据库

//        template.getConnectionFactory().getConnection().flushDb();
        //template中没有封装清空
        template.execute((RedisCallback<Void>) connection ->{
            connection.flushDb();
            return null;
        });
    }

    @Override
    public int getSize() {
        //template中没有封装   借助connnection对象获取当前key数量  方法二选一
//        return template.getConnectionFactory().getConnection().dbSize().intValue();
        return template.execute(RedisServerCommands::dbSize).intValue();
    }

}
