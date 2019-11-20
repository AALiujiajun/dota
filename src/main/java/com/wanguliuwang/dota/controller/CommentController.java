package com.wanguliuwang.dota.controller;

import com.wanguliuwang.dota.Mapper.UserMapper;
import com.wanguliuwang.dota.dto.CommentCreateDTO;
import com.wanguliuwang.dota.dto.CommentDTO;
import com.wanguliuwang.dota.dto.ResultDTO;
import com.wanguliuwang.dota.enums.CommentTypeEnum;
import com.wanguliuwang.dota.exception.CustomerErrorCode;
import com.wanguliuwang.dota.model.Comment;
import com.wanguliuwang.dota.model.User;
import com.wanguliuwang.dota.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {



    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value="/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){




        User user = (User) request.getSession().getAttribute("user");
        if(user == null)
        {
            return ResultDTO.errorOf(CustomerErrorCode.NO_LOGIN);
        }
        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent()))
        {
            return ResultDTO.errorOf(CustomerErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value="/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name="id") Long id){
        List<CommentDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnum.COMMENT.getType());
        return ResultDTO.okOf(commentDTOList);
    }
}
