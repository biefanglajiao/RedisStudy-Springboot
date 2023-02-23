package com.example.redisstudyspringboot.Config;

import com.example.redisstudyspringboot.Cache.RedisTokenRepository;
import com.example.redisstudyspringboot.Service.AuthService;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;
@Configuration
public class SecurityConfiguration{
    @Resource
    RedisTokenRepository redisTokenRepository;
    @Resource
    AuthService authService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{

        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws  Exception{
        httpSecurity
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .rememberMe()
                .userDetailsService(authService)
                .tokenRepository(redisTokenRepository);
        return httpSecurity.build();
    }

}



//旧版写法
//@Configuration
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Resource
//    RedisTokenRepository redisTokenRepository;
//    @Resource
//    AuthService authService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(authService)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//     http .authorizeRequests()
//             .anyRequest().authenticated()
//             .and()
//             .formLogin()
//             .and()
//             .rememberMe()
//             .tokenRepository(redisTokenRepository);
//    }
//
//
//
//}
