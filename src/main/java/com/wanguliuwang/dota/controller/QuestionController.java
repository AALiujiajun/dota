package com.wanguliuwang.dota.controller;

import com.wanguliuwang.dota.dto.CommentDTO;
import com.wanguliuwang.dota.dto.QuestionDTO;
import com.wanguliuwang.dota.enums.CommentTypeEnum;
import com.wanguliuwang.dota.service.CommentService;
import com.wanguliuwang.dota.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name ="id") Long id, Model model){
        QuestionDTO questionDTO =questionService.getById(id);
        List<CommentDTO> commentDTOList= commentService.listByTargetId(id, CommentTypeEnum.Question.getType());
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTOList);
     return  "question";
    }
}
