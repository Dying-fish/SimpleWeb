package com.example.controller;

import com.example.service.AuthService;
import com.example.vo.CaptchaVO;
import com.example.vo.LoginVO;
import com.example.vo.RegisterVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    // 登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap login(@Valid @RequestBody LoginVO userInfo) {
        return authService.login(userInfo.getUsername(), userInfo.getPassword());
    }

    // 获取验证码
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.POST)
    public HashMap getCaptcha(@Valid @RequestBody CaptchaVO userInfo) {
        return authService.getCaptcha(userInfo.getUsername(), userInfo.getEmail());
    }

    // 注册账号
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HashMap register(@Valid @RequestBody RegisterVO userInfo) {
        return authService.register(userInfo.getUsername(), userInfo.getEmail(), userInfo.getPassword(), userInfo.getCode());
    }

    @RequestMapping(value = "/test")
    public String register() {
        return "我们是冠军";
    }

}
