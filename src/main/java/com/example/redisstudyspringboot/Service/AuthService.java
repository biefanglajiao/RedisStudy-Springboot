package com.example.redisstudyspringboot.Service;

import com.example.redisstudyspringboot.Mapper.UserMapper;
import com.example.redisstudyspringboot.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserMapper mapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account=mapper.getAccountByUsername(username);
        if (account==null)throw new UsernameNotFoundException("用户名为空");
        return User.withUsername(username)
                .password(account.getPassword())
                .roles(account.getRole())
                .build();

    }
}
