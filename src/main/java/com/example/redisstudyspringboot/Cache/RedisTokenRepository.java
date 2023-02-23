package com.example.redisstudyspringboot.Cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/***
 * 之前使用SpringSecurity时，remember-me的Token是支持持久化存储的，而我们当时是存储在数据库中，现在将Token信息能否存储在缓存中
 */
@Component
public class RedisTokenRepository implements PersistentTokenRepository {
    //创建一个key前缀 方便区分
    private final static String REMEMBER_ME_KEY ="spring:security:rememberMe:";
    @Resource
    RedisTemplate<Object,Object> template;


    //创建
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        //这里要放两个，一个存seriesId->Token，一个存username->seriesId，因为删除时是通过username删除
        template.opsForValue().set(REMEMBER_ME_KEY+"username:"+token.getUsername(),token.getSeries());
        template.expire(REMEMBER_ME_KEY+"username:"+token.getUsername(),1, TimeUnit.MINUTES        );
this.setToken(token);
    }

    //更新  	//先获取，然后修改创建一个新的，再放入
    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentRememberMeToken token = this.getToken(series);
        if(token != null)
            this.setToken(new PersistentRememberMeToken(token.getUsername(), series, tokenValue, lastUsed));
    }

    //获取
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return this.getToken(seriesId);
    }

    //删除
    @Override
    public void removeUserTokens(String username) {
        String series = (String) template.opsForValue().get(REMEMBER_ME_KEY+"username:"+username);
        template.delete(REMEMBER_ME_KEY+series);
        template.delete(REMEMBER_ME_KEY+"username:"+username);

    }
    //由于PersistentRememberMeToken没实现序列化接口，这里只能用Hash来存储了，所以单独编写一个set和get操作
    private PersistentRememberMeToken getToken(String series){
        Map<Object, Object> map = template.opsForHash().entries(REMEMBER_ME_KEY+series);
        if(map.isEmpty()) return null;
        return new PersistentRememberMeToken(
                (String) map.get("username"),
                (String) map.get("series"),
                (String) map.get("tokenValue"),
                new Date(Long.parseLong((String) map.get("date"))));
    }

    private void setToken(PersistentRememberMeToken token){
        Map<String, String> map = new HashMap<>();
        map.put("username", token.getUsername());
        map.put("series", token.getSeries());
        map.put("tokenValue", token.getTokenValue());
        map.put("date", ""+token.getDate().getTime());
        template.opsForHash().putAll(REMEMBER_ME_KEY+token.getSeries(), map);
        template.expire(REMEMBER_ME_KEY+token.getSeries(), 1, TimeUnit.DAYS);
    }
}
