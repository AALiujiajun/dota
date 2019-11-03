package com.wanguliuwang.dota.controller;

import com.wanguliuwang.dota.Mapper.QuestionMapper;
import com.wanguliuwang.dota.Mapper.UserMapper;
import com.wanguliuwang.dota.dto.QuestionDTO;
import com.wanguliuwang.dota.model.Question;
import com.wanguliuwang.dota.model.User;
import com.wanguliuwang.dota.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user.getName());
                    }
                    break;
                }
            }
        }
        List<QuestionDTO> list=questionService.list();
        model.addAttribute("questions",list);
        return "index";
    }

    @GetMapping("/index")
    public String index1(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user.getName());
                    }
                    break;
                }
            }
        }
        List<QuestionDTO> list=questionService.list();
        model.addAttribute("questions",list);
        return "index";
    }
}
