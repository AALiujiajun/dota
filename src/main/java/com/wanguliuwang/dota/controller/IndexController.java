package com.wanguliuwang.dota.controller;

import com.wanguliuwang.dota.Mapper.QuestionMapper;
import com.wanguliuwang.dota.Mapper.UserMapper;
import com.wanguliuwang.dota.dto.PaginationDTO;
import com.wanguliuwang.dota.dto.QuestionDTO;
import com.wanguliuwang.dota.model.Question;
import com.wanguliuwang.dota.model.User;
import com.wanguliuwang.dota.model.UserExample;
import com.wanguliuwang.dota.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String index(HttpServletRequest request, Model model, @RequestParam(name="page",defaultValue = "1") Integer page,@RequestParam(name="size",defaultValue = "5") Integer size) {

       /* Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size()!=0) {
                        request.getSession().setAttribute("user", users.get(0));
                    }
                    break;
                }
            }
        }*/

        PaginationDTO list=questionService.list(page,size);
        model.addAttribute("pagination",list);
        return "index";
    }

    @GetMapping("/index")
    public String index1( Model model,@RequestParam(name="page",defaultValue = "1") Integer page,@RequestParam(name="size",defaultValue = "5") Integer size) {
        PaginationDTO list=questionService.list(page,size);
        model.addAttribute("pagination",list);
        return "index";
    }
}
