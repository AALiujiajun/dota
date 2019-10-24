package com.wanguliuwang.dota.controller;

import com.wanguliuwang.dota.dto.AccessTokenDTO;
import com.wanguliuwang.dota.privider.GithubProvider;
import com.wanguliuwang.dota.privider.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code, @RequestParam(name="state") String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("599dc3e6a89c242d262c");
        accessTokenDTO.setClient_secret("81c82674f28fcccfe055c5ada189569c8bd44669");
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getgithubUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }

}
