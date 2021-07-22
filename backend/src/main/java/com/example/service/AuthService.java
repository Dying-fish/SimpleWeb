package com.example.service;

import com.example.domain.Authority;
import com.example.domain.User;
import com.example.repository.AuthRepository;
import com.example.repository.UserRepository;
import com.example.utils.CaptchaUtils;
import com.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Service
public class AuthService {

    private UserRepository userRepository;
    private AuthRepository authRepository;

    @Autowired
    public AuthService(UserRepository userRepository, AuthRepository authRepository) {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    /*
        登录功能
        通知码 code
        0: 成功  -1: 用户名或密码错误
     */
    public HashMap login(String username, String pass) {
        HashMap resp = new HashMap();
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(pass)) {
            resp.put("code", -1);
        } else {
            resp.put("code", 0);
            resp.put("token", JwtUtils.generateToken(username, user.getAuth()));
        }
        return resp;
    }

    /*
        获取邮箱验证码
        通知码 code
        0: 成功  -1: 发送失败  -2: 发送请求过快  -3: 用户名已被注册  -4: 邮箱已被使用
    */
    public HashMap getCaptcha(String username, String email) {
        HashMap resp = new HashMap();
        ArrayList errorCode = new ArrayList();
        if (userRepository.findByUsername(username) != null){
            errorCode.add(-3);
        }
        if(userRepository.findByEmail(email) != null) {
            errorCode.add(-4);
        }
        if (errorCode.isEmpty()) {
            String captcha = CaptchaUtils.generateCaptcha();
            resp.put("code", CaptchaUtils.sendCaptcha(email, captcha));
        } else {
            resp.put("code", errorCode);
        }
        return resp;
    }

    /*
        注册功能
        通知码 code
        0: 成功  -1: 用户名或邮箱已被注册  -2: 验证码错误
    */
    public HashMap register(String username, String email, String pass, String code) {
        HashMap resp = new HashMap();
        if (CaptchaUtils.validateMailCode(email, code)) {
            Authority auth = authRepository.findByAuthority("User");
            User user = new User(username, email, pass, auth);
            if (userRepository.findByUsername(username) != null || userRepository.findByEmail(email) != null){
                resp.put("code", -1);
            } else {
                userRepository.save(user);
                resp.put("code", 0);
            }
        } else {
            resp.put("code", -2);
        }
        return resp;
    }
}
