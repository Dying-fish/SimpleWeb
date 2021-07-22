package com.example.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CaptchaVO {
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^\\w{6,32}$", message = "用户名应由6-32位的英文字母，数字与下划线组成")
    private String username;
    @NotBlank(message = "邮箱不能为空")
    @Email
    private String email;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![-_]+$)[\\da-zA-Z_-]*$", message = "密码应至少包含英文字⺟，数字或-_中的两种")
    @Size(min = 6, max = 32, message = "密码应该是6-32位")
    private String password;

    public CaptchaVO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
