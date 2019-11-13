package com.wanguliuwang.dota.controller;

import com.wanguliuwang.dota.Mapper.QuestionMapper;
import com.wanguliuwang.dota.Mapper.UserMapper;
import com.wanguliuwang.dota.model.Question;
import com.wanguliuwang.dota.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;


    @GetMapping("/publish")
    public String getpublish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String postpublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request, Model model
    ) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title.equals("") || title.isEmpty()) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description.equals("") || description.isEmpty()) {
            model.addAttribute("error", "描述不能为空");
            return "publish";
        }
        if (tag.equals("") || tag.isEmpty()) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        Integer userId= (Integer) request.getSession().getAttribute("userId");
        question.setCreator(userId);
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        questionMapper.create(question);
        return "redirect:index";
    }

}
