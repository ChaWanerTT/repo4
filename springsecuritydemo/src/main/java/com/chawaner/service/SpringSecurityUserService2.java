package com.chawaner.service;

import com.chawaner.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 要从数据库动态查询用户信息，就必须按照spring security框架的要求提供一个实现
 * UserDetailsService接口的实现类，并按照框架的要求进行配置即可。
 * 框架会自动调用实现类中的方法，并自动进行密码校验。
 */
public class SpringSecurityUserService2 implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //模拟数据库中的用户数据
    public  Map<String, User> map = new HashMap<>();

    //初始化数据
    public void initUserData(){
        User user1 = new User();
        user1.setUsername("admin");
        //使用bcrypt对密码进行
        user1.setPassword(passwordEncoder.encode("admin"));

        User user2 = new User();
        user2.setUsername("xiaoming");
        user2.setPassword(passwordEncoder.encode("1234"));

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }


    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        initUserData();

        System.out.println("用户输入的用户名为：" + username);

        //根据用户名查询数据库获得用户信息（包含数据库中存储的密码信息）
        User user = map.get(username);
        if (user == null){
            //用户名不存在
            return null;
        }else {
            //用户名存在
            //将用户信息返回给框架
            //框架会进行秘密比对（页面提交的密码和数据库中查询的密码进行比对）
            List<GrantedAuthority> list = new ArrayList<>();

            //为当前登录用户授权，后期需要改为从数据库中查询当前用户对应的权限
            //授权
            list.add(new SimpleGrantedAuthority("permission_A"));
            list.add(new SimpleGrantedAuthority("permission_B"));

            if (username.equals("admin")){
                //授予角色
                list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                //授权
                list.add(new SimpleGrantedAuthority("add"));
            }

            org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);

            return securityUser;
        }

    }
}















