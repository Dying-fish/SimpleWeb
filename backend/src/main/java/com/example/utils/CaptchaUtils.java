package com.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class CaptchaUtils {
    private static JavaMailSender mailSender;
    private static StringRedisTemplate stringRedisTemplate;
    private static String sender;

    private JavaMailSender configMailSender;
    private StringRedisTemplate configStringRedisTemplate;
    @Value("${spring.mail.username}")
    private String configSender;

    private static final char[] SYMBOLS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final Random RANDOM = new SecureRandom();
    private static final int captchaExpires = 3*60; //超时时间3min

    @Autowired
    public CaptchaUtils(JavaMailSender configMailSender, StringRedisTemplate configStringRedisTemplate) {
        this.configMailSender = configMailSender;
        this.configStringRedisTemplate = configStringRedisTemplate;
    }

    @PostConstruct
    public void init() {
        sender = configSender;
        mailSender = configMailSender;
        stringRedisTemplate = configStringRedisTemplate;
    }

    static Logger logger = LoggerFactory.getLogger(CaptchaUtils.class);

    //随机生成验证码
    public static String generateCaptcha() {
        char[] nonceChars = new char[6];
        for (int i = 0; i < nonceChars.length; i++) {
            nonceChars[i] = SYMBOLS[RANDOM.nextInt(SYMBOLS.length)];
        }
        return new String(nonceChars);
    }

    /*
        发送验证码
        0: 成功   -1: 发送失败  -2: 发送请求过快
    */
    public static int sendCaptcha(String mailAddress, String captcha) {
        if (captchaExpires - stringRedisTemplate.getExpire(mailAddress, TimeUnit.SECONDS) < 60) {
            return -2;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        // 发件人
        message.setFrom(sender);
        // 收件人
        message.setTo(mailAddress);
        // 邮件标题
        message.setSubject("管人痴注册验证码");
        // 邮件内容
        String content = "【管人痴】验证码: " + captcha + " ,你正在注册逆天MMR做的奇怪网站，请勿告诉他人。";
        message.setText(content);
        try {
            mailSender.send(message);
            /* Redis保存!!!!!!!!!!!!!!!!!!!!!!!!!! */
            stringRedisTemplate.opsForValue().set(mailAddress, captcha, captchaExpires, TimeUnit.SECONDS);
        } catch (MailException e){
            logger.info(e.toString());
            return -1;
        }
        logger.info("Already send code:" + captcha + " to " + mailAddress);
        return 0;
    }

    //验证功能
    public static boolean validateMailCode(String mail, String code) {
        /* Redis取出!!!!!!!!!!!!!!!!!!!!!!!!!! */
        String captcha = stringRedisTemplate.opsForValue().get(mail);
        if (code.equals(captcha)) {
            return true;
        }
        return false;
    }
}
