package com.wanguliuwang.dota.advice;

import com.alibaba.fastjson.JSON;
import com.wanguliuwang.dota.dto.ResultDTO;
import com.wanguliuwang.dota.exception.CustomerErrorCode;
import com.wanguliuwang.dota.exception.CustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handle (Throwable e, Model model,HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            if (e instanceof CustomerException) {
                resultDTO= ResultDTO.errorOf((CustomerException) e);
            } else {
                resultDTO= ResultDTO.errorOf(CustomerErrorCode.SYS_ERROR);
            }
            PrintWriter writer = null;
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
      return  null;
        } else {
            if (e instanceof CustomerException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomerErrorCode.SYS_ERROR.getMessage());
            }

            return new ModelAndView("error");
        }
    }

}
