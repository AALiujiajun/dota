package com.wanguliuwang.dota.service;

import com.wanguliuwang.dota.Mapper.UserMapper;
import com.wanguliuwang.dota.model.User;
import com.wanguliuwang.dota.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        User user1=new User();
        if(users.size() ==0)
        {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            return user;
        }
        else
        {
             user1=users.get(0);
            user1.setGmtModified(System.currentTimeMillis());
            user1.setAvatarUrl(user.getAvatarUrl());
            user1.setName(user.getName());
            user1.setToken(user.getToken());
            User user2 = new User();
            user2.setGmtModified(System.currentTimeMillis());
            user2.setAvatarUrl(user.getAvatarUrl());
            user2.setName(user.getName());
            user2.setToken(user.getToken());
            UserExample u2= new UserExample();
            u2.createCriteria().andIdEqualTo(user1.getId());
            userMapper.updateByExampleSelective(user2,u2);
        }
    return user1;
    }
}
