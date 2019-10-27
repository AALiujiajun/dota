package com.wanguliuwang.dota.controller;

import com.wanguliuwang.dota.Mapper.UserMapper;
import com.wanguliuwang.dota.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

   @GetMapping("/")
    public String index(HttpServletRequest request){
       Cookie[] cookies = request.getCookies();
       for(Cookie cookie: cookies)
       {
           if(cookie.getName().equals("token"))
           {
               String token = cookie.getValue();
               User user=userMapper.findByToken(token);
               if(user !=null)
               {
                   request.getSession().setAttribute("user",user);
               }
               break;
           }
       }

       return "index";}
    @GetMapping("/index")
    public String index1(){return "index";}
}
