package com.wanguliuwang.dota.controller;

import com.wanguliuwang.dota.Mapper.UserMapper;
import com.wanguliuwang.dota.dto.PaginationDTO;
import com.wanguliuwang.dota.model.User;
import com.wanguliuwang.dota.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile( HttpServletRequest request,@PathVariable(name = "action")String action, Model model, @RequestParam(name="page",defaultValue = "1") Integer page, @RequestParam(name="size",defaultValue = "5") Integer size){

       Integer userId= (Integer) request.getSession().getAttribute("userId");
  /*      Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user.getName());
                    }
                    break;
                }
            }
        }
        if(user==null)
        {
            return "redirect:index";
        }*/

        if("questions".equals(action))
        {
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        else if("repies".equals(action))
        {
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO list=questionService.list(userId,page,size);
        model.addAttribute("pagination",list);
        return "profile";
    }
}
