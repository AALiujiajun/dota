package com.wanguliuwang.dota.controller;

import com.wanguliuwang.dota.Mapper.UserMapper;
import com.wanguliuwang.dota.dto.AccessTokenDTO;
import com.wanguliuwang.dota.model.User;
import com.wanguliuwang.dota.privider.GithubProvider;
import com.wanguliuwang.dota.privider.GithubUser;
import com.wanguliuwang.dota.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientsecret;

    @Value("${github.redirect.uri}")
    private String redirecturi;

    @Autowired
    private UserService userService;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code, @RequestParam(name="state") String state, HttpServletResponse response, HttpServletRequest request){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientsecret);
        accessTokenDTO.setRedirect_uri(redirecturi);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getgithubUser(accessToken);
        String name = githubUser.getName();
        if(githubUser !=null && githubUser.getId()!=null)
        {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setAvatar_url(githubUser.getAvatar_url());
            user =  userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));
            request.getSession().setAttribute("user", user.getName());
            request.getSession().setAttribute("userId", user.getId());
                return "redirect:index";
        }
        else
        {
            return "redirect:index";
        }

    }

}
