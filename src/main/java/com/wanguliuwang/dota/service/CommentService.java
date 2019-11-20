package com.wanguliuwang.dota.service;

import com.wanguliuwang.dota.Mapper.CommentMapper;
import com.wanguliuwang.dota.Mapper.QuestionExtMapper;
import com.wanguliuwang.dota.Mapper.QuestionMapper;
import com.wanguliuwang.dota.Mapper.UserMapper;
import com.wanguliuwang.dota.dto.CommentDTO;
import com.wanguliuwang.dota.enums.CommentTypeEnum;
import com.wanguliuwang.dota.exception.CustomerErrorCode;
import com.wanguliuwang.dota.exception.CustomerException;
import com.wanguliuwang.dota.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0)
        {
            throw new CustomerException(CustomerErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType() ==null || !CommentTypeEnum.isExist(comment.getType()))
        {
            throw new CustomerException(CustomerErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType()== CommentTypeEnum.COMMENT.getType())
        {
            Comment c = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(c ==null)

            {
                throw new CustomerException(CustomerErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

        }
        else
        {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null)
            {
                throw new CustomerException(CustomerErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }

    public List<CommentDTO> listByTargetId(Long id, Integer type) {
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type);
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);
        if(comments.size()==0)
        {
            return  new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> collect= comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds=new ArrayList<>();
        userIds.addAll(collect);

        //获取评论人并转换为MAP
       UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users1 = userMapper.selectByExample(userExample);
     Map<Long,User> userMap=  users1.stream().collect(Collectors.toMap(user-> user.getId(), user-> user));

     //转换COMMENT为commentDTO
        List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOs;
    }
}
