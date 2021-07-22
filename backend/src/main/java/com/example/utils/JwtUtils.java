package com.example.utils;

import com.example.config.JwtConfig;
import com.example.domain.User;
import com.example.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Component
public class JwtUtils {
    final private static HashMap<String, Object> header = new HashMap(){{this.put("type", "token"); this.put("alg", "HS512");}};
    private static String issuer;
    private static String secret;
    private static long timeLimit;

    private JwtService jwtService;
    private JwtConfig jwtConfig;

    @Autowired
    public JwtUtils(JwtConfig jwtConfig, JwtService jwtService) {
        this.jwtConfig = jwtConfig;
        this.jwtService = jwtService;
    }

    @PostConstruct
    public void init() {
        issuer = jwtConfig.getIssuer();
        secret = jwtConfig.getSecret();
        timeLimit = jwtConfig.getTimeLimit();
    }

    //根据用户生成令牌
    public static String generateToken(String username, String authority) {
        long currentTime = System.currentTimeMillis();
        return Jwts.builder().
                setHeader(header).
                setId(UUID.randomUUID().toString()).
                setIssuer(issuer).
                setSubject(authority).
                setAudience(username).
                setIssuedAt(new Date(currentTime)).
                setExpiration(new Date(currentTime + timeLimit)).
                signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    // 获取用户名
    public static String getUsername(String token) {
        return getAllClaims(token).getAudience();
    }

    // 获取身份
    public static String getAuth(String token) {
        return getAllClaims(token).getSubject();
    }

    // 身份验证
    public static boolean isAdmin(String token){
        return getAuth(token).equals("Admin") || getAuth(token).equals("Founder");
    }

    // 验证令牌有效性
    public User validateToken(String token) {
        final Claims claims = getAllClaims(token);
        final String username = claims.getAudience();
        final String auth = claims.getSubject();
        final Date expiration = claims.getExpiration();
        if (expiration.after(new Date())) {
            User user = (User) jwtService.loadUserByUsername(username);
            if (user != null && user.getAuth().equals(auth)) {
                return user;
            }
        }
        return null;
    }

    //从令牌获取全部Claim
    private static Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


}
