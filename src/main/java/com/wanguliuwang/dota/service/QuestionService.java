package com.wanguliuwang.dota.service;

import com.wanguliuwang.dota.Mapper.QuestionMapper;
import com.wanguliuwang.dota.Mapper.UserMapper;
import com.wanguliuwang.dota.dto.PaginationDTO;
import com.wanguliuwang.dota.dto.QuestionDTO;
import com.wanguliuwang.dota.model.Question;
import com.wanguliuwang.dota.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {
        Integer offset = size * (page-1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
         User user= userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        Integer totalCount=questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
       return  paginationDTO;
    }

    public  PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer offset = size * (page-1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            User user= userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        Integer totalCount=questionMapper.countByUserId(userId);
        paginationDTO.setPagination(totalCount,page,size);
        return  paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=   questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user= userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO ;
    }
}
