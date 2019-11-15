package com.wanguliuwang.dota.service;

import com.wanguliuwang.dota.Mapper.QuestionMapper;
import com.wanguliuwang.dota.Mapper.UserMapper;
import com.wanguliuwang.dota.dto.PaginationDTO;
import com.wanguliuwang.dota.dto.QuestionDTO;
import com.wanguliuwang.dota.model.Question;
import com.wanguliuwang.dota.model.QuestionExample;
import com.wanguliuwang.dota.model.User;
import org.apache.ibatis.session.RowBounds;
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
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
         User user= userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        Integer totalCount=(int)questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(totalCount,page,size);
       return  paginationDTO;
    }

    public  PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer offset = size * (page-1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            User user= userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        QuestionExample qexample = new QuestionExample();
        qexample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount=(int)questionMapper.countByExample(qexample);
        paginationDTO.setPagination(totalCount,page,size);
        return  paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=   questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user= userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO ;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            questionMapper.insert(question);
            question.setGmtCreate(System.currentTimeMillis());

        }
        else{
            question.setGmtModified(question.getGmtCreate());
            Question uq = new Question();
            uq.setGmtModified(System.currentTimeMillis());
            uq.setTitle(question.getTitle());
            uq.setDescription(question.getDescription());
            uq.setTag(question.getTag());

            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(uq, example);
        }
    }
}
