package com.wanguliuwang.dota.service;

import com.wanguliuwang.dota.Mapper.UserMapper;
import com.wanguliuwang.dota.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User createOrUpdate(User user) {
        User user1=userMapper.findByAccountID(user.getAccount_id());

        if(user1==null)
        {
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        }
        else
        {

            user1.setGmt_modified(System.currentTimeMillis());
            user1.setAvatar_url(user.getAvatar_url());
            user1.setName(user.getName());
            user1.setToken(user.getToken());

            userMapper.update(user1);
        }
        return  user1;
    }
}
