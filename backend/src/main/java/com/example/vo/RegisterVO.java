package com.example.vo;

import javax.validation.constraints.NotBlank;

public class RegisterVO extends CaptchaVO {
    @NotBlank(message = "请输入验证码")
    
    private String code;

    public RegisterVO(String username, String email, String password, String code) {
        super(username, email, password);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
